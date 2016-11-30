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

public class Nid {

  private final int     nac;
  private final Duid    duid;
  private final boolean intact;

  public Nid(int nac, Duid duid, boolean intact) {
    this.nac    = nac;
    this.duid   = duid;
    this.intact = intact;
  }

  public int getNac() {
    return nac;
  }

  public Duid getDuid() {
    return duid;
  }

  public boolean isIntact() {
    return intact;
  }

  @Override
  public String toString() {
    return "[duid: " + duid.toString() + ", nac: " + nac + "]";
  }

}
