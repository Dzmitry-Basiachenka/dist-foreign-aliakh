package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * Common window for editing scenario name.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 12/21/2023
 *
 * @author Dzmitry Basiachenka
 */
public abstract class CommonEditScenarioNameWindow extends Window {

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
        super.setWidth(320, Unit.PIXELS);
        super.setCaption(ForeignUi.getMessage("window.edit_scenario_name"));
        initScenarioNameField(scenarioName);
        var buttonsLayout = initButtonsLayout();
        var layout = new VerticalLayout(scenarioNameField, buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        scenarioNameField.focus();
        super.setContent(layout);
        VaadinUtils.addComponentStyle(this, styleName);
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
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(listener -> onSaveButtonClicked());
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        HorizontalLayout layout = new HorizontalLayout(saveButton, Buttons.createCancelButton(this));
        layout.setSpacing(true);
        return layout;
    }

    private void initScenarioNameField(String scenarioName) {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        scenarioNameField.setRequiredIndicatorVisible(true);
        scenarioNameField.setValue(scenarioName);
        binder.forField(scenarioNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !isScenarioExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
    }

    private void onSaveButtonClicked() {
        if (binder.isValid()) {
            updateScenarioName(scenarioNameField.getValue().trim());
            close();
        } else {
            Windows.showValidationErrorWindow(Set.of(scenarioNameField));
        }
    }
}
