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

package org.anhonesteffort.p25.protocol.frame.tsbk;

public class IdUpdateNoVuhf extends IdUpdateBlock {

  private final int bandwidth;
  private final int transmitOffset;

  public IdUpdateNoVuhf(int[] bytes12) {
    super(bytes12);

    bandwidth      = ((bytes12[2] & 0x0F) << 5) + ((bytes12[3] & 0xF8) >> 3);
    transmitOffset = ((bytes12[3] & 0x07) << 6) + ((bytes12[4] & 0xFC) >> 2);
  }

  @Override
  public int getBandwidth() {
    return bandwidth;
  }

  @Override
  public long getTransmitOffset() {
    return transmitOffset;
  }

}
