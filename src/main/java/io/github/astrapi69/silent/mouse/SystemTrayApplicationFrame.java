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
package io.github.astrapi69.silent.mouse;

import java.awt.Image;
import java.awt.Point;
import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;
import io.github.astrapi69.model.BaseModel;
import io.github.astrapi69.silent.mouse.model.SettingsExtensions;
import io.github.astrapi69.swing.base.ApplicationPanelFrame;
import io.github.astrapi69.swing.base.BasePanel;
import io.github.astrapi69.swing.dialog.JOptionPaneExtensions;
import io.github.astrapi69.swing.panel.info.AppInfoPanel;
import io.github.astrapi69.swing.panel.info.InfoModelBean;
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
	private MouseMovementManager mouseMovementManager;

	/**
	 * Constructs a new {@link SystemTrayApplicationFrame} with the specified title from the
	 * resource bundle
	 */
	public SystemTrayApplicationFrame()
	{
		super(Messages.getString("mainframe.title"));
	}

	/**
	 * Initializes the system tray components and sets up menu items
	 */
	protected void initializeSystemTray()
	{
		SystemTray systemTray = SystemTray.get();
		if (systemTray == null)
		{
			throw new RuntimeException("Unable to load SystemTray!");
		}

		Image image = IconPreloader.getTrayImageIcon().getImage();
		systemTray.setImage(image);

		systemTray.setStatus("Initializing...");

		MenuItem startItem = new MenuItem("Start");
		MenuItem stopItem = new MenuItem("Stop");
		MenuItem exitItem = new MenuItem("Exit");
		MenuItem aboutItem = new MenuItem("About");
		MenuItem settingsItem = new MenuItem("Settings");

		exitItem.setCallback(e -> {
			systemTray.shutdown();
			System.exit(0);
		});
		if (settingsModelBean.isMoveOnStartup())
		{
			startMoving(stopItem, startItem);
			systemTray.setStatus("Moving around");
		}

		settingsItem.setCallback(e -> {
			int option = JOptionPaneExtensions.getInfoDialogWithOkCancelButton(
				mouseMoveSettingsPanel, "Settings", mouseMoveSettingsPanel.getCmbVariableX());
			if (option == JOptionPane.OK_OPTION)
			{
				final String text = mouseMoveSettingsPanel.getTxtIntervalOfSeconds().getText();
				if (text != null)
				{
					settingsModelBean.setIntervalOfSeconds(Integer.valueOf(text));
				}
				settingsModelBean = mouseMoveSettingsPanel.getModelObject();
			}
		});

		aboutItem.setCallback(e -> {
			InfoModelBean infoModelBean = InfoModelBean.builder().applicationName("silent mouse")
				.labelApplicationName("Application name:").labelCopyright("Copyright:")
				.copyright("Asterios Raptis") // !!!
				// IMPORTANT
				// Change
				// version
				// on
				// deployment
				// IMPORTANT
				// !!!
				.labelVersion("Version:").version("2.0.1") // !!!
				// IMPORTANT
				// Change
				// version
				// on
				// deployment
				// IMPORTANT
				// !!!
				.licence("This Software is licensed under the MIT Licence").build();
			AppInfoPanel appInfoPanel = new AppInfoPanel(BaseModel.of(infoModelBean));
			JOptionPaneExtensions.getInfoDialogWithOkCancelButton(appInfoPanel, "About", null);
		});

		stopItem.setEnabled(mouseMovementManager.getCurrentExecutionThread() != null
			&& !mouseMovementManager.getCurrentExecutionThread().isInterrupted());

		stopItem.setCallback(e -> {
			stopMoving(stopItem, startItem);
			systemTray.setStatus("Stopped Moving");
		});

		startItem.setCallback(e -> {
			startMoving(stopItem, startItem);
			systemTray.setStatus("Moving around");
		});

		// Add components to pop-up menu
		systemTray.getMenu().add(aboutItem).setShortcut('b');
		systemTray.getMenu().add(settingsItem).setShortcut('t');
		systemTray.getMenu().add(new Separator());
		systemTray.getMenu().add(startItem).setShortcut('a');
		systemTray.getMenu().add(stopItem).setShortcut('s');
		systemTray.getMenu().add(new Separator());
		systemTray.getMenu().add(exitItem).setShortcut('e');

		// Ensure status is set last
		systemTray.setStatus("Ready");
	}

	/**
	 * Starts the mouse movement and tracking threads, adjusting the system tray items accordingly
	 *
	 * @param stopItem
	 *            the stop menu item
	 * @param startItem
	 *            the start menu item
	 */
	private void startMoving(MenuItem stopItem, MenuItem startItem)
	{
		if (mouseMovementManager != null)
		{
			log.info("Starting mouse movement...");
			mouseMovementManager.start();
			stopItem.setEnabled(true);
			startItem.setEnabled(false);
		}
		else
		{
			log.warning("MouseMovementManager is null. Unable to start mouse movement.");
		}
	}

	/**
	 * Stops the mouse movement and tracking threads, adjusting the system tray items accordingly
	 *
	 * @param stopItem
	 *            the stop menu item
	 * @param startItem
	 *            the start menu item
	 */
	private void stopMoving(MenuItem stopItem, MenuItem startItem)
	{
		if (mouseMovementManager != null)
		{
			log.info("Stopping mouse movement...");
			mouseMovementManager.stop();
			stopItem.setEnabled(false);
			startItem.setEnabled(true);
		}
		else
		{
			log.warning("MouseMovementManager is null. Unable to stop mouse movement.");
		}
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
		setModel(BaseModel.of(applicationModelBean));
		super.onBeforeInitialize();
	}

	@Override
	protected void onInitializeComponents()
	{
		super.onInitializeComponents();
		mouseMoveSettingsPanel = new MouseMoveSettingsPanel(BaseModel.of(settingsModelBean));
		initializeSystemTray();
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
		ApplicationPanel applicationPanel = new ApplicationPanel(getModel());
		return applicationPanel;
	}

}
