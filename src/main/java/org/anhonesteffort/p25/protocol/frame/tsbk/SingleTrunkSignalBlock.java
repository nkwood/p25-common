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

public class SingleTrunkSignalBlock extends TrunkSignalBlock {

  protected final boolean isLast;
  protected final boolean isEncrypted;
  protected final int     manufacturerId;

  public SingleTrunkSignalBlock(int[] bytes12) {
    super(bytes12[0] & 0x3F);

    isLast         = (bytes12[0] & 0x80) == 0x80;
    isEncrypted    = (bytes12[0] & 0x40) == 0x40;
    manufacturerId = bytes12[1];
  }

  public boolean isLast() {
    return isLast;
  }

  public boolean isEncrypted() {
    return isEncrypted;
  }

  public int getManufacturerId() {
    return manufacturerId;
  }

  @Override
  public String toString() {
    return super.toString()     + ", " +
        "last: "  + isLast      + ", " +
        "crypt: " + isEncrypted + ", " +
        "make: "  + manufacturerId;
  }

}
