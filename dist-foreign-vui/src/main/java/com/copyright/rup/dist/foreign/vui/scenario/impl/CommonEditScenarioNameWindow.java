package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

/**
 * Common window for editing scenario name.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 12/21/2023
 *
 * @author Dzmitry Basiachenka
 */
public abstract class CommonEditScenarioNameWindow extends CommonDialog {

    private static final long serialVersionUID = 2640928797386096029L;
    private static final int MAX_LENGTH = 50;

    private final Binder<String> binder = new Binder<>();
    private TextField scenarioNameField;

    /**
     * Constructor.
     *
     * @param scenarioName scenario name
     * @param styleName    window style name
     */
    protected CommonEditScenarioNameWindow(String scenarioName, String styleName) {
        super.setResizable(false);
        super.setWidth("400px");
        super.setHeaderTitle(ForeignUi.getMessage("window.edit_scenario_name"));
        super.add(initContent(scenarioName));
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties(styleName, false);
    }

    /**
     * Validates scenario name field.
     */
    protected void validateBinder() {
        binder.validate();
    }

    /**
     * Checks whether scenario with specified name already exists in database.
     *
     * @param scenarioName name of scenario
     * @return {@code true} if scenario} with specified name already exists in database,
     * {@code false} - if doesn't
     */
    protected abstract boolean isScenarioExists(String scenarioName);

    /**
     * Updates name for selected scenario.
     *
     * @param scenarioName new scenario name
     */
    protected abstract void updateScenarioName(String scenarioName);

    private HorizontalLayout initButtonsLayout() {
        var saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(listener -> onSaveButtonClicked());
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        var layout = new HorizontalLayout(saveButton, Buttons.createCancelButton(this));
        layout.setSpacing(true);
        return layout;
    }

    private VerticalLayout initContent(String scenarioName) {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        scenarioNameField.setRequiredIndicatorVisible(true);
        scenarioNameField.setValue(scenarioName);
        scenarioNameField.focus();
        binder.forField(scenarioNameField)
            .withValidator(new RequiredValidator())
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", MAX_LENGTH), 0, MAX_LENGTH))
            .withValidator(value -> !isScenarioExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
        return new VerticalLayout(scenarioNameField);
    }

    private void onSaveButtonClicked() {
        if (binder.isValid()) {
            updateScenarioName(scenarioNameField.getValue().trim());
            close();
        } else {
            Windows.showValidationErrorWindow();
        }
    }
}
