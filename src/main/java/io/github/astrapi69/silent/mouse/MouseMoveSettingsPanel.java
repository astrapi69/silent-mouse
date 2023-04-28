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
import io.github.astrapi69.model.BaseModel;
import io.github.astrapi69.model.LambdaModel;
import io.github.astrapi69.model.api.IModel;
import io.github.astrapi69.model.check.CheckedModel;
import io.github.astrapi69.swing.base.BasePanel;
import io.github.astrapi69.swing.combobox.model.GenericComboBoxModel;
import io.github.astrapi69.swing.component.JMCheckBox;
import io.github.astrapi69.swing.component.JMComboBox;
import io.github.astrapi69.swing.component.JMTextField;
import io.github.astrapi69.swing.document.NumberValuesDocument;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

@Getter
public class MouseMoveSettingsPanel extends BasePanel<SettingsModelBean>
{
	public static final String NOT_SET = "not set";
	public static final String INTERVAL_OF_SECONDS = "intervalOfSeconds";
	public static final String INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS = "intervalOfMouseMovementsCheckInSeconds";
	public static final String X_AXIS = "xAxis";
	public static final String Y_AXIS = "yAxis";
	public static final String MOVE_ON_STARTUP = "moveOnStartup";
	private static Preferences applicationPreferences = Preferences.userRoot()
		.node(PureSwingSystemTray.class.getName());
	private JMComboBox<Integer, GenericComboBoxModel<Integer>> cmbVariableX;
	private JMComboBox<Integer, GenericComboBoxModel<Integer>> cmbVariableY;
	private JLabel lblIntervalOfSeconds;
	private JLabel lblIntervalOfMouseMovementsCheckInSeconds;
	private JLabel lblSettings;
	private JLabel lblVariableX;
	private JLabel lblVariableY;
	private JLabel lblMoveOnStartup;
	private JMTextField txtIntervalOfSeconds;
	private JMTextField txtIntervalOfMouseMovementsCheckInSeconds;
	private JMCheckBox checkBoxMoveOnStartup;

	public MouseMoveSettingsPanel()
	{
		this(BaseModel.of(SettingsModelBean.builder().build()));
	}

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
			JMTextField source = (JMTextField)actionEvent.getSource();
			IModel<String> propertyModel = source.getPropertyModel();
			return Optional.of(propertyModel);
		}
		return Optional.empty();
	}

	private void initializeModelWithPreferences()
	{
		String xAxisAsString = applicationPreferences.get(X_AXIS, NOT_SET);
		if (NOT_SET.equals(xAxisAsString))
		{
			applicationPreferences.put(X_AXIS, "1");
		}
		else
		{
			getModelObject().setXAxis(Integer.valueOf(xAxisAsString));
		}

		String yAxisAsString = applicationPreferences.get(Y_AXIS, NOT_SET);
		if (NOT_SET.equals(yAxisAsString))
		{
			applicationPreferences.put(Y_AXIS, "1");
		}
		else
		{
			getModelObject().setYAxis(Integer.valueOf(yAxisAsString));
		}

		String intervalOfSecondsAsString = applicationPreferences.get(INTERVAL_OF_SECONDS, NOT_SET);
		if (NOT_SET.equals(intervalOfSecondsAsString))
		{
			applicationPreferences.put(INTERVAL_OF_SECONDS, "180");
		}
		else
		{
			getModelObject().setIntervalOfSeconds(Integer.valueOf(intervalOfSecondsAsString));
		}

		String intervalOfMouseMovementsCheckInSecondsAsString = applicationPreferences
			.get(INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS, NOT_SET);
		if (NOT_SET.equals(intervalOfMouseMovementsCheckInSecondsAsString))
		{
			applicationPreferences.put(INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS, "90");
		}
		else
		{
			getModelObject().setIntervalOfMouseMovementsCheckInSeconds(
				Integer.valueOf(intervalOfMouseMovementsCheckInSecondsAsString));
		}

		String moveOnStartupAsString = applicationPreferences.get(MOVE_ON_STARTUP, NOT_SET);
		if (NOT_SET.equals(moveOnStartupAsString))
		{
			applicationPreferences.put(MOVE_ON_STARTUP, "false");
		}
		else
		{
			Boolean moveOnStartup = Boolean.valueOf(moveOnStartupAsString);
			getModelObject().setMoveOnStartup(moveOnStartup);
		}
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
		Integer[] cmbArray = ArrayFactory.newArray(1, 2, 3, 4);
		cmbVariableX = new JMComboBox<>(
			new GenericComboBoxModel<>(ArrayFactory.newArray(1, 2, 3, 4)));
		cmbVariableY = new JMComboBox<>(
			new GenericComboBoxModel<>(ArrayFactory.newArray(1, 2, 3, 4)));
		txtIntervalOfSeconds = new JMTextField();
		txtIntervalOfSeconds.setDocument(new NumberValuesDocument());
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
		boolean selected = checkBoxMoveOnStartup.isSelected();
		checkBoxMoveOnStartup.setName("checkBoxMoveOnStartup");
		checkBoxMoveOnStartup.addActionListener(this::onChangeCheckboxMoveOnStartup);

		cmbVariableX.setModel(new DefaultComboBoxModel<>(new Integer[] { 1, 2, 3, 4 }));
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
			}
		});
	}

	protected void onChangeCheckboxMoveOnStartup(final ActionEvent actionEvent)
	{
		JMCheckBox source = (JMCheckBox)actionEvent.getSource();
		Boolean moveOnStartup = source.getPropertyModel().getObject();
		getModelObject().setMoveOnStartup(moveOnStartup);
		String moveOnStartupAsString = Boolean.toString(getModelObject().isMoveOnStartup());
		getApplicationPreferences().put(MOVE_ON_STARTUP, moveOnStartupAsString);
		String foo = getApplicationPreferences().get(MOVE_ON_STARTUP, "foo");
		System.out.println(foo);
	}

	protected void onChangeCmbVariableY(final ActionEvent actionEvent)
	{
		JMComboBox<Integer, GenericComboBoxModel<Integer>> source = (JMComboBox<Integer, GenericComboBoxModel<Integer>>)actionEvent
			.getSource();
		final Object selectedItem = source.getModel().getSelectedItem();
		getModelObject().setYAxis(Integer.valueOf(selectedItem.toString()));
		getApplicationPreferences().put(Y_AXIS, getModelObject().getYAxis().toString());
	}

	protected void onChangeCmbVariableX(final ActionEvent actionEvent)
	{
		JMComboBox<Integer, GenericComboBoxModel<Integer>> source = (JMComboBox<Integer, GenericComboBoxModel<Integer>>)actionEvent
			.getSource();
		final Object selectedItem = source.getModel().getSelectedItem();
		getModelObject().setXAxis(Integer.valueOf(selectedItem.toString()));
		getApplicationPreferences().put(X_AXIS, getModelObject().getXAxis().toString());
	}

	protected void onChangeTxtIntervalOfSeconds(final ActionEvent actionEvent)
	{
		getJMTextFieldModel(actionEvent).ifPresent(propertyModel -> {
			getModelObject().setIntervalOfSeconds(Integer.valueOf(propertyModel.getObject()));
			getApplicationPreferences().put(INTERVAL_OF_SECONDS,
				getModelObject().getIntervalOfSeconds().toString());
		});
	}

	protected void onChangeTxtIntervalOfMouseMovementsCheckInSeconds(final ActionEvent actionEvent)
	{
		getJMTextFieldModel(actionEvent).ifPresent(propertyModel -> {
			getModelObject().setIntervalOfMouseMovementsCheckInSeconds(
				Integer.valueOf(propertyModel.getObject()));
			getApplicationPreferences().put(INTERVAL_OF_MOUSE_MOVEMENTS_CHECK_IN_SECONDS,
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
