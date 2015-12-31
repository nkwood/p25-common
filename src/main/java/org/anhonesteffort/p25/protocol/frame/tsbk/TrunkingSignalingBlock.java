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

import org.anhonesteffort.dsp.Copyable;

public class TrunkingSignalingBlock implements Copyable<TrunkingSignalingBlock> {

  public static final int GROUP_VOICE_CHAN_GRANT = 0x00;
  public static final int ID_UPDATE_VUHF         = 0x34;
  public static final int RFSS_STATUS_BROADCAST  = 0x3A;
  public static final int NETWORK_STATUS         = 0x3B;
  public static final int ID_UPDATE_NO_VUHF      = 0x3D;

  protected final boolean isLast;
  protected final boolean isEncrypted;
  protected final int     opCode;

  protected TrunkingSignalingBlock(boolean isLast, boolean isEncrypted, int opCode) {
    this.isLast      = isLast;
    this.isEncrypted = isEncrypted;
    this.opCode      = opCode;

    // todo: manufacturer id field
  }

  public boolean isLast() {
    return isLast;
  }

  public boolean isEncrypted() {
    return isEncrypted;
  }

  public int getOpCode() {
    return opCode;
  }

  @Override
  public TrunkingSignalingBlock copy() {
    return new TrunkingSignalingBlock(isLast, isEncrypted, opCode);
  }

  @Override
  public String toString() {
    return "[last: "  + isLast      + ", " +
            "crypt: " + isEncrypted + ", " +
            "opc: "   + opCode      + "]";
  }

}
