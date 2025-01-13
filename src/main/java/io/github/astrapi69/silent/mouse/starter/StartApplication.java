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
package io.github.astrapi69.silent.mouse.starter;

import javax.swing.JFrame;

import io.github.astrapi69.icon.ImageIconPreloader;
import io.github.astrapi69.silent.mouse.frame.SystemTrayApplicationFrame;
import io.github.astrapi69.silent.mouse.model.SettingsModelBean;
import io.github.astrapi69.silent.mouse.robot.MouseMovementManager;
import io.github.astrapisixtynine.easy.logger.LoggingConfiguration;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 * The class {@link StartApplication} starts the application as a service or a standard application
 */
@Log
public class StartApplication
{
	@Getter
	private static boolean running = false;

	/**
	 * Main method to start the application in standalone mode
	 *
	 * @param args
	 *            the arguments passed to the application
	 */
	public static void main(final String[] args)
	{
		if (args.length > 0 && "service".equalsIgnoreCase(args[0]))
		{
			start(); // Service mode
		}
		else
		{
			run(); // Standalone mode
		}
	}

	/**
	 * Starts the service
	 */
	public static void start()
	{
		LoggingConfiguration.setup();
		log.info("Service started...");
		SettingsModelBean settingsModelBean = SettingsModelBean.builder().intervalOfSeconds(180)
			.intervalOfMouseMovementsCheckInSeconds(90).xAxis(1).yAxis(1).moveOnStartup(true)
			.build();

		MouseMovementManager manager = new MouseMovementManager(settingsModelBean);

		Runtime.getRuntime().addShutdownHook(new Thread(manager::stop));
		manager.start();
		running = true;
	}

	/**
	 * Runs the application in standalone mode
	 */
	public static void run()
	{
		LoggingConfiguration.setup();
		log.info("Application started in standalone mode.");
		ImageIconPreloader.loadIcon("io/github/astrapi69/silk/icons/anchor.png", "Keep moving");
		SystemTrayApplicationFrame frame = new SystemTrayApplicationFrame();
		frame.setExtendedState(JFrame.ICONIFIED);
		frame.pack();
		frame.setVisible(false);
		running = true;
	}

	/**
	 * Stops the service
	 */
	public static void stop()
	{
		log.info("Service stopping...");
		running = false;
	}
}
