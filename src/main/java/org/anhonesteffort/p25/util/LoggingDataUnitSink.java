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

package org.anhonesteffort.p25.util;

import org.anhonesteffort.dsp.ChannelSpec;
import org.anhonesteffort.dsp.Sink;
import org.anhonesteffort.p25.protocol.frame.DataUnit;
import org.anhonesteffort.p25.protocol.frame.Duid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingDataUnitSink implements Sink<DataUnit> {

  private final static Logger log = LoggerFactory.getLogger(LoggingDataUnitSink.class);

  private final ChannelSpec channelSpec;

  public LoggingDataUnitSink(ChannelSpec channelSpec) {
    this.channelSpec = channelSpec;
  }

  @Override
  public void consume(DataUnit element) {
    switch (element.getNid().getDuid().getId()) {
      case Duid.ID_HEADER:
        log.debug(channelSpec + " decoded hdu: " + element);
        break;

      case Duid.ID_LLDU1:
        log.debug(channelSpec + " decoded lldu1: " + element);
        break;

      case Duid.ID_TRUNK_SIGNALING:
        log.debug(channelSpec + " decoded tsdu: " + element);
        break;

      case Duid.ID_LLDU2:
        log.debug("decoded lldu2: " + element);
        break;

      case Duid.ID_TERMINATOR_W_LINK:
        log.debug(channelSpec + " decoded terminator w/ link control");
        break;

      case Duid.ID_TERMINATOR_WO_LINK:
        log.debug(channelSpec + " decoded terminator w/o link control");
        break;

      case Duid.ID_PACKET:
        log.debug(channelSpec + " decoded a packet 0.o");
        break;
    }
  }

}
