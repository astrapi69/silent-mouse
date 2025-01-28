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

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;
import io.github.astrapi69.icon.ImageIconPreloader;
import io.github.astrapi69.model.BaseModel;
import io.github.astrapi69.silent.mouse.frame.SystemTrayApplicationFrame;
import io.github.astrapi69.silent.mouse.i18n.Messages;
import io.github.astrapi69.silent.mouse.model.SettingsModelBean;
import io.github.astrapi69.silent.mouse.robot.MouseMovementManager;
import io.github.astrapi69.swing.dialog.JOptionPaneExtensions;
import io.github.astrapi69.swing.panel.info.AppInfoPanel;
import io.github.astrapi69.swing.panel.info.InfoModelBean;
import lombok.extern.java.Log;

/**
 * The class {@link DefaultSystemTrayHandler} implements the {@link SystemTrayHandler} interface and
 * provides functionality to manage the system tray behavior for the Silent Mouse application
 */
@Log
public class DefaultSystemTrayHandler implements SystemTrayHandler
{

	MenuItem startItem;
	MenuItem stopItem;
	/**
	 * The {@link SystemTray} instance used to manage the system tray menu and status
	 */
	private SystemTray systemTray;

	/** The manager class for handle the mouse movement */
	MouseMovementManager mouseMovementManager;

	public DefaultSystemTrayHandler(MouseMovementManager mouseMovementManager)
	{
		this.mouseMovementManager = mouseMovementManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(SystemTrayApplicationFrame frame, SettingsModelBean settingsModelBean)
	{
		systemTray = SystemTray.get();
		if (systemTray == null)
		{
			throw new RuntimeException("Unable to load SystemTray!");
		}

		systemTray.setImage(
			ImageIconPreloader.getIcon("io/github/astrapi69/silk/icons/anchor.png").getImage());
		systemTray.setStatus("Initializing...");

		startItem = new MenuItem("Start");
		stopItem = new MenuItem("Stop");
		MenuItem exitItem = new MenuItem("Exit");
		MenuItem aboutItem = new MenuItem("About");
		MenuItem settingsItem = new MenuItem("Settings");
		MenuItem networkInfoItem = new MenuItem("Network Info");

		networkInfoItem.setCallback(e -> {
			String networkInfo = getNetworkInformation();
			JOptionPane.showMessageDialog(null, networkInfo, "Network Information",
				JOptionPane.INFORMATION_MESSAGE);
		});

		systemTray.getMenu().add(networkInfoItem).setShortcut('n');

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
				frame.getMouseMoveSettingsPanel(), "Settings",
				frame.getMouseMoveSettingsPanel().getCmbVariableX());
			if (option == JOptionPane.OK_OPTION)
			{
				String text = frame.getMouseMoveSettingsPanel().getTxtIntervalOfSeconds().getText();
				if (text != null)
				{
					settingsModelBean.setIntervalOfSeconds(Integer.valueOf(text));
				}
				frame.getModelObject()
					.setSettingsModelBean(frame.getMouseMoveSettingsPanel().getModelObject());
			}
		});

		aboutItem.setCallback(e -> {
			InfoModelBean infoModelBean = InfoModelBean.builder().applicationName("silent mouse")
				.labelApplicationName("Application name:").labelCopyright("Copyright:")
				.copyright("Asterios Raptis").labelVersion("Version:")
				.version(Messages.getString("InfoJPanel.version.value"))
				.licence("This Software is licensed under the MIT Licence").build();
			AppInfoPanel appInfoPanel = new AppInfoPanel(BaseModel.of(infoModelBean));
			JOptionPaneExtensions.getInfoDialogWithOkCancelButton(appInfoPanel, "About", null);
		});

		stopItem.setEnabled(frame.getMouseMovementManager().getCurrentExecutionThread() != null
			&& !frame.getMouseMovementManager().getCurrentExecutionThread().isInterrupted());

		stopItem.setCallback(e -> {
			stopMoving(stopItem, startItem);
			systemTray.setStatus("Stopped Moving");
		});

		startItem.setCallback(e -> {
			startMoving(stopItem, startItem);
			systemTray.setStatus("Moving around");
		});

		systemTray.getMenu().add(aboutItem).setShortcut('b');
		systemTray.getMenu().add(settingsItem).setShortcut('t');
		systemTray.getMenu().add(new Separator());
		systemTray.getMenu().add(startItem).setShortcut('a');
		systemTray.getMenu().add(stopItem).setShortcut('s');
		systemTray.getMenu().add(new Separator());
		systemTray.getMenu().add(exitItem).setShortcut('e');

		systemTray.setStatus("Ready");
	}


	public static String getConnectedIPAddress()
	{
		String ipAddress = "Unable to determine IP address";
		try (Socket socket = new Socket("8.8.8.8", 53))
		{
			// Query the socket's local address
			InetAddress localAddress = socket.getLocalAddress();
			ipAddress = localAddress.getHostAddress();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ipAddress;
	}

	private String getNetworkInformation()
	{
		StringBuilder networkInfo = new StringBuilder();
		try
		{
			String connectedIPAddress = getConnectedIPAddress();
			InetAddress localHost = InetAddress.getLocalHost();
			networkInfo.append("Host Name: ").append(localHost.getHostName())
				.append(System.lineSeparator());
			networkInfo.append("IP Address: ").append(localHost.getHostAddress())
				.append(System.lineSeparator());
			networkInfo.append("Network IP Address: ").append(connectedIPAddress);
		}
		catch (UnknownHostException e)
		{
			networkInfo.append("Unable to retrieve network information: ").append(e.getMessage());
		}
		return networkInfo.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void shutdown()
	{
		if (systemTray != null)
		{
			systemTray.shutdown();
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
