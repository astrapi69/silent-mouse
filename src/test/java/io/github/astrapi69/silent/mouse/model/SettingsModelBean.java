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

import java.util.prefs.Preferences;

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
	public static final String NOT_SET = "not set";
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

	public static SettingsModelBean setModelFromPreferences(final SettingsModelBean modelObject,
		final Preferences applicationPreferences)
	{
		String xAxisAsString = applicationPreferences.get(SettingsModelBean.X_AXIS, NOT_SET);
		if (NOT_SET.equals(xAxisAsString))
		{
			applicationPreferences.put(SettingsModelBean.X_AXIS, "1");
		}
		else
		{
			modelObject.setXAxis(Integer.valueOf(xAxisAsString));
		}

		String yAxisAsString = applicationPreferences.get(SettingsModelBean.Y_AXIS, NOT_SET);
		if (NOT_SET.equals(yAxisAsString))
		{
			applicationPreferences.put(SettingsModelBean.Y_AXIS, "1");
		}
		else
		{
			modelObject.setYAxis(Integer.valueOf(yAxisAsString));
		}

		String intervalOfSecondsAsString = applicationPreferences
			.get(SettingsModelBean.INTERVAL_OF_SECONDS, NOT_SET);
		if (NOT_SET.equals(intervalOfSecondsAsString))
		{
			applicationPreferences.put(SettingsModelBean.INTERVAL_OF_SECONDS, "180");
		}
		else
		{
			modelObject.setIntervalOfSeconds(Integer.valueOf(intervalOfSecondsAsString));
		}

		String intervalOfMouseMovementsCheckInSecondsAsString = applicationPreferences
			.get(SettingsModelBean.INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS, NOT_SET);
		if (NOT_SET.equals(intervalOfMouseMovementsCheckInSecondsAsString))
		{
			applicationPreferences
				.put(SettingsModelBean.INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS, "90");
		}
		else
		{
			modelObject.setIntervalOfMouseMovementsCheckInSeconds(
				Integer.valueOf(intervalOfMouseMovementsCheckInSecondsAsString));
		}

		String moveOnStartupAsString = applicationPreferences.get(SettingsModelBean.MOVE_ON_STARTUP,
			NOT_SET);
		if (NOT_SET.equals(moveOnStartupAsString))
		{
			applicationPreferences.put(SettingsModelBean.MOVE_ON_STARTUP, "false");
		}
		else
		{
			boolean moveOnStartup = Boolean.parseBoolean(moveOnStartupAsString);
			modelObject.setMoveOnStartup(moveOnStartup);
		}
		return modelObject;
	}
}
