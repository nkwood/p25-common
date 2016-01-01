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

package org.anhonesteffort.p25.model;

import org.junit.Test;

import static org.anhonesteffort.p25.util.TestingYamlUtil.asYaml;
import static org.anhonesteffort.p25.util.TestingYamlUtil.fromYaml;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TalkGroupTest {

  public static TalkGroup buildTalkGroup() {
    return new TalkGroup("tg00", 0);
  }

  @Test
  public void testSerializeToYml() throws Exception {
    assertThat("TalkGroup can be serialized to YAML",
        asYaml(buildTalkGroup()),
        is(equalTo(asYaml("talk_group.yml", TalkGroup.class))));
  }

  @Test
  public void testDeserializeFromYml() throws Exception {
    assertThat("TalkGroup can be deserialized from YAML",
        fromYaml("talk_group.yml", TalkGroup.class),
        is(buildTalkGroup()));
  }

}