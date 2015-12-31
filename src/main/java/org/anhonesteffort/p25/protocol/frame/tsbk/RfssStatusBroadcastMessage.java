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

public class RfssStatusBroadcastMessage extends TrunkingSignalingBlock implements DownlinkFreqProvider {

  private final int systemId;
  private final int rfSubSystemId;
  private final int siteId;
  private final int channelId;
  private final int channelNumber;

  public RfssStatusBroadcastMessage(int[]   bytes12,
                                    boolean isLast,
                                    boolean isEncrypted,
                                    int     opCode)
  {
    super(isLast, isEncrypted, opCode);

    systemId      = ((bytes12[3] & 0x0F) << 8) + bytes12[4];
    rfSubSystemId = bytes12[5];
    siteId        = bytes12[6];
    channelId     = (bytes12[7] & 0xF0) >> 4;
    channelNumber = ((bytes12[7] & 0x0F) << 8) + bytes12[8];
  }

  private RfssStatusBroadcastMessage(boolean isLast,
                                     boolean isEncrypted,
                                     int     opCode,
                                     int     systemId,
                                     int     rfSubSystemId,
                                     int     siteId,
                                     int     channelId,
                                     int     channelNumber)
  {
    super(isLast, isEncrypted, opCode);

    this.systemId      = systemId;
    this.rfSubSystemId = rfSubSystemId;
    this.siteId        = siteId;
    this.channelId     = channelId;
    this.channelNumber = channelNumber;
  }

  public int getSystemId() {
    return systemId;
  }

  public int getRfSubSystemId() {
    return rfSubSystemId;
  }

  public int getSiteId() {
    return siteId;
  }

  public int getChannelId() {
    return channelId;
  }

  public int getChannelNumber() {
    return channelNumber;
  }

  @Override
  public double getDownlinkFreq(IdUpdateBlock idBlock) {
    return idBlock.getBaseFreq() + (channelNumber * idBlock.getChannelSpacing());
  }

  @Override
  public RfssStatusBroadcastMessage copy() {
    return new RfssStatusBroadcastMessage(
        isLast, isEncrypted, opCode, systemId, rfSubSystemId, siteId, channelId, channelNumber
    );
  }

  @Override
  public String toString() {
    return "[last: "    + isLast        + ", " +
            "crypt: "   + isEncrypted   + ", " +
            "opc: "     + opCode        + ", " +
            "sysid: "   + systemId      + ", " +
            "rfsub: "   + rfSubSystemId + ", " +
            "siteid: "  + siteId        + "]";
  }

}