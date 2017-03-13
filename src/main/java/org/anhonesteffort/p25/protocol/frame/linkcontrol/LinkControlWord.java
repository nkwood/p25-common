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

package org.anhonesteffort.p25.protocol.frame.linkcontrol;

import org.anhonesteffort.dsp.util.Copyable;

public class LinkControlWord implements Copyable<LinkControlWord> {

  public static final int LCF_GROUP = 0X00;
  public static final int LCF_UNIT  = 0x03;

  protected final boolean protectedFlag;
  protected final boolean implicitMfid;
  protected final int     linkControlOpcode;

  public LinkControlWord(boolean protectedFlag,
                         boolean implicitMfid,
                         int     linkControlOpcode)
  {
    this.protectedFlag     = protectedFlag;
    this.implicitMfid      = implicitMfid;
    this.linkControlOpcode = linkControlOpcode;
  }

  public boolean isProtectedFlag() {
    return protectedFlag;
  }

  public boolean isImplicitMfid() {
    return implicitMfid;
  }

  public int getLinkControlOpcode() {
    return linkControlOpcode;
  }

  @Override
  public LinkControlWord copy() {
    return this;
  }

  @Override
  public String toString() {
    return "[p: "  + protectedFlag     + ", " +
           "sf: "  + implicitMfid      + ", " +
           "lco: " + linkControlOpcode + "]";
  }

}
