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

import org.anhonesteffort.jmbe.JMBEAudioLibrary;
import org.anhonesteffort.jmbe.iface.AudioConverter;

import javax.sound.sampled.AudioFormat;
import java.util.Optional;

public class ImbeConverterFactory {

  public static final String JMBE_CODEC = "IMBE";

  public static final int SAMPLE_RATE_8khz  = 8000;
  public static final int SAMPLE_RATE_48khz = 48000;
  public static final int SAMPLE_BIT_LENGTH = 16;
  public static final int FRAME_BYTE_LENGTH = SAMPLE_BIT_LENGTH / 8;
  public static final int CHANNEL_COUNT     = 1;

  public static final AudioFormat AUDIO_FORMAT_8khz = new AudioFormat(
      AudioFormat.Encoding.PCM_SIGNED, SAMPLE_RATE_8khz, SAMPLE_BIT_LENGTH,
      CHANNEL_COUNT, FRAME_BYTE_LENGTH, SAMPLE_RATE_8khz, false
  );

  public static final AudioFormat AUDIO_FORMAT_48khz = new AudioFormat(
      AudioFormat.Encoding.PCM_SIGNED, SAMPLE_RATE_48khz, SAMPLE_BIT_LENGTH,
      CHANNEL_COUNT, FRAME_BYTE_LENGTH, SAMPLE_RATE_48khz, false
  );

  public Optional<AudioConverter> create(AudioFormat format) {
    try {
      return Optional.ofNullable(new JMBEAudioLibrary().getAudioConverter(JMBE_CODEC, format));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

}
