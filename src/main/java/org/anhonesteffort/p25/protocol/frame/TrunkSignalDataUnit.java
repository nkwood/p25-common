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

package org.anhonesteffort.p25.protocol.frame;

import org.anhonesteffort.p25.protocol.Nid;
import org.anhonesteffort.p25.protocol.frame.tsbk.TrunkSignalBlock;
import org.anhonesteffort.p25.protocol.frame.tsbk.TrunkSignalBlockFactory;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

public class TrunkSignalDataUnit extends DataUnit {

  private final List<TrunkSignalBlock> blocks;
  private final boolean                intact;

  public TrunkSignalDataUnit(Nid nid, ByteBuffer buffer) {
    super(nid, buffer);

    blocks = new TrunkSignalBlockFactory().getBlocksFor(buffer.array());
    intact = blocks.size() > 0;
  }

  protected TrunkSignalDataUnit(Nid                    nid,
                                ByteBuffer             buffer,
                                List<TrunkSignalBlock> blocks,
                                boolean                intact)
  {
    super(nid, buffer);

    this.blocks = blocks;
    this.intact = intact;
  }

  @Override
  public boolean isIntact() {
    return intact;
  }

  public List<TrunkSignalBlock> getBlocks() {
    return blocks;
  }

  public Optional<TrunkSignalBlock> getFirstOf(int opCode) {
    return blocks.stream()
                 .filter(block -> block.getOpCode() == opCode)
                 .findFirst();
  }

  @Override
  public TrunkSignalDataUnit copy() {
    return new TrunkSignalDataUnit(nid, copyBuffer(), blocks, intact);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append(super.toString());
    builder.append(", blocks: [");

    blocks.forEach(block -> {
      builder.append(block);
      builder.append(", ");
    });
    builder.append("]");

    return builder.toString();
  }

}
