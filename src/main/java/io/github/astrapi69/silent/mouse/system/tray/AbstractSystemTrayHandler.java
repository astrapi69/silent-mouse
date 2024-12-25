package io.github.astrapi69.silent.mouse.system.tray;

import io.github.astrapi69.silent.mouse.robot.MouseMovementManager;

/**
 * Abstract implementation of {@link SystemTrayHandler} to provide common functionality for system
 * tray handlers.
 */
public abstract class AbstractSystemTrayHandler implements SystemTrayHandler
{

	/** The manager class for handling the mouse movement. */
	protected final MouseMovementManager mouseMovementManager;

	/**
	 * Constructor to initialize the mouse movement manager.
	 *
	 * @param mouseMovementManager
	 *            the mouse movement manager
	 */
	protected AbstractSystemTrayHandler(MouseMovementManager mouseMovementManager)
	{
		this.mouseMovementManager = mouseMovementManager;
	}

	/**
	 * Logs messages for debugging purposes.
	 *
	 * @param message
	 *            the message to log
	 */
	protected abstract void log(String message);

}
