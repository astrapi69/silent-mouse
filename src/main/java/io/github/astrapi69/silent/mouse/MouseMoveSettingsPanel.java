/**
 * The MIT License
 *
 * Copyright (C) 2022 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.silent.mouse;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Optional;
import java.util.prefs.Preferences;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import io.github.astrapi69.collection.array.ArrayFactory;
import io.github.astrapi69.collection.pair.ValueBox;
import io.github.astrapi69.model.BaseModel;
import io.github.astrapi69.model.LambdaModel;
import io.github.astrapi69.model.api.IModel;
import io.github.astrapi69.model.check.CheckedModel;
import io.github.astrapi69.swing.base.BasePanel;
import io.github.astrapi69.swing.component.JMCheckBox;
import io.github.astrapi69.swing.component.JMComboBox;
import io.github.astrapi69.swing.component.JMTextField;
import io.github.astrapi69.swing.document.NumberValuesDocument;
import io.github.astrapi69.swing.model.combobox.GenericComboBoxModel;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

@Getter
public class MouseMoveSettingsPanel extends BasePanel<SettingsModelBean>
{
	public static final String NOT_SET = "not set";
	private static final Preferences applicationPreferences = Preferences.userRoot()
		.node(PureSwingSystemTray.class.getName());
	private JMComboBox<Integer, GenericComboBoxModel<Integer>> cmbVariableX;
	private JMComboBox<Integer, GenericComboBoxModel<Integer>> cmbVariableY;
	private JLabel lblIntervalOfSeconds;
	private JLabel lblIntervalOfMouseMovementsCheckInSeconds;
	private JLabel lblSettings;
	private JLabel lblVariableX;
	private JLabel lblVariableY;
	private JMTextField txtIntervalOfSeconds;
	private JMTextField txtIntervalOfMouseMovementsCheckInSeconds;
	private JMCheckBox checkBoxMoveOnStartup;

	public MouseMoveSettingsPanel(final IModel<SettingsModelBean> model)
	{
		super(model);
	}

	public static Preferences getApplicationPreferences()
	{
		return applicationPreferences;
	}

	@NotNull
	private static Optional<IModel<String>> getJMTextFieldModel(ActionEvent actionEvent)
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

	private void initializeModelWithPreferences()
	{
		SettingsModelBean modelObject = getModelObject();
		if (modelObject == null)
		{
			modelObject = SettingsModelBean.builder().intervalOfSeconds(180)
				.intervalOfMouseMovementsCheckInSeconds(90).xAxis(1).yAxis(1).moveOnStartup(false)
				.build();
			setModel(BaseModel.of(modelObject));
		}
		SettingsModelBean.setModelFromPreferences(modelObject, applicationPreferences);
	}

	@Override
	protected void onInitializeComponents()
	{
		initializeModelWithPreferences();
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
		final CheckedModel checkedModelBean;
		checkedModelBean = CheckedModel.builder().build();
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
		String interalOfSecondsAsString = getModelObject().getIntervalOfSeconds() != null
			? getModelObject().getIntervalOfSeconds().toString()
			: "180";
		txtIntervalOfSeconds.setText(interalOfSecondsAsString);
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

				getApplicationPreferences().put(SettingsModelBean.INTERVAL_OF_SECONDS,
					getModelObject().getIntervalOfSeconds().toString());
			}
		});
		String intervalOfMouseMovementsCheckInSecondsAsString = getModelObject()
			.getIntervalOfMouseMovementsCheckInSeconds() != null
				? getModelObject().getIntervalOfMouseMovementsCheckInSeconds().toString()
				: "90";
		txtIntervalOfMouseMovementsCheckInSeconds
			.setText(intervalOfMouseMovementsCheckInSecondsAsString);
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
				getApplicationPreferences().put(
					SettingsModelBean.INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS,
					getModelObject().getIntervalOfMouseMovementsCheckInSeconds().toString());
			}
		});
	}

	protected void onChangeCheckboxMoveOnStartup(final ActionEvent actionEvent)
	{
		JMCheckBox source = (JMCheckBox)actionEvent.getSource();
		Boolean moveOnStartup = source.getPropertyModel().getObject();
		getModelObject().setMoveOnStartup(moveOnStartup);
		String moveOnStartupAsString = Boolean.toString(getModelObject().isMoveOnStartup());
		getApplicationPreferences().put(SettingsModelBean.MOVE_ON_STARTUP, moveOnStartupAsString);
		String foo = getApplicationPreferences().get(SettingsModelBean.MOVE_ON_STARTUP, "foo");
		System.out.println(foo);
	}

	@SuppressWarnings("unchecked")
	protected void onChangeCmbVariableY(final ActionEvent actionEvent)
	{
		JMComboBox<Integer, GenericComboBoxModel<Integer>> source = (JMComboBox<Integer, GenericComboBoxModel<Integer>>)actionEvent
			.getSource();
		final Object selectedItem = source.getModel().getSelectedItem();
		getModelObject().setYAxis(Integer.valueOf(selectedItem.toString()));
		getApplicationPreferences().put(SettingsModelBean.Y_AXIS,
			getModelObject().getYAxis().toString());
	}

	@SuppressWarnings("unchecked")
	protected void onChangeCmbVariableX(final ActionEvent actionEvent)
	{
		JMComboBox<Integer, GenericComboBoxModel<Integer>> source = (JMComboBox<Integer, GenericComboBoxModel<Integer>>)actionEvent
			.getSource();
		final Object selectedItem = source.getModel().getSelectedItem();
		getModelObject().setXAxis(Integer.valueOf(selectedItem.toString()));
		getApplicationPreferences().put(SettingsModelBean.X_AXIS,
			getModelObject().getXAxis().toString());
	}

	protected void onChangeTxtIntervalOfSeconds(final ActionEvent actionEvent)
	{
		getJMTextFieldModel(actionEvent).ifPresent(propertyModel -> {
			getModelObject().setIntervalOfSeconds(Integer.valueOf(propertyModel.getObject()));
			getApplicationPreferences().put(SettingsModelBean.INTERVAL_OF_SECONDS,
				getModelObject().getIntervalOfSeconds().toString());
		});
	}

	protected void onChangeTxtIntervalOfMouseMovementsCheckInSeconds(final ActionEvent actionEvent)
	{
		getJMTextFieldModel(actionEvent).ifPresent(propertyModel -> {
			getModelObject().setIntervalOfMouseMovementsCheckInSeconds(
				Integer.valueOf(propertyModel.getObject()));
			getApplicationPreferences().put(
				SettingsModelBean.INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS,
				getModelObject().getIntervalOfMouseMovementsCheckInSeconds().toString());
		});
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
