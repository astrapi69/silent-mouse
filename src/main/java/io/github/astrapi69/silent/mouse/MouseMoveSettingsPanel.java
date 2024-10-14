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
import java.util.prefs.Preferences;

import javax.swing.*;

import io.github.astrapi69.collection.array.ArrayFactory;
import io.github.astrapi69.collection.pair.ValueBox;
import io.github.astrapi69.component.model.check.CheckedModel;
import io.github.astrapi69.model.LambdaModel;
import io.github.astrapi69.model.api.IModel;
import io.github.astrapi69.swing.base.BasePanel;
import io.github.astrapi69.swing.document.NumberValuesDocument;
import io.github.astrapi69.swing.model.combobox.GenericComboBoxModel;
import io.github.astrapi69.swing.model.component.JMCheckBox;
import io.github.astrapi69.swing.model.component.JMComboBox;
import io.github.astrapi69.swing.model.component.JMTextField;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

/**
 * The {@link MouseMoveSettingsPanel} is a Swing panel for configuring mouse movement settings
 * including intervals for movements, checking movements, and enabling mouse movement at startup
 */
@Getter
public class MouseMoveSettingsPanel extends BasePanel<SettingsModelBean>
{
	/** Constant for the "not set" option */
	public static final String NOT_SET = "not set";

	/** Preferences object to save and retrieve user settings */
	private static final Preferences applicationPreferences = Preferences.userRoot()
		.node(SilentMouseApplicationFrame.class.getName());

	/**
	 * Combo box for selecting the X-axis movement value
	 */
	private JMComboBox<Integer, GenericComboBoxModel<Integer>> cmbVariableX;

	/**
	 * Combo box for selecting the Y-axis movement value
	 */
	private JMComboBox<Integer, GenericComboBoxModel<Integer>> cmbVariableY;

	/**
	 * Label for displaying the interval of seconds for mouse movements
	 */
	private JLabel lblIntervalOfSeconds;

	/**
	 * Label for displaying the interval of mouse movements check in seconds
	 */
	private JLabel lblIntervalOfMouseMovementsCheckInSeconds;

	/**
	 * Label for displaying the settings header
	 */
	private JLabel lblSettings;

	/**
	 * Label for displaying the description of X-axis movement
	 */
	private JLabel lblVariableX;

	/**
	 * Label for displaying the description of Y-axis movement
	 */
	private JLabel lblVariableY;

	/**
	 * Text field for inputting the interval of seconds for mouse movements
	 */
	private JMTextField txtIntervalOfSeconds;

	/**
	 * Text field for inputting the interval of mouse movements check in seconds
	 */
	private JMTextField txtIntervalOfMouseMovementsCheckInSeconds;

	/**
	 * Checkbox for enabling or disabling mouse movement on startup
	 */
	private JMCheckBox checkBoxMoveOnStartup;

	/**
	 * Instantiates a new {@link MouseMoveSettingsPanel} with the given model
	 *
	 * @param model
	 *            the model containing the settings data
	 */
	public MouseMoveSettingsPanel(final IModel<SettingsModelBean> model)
	{
		super(model);
	}

	/**
	 * Gets the text field model from the given action event
	 *
	 * @param actionEvent
	 *            the action event
	 * @return an optional containing the text field model if present
	 */
	private Optional<IModel<String>> getJMTextFieldModel(ActionEvent actionEvent)
	{
		if (actionEvent.getSource() instanceof JMTextField)
		{
			var source = (JMTextField)actionEvent.getSource();
			String sourceText = source.getText();
			IModel<String> propertyModel = source.getPropertyModel();
			propertyModel.setObject(sourceText);
			return Optional.of(propertyModel);
		}
		return Optional.empty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onInitializeComponents()
	{
		lblSettings = new JLabel();
		lblVariableX = new JLabel();
		lblVariableY = new JLabel();
		lblIntervalOfSeconds = new JLabel();
		lblIntervalOfMouseMovementsCheckInSeconds = new JLabel();
		cmbVariableX = new JMComboBox<>(
			new GenericComboBoxModel<>(ArrayFactory.newArray(1, 2, 3, 4)),
			LambdaModel.of(getModelObject()::getXAxis, getModelObject()::setXAxis));
		cmbVariableY = new JMComboBox<>(
			new GenericComboBoxModel<>(ArrayFactory.newArray(1, 2, 3, 4)),
			LambdaModel.of(getModelObject()::getYAxis, getModelObject()::setYAxis));
		txtIntervalOfSeconds = new JMTextField();
		txtIntervalOfSeconds.setDocument(new NumberValuesDocument());
		ValueBox<String> intervalOfSecondsAsStringBox = ValueBox.<String> builder()
			.value(getModelObject().getIntervalOfSeconds().toString()).build();
		txtIntervalOfSeconds.setPropertyModel(LambdaModel.of(intervalOfSecondsAsStringBox::getValue,
			intervalOfSecondsAsStringBox::setValue));
		txtIntervalOfMouseMovementsCheckInSeconds = new JMTextField();
		txtIntervalOfMouseMovementsCheckInSeconds.setDocument(new NumberValuesDocument());
		checkBoxMoveOnStartup = new JMCheckBox("Moving mouse already from start");

		lblVariableX.setText("Move mouse on X axis in pixel");

		lblSettings.setText("Settings");

		lblVariableY.setText("Move mouse on Y axis in pixel");

		lblIntervalOfSeconds.setText("Move mouse every time (in seconds)");
		lblIntervalOfMouseMovementsCheckInSeconds
			.setText("Check mouse movement every time (in seconds)");

		final CheckedModel checkedModelBean = CheckedModel.builder().build();
		checkedModelBean.setChecked(getModelObject().isMoveOnStartup());
		final IModel<Boolean> booleanModel = LambdaModel.of(checkedModelBean::isChecked,
			checkedModelBean::setChecked);
		checkBoxMoveOnStartup.setPropertyModel(booleanModel);
		checkBoxMoveOnStartup.setSelected(getModelObject().isMoveOnStartup());
		checkBoxMoveOnStartup.setName("checkBoxMoveOnStartup");
		checkBoxMoveOnStartup.addActionListener(this::onChangeCheckboxMoveOnStartup);

		cmbVariableX.setName("cmbVariableX");
		cmbVariableX.addActionListener(this::onChangeCmbVariableX);

		cmbVariableY.setName("cmbVariableY");
		cmbVariableY.addActionListener(this::onChangeCmbVariableY);

		txtIntervalOfSeconds.setText(getModelObject().getIntervalOfSeconds() != null
			? getModelObject().getIntervalOfSeconds().toString()
			: "180");
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
				SilentMouseApplicationFrame.getInstance().getApplicationPreferences().put(
					SettingsModelBean.INTERVAL_OF_SECONDS,
					getModelObject().getIntervalOfSeconds().toString());
			}
		});

		txtIntervalOfMouseMovementsCheckInSeconds
			.setText(getModelObject().getIntervalOfMouseMovementsCheckInSeconds() != null
				? getModelObject().getIntervalOfMouseMovementsCheckInSeconds().toString()
				: "90");
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
				SilentMouseApplicationFrame.getInstance().getApplicationPreferences().put(
					SettingsModelBean.INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS,
					getModelObject().getIntervalOfMouseMovementsCheckInSeconds().toString());
			}
		});
	}

	/**
	 * Action handler for the checkbox to update the move-on-startup setting
	 *
	 * @param actionEvent
	 *            the action event triggered by checkbox interaction
	 */
	protected void onChangeCheckboxMoveOnStartup(final ActionEvent actionEvent)
	{
		JMCheckBox source = (JMCheckBox)actionEvent.getSource();
		Boolean moveOnStartup = source.getPropertyModel().getObject();
		getModelObject().setMoveOnStartup(moveOnStartup);
		String moveOnStartupAsString = Boolean.toString(getModelObject().isMoveOnStartup());
		SilentMouseApplicationFrame.getInstance().getApplicationPreferences()
			.put(SettingsModelBean.MOVE_ON_STARTUP, moveOnStartupAsString);
	}

	/**
	 * Action handler for the combo box controlling movement on the Y axis
	 *
	 * @param actionEvent
	 *            the action event triggered by combo box interaction
	 */
	@SuppressWarnings("unchecked")
	protected void onChangeCmbVariableY(final ActionEvent actionEvent)
	{
		JMComboBox<Integer, GenericComboBoxModel<Integer>> source = (JMComboBox<Integer, GenericComboBoxModel<Integer>>)actionEvent
			.getSource();
		final Object selectedItem = source.getModel().getSelectedItem();
		getModelObject().setYAxis(Integer.valueOf(selectedItem.toString()));
		SilentMouseApplicationFrame.getInstance().getApplicationPreferences()
			.put(SettingsModelBean.Y_AXIS, getModelObject().getYAxis().toString());
	}

	/**
	 * Action handler for the combo box controlling movement on the X axis
	 *
	 * @param actionEvent
	 *            the action event triggered by combo box interaction
	 */
	@SuppressWarnings("unchecked")
	protected void onChangeCmbVariableX(final ActionEvent actionEvent)
	{
		JMComboBox<Integer, GenericComboBoxModel<Integer>> source = (JMComboBox<Integer, GenericComboBoxModel<Integer>>)actionEvent
			.getSource();
		final Object selectedItem = source.getModel().getSelectedItem();
		getModelObject().setXAxis(Integer.valueOf(selectedItem.toString()));
		SilentMouseApplicationFrame.getInstance().getApplicationPreferences()
			.put(SettingsModelBean.X_AXIS, getModelObject().getXAxis().toString());
	}

	/**
	 * Action handler for the text field controlling the interval of mouse movements
	 *
	 * @param actionEvent
	 *            the action event triggered by text field interaction
	 */
	protected void onChangeTxtIntervalOfSeconds(final ActionEvent actionEvent)
	{
		getJMTextFieldModel(actionEvent).ifPresent(propertyModel -> {
			getModelObject().setIntervalOfSeconds(Integer.valueOf(propertyModel.getObject()));
			SilentMouseApplicationFrame.getInstance().getApplicationPreferences().put(
				SettingsModelBean.INTERVAL_OF_SECONDS,
				getModelObject().getIntervalOfSeconds().toString());
		});
	}

	/**
	 * Action handler for the text field controlling the interval of checking mouse movements
	 *
	 * @param actionEvent
	 *            the action event triggered by text field interaction
	 */
	protected void onChangeTxtIntervalOfMouseMovementsCheckInSeconds(final ActionEvent actionEvent)
	{
		getJMTextFieldModel(actionEvent).ifPresent(propertyModel -> {
			getModelObject().setIntervalOfMouseMovementsCheckInSeconds(
				Integer.valueOf(propertyModel.getObject()));
			SilentMouseApplicationFrame.getInstance().getApplicationPreferences().put(
				SettingsModelBean.INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS,
				getModelObject().getIntervalOfMouseMovementsCheckInSeconds().toString());
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onInitializeLayout()
	{
		this.onInitializeMigLayout();
	}

	/**
	 * Initializes the layout of the panel using MigLayout
	 */
	protected void onInitializeMigLayout()
	{
		final MigLayout layout = new MigLayout();
		this.setLayout(layout);
		this.add(lblSettings, "wrap");

		this.add(checkBoxMoveOnStartup, "wrap");
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
