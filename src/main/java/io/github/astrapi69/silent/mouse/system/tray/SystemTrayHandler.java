package io.github.astrapi69.silent.mouse.system.tray;

import io.github.astrapi69.silent.mouse.frame.SystemTrayApplicationFrame;
import io.github.astrapi69.silent.mouse.model.SettingsModelBean;

public interface SystemTrayHandler
{
	void initialize(SystemTrayApplicationFrame frame, SettingsModelBean settingsModelBean);

	void shutdown();
}
