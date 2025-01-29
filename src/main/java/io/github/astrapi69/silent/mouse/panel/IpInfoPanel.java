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
		setBorder(BorderFactory.createTitledBorder("IP Information"));
	}
}