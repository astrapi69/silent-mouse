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
package io.github.astrapi69.silent.mouse.frame;

import java.awt.Point;
import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.prefs.Preferences;

import io.github.astrapi69.model.BaseModel;
import io.github.astrapi69.silent.mouse.extension.SettingsExtensions;
import io.github.astrapi69.silent.mouse.i18n.Messages;
import io.github.astrapi69.silent.mouse.model.ApplicationModelBean;
import io.github.astrapi69.silent.mouse.model.SettingsModelBean;
import io.github.astrapi69.silent.mouse.panel.ApplicationPanel;
import io.github.astrapi69.silent.mouse.panel.MouseMoveSettingsPanel;
import io.github.astrapi69.silent.mouse.robot.MouseMovementManager;
import io.github.astrapi69.silent.mouse.system.tray.DefaultSystemTrayHandler;
import io.github.astrapi69.silent.mouse.system.tray.JavaSystemTrayHandler;
import io.github.astrapi69.silent.mouse.system.tray.SystemTrayHandler;
import io.github.astrapi69.silent.mouse.system.tray.SystemTrayType;
import io.github.astrapi69.swing.base.ApplicationPanelFrame;
import io.github.astrapi69.swing.base.BasePanel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

/**
 * The class {@link SystemTrayApplicationFrame} represents the main frame of the application that
 * sets up and initializes the application window with specific settings and components
 */
@Log
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SystemTrayApplicationFrame extends ApplicationPanelFrame<ApplicationModelBean>
{

	/**
	 * The single instance of {@link SystemTrayApplicationFrame}
	 */
	@Getter
	private static SystemTrayApplicationFrame instance;

	/** The manager class for handle the system tray */
	SystemTrayHandler systemTrayHandler;

	/** The flag if the system tray from dorkbox should be used */
	boolean dorkbox;

	/** The main application panel */
	ApplicationPanel applicationPanel;

	/** Map to track the mouse's positions over time */
	NavigableMap<LocalDateTime, Point> mouseTracks;

	/** Preferences object to store and retrieve user settings */
	Preferences applicationPreferences;

	/** Model bean containing the settings for mouse movements */
	SettingsModelBean settingsModelBean;

	/** Panel for configuring the mouse movement settings */
	MouseMoveSettingsPanel mouseMoveSettingsPanel;

	/** The manager class for handle the mouse movement */
	MouseMovementManager mouseMovementManager;

	/**
	 * Constructs a new {@link SystemTrayApplicationFrame} with the specified title from the
	 * resource bundle
	 */
	public SystemTrayApplicationFrame()
	{
		super(Messages.getString("mainframe.title"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBeforeInitialize()
	{
		if (instance == null)
		{
			instance = this;
		}
		// initialize model and model object
		mouseTracks = new TreeMap<>();
		applicationPreferences = Preferences.userRoot()
			.node(SystemTrayApplicationFrame.class.getName());

		settingsModelBean = SettingsExtensions.setModelFromPreferences(SettingsModelBean.builder()
			.intervalOfSeconds(180).intervalOfMouseMovementsCheckInSeconds(90).xAxis(1).yAxis(1)
			.moveOnStartup(false).build(), applicationPreferences);
		ApplicationModelBean applicationModelBean = ApplicationModelBean.builder()
			.settingsModelBean(settingsModelBean).title(Messages.getString("mainframe.title"))
			.build();

		mouseMovementManager = new MouseMovementManager(settingsModelBean);
		if (getSettingsModelBean().getSystemTrayType().equals(SystemTrayType.DORKBOX))
		{
			systemTrayHandler = new DefaultSystemTrayHandler(mouseMovementManager);
		}
		else
		{
			systemTrayHandler = new JavaSystemTrayHandler(mouseMovementManager);
		}

		setModel(BaseModel.of(applicationModelBean));
		super.onBeforeInitialize();
	}

	@Override
	protected void onInitializeComponents()
	{
		super.onInitializeComponents();
		mouseMoveSettingsPanel = new MouseMoveSettingsPanel(BaseModel.of(settingsModelBean));
		systemTrayHandler.initialize(this, settingsModelBean);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onAfterInitialize()
	{
		super.onAfterInitialize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String newIconPath()
	{
		return Messages.getString("global.icon.app.path");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BasePanel<ApplicationModelBean> newMainComponent()
	{
		applicationPanel = newApplicationPanel();
		return applicationPanel;
	}

	/**
	 * Factory method for create a new {@link ApplicationPanel} object
	 *
	 * @return the new {@link ApplicationPanel} object
	 */
	protected ApplicationPanel newApplicationPanel()
	{
		return new ApplicationPanel(getModel());
	}

}
