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

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.*;

import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;
import io.github.astrapi69.icon.ImageIconFactory;
import io.github.astrapi69.model.BaseModel;
import io.github.astrapi69.roboter.MouseExtensions;
import io.github.astrapi69.swing.dialog.JOptionPaneExtensions;
import io.github.astrapi69.swing.panel.info.AppInfoPanel;
import io.github.astrapi69.swing.panel.info.InfoModelBean;

/**
 * The {@link PureSwingSystemTray} class handles system tray interactions and automates mouse
 * movements for the application. It provides features like starting, stopping, and configuring
 * mouse movement through a system tray menu
 */
public class PureSwingSystemTray
{
	/** Logger for this class */
	static final Logger logger = Logger.getLogger(PureSwingSystemTray.class.getName());

	/** Thread for executing mouse movements */
	static InterruptableThread currentExecutionThread;

	/** Thread for tracking mouse movements */
	static InterruptableThread mouseTrackThread;

	/** Map to track the mouse's positions over time */
	static NavigableMap<LocalDateTime, Point> mouseTracks = new TreeMap<>();

	/** Robot for automating mouse movements */
	static Robot robot;

	/** Preferences object to store and retrieve user settings */
	private static final Preferences applicationPreferences = Preferences.userRoot()
		.node(PureSwingSystemTray.class.getName());

	/** Model bean containing the settings for mouse movements */
	static SettingsModelBean settingsModelBean = SettingsModelBean
		.setModelFromPreferences(SettingsModelBean.builder().intervalOfSeconds(180)
			.intervalOfMouseMovementsCheckInSeconds(90).xAxis(1).yAxis(1).moveOnStartup(false)
			.build(), applicationPreferences);

	/** Panel for configuring the mouse movement settings */
	static MouseMoveSettingsPanel mouseMoveSettingsPanel = new MouseMoveSettingsPanel(
		BaseModel.of(settingsModelBean));

	/**
	 * Gets the {@link Robot} used for automating mouse movements
	 *
	 * @return the robot instance
	 * @throws RuntimeException
	 *             if unable to create a robot instance
	 */
	static Robot getRobot()
	{
		if (robot == null)
		{
			try
			{
				robot = new Robot();
			}
			catch (AWTException e)
			{
				throw new RuntimeException(e);
			}
		}
		return robot;
	}

	/**
	 * The main entry point of the application
	 *
	 * @param args
	 *            the arguments passed to the application
	 */
	public static void main(final String[] args)
	{
		logger.setLevel(Level.FINE);
		final JFrame frame = new JFrame("MouseTrayApp");
		initializeComponents();
		frame.setExtendedState(JFrame.ICONIFIED);
		frame.pack();
		frame.setVisible(false);
	}

	/**
	 * Initializes the system tray components and sets up menu items
	 */
	private static void initializeComponents()
	{
		SystemTray systemTray = SystemTray.get();
		if (systemTray == null)
		{
			throw new RuntimeException("Unable to load SystemTray!");
		}

		ImageIcon trayImageIcon = ImageIconFactory
			.newImageIcon("io/github/astrapi69/silk/icons/anchor.png", "Keep moving");
		Image image = trayImageIcon.getImage();
		systemTray.setImage(image);

		systemTray.setStatus("Not Started");

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
				if (currentExecutionThread != null && currentExecutionThread.isAlive())
				{
					stopMoving(stopItem, startItem);
					startMoving(stopItem, startItem);
				}
			}
		});

		aboutItem.setCallback(e -> {
			InfoModelBean infoModelBean = InfoModelBean.builder().applicationName("silent mouse")
				.labelApplicationName("Application name:").labelCopyright("Copyright:")
				.copyright("Asterios Raptis").labelVersion("Version:").version("1.4") // !!!
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

		stopItem
			.setEnabled(currentExecutionThread != null && !currentExecutionThread.isInterrupted());
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
	}

	/**
	 * Starts the mouse movement and tracking threads, adjusting the system tray items accordingly
	 *
	 * @param stopItem
	 *            the stop menu item
	 * @param startItem
	 *            the start menu item
	 */
	private static void startMoving(MenuItem stopItem, MenuItem startItem)
	{
		if (currentExecutionThread != null)
		{
			currentExecutionThread.interrupt();
		}
		if (mouseTrackThread != null)
		{
			mouseTrackThread.interrupt();
		}
		mouseTrackThread = new InterruptableThread()
		{
			@Override
			protected void process()
			{
				while (!isInterrupted())
				{
					try
					{
						mouseTracks.put(LocalDateTime.now(), MouseExtensions.getMousePosition());
						Thread.sleep(
							settingsModelBean.getIntervalOfMouseMovementsCheckInSeconds() * 1000);
					}
					catch (InterruptedException ex)
					{
						logger.log(Level.INFO,
							"Mouse tracking has thrown exception with the following message: "
								+ ex.getLocalizedMessage());
					}
				}
			}
		};
		currentExecutionThread = new InterruptableThread()
		{
			@Override
			protected void process()
			{
				while (!isInterrupted())
				{
					final Map.Entry<LocalDateTime, Point> lastTrackedMousePointEntry = mouseTracks
						.lastEntry();
					final Point currentMousePosition = MouseExtensions.getMousePosition();
					if (lastTrackedMousePointEntry != null)
					{
						final Point lastTrackedMousePoint = lastTrackedMousePointEntry.getValue();
						// mouse not moved
						if (lastTrackedMousePoint.equals(currentMousePosition))
						{
							MouseExtensions.setMousePosition(getRobot(),
								currentMousePosition.x + settingsModelBean.getXAxis(),
								currentMousePosition.y + settingsModelBean.getYAxis());
							try
							{
								Thread.sleep(settingsModelBean.getIntervalOfSeconds() * 1000);
							}
							catch (InterruptedException ex)
							{
								logger.log(Level.INFO,
									"Set mouse position by tracking and went to sleep"
										+ " has thrown exception with the following message: "
										+ ex.getLocalizedMessage());
							}
						}
						else
						{
							int diff = settingsModelBean.getIntervalOfSeconds()
								- settingsModelBean.getIntervalOfMouseMovementsCheckInSeconds();
							try
							{
								Thread.sleep(diff);
							}
							catch (InterruptedException e)
							{
								logger.log(Level.INFO,
									"Go to sleep with the difference of 'interval of seconds'"
										+ " and 'interval of mouse movements check'"
										+ " has thrown exception with the following message: "
										+ e.getLocalizedMessage());
							}
						}
					}
					else
					{
						MouseExtensions.setMousePosition(getRobot(),
							currentMousePosition.x + settingsModelBean.getXAxis(),
							currentMousePosition.y + settingsModelBean.getYAxis());
						try
						{
							Thread.sleep(
								mouseMoveSettingsPanel.getModelObject().getIntervalOfSeconds()
									* 1000);
						}
						catch (InterruptedException e)
						{
							logger.log(Level.INFO,
								"Set mouse position by tracking and went to sleep"
									+ " where 'lastTrackedMousePoint is not equal to currentMousePosition'"
									+ " has thrown exception with the following message: "
									+ e.getLocalizedMessage());
						}
					}
				}
			}
		};
		mouseTrackThread.start();
		mouseTrackThread.setPriority(Thread.MIN_PRIORITY);
		currentExecutionThread.start();
		currentExecutionThread.setPriority(Thread.MIN_PRIORITY);
		stopItem.setEnabled(true);
		startItem.setEnabled(false);
	}

	/**
	 * Stops the mouse movement and tracking threads, adjusting the system tray items accordingly
	 *
	 * @param stopItem
	 *            the stop menu item
	 * @param startItem
	 *            the start menu item
	 */
	private static void stopMoving(MenuItem stopItem, MenuItem startItem)
	{
		if (currentExecutionThread != null)
		{
			currentExecutionThread.setPriority(Thread.MIN_PRIORITY);
			currentExecutionThread.interrupt();
			while (!currentExecutionThread.isInterrupted())
			{
				currentExecutionThread.interrupt();
			}
			stopItem.setEnabled(false);
			startItem.setEnabled(true);
		}

		if (mouseTrackThread != null)
		{
			mouseTrackThread.setPriority(Thread.MIN_PRIORITY);
			mouseTrackThread.interrupt();
			while (!mouseTrackThread.isInterrupted())
			{
				mouseTrackThread.interrupt();
			}
		}
	}
}
