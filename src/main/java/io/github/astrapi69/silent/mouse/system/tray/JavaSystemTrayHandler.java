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
package io.github.astrapi69.silent.mouse.system.tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import io.github.astrapi69.icon.ImageIconPreloader;
import io.github.astrapi69.silent.mouse.frame.SystemTrayApplicationFrame;
import io.github.astrapi69.silent.mouse.i18n.Messages;
import io.github.astrapi69.silent.mouse.model.SettingsModelBean;
import io.github.astrapi69.silent.mouse.robot.MouseMovementManager;
import lombok.extern.java.Log;

/**
 * The class {@link JavaSystemTrayHandler} implements the {@link SystemTrayHandler} interface and
 * provides functionality to manage the system tray behavior using the standard java.awt.SystemTray
 */
@Log
public class JavaSystemTrayHandler implements SystemTrayHandler
{
	SystemTray systemTray;
	MenuItem startItem;
	MenuItem stopItem;

	/**
	 * The {@link TrayIcon} instance used to manage the system tray icon and menu
	 */
	private TrayIcon trayIcon;

	/** The manager class for handle the mouse movement */
	MouseMovementManager mouseMovementManager;

	public JavaSystemTrayHandler(MouseMovementManager mouseMovementManager)
	{
		this.mouseMovementManager = mouseMovementManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(SystemTrayApplicationFrame frame, SettingsModelBean settingsModelBean)
	{
		if (!SystemTray.isSupported())
		{
			throw new RuntimeException("SystemTray is not supported on this platform.");
		}

		systemTray = SystemTray.getSystemTray();
		Image iconImage = ImageIconPreloader.getIcon("io/github/astrapi69/silk/icons/anchor.png")
			.getImage();

		PopupMenu popupMenu = new PopupMenu();

		startItem = new MenuItem("Start");
		stopItem = new MenuItem("Stop");
		MenuItem settingsItem = new MenuItem("Settings");
		MenuItem aboutItem = new MenuItem("About");
		MenuItem exitItem = new MenuItem("Exit");

		// Exit menu item
		exitItem.addActionListener(e -> {
			shutdown();
			System.exit(0);
		});

		// Settings menu item
		settingsItem.addActionListener(e -> SwingUtilities.invokeLater(() -> {
			int option = JOptionPane.showConfirmDialog(frame, frame.getMouseMoveSettingsPanel(),
				"Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (option == JOptionPane.OK_OPTION)
			{
				String text = frame.getMouseMoveSettingsPanel().getTxtIntervalOfSeconds().getText();
				if (text != null)
				{
					settingsModelBean.setIntervalOfSeconds(Integer.parseInt(text));
				}
				frame.getModelObject()
					.setSettingsModelBean(frame.getMouseMoveSettingsPanel().getModelObject());
			}
		}));

		// About menu item
		aboutItem.addActionListener(e -> SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(frame, "Silent Mouse\nVersion: "
				+ Messages.getString("InfoJPanel.version.value")
				+ "\nCopyright: Asterios Raptis\nThis Software is licensed under the MIT Licence",
				"About", JOptionPane.INFORMATION_MESSAGE);
		}));

		// Start menu item
		startItem.addActionListener(e -> {
			startMoving();
			trayIcon.setToolTip("Moving around");
		});

		// Stop menu item
		stopItem.setEnabled(frame.getMouseMovementManager().getCurrentExecutionThread() != null
			&& !frame.getMouseMovementManager().getCurrentExecutionThread().isInterrupted());
		stopItem.addActionListener(e -> {
			stopMoving();
			trayIcon.setToolTip("Stopped Moving");
		});

		popupMenu.add(aboutItem);
		popupMenu.add(settingsItem);
		popupMenu.addSeparator();
		popupMenu.add(startItem);
		popupMenu.add(stopItem);
		popupMenu.addSeparator();
		popupMenu.add(exitItem);

		trayIcon = new TrayIcon(iconImage, "Silent Mouse", popupMenu);
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("Initializing...");

		try
		{
			systemTray.add(trayIcon);
			trayIcon.setToolTip("Ready");
		}
		catch (AWTException e)
		{
			throw new RuntimeException("Unable to add TrayIcon to SystemTray", e);
		}

		if (settingsModelBean.isMoveOnStartup())
		{
			startMoving();
			trayIcon.setToolTip("Moving around");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void shutdown()
	{
		if (trayIcon != null)
		{
			SystemTray.getSystemTray().remove(trayIcon);
			trayIcon = null;
		}
	}

	/**
	 * Starts the mouse movement and tracking threads, adjusting the system tray items accordingly
	 *
	 * @param stopItem
	 *            the stop menu item
	 * @param startItem
	 *            the start menu item
	 */
	public void startMoving(MenuItem stopItem, MenuItem startItem)
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
	 * {@inheritDoc}
	 */
	public void startMoving()
	{
		startMoving(stopItem, startItem);
	}

	/**
	 * {@inheritDoc}
	 */
	public void stopMoving()
	{
		stopMoving(stopItem, startItem);
	}

	/**
	 * Stops the mouse movement and tracking threads, adjusting the system tray items accordingly
	 *
	 * @param stopItem
	 *            the stop menu item
	 * @param startItem
	 *            the start menu item
	 */
	public void stopMoving(MenuItem stopItem, MenuItem startItem)
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

}
