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

package org.anhonesteffort.p25.protocol.frame.tsbk;

public class LraSingleTrunkBlock extends SingleTrunkSignalBlock {

  protected final int lra;

  public LraSingleTrunkBlock(int[] bytes12) {
    super(bytes12);
    lra = bytes12[2];
  }

  public int getLra() {
    return lra;
  }

  @Override
  public String toString() {
    return super.toString() + ", lra: " + lra;
  }

}
