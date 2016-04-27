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

package org.anhonesteffort.p25.protocol.frame.lldu;

import org.anhonesteffort.dsp.util.Util;
import org.anhonesteffort.p25.ecc.Hamming_10_6_3;
import org.anhonesteffort.p25.protocol.Nid;
import org.anhonesteffort.p25.protocol.frame.DataUnit;

import java.nio.ByteBuffer;

public abstract class LogicalLinkDataUnit extends DataUnit {

  protected final int[]           rsHexbits24;
  protected final VoiceCodeWord[] voiceCodeWords;

  public LogicalLinkDataUnit(Nid nid, ByteBuffer buffer) {
    super(nid, buffer);

    Hamming_10_6_3 hamming     = new Hamming_10_6_3();
    byte[]         bytes       = buffer.array();
    int            hexCount    = 0;
                   rsHexbits24 = new int[24];

    for (int i = 288; i < 1248; i += 184) {
      for (int j = 0; j < 40; j += 10) {
        int codeword10 = Util.bytesToInt(bytes, i + j, 10);
        int info6      = codeword10 >> 4;
        int parity4    = codeword10 & 0x0F;

        rsHexbits24[hexCount++] = hamming.decode(info6, parity4);
      }
    }

    voiceCodeWords = new VoiceCodeWordFactory().wordsFor(bytes);
    // todo: parse low speed data
  }

  protected LogicalLinkDataUnit(Nid nid, ByteBuffer buffer, VoiceCodeWord[] voiceCodeWords) {
    super(nid, buffer);
    this.rsHexbits24    = null;
    this.voiceCodeWords = voiceCodeWords;
  }

  public VoiceCodeWord[] getVoiceCodeWords() {
    return voiceCodeWords;
  }

}
