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

public class GroupVoiceChannelGrant extends ChannelGrantBlock {

  private final int groupAddress;
  private final int sourceAddress;

  public GroupVoiceChannelGrant(int[] bytes12) {
    super(bytes12);

    groupAddress  = (bytes12[5] << 8)  + bytes12[6];
    sourceAddress = (bytes12[7] << 16) + (bytes12[8] << 8) + bytes12[9];
  }

  public int getGroupAddress() {
    return groupAddress;
  }

  public int getSourceAddress() {
    return sourceAddress;
  }

  @Override
  public double getDownlinkFreq(IdUpdateBlock idBlock) {
    return idBlock.getBaseFreq() + (channelNumber * idBlock.getChannelSpacing());
  }

}
