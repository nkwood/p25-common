/*
 * Copyright (C) 2015 An Honest Effort LLC, coping.
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

package org.anhonesteffort.p25.audio;

import org.anhonesteffort.dsp.Sink;
import org.anhonesteffort.jmbe.iface.AudioConverter;
import org.anhonesteffort.p25.protocol.frame.DataUnit;
import org.anhonesteffort.p25.protocol.Duid;
import org.anhonesteffort.p25.protocol.frame.LogicalLinkDataUnit;
import org.anhonesteffort.p25.protocol.frame.VoiceFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.Optional;

public class ImbeAudioOutput implements Sink<DataUnit> {

  private final static Logger log = LoggerFactory.getLogger(ImbeAudioOutput.class);

  private final AudioConverter audioConverter;
  private final SourceDataLine output;

  public ImbeAudioOutput() throws ClassNotFoundException, LineUnavailableException {
    ImbeConverterFactory     converterFactory = new ImbeConverterFactory();
    Optional<AudioConverter> converter        = converterFactory.create(ImbeConverterFactory.AUDIO_FORMAT_48khz);

    if (!converter.isPresent()) {
      throw new ClassNotFoundException("unable to instantiate jmbe audio converter");
    } else {
      audioConverter = converter.get();
    }

    output = AudioSystem.getSourceDataLine(ImbeConverterFactory.AUDIO_FORMAT_48khz);
    output.open(
        ImbeConverterFactory.AUDIO_FORMAT_48khz,
        (ImbeConverterFactory.SAMPLE_RATE_48khz * ImbeConverterFactory.FRAME_BYTE_LENGTH)
    );
    output.start();
  }

  @Override
  public void consume(DataUnit element) {
    switch (element.getNid().getDuid().getId()) {
      case Duid.ID_LLDU1:
      case Duid.ID_LLDU2:
        LogicalLinkDataUnit lldu = (LogicalLinkDataUnit) element;
        if (!lldu.isIntact()) {
          log.warn("skipping audio playback, frame is corrupted");
          return;
        }

        for (VoiceFrame voiceFrame : lldu.getVoiceFrames()) {
          byte[] audio = audioConverter.convert(voiceFrame.getBytes());
          output.write(audio, 0, audio.length);
        }
    }
  }

  public void stop() {
    output.drain();
    output.stop();
    output.close();
  }

}
