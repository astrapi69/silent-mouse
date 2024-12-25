package io.github.astrapi69.silent.mouse.system.tray;

import java.awt.*;

import javax.swing.*;

import io.github.astrapi69.silent.mouse.frame.SystemTrayApplicationFrame;
import io.github.astrapi69.silent.mouse.model.SettingsModelBean;
import io.github.astrapi69.silent.mouse.robot.MouseMovementManager;

/**
 * Implementation of {@link AbstractSystemTrayHandler} for handling system tray interactions using
 * the standard {@link java.awt.SystemTray} API.
 */
public class JavaSystemTrayHandler implements SystemTrayHandler
{

	/**
	 * The {@link TrayIcon} instance used to manage the system tray icon and menu.
	 */
	private TrayIcon trayIcon;

	/**
	 * Constructs a new {@link JavaSystemTrayHandler} with the given {@link MouseMovementManager}.
	 *
	 * @param mouseMovementManager
	 *            the manager for handling mouse movement.
	 */
	public JavaSystemTrayHandler(MouseMovementManager mouseMovementManager)
	{
		super(mouseMovementManager);
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

		SystemTray systemTray = SystemTray.getSystemTray();
		Image iconImage = Toolkit.getDefaultToolkit()
			.getImage(getClass().getResource("/io/github/astrapi69/silk/icons/anchor.png"));

		PopupMenu popupMenu = new PopupMenu();

		MenuItem startItem = new MenuItem("Start");
		MenuItem stopItem = new MenuItem("Stop");
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
				+ "1.0\nCopyright: Asterios Raptis\nThis Software is licensed under the MIT Licence",
				"About", JOptionPane.INFORMATION_MESSAGE);
		}));

		// Start menu item
		startItem.addActionListener(e -> startMoving(stopItem, startItem));
		stopItem.addActionListener(e -> stopMoving(stopItem, startItem));

		popupMenu.add(aboutItem);
		popupMenu.add(settingsItem);
		popupMenu.addSeparator();
		popupMenu.add(startItem);
		popupMenu.add(stopItem);
		popupMenu.addSeparator();
		popupMenu.add(exitItem);

		trayIcon = new TrayIcon(iconImage, "Silent Mouse", popupMenu);
		trayIcon.setImageAutoSize(true);

		try
		{
			systemTray.add(trayIcon);
		}
		catch (AWTException e)
		{
			throw new RuntimeException("Unable to add TrayIcon to SystemTray", e);
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
	 * {@inheritDoc}
	 */
	@Override
	protected void log(String message)
	{
		System.out.println("[JavaSystemTrayHandler] " + message);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void updateMenuItems(MenuItem stopItem, MenuItem startItem, boolean isMoving)
	{
		stopItem.setEnabled(isMoving);
		startItem.setEnabled(!isMoving);
	}
}
