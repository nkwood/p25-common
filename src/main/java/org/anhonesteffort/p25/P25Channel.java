/*
 * Copyright (C) 2016 An Honest Effort LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.anhonesteffort.p25;

import org.anhonesteffort.dsp.Sink;
import org.anhonesteffort.dsp.Source;
import org.anhonesteffort.dsp.StatefulSink;
import org.anhonesteffort.dsp.filter.ComplexNumberFrequencyTranslatingFilter;
import org.anhonesteffort.dsp.filter.ComplexNumberMovingGainControl;
import org.anhonesteffort.dsp.filter.Filter;
import org.anhonesteffort.dsp.filter.FilterFactory;
import org.anhonesteffort.dsp.filter.NoOpComplexNumberFilter;
import org.anhonesteffort.dsp.filter.rate.RateChangeFilter;
import org.anhonesteffort.dsp.sample.Samples;
import org.anhonesteffort.dsp.util.ComplexNumber;
import org.anhonesteffort.p25.filter.decode.QpskPolarSlicer;
import org.anhonesteffort.p25.filter.demod.ComplexNumberCqpskDemodulator;
import org.anhonesteffort.p25.protocol.frame.DataUnit;
import org.anhonesteffort.p25.protocol.DataUnitFramer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
public class P25Channel extends Source<DataUnit, Sink<DataUnit>> implements StatefulSink<Samples> {

  private static final Logger log = LoggerFactory.getLogger(P25Channel.class);

  private final P25Config      config;
  private final P25ChannelSpec spec;

  private Filter<ComplexNumber> freqTranslation = new NoOpComplexNumberFilter();
  private DataUnitFramer        framer          = new DataUnitFramer(Optional.empty());

  private Long channelRate = -1l;

  public P25Channel(P25Config config, P25ChannelSpec spec) {
    super(true);
    this.config = config;
    this.spec   = spec;
  }

  public P25ChannelSpec getSpec() {
    return spec;
  }

  private Optional<RateChangeFilter<ComplexNumber>> initResampling(Long sourceRate) {
    Optional<RateChangeFilter<ComplexNumber>> resampling;

    if (Math.abs(sourceRate - P25Config.SAMPLE_RATE) > config.getMaxRateDiff()) {
      resampling  = Optional.of(FilterFactory.getCicResampler(sourceRate, P25Config.SAMPLE_RATE, config.getMaxRateDiff()));
      channelRate = (long) (sourceRate * resampling.get().getRateChange());
      log.info(spec + " interpolation: " + resampling.get().getInterpolation() + "," +
                      " decimation: "    + resampling.get().getDecimation());
    } else {
      resampling  = Optional.empty();
      channelRate = sourceRate;
    }

    log.info(spec + " source rate: " + sourceRate + ", channel rate: " + channelRate);
    return resampling;
  }

  private Filter<ComplexNumber> getFreqTranslation(Long sourceRate, Double sourceFreq) {
    if (sourceFreq >= 1d) {
      return new ComplexNumberFrequencyTranslatingFilter(sourceRate, sourceFreq, spec.getCenterFrequency());
    } else {
      return new NoOpComplexNumberFilter();
    }
  }

  @Override
  public void onStateChange(long sampleRate, double frequency) {
    Optional<RateChangeFilter<ComplexNumber>> resampling        = initResampling(sampleRate);
    Filter<ComplexNumber>                     baseband          = FilterFactory.getKaiserBessel(channelRate, config.getPassbandStop(), config.getStopbandStart(), config.getAttenuation(), 1f);
    Filter<ComplexNumber>                     gainControl       = new ComplexNumberMovingGainControl((int) (channelRate / P25Config.SYMBOL_RATE));
    ComplexNumberCqpskDemodulator             cqpskDemodulation = new ComplexNumberCqpskDemodulator(channelRate, P25Config.SYMBOL_RATE);
    QpskPolarSlicer                           slicer            = new QpskPolarSlicer();

    freqTranslation = getFreqTranslation(sampleRate, frequency);
    framer          = new DataUnitFramer(Optional.of(cqpskDemodulation));

    if (resampling.isPresent()) {
      freqTranslation.addSink(resampling.get());
      resampling.get().addSink(baseband);
    } else {
      freqTranslation.addSink(baseband);
    }

    baseband.addSink(gainControl);
    gainControl.addSink(cqpskDemodulation);
    cqpskDemodulation.addSink(slicer);
    slicer.addSink(framer);

    sinks.forEach(framer::addSink);
  }

  @Override
  public boolean addSink(Sink<DataUnit> sink) {
    return super.addSink(sink) && framer.addSink(sink);
  }

  @Override
  public boolean removeSink(Sink<DataUnit> sink) {
    return super.removeSink(sink) && framer.removeSink(sink);
  }

  @Override
  public void consume(Samples samples) {
    for (ComplexNumber sample : samples.getSamples()) {
      freqTranslation.consume(sample);
    }
  }

}
