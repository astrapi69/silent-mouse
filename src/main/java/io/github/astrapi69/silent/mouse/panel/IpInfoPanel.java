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
package io.github.astrapi69.silent.mouse.panel;

import java.awt.*;

import javax.swing.*;

import io.github.astrapi69.model.api.IModel;
import io.github.astrapi69.net.ip.IpInfo;
import io.github.astrapi69.swing.base.BasePanel;

/**
 * Swing panel to display IP information
 */
public class IpInfoPanel extends BasePanel<IpInfo>
{
	private JLabel localIPLabel;
	private JLabel routerIPLabel;
	private JLabel externalIPLabel;
	private JLabel localNetworkIPLabel;

	public IpInfoPanel(final IModel<IpInfo> model)
	{
		super(model);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onInitializeComponents()
	{
		super.onInitializeComponents();

		add(new JLabel("Local IP Address:"));
		localIPLabel = new JLabel(getModelObject().getLocalIPAddress());
		add(localIPLabel);

		add(new JLabel("Router IP Address:"));
		routerIPLabel = new JLabel(getModelObject().getRouterIPAddress());
		add(routerIPLabel);

		add(new JLabel("External IP Address:"));
		externalIPLabel = new JLabel(getModelObject().getExternalIPAddress());
		add(externalIPLabel);

		add(new JLabel("Local Network IP Address:"));
		localNetworkIPLabel = new JLabel(getModelObject().getLocalNetworkIPAddress());
		add(localNetworkIPLabel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onInitializeLayout()
	{
		super.onInitializeLayout();
		setLayout(new GridLayout(4, 2, 10, 10));
	}
}