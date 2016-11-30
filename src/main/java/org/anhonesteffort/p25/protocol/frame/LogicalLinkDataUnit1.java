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

package org.anhonesteffort.p25.protocol.frame;

import org.anhonesteffort.p25.ecc.ReedSolomon_24_12_13;
import org.anhonesteffort.p25.protocol.Nid;
import org.anhonesteffort.p25.protocol.frame.linkcontrol.LinkControlWord;
import org.anhonesteffort.p25.protocol.frame.linkcontrol.LinkControlWordFactory;

import java.nio.ByteBuffer;

public class LogicalLinkDataUnit1 extends LogicalLinkDataUnit {

  private final LinkControlWord linkControlWord;
  private final boolean         intact;

  public LogicalLinkDataUnit1(Nid nid, ByteBuffer buffer) {
    super(nid, buffer);

    ReedSolomon_24_12_13 reedSolomon = new ReedSolomon_24_12_13();
    int                  rsResult    = reedSolomon.decode(rsHexbits24);

    linkControlWord = new LinkControlWordFactory().getLinkControlFor(rsHexbits24);
    intact          = rsResult >= 0;
  }

  protected LogicalLinkDataUnit1(Nid             nid,
                                 ByteBuffer      buffer,
                                 VoiceFrame[]    voiceFrames,
                                 LinkControlWord linkControlWord,
                                 boolean         intact)
  {
    super(nid, buffer, voiceFrames);

    this.linkControlWord = linkControlWord;
    this.intact          = intact;
  }

  @Override
  public boolean isIntact() {
    return intact;
  }

  public LinkControlWord getLinkControlWord() {
    return linkControlWord;
  }

  @Override
  public LogicalLinkDataUnit1 copy() {
    return new LogicalLinkDataUnit1(
        nid, copyBuffer(), voiceFrames, linkControlWord, intact
    );
  }

  @Override
  public String toString() {
    return super.toString() + ", link: "   + linkControlWord.toString();
  }

}
