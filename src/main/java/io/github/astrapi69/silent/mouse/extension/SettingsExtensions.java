/**
 * The MIT License
 *
 * Copyright (C) 2022 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.silent.mouse.extension;

import java.util.prefs.Preferences;

import io.github.astrapi69.silent.mouse.model.SettingsModelBean;
import io.github.astrapi69.silent.mouse.system.tray.SystemTrayType;

/**
 * The class {@link SettingsExtensions}
 */
public class SettingsExtensions
{

	/**
	 * Constant for the "not set" option
	 */
	public static final String NOT_SET = "not set";

	/**
	 * Key for the X-axis setting in preferences
	 */
	public static final String X_AXIS = "xAxis";

	/**
	 * Key for the Y-axis setting in preferences
	 */
	public static final String Y_AXIS = "yAxis";

	/**
	 * Key for the interval of seconds setting in preferences
	 */
	public static final String INTERVAL_OF_SECONDS = "intervalOfSeconds";

	/**
	 * Key for the interval of mouse movements check in preferences
	 */
	public static final String INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS = "intervalOfMouseMovementsCheckInSeconds";

	/**
	 * Key for the move on startup setting in preferences
	 */
	public static final String MOVE_ON_STARTUP = "moveOnStartup";

	/**
	 * Key for the systemTrayType setting in preferences
	 */
	public static final String SYSTEM_TRAY_TYPE = "systemTrayType";

	/**
	 * The constant for the default settings of the application
	 */
	public static final SettingsModelBean DEFAULT_SETTINGS = SettingsModelBean.builder()
		.intervalOfSeconds(180).intervalOfMouseMovementsCheckInSeconds(90).xAxis(1).yAxis(1)
		.moveOnStartup(true).systemTrayType(SystemTrayType.DORKBOX).build();

	/**
	 * Sets model from preferences.
	 *
	 * @param applicationPreferences
	 *            the application preferences
	 * @return the model from preferences
	 */
	public static SettingsModelBean setModelFromPreferences(
		final Preferences applicationPreferences)
	{
		return setModelFromPreferences(DEFAULT_SETTINGS, applicationPreferences);
	}

	/**
	 * Sets model from preferences.
	 *
	 * @param modelObject
	 *            the model object
	 * @param applicationPreferences
	 *            the application preferences
	 * @return the model from preferences
	 */
	public static SettingsModelBean setModelFromPreferences(final SettingsModelBean modelObject,
		final Preferences applicationPreferences)
	{
		String xAxisAsString = applicationPreferences.get(SettingsExtensions.X_AXIS, NOT_SET);
		if (NOT_SET.equals(xAxisAsString))
		{
			applicationPreferences.put(SettingsExtensions.X_AXIS, "1");
		}
		else
		{
			modelObject.setXAxis(Integer.valueOf(xAxisAsString));
		}

		String yAxisAsString = applicationPreferences.get(SettingsExtensions.Y_AXIS, NOT_SET);
		if (NOT_SET.equals(yAxisAsString))
		{
			applicationPreferences.put(SettingsExtensions.Y_AXIS, "1");
		}
		else
		{
			modelObject.setYAxis(Integer.valueOf(yAxisAsString));
		}

		String intervalOfSecondsAsString = applicationPreferences
			.get(SettingsExtensions.INTERVAL_OF_SECONDS, NOT_SET);
		if (NOT_SET.equals(intervalOfSecondsAsString))
		{
			applicationPreferences.put(SettingsExtensions.INTERVAL_OF_SECONDS, "180");
		}
		else
		{
			modelObject.setIntervalOfSeconds(Integer.valueOf(intervalOfSecondsAsString));
		}

		String intervalOfMouseMovementsCheckInSecondsAsString = applicationPreferences
			.get(SettingsExtensions.INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS, NOT_SET);
		if (NOT_SET.equals(intervalOfMouseMovementsCheckInSecondsAsString))
		{
			applicationPreferences
				.put(SettingsExtensions.INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS, "90");
		}
		else
		{
			modelObject.setIntervalOfMouseMovementsCheckInSeconds(
				Integer.valueOf(intervalOfMouseMovementsCheckInSecondsAsString));
		}

		String moveOnStartupAsString = applicationPreferences
			.get(SettingsExtensions.MOVE_ON_STARTUP, NOT_SET);
		if (NOT_SET.equals(moveOnStartupAsString))
		{
			applicationPreferences.put(SettingsExtensions.MOVE_ON_STARTUP, "false");
		}
		else
		{
			boolean moveOnStartup = Boolean.parseBoolean(moveOnStartupAsString);
			modelObject.setMoveOnStartup(moveOnStartup);
		}
		String systemTrayTypeAsString = applicationPreferences
			.get(SettingsExtensions.SYSTEM_TRAY_TYPE, NOT_SET);
		if (NOT_SET.equals(systemTrayTypeAsString))
		{
			applicationPreferences.put(SettingsExtensions.SYSTEM_TRAY_TYPE,
				SystemTrayType.DORKBOX.name());
		}
		else
		{
			SystemTrayType systemTrayType = SystemTrayType.valueOf(systemTrayTypeAsString);
			modelObject.setSystemTrayType(systemTrayType);
		}
		return modelObject;
	}
}
