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

import javax.swing.ImageIcon;

import io.github.astrapi69.icon.ImageIconFactory;
import lombok.Getter;

/**
 * The {@link IconPreloader} class is responsible for preloading and caching the tray icon. This
 * ensures that the icon is ready to be displayed as soon as the system tray is initialized,
 * reducing delays during application startup.
 */
public class IconPreloader
{

	/**
	 * The preloaded tray icon -- GETTER -- Gets the preloaded tray icon.
	 *
	 * @return the {@link ImageIcon} instance for the tray icon
	 */
	@Getter
	private static final ImageIcon trayImageIcon;

	/**
	 * Static block to initialize the tray icon at class load time
	 */
	static
	{
		trayImageIcon = ImageIconFactory.newImageIcon("io/github/astrapi69/silk/icons/anchor.png",
			"Keep moving");
	}

	/**
	 * Private constructor to prevent instantiation.
	 */
	private IconPreloader()
	{
		// Utility class
	}

}
