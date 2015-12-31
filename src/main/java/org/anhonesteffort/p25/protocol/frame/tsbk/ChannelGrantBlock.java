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

public abstract class ChannelGrantBlock
    extends SingleTrunkSignalBlock implements DownlinkFreqProvider {

  protected final int channelId;
  protected final int channelNumber;

  public ChannelGrantBlock(int bytes12[]) {
    super(bytes12);

    channelId     = (bytes12[3] & 0xF0) >> 4;
    channelNumber = ((bytes12[3] & 0x0F) << 8) + bytes12[4];
  }

  public int getChannelId() {
    return channelId;
  }

  public int getChannelNumber() {
    return channelNumber;
  }

  @Override
  public String toString() {
    return super.toString() + ", " + "chnId: " + channelId + ", " + "chnN: " + channelNumber;
  }

}
