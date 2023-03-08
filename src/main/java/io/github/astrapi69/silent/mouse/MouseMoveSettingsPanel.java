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

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

import org.jetbrains.annotations.NotNull;

import io.github.astrapi69.collection.array.ArrayFactory;
import io.github.astrapi69.model.BaseModel;
import io.github.astrapi69.model.api.IModel;
import io.github.astrapi69.swing.base.BasePanel;
import io.github.astrapi69.swing.combobox.model.GenericComboBoxModel;
import io.github.astrapi69.swing.component.JMComboBox;
import io.github.astrapi69.swing.component.JMTextField;
import io.github.astrapi69.swing.document.NumberValuesDocument;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

@Getter
public class MouseMoveSettingsPanel extends BasePanel<SettingsModelBean>
{
	private JMComboBox<Integer, GenericComboBoxModel<Integer>> cmbVariableX;
	private JMComboBox<Integer, GenericComboBoxModel<Integer>> cmbVariableY;
	private JLabel lblIntervalOfSeconds;
	private JLabel lblIntervalOfMouseMovementsCheckInSeconds;
	private JLabel lblSettings;
	private JLabel lblVariableX;
	private JLabel lblVariableY;
	private JMTextField txtIntervalOfSeconds;
	private JMTextField txtIntervalOfMouseMovementsCheckInSeconds;

	public MouseMoveSettingsPanel()
	{
		this(BaseModel.of(SettingsModelBean.builder().build()));
	}

	public MouseMoveSettingsPanel(final IModel<SettingsModelBean> model)
	{
		super(model);
	}

	@Override
	protected void onInitializeComponents()
	{
		lblSettings = new JLabel();
		lblVariableX = new JLabel();
		lblVariableY = new JLabel();
		lblIntervalOfSeconds = new JLabel();
		lblIntervalOfMouseMovementsCheckInSeconds = new JLabel();
		Integer[] cmbArray = ArrayFactory.newArray(1, 2, 3, 4);
		cmbVariableX = new JMComboBox<>(
			new GenericComboBoxModel<>(ArrayFactory.newArray(1, 2, 3, 4)));
		cmbVariableY = new JMComboBox<>(
			new GenericComboBoxModel<>(ArrayFactory.newArray(1, 2, 3, 4)));
		txtIntervalOfSeconds = new JMTextField();
		txtIntervalOfSeconds.setDocument(new NumberValuesDocument());
		txtIntervalOfMouseMovementsCheckInSeconds = new JMTextField();
		txtIntervalOfMouseMovementsCheckInSeconds.setDocument(new NumberValuesDocument());

		lblVariableX.setText("Move mouse on X axis in pixel");

		lblSettings.setText("Settings");

		lblVariableY.setText("Move mouse on Y axis in pixel");

		lblIntervalOfSeconds.setText("Move mouse every time (in seconds)");
		lblIntervalOfMouseMovementsCheckInSeconds
			.setText("Check mouse movement every time (in seconds)");

		cmbVariableX.setModel(new DefaultComboBoxModel<>(new Integer[] { 1, 2, 3, 4 }));
		cmbVariableX.setName("cmbVariableX");

		cmbVariableX.addActionListener(this::onChangeCmbVariableX);
		cmbVariableY.setName("cmbVariableY");
		cmbVariableY.addActionListener(this::onChangeCmbVariableY);

		txtIntervalOfSeconds.setText(getModelObject().getIntervalOfSeconds() != null
			? getModelObject().getIntervalOfSeconds().toString()
			: "60");
		txtIntervalOfSeconds.setName("txtIntervalOfSeconds");
		txtIntervalOfSeconds.addActionListener(this::onChangeTxtIntervalOfSeconds);
		txtIntervalOfSeconds.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent event)
			{
				JMTextField source = (JMTextField)event.getSource();
				final String text = source.getText();
				getModelObject().setIntervalOfSeconds(Integer.valueOf(text));
			}
		});

		txtIntervalOfMouseMovementsCheckInSeconds
			.setText(getModelObject().getIntervalOfMouseMovementsCheckInSeconds() != null
				? getModelObject().getIntervalOfMouseMovementsCheckInSeconds().toString()
				: "30");
		txtIntervalOfMouseMovementsCheckInSeconds
			.setName("txtIntervalOfMouseMovementsCheckInSeconds");
		txtIntervalOfMouseMovementsCheckInSeconds
			.addActionListener(this::onChangeTxtIntervalOfMouseMovementsCheckInSeconds);
		txtIntervalOfMouseMovementsCheckInSeconds.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent event)
			{
				JMTextField source = (JMTextField)event.getSource();
				final String text = source.getText();
				getModelObject().setIntervalOfMouseMovementsCheckInSeconds(Integer.valueOf(text));
			}
		});
	}

	protected void onChangeCmbVariableY(final ActionEvent actionEvent)
	{
		JMComboBox<Integer, GenericComboBoxModel<Integer>> source = (JMComboBox<Integer, GenericComboBoxModel<Integer>>)actionEvent
			.getSource();
		final Object selectedItem = source.getModel().getSelectedItem();
		getModelObject().setYAxis(Integer.valueOf(selectedItem.toString()));
	}

	protected void onChangeCmbVariableX(final ActionEvent actionEvent)
	{
		JMComboBox<Integer, GenericComboBoxModel<Integer>> source = (JMComboBox<Integer, GenericComboBoxModel<Integer>>)actionEvent
			.getSource();
		final Object selectedItem = source.getModel().getSelectedItem();
		getModelObject().setXAxis(Integer.valueOf(selectedItem.toString()));
	}

	protected void onChangeTxtIntervalOfSeconds(final ActionEvent actionEvent)
	{
		getJMTextFieldModel(actionEvent).ifPresent(propertyModel -> getModelObject()
			.setIntervalOfSeconds(Integer.valueOf(propertyModel.getObject())));
	}

	@NotNull
	private static Optional<IModel<String>> getJMTextFieldModel(ActionEvent actionEvent)
	{
		if (actionEvent.getSource() instanceof JMTextField)
		{
			JMTextField source = (JMTextField)actionEvent.getSource();
			IModel<String> propertyModel = source.getPropertyModel();
			return Optional.of(propertyModel);
		}
		return Optional.empty();
	}

	protected void onChangeTxtIntervalOfMouseMovementsCheckInSeconds(final ActionEvent actionEvent)
	{
		getJMTextFieldModel(actionEvent).ifPresent(propertyModel -> getModelObject()
			.setIntervalOfMouseMovementsCheckInSeconds(Integer.valueOf(propertyModel.getObject())));
	}

	@Override
	protected void onInitializeLayout()
	{
		this.onInitializeMigLayout();
	}

	protected void onInitializeMigLayout()
	{
		final MigLayout layout = new MigLayout();
		this.setLayout(layout);
		this.add(lblSettings, "wrap");

		this.add(lblVariableX);
		this.add(cmbVariableX, "wrap");

		this.add(lblVariableY);
		this.add(cmbVariableY, "wrap");

		this.add(lblIntervalOfSeconds);
		this.add(txtIntervalOfSeconds, "wrap");

		this.add(lblIntervalOfMouseMovementsCheckInSeconds);
		this.add(txtIntervalOfMouseMovementsCheckInSeconds, "wrap");
	}

}
