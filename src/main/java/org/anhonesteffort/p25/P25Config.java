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

package org.anhonesteffort.p25;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class P25Config {

  private static final Logger log = LoggerFactory.getLogger(P25Config.class);

  public static final Long   SAMPLE_RATE   = 48_000l;
  public static final Long   SYMBOL_RATE   =  4_800l;
  public static final Double CHANNEL_WIDTH = 12_500d;

  public static final long SYNC0DEG        = 0x5575F5FF77FFl;
  public static final long SYNC90DEG       = 0x001050551155l;
  public static final long SYNC180DEG      = 0xAA8A0A008800l;
  public static final long SYNC270DEG      = 0xFFEFAFAAEEAAl;
  public static final int  SYNC_BIT_LENGTH = 48;

  public static final int NID_LENGTH = 64;

  public static final int UNIT_ID_NONE = 0x000000;
  public static final int UNIT_ID_ALL  = 0xFFFFFF;

  private final Long    maxRateDiff;
  private final Long    passbandStop;
  private final Long    stopbandStart;
  private final Integer attenuation;

  public P25Config() {
    Properties properties = new Properties();

    try {

      properties.load(new FileInputStream("p25.properties"));

    } catch (IOException e) {
      properties = null;
      log.info("no p25.properties config file found, using defaults");
    }

    if (properties == null) {
      maxRateDiff   =  150l;
      passbandStop  = 6250l;
      stopbandStart = 7500l;
      attenuation   =   40;
    } else {
      maxRateDiff   = Long.parseLong(properties.getProperty("max_rate_diff"));
      passbandStop  = Long.parseLong(properties.getProperty("passband_stop"));
      stopbandStart = Long.parseLong(properties.getProperty("stopband_start"));
      attenuation   = Integer.parseInt(properties.getProperty("attenuation"));
    }
  }

  public Long getMaxRateDiff() {
    return maxRateDiff;
  }

  public Long getPassbandStop() {
    return passbandStop;
  }

  public Long getStopbandStart() {
    return stopbandStart;
  }

  public Integer getAttenuation() {
    return attenuation;
  }

}
