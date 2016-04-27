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

package org.anhonesteffort.p25.protocol.frame.lldu;

import org.anhonesteffort.dsp.util.Util;

import java.util.BitSet;
import java.util.stream.IntStream;

public class VoiceCodeWordFactory {

  private static final int   VOICE_CW_COUNT   =   9;
  private static final int   VOICE_CW_LENGTH  = 144;
  private static final int[] VOICE_CW_INDEXES = new int[] {
      0, 144, 328, 512, 696, 880, 1064, 1248, 1424
  };

  private BitSet bytesToBitSet(byte[] bytes) {
    BitSet bitSet = new BitSet(bytes.length * 8);

    IntStream.range(0, bytes.length * 8)
             .filter(bit -> Util.bytesToInt(bytes, bit, 1) == 1)
             .forEach(bitSet::set);

    return bitSet;
  }

  public VoiceCodeWord[] wordsFor(byte[] llduBytes) {
    BitSet          llduBits  = bytesToBitSet(llduBytes);
    VoiceCodeWord[] words     = new VoiceCodeWord[VOICE_CW_COUNT];
    int             wordCount = 0;

    for (int bitIndex : VOICE_CW_INDEXES) {
      words[wordCount++] = new VoiceCodeWord(
          llduBits.get(bitIndex, bitIndex + VOICE_CW_LENGTH).toByteArray()
      );
    }

    return words;
  }

}
