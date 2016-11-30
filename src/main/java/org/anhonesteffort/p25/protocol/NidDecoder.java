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

package org.anhonesteffort.p25.protocol;

import org.anhonesteffort.dsp.util.Util;
import org.anhonesteffort.p25.ecc.BchDecoder;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

public class NidDecoder {

  private final BchDecoder bchDecoder = new BchDecoder();

  public Nid decode(ByteBuffer eccBytes) {
    int[] bits64  = Util.toBinaryIntArray(eccBytes.array(), 0, 64);
    int[] rBits64 = new int[64];

    IntStream.range(0, 64).forEach(i -> rBits64[i] = bits64[63 - i]);
    boolean intact = bchDecoder.decode(rBits64) >= 0;
    IntStream.range(0, 64).forEach(i -> bits64[i] = rBits64[63 - i]);

    int  nac  = Util.binaryIntArrayToInt(bits64, 0, 12);
    Duid duid = new Duid(Util.binaryIntArrayToInt(bits64, 12, 4));

    return new Nid(nac, duid, intact);
  }

}
