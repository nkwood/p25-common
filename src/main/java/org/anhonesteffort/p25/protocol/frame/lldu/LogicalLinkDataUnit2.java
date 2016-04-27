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

package org.anhonesteffort.p25.protocol.frame.lldu;

import org.anhonesteffort.p25.ecc.ReedSolomon_24_16_9;
import org.anhonesteffort.p25.protocol.Nid;

import java.nio.ByteBuffer;

public class LogicalLinkDataUnit2 extends LogicalLinkDataUnit {

  private final byte[]  messageIndicator;
  private final int     algorithmId;
  private final int     keyId;
  private final boolean intact;

  public LogicalLinkDataUnit2(Nid nid, ByteBuffer buffer) {
    super(nid, buffer);

    ReedSolomon_24_16_9 reedSolomon = new ReedSolomon_24_16_9();
    int                 rsResult    = reedSolomon.decode(rsHexbits24);

    messageIndicator = new byte[0]; // todo
    algorithmId      = (rsHexbits24[12] << 2) + (rsHexbits24[13] >> 4);
    keyId            = ((rsHexbits24[13] & 0x0F) << 12) + (rsHexbits24[14] << 6) + rsHexbits24[15];
    intact           = rsResult >= 0;
  }

  protected LogicalLinkDataUnit2(Nid          nid,
                                 ByteBuffer   buffer,
                                 VoiceFrame[] voiceFrames,
                                 byte[]       messageIndicator,
                                 int          algorithmId,
                                 int          keyId,
                                 boolean      intact)
  {
    super(nid, buffer, voiceFrames);

    this.messageIndicator = messageIndicator;
    this.algorithmId      = algorithmId;
    this.keyId            = keyId;
    this.intact           = intact;
  }

  @Override
  public boolean isIntact() {
    return intact;
  }

  public byte[] getMessageIndicator() {
    return messageIndicator;
  }

  public int getAlgorithmId() {
    return algorithmId;
  }

  public int getKeyId() {
    return keyId;
  }

  @Override
  public LogicalLinkDataUnit2 copy() {
    return new LogicalLinkDataUnit2(
        nid, copyBuffer(), voiceFrames, messageIndicator, algorithmId, keyId, intact
    );
  }

  @Override
  public String toString() {
    return super.toString() + ", alg: " + algorithmId + ", kid: " + keyId;
  }

}
