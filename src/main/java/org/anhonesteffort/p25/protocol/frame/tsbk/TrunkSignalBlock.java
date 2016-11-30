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

import org.anhonesteffort.dsp.Copyable;

public class TrunkSignalBlock implements Copyable<TrunkSignalBlock> {

  public static final int GROUP_VOICE_CHAN_GRANT                 = 0x00;
  public static final int GROUP_VOICE_CHAN_GRANT_UPDATE_EXPLICIT = 0x03;
  public static final int UNIT_REGISTRATION_RESPONSE             = 0x2C;
  public static final int ID_UPDATE_VUHF                         = 0x34;
  public static final int SYSTEM_SERVICE_BROADCAST               = 0x38;
  public static final int SECONDARY_CONTROL_BROADCAST            = 0x39;
  public static final int RFSS_STATUS_BROADCAST                  = 0x3A;
  public static final int NETWORK_STATUS                         = 0x3B;
  public static final int ADJACENT_STATUS_BROADCAST              = 0x3C;
  public static final int ID_UPDATE_NO_VUHF                      = 0x3D;

  protected final int opCode;

  public TrunkSignalBlock(int opCode) {
    this.opCode = opCode;
  }

  public int getOpCode() {
    return opCode;
  }

  @Override
  public TrunkSignalBlock copy() {
    return this;
  }

  @Override
  public String toString() {
    return "opc: " + opCode;
  }

}
