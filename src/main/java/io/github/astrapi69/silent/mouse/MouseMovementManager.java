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

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;

import io.github.astrapi69.lang.thread.InterruptableThread;
import io.github.astrapi69.roboter.MouseExtensions;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 * The {@link MouseMovementManager} class manages mouse movements and tracking using {@link Robot}.
 * It allows starting and stopping mouse movement logic independently of any GUI components.
 */
@Log
@Getter
public class MouseMovementManager
{

	/** The {@link Robot} instance used for automating mouse movements */
	private final Robot robot;

	/** The settings model containing configuration for mouse movements */
	private final SettingsModelBean settingsModelBean;

	/** A map to track mouse positions over time */
	private final NavigableMap<LocalDateTime, Point> mouseTracks = new TreeMap<>();

	/** Thread responsible for executing mouse movements */
	private InterruptableThread currentExecutionThread;

	/** Thread responsible for tracking mouse movements */
	private InterruptableThread mouseTrackThread;

	/**
	 * Constructs a new {@link MouseMovementManager} with the given settings model.
	 *
	 * @param settingsModelBean
	 *            the settings model containing mouse movement configurations
	 */
	public MouseMovementManager(SettingsModelBean settingsModelBean)
	{
		this.settingsModelBean = settingsModelBean;
		this.robot = newRobot();
	}

	/**
	 * Creates and returns a new {@link Robot} instance.
	 *
	 * @return the {@link Robot} instance
	 * @throws RuntimeException
	 *             if unable to create a {@link Robot} instance
	 */
	private Robot newRobot()
	{
		try
		{
			return new Robot();
		}
		catch (AWTException e)
		{
			throw new RuntimeException("Unable to create Robot instance", e);
		}
	}

	/**
	 * Starts the mouse movement and tracking threads.
	 */
	public void start()
	{
		log.info("Starting mouse movement manager...");

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
						log.log(Level.INFO, "Mouse tracking interrupted: " + ex.getMessage());
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
						if (lastTrackedMousePoint.equals(currentMousePosition))
						{
							moveMouse();
						}
						else
						{
							sleepDiff();
						}
					}
					else
					{
						moveMouse();
					}
				}
			}
		};

		mouseTrackThread.start();
		currentExecutionThread.start();
	}

	/**
	 * Stops the mouse movement and tracking threads.
	 */
	public void stop()
	{
		log.info("Stopping mouse movement manager...");
		if (currentExecutionThread != null)
		{
			currentExecutionThread.interrupt();
		}
		if (mouseTrackThread != null)
		{
			mouseTrackThread.interrupt();
		}
	}

	/**
	 * Moves the mouse to a new position based on the settings model.
	 */
	private void moveMouse()
	{
		final Point currentMousePosition = MouseExtensions.getMousePosition();
		MouseExtensions.setMousePosition(robot,
			currentMousePosition.x + settingsModelBean.getXAxis(),
			currentMousePosition.y + settingsModelBean.getYAxis());
		try
		{
			Thread.sleep(settingsModelBean.getIntervalOfSeconds() * 1000);
		}
		catch (InterruptedException ex)
		{
			log.log(Level.INFO, "Mouse movement interrupted: " + ex.getMessage());
		}
	}

	/**
	 * Sleeps for the difference between the movement interval and the tracking interval.
	 */
	private void sleepDiff()
	{
		int diff = settingsModelBean.getIntervalOfSeconds()
			- settingsModelBean.getIntervalOfMouseMovementsCheckInSeconds();
		try
		{
			Thread.sleep(diff * 1000);
		}
		catch (InterruptedException ex)
		{
			log.log(Level.INFO, "Sleep interrupted: " + ex.getMessage());
		}
	}
}
