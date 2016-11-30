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

import org.anhonesteffort.dsp.Sink;
import org.anhonesteffort.p25.protocol.frame.tsbk.IdUpdateBlock;
import org.anhonesteffort.p25.protocol.frame.tsbk.TrunkSignalBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChannelIdUpdateBlockMap implements Sink<TrunkSignalBlock> {

  private final Map<Integer, IdUpdateBlock> idMap = new HashMap<>();

  public Optional<IdUpdateBlock> getBlockForId(int channelId) {
    return Optional.ofNullable(idMap.get(channelId));
  }

  @Override
  public void consume(TrunkSignalBlock element) {
    switch (element.getOpCode()) {
      case TrunkSignalBlock.ID_UPDATE_VUHF:
      case TrunkSignalBlock.ID_UPDATE_NO_VUHF:
        IdUpdateBlock idUpdateBlock = (IdUpdateBlock) element;
        idMap.put(idUpdateBlock.getId(), idUpdateBlock);
        break;
    }
  }

}
