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

import javax.swing.*;

import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;
import io.github.astrapi69.icon.ImageIconFactory;
import io.github.astrapi69.model.BaseModel;
import io.github.astrapi69.swing.dialog.JOptionPaneExtensions;
import io.github.astrapi69.swing.robot.MouseExtensions;

public class PureSwingSystemTray
{

	static InterruptableThread currentExecutionThread;
	static InterruptableThread mouseTrackThread;

	static NavigableMap<LocalDateTime, Point> mouseTracks = new TreeMap<>();
	static SettingsModelBean settingsModelBean = SettingsModelBean.builder().intervalOfSeconds(180)
		.intervalOfMouseMovementsCheckInSeconds(90).xAxis(1).yAxis(1).build();
	static MouseMoveSettingsPanel panel = new MouseMoveSettingsPanel(
		BaseModel.of(settingsModelBean));
	static Robot robot;

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

	public static void main(final String[] args)
	{
		final JFrame frame = new JFrame("MouseTrayApp");
		initializeComponents();
		frame.setExtendedState(JFrame.ICONIFIED);
		frame.pack();
		frame.setVisible(false);
	}

	private static void initializeComponents()
	{
		SystemTray systemTray = SystemTray.get();
		if (systemTray == null)
		{
			throw new RuntimeException("Unable to load SystemTray!");
		}

		// systemTray.installShutdownHook();
		ImageIcon trayImageIcon = ImageIconFactory
			.newImageIcon("io/github/astrapi69/silk/icons/anchor.png", "Keep moving");
		Image image = trayImageIcon.getImage();
		systemTray.setImage(image);

		systemTray.setStatus("Not Started");

		MenuItem startItem = new MenuItem("Start");
		MenuItem stopItem = new MenuItem("Stop");
		MenuItem exitItem = new MenuItem("Exit");
		MenuItem aboutItem = new MenuItem("About");

		exitItem.setCallback(e -> {
			systemTray.shutdown();
			System.exit(0);
		});

		aboutItem.setCallback(e -> {
			int option = JOptionPaneExtensions.getInfoDialogWithOkCancelButton(panel, "Settings",
				panel.getCmbVariableX());
			if (option == JOptionPane.OK_OPTION)
			{
				final String text = panel.getTxtIntervalOfSeconds().getText();
				if (text != null)
				{
					settingsModelBean.setIntervalOfSeconds(Integer.valueOf(text));
				}
				settingsModelBean = panel.getModelObject();
				if (currentExecutionThread != null && currentExecutionThread.isAlive())
				{
					stopMoving(stopItem, startItem);
					startMoving(stopItem, startItem);
				}
			}
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
		systemTray.getMenu().add(aboutItem).setShortcut('q');
		systemTray.getMenu().add(new Separator());
		systemTray.getMenu().add(startItem);
		systemTray.getMenu().add(stopItem);
		systemTray.getMenu().add(new Separator());
		systemTray.getMenu().add(exitItem);
	}

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
						throw new RuntimeException(ex);
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
					try
					{
						final Map.Entry<LocalDateTime, Point> lastTrackedMousePointEntry = mouseTracks
							.lastEntry();
						final Point currentMousePosition = MouseExtensions.getMousePosition();
						if (lastTrackedMousePointEntry != null)
						{
							final Point lastTrackedMousePoint = lastTrackedMousePointEntry
								.getValue();
							// mouse not moved
							if (lastTrackedMousePoint.equals(currentMousePosition))
							{
								MouseExtensions.setMousePosition(getRobot(),
									currentMousePosition.x + settingsModelBean.getXAxis(),
									currentMousePosition.y + settingsModelBean.getYAxis());
								Thread.sleep(settingsModelBean.getIntervalOfSeconds() * 1000);
							}
							else
							{
								int diff = settingsModelBean.getIntervalOfSeconds()
									- settingsModelBean.getIntervalOfMouseMovementsCheckInSeconds();
								Thread.sleep(diff);
							}
						}
						else
						{
							MouseExtensions.setMousePosition(getRobot(),
								currentMousePosition.x + settingsModelBean.getXAxis(),
								currentMousePosition.y + settingsModelBean.getYAxis());
							Thread.sleep(settingsModelBean.getIntervalOfSeconds() * 1000);
						}

					}
					catch (InterruptedException ex)
					{
						throw new RuntimeException(ex);
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
