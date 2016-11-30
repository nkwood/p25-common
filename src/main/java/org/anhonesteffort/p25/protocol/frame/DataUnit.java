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

package org.anhonesteffort.p25.protocol.frame;

import org.anhonesteffort.dsp.Copyable;
import org.anhonesteffort.p25.protocol.Nid;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class DataUnit implements Copyable<DataUnit> {

  protected final Nid        nid;
  protected final ByteBuffer buffer;

  public DataUnit(Nid nid, ByteBuffer buffer) {
    this.nid    = nid;
    this.buffer = buffer;
  }

  public Nid getNid() {
    return nid;
  }

  public ByteBuffer getBuffer() {
    return buffer;
  }

  public boolean isIntact() {
    return false;
  }

  protected ByteBuffer copyBuffer() {
    byte[] bytes = buffer.array();
    return ByteBuffer.wrap(Arrays.copyOf(bytes, bytes.length));
  }

  @Override
  public DataUnit copy() {
    return new DataUnit(nid, copyBuffer());
  }

  @Override
  public String toString() {
    return "nid: " + nid.toString() + ", intact: " + isIntact();
  }

}
