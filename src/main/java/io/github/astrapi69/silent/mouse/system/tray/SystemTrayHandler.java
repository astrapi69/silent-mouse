package io.github.astrapi69.silent.mouse.system.tray;

import io.github.astrapi69.silent.mouse.frame.SystemTrayApplicationFrame;
import io.github.astrapi69.silent.mouse.model.SettingsModelBean;

/**
 * The interface {@link SystemTrayHandler} defines methods to handle system tray operations for
 * initializing, managing, and shutting down the system tray.
 */
public interface SystemTrayHandler
{

	/**
	 * Initializes the system tray with the specified frame and settings model bean.
	 *
	 * @param frame
	 *            the application frame that manages the system tray behavior
	 * @param settingsModelBean
	 *            the settings model bean containing configuration settings
	 */
	void initialize(SystemTrayApplicationFrame frame, SettingsModelBean settingsModelBean);

	/**
	 * Shuts down the system tray and releases resources.
	 */
	void shutdown();

	/**
	 * Starts the mouse movement and adjusts the system tray items accordingly.
	 *
	 * @param stopItem
	 *            the stop menu item
	 * @param startItem
	 *            the start menu item
	 */
	void startMoving(Object stopItem, Object startItem);

	/**
	 * Stops the mouse movement and adjusts the system tray items accordingly.
	 *
	 * @param stopItem
	 *            the stop menu item
	 * @param startItem
	 *            the start menu item
	 */
	void stopMoving(Object stopItem, Object startItem);
}