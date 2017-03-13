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

package org.anhonesteffort.p25.filter.gate;

import org.anhonesteffort.dsp.Sink;
import org.anhonesteffort.dsp.Source;
import org.anhonesteffort.dsp.util.Copyable;

public abstract class SyncGate<T extends Copyable<T>> extends Source<T, SyncGateSink<T>> implements Sink<T> {

  protected void onSyncConsumed() {
    sinks.forEach(SyncGateSink::onSyncConsumed);
  }

}
