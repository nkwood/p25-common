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
 *
 * interleave schedule sourced from p25p1_const.h - Copyright (C) 2010 DSD Author
 */

package org.anhonesteffort.p25.protocol.frame.lldu;

import org.anhonesteffort.dsp.util.Util;

public class VoiceFrameFactory {

  private static final int[] iW = new int[] {
      0, 2, 4, 1, 3, 5,
      0, 2, 4, 1, 3, 6,
      0, 2, 4, 1, 3, 6,
      0, 2, 4, 1, 3, 6,
      0, 2, 4, 1, 3, 6,
      0, 2, 4, 1, 3, 6,
      0, 2, 5, 1, 3, 6,
      0, 2, 5, 1, 3, 6,
      0, 2, 5, 1, 3, 7,
      0, 2, 5, 1, 3, 7,
      0, 2, 5, 1, 4, 7,
      0, 3, 5, 2, 4, 7
  };

  private static final int[] iX = new int[] {
      22, 20, 10, 20, 18,  0,
      20, 18,  8, 18, 16, 13,
      18, 16,  6, 16, 14, 11,
      16, 14,  4, 14, 12,  9,
      14, 12,  2, 12, 10,  7,
      12, 10,  0, 10,  8,  5,
      10,  8, 13,  8,  6,  3,
       8,  6, 11,  6,  4,  1,
       6,  4,  9,  4,  2,  6,
       4,  2,  7,  2,  0,  4,
       2,  0,  5,  0, 13,  2,
       0, 21,  3, 21, 11,  0
  };

  private static final int[] iY = new int[] {
      1, 3, 5, 0, 2, 4,
      1, 3, 6, 0, 2, 4,
      1, 3, 6, 0, 2, 4,
      1, 3, 6, 0, 2, 4,
      1, 3, 6, 0, 2, 4,
      1, 3, 6, 0, 2, 5,
      1, 3, 6, 0, 2, 5,
      1, 3, 6, 0, 2, 5,
      1, 3, 6, 0, 2, 5,
      1, 3, 7, 0, 2, 5,
      1, 4, 7, 0, 3, 5,
      2, 4, 7, 1, 3, 5
  };

  private static final int[] iZ = new int[] {
      21, 19,  1, 21, 19,  9,
      19, 17, 14, 19, 17,  7,
      17, 15, 12, 17, 15,  5,
      15, 13, 10, 15, 13,  3,
      13, 11,  8, 13, 11,  1,
      11,  9,  6, 11,  9, 14,
       9,  7,  4,  9,  7, 12,
       7,  5,  2,  7,  5, 10,
       5,  3,  0,  5,  3,  8,
       3,  1,  5,  3,  1,  6,
       1, 14,  3,  1, 22,  4,
      22, 12,  1, 22, 20,  2
  };

  public VoiceFrame create(VoiceCodeWord codeWord) {
    byte[]   cwBytes    = codeWord.getBytes();
    byte[][] frameBytes = new byte[8][23];

    for (int diBit = 0; diBit < 72; diBit++) {
      frameBytes[iW[diBit]][iX[diBit]] = (byte) Util.bytesToInt(cwBytes, (diBit * 2) + 1, 1);
      frameBytes[iY[diBit]][iZ[diBit]] = (byte) Util.bytesToInt(cwBytes, (diBit * 2), 1);
    }

    return new VoiceFrame(frameBytes);
  }

}
