/**
 * The MIT License
 *
 * Copyright (C) 2022 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.silent.mouse.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SettingsModelBean
{
	public static final String X_AXIS = "xAxis";
	public static final String Y_AXIS = "yAxis";
	public static final String INTERVAL_OF_SECONDS = "intervalOfSeconds";
	public static final String INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS = "intervalOfMouseMovementsCheckInSeconds";
	public static final String MOVE_ON_STARTUP = "moveOnStartup";
	@Builder.Default
	Integer xAxis = 1;
	@Builder.Default
	Integer yAxis = 1;
	@Builder.Default
	Integer intervalOfSeconds = 10;
	@Builder.Default
	Integer intervalOfMouseMovementsCheckInSeconds = 5;
	@Builder.Default
	boolean moveOnStartup = true;
}
