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

public class GroupVoiceChannelGrantUpdateExplicit
    extends SingleTrunkSignalBlock implements DownlinkFreqProvider
{

  private final int transmitId;
  private final int transmitNumber;
  private final int receiveId;
  private final int receiveNumber;
  private final int groupId;
  private final int sourceId;

  public GroupVoiceChannelGrantUpdateExplicit(int[] bytes12) {
    super(bytes12);

    transmitId     = (bytes12[4] & 0xF0) >> 4;
    transmitNumber = ((bytes12[4] & 0x0F) << 8) + bytes12[5];
    receiveId      = (bytes12[6] & 0xF0) >> 4;
    receiveNumber  = ((bytes12[6] & 0x0F) << 8) + bytes12[7];
    groupId        = (bytes12[8] << 8)  + bytes12[9];
    sourceId       = 0xFFFF;
  }

  public int getTransmitId() {
    return transmitId;
  }

  public int getTransmitNumber() {
    return transmitNumber;
  }

  public int getReceiveId() {
    return receiveId;
  }

  public int getReceiveNumber() {
    return receiveNumber;
  }

  public int getGroupId() {
    return groupId;
  }

  public int getSourceId() {
    return sourceId;
  }

  @Override
  public double getDownlinkFreq(IdUpdateBlock idBlock) {
    return idBlock.getBaseFreq() + (transmitNumber * idBlock.getChannelSpacing());
  }

  @Override
  public String toString() {
    return super.toString()       + ", " +
        "txId: " + transmitId     + ", " +
        "txN: "  + transmitNumber + ", " +
        "rxId: " + receiveId      + ", " +
        "rxN: "  + receiveNumber  + ", " +
        "gId: "  + groupId        + ", " +
        "sId: "  + sourceId;
  }

}
