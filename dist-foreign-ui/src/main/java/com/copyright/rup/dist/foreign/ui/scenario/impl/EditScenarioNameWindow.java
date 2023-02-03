package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
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
 * Modal window for editing {@link Scenario} name.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 01/22/21
 *
 * @author Ihar Suvorau
 */
class EditScenarioNameWindow extends Window {

    private final Scenario scenario;
    private final ICommonScenariosController controller;
    private final Binder<String> binder = new Binder<>();
    private TextField scenarioNameField;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonScenariosController}
     * @param scenario   selected {@link Scenario}
     */
    EditScenarioNameWindow(ICommonScenariosController controller, Scenario scenario) {
        this.controller = controller;
        this.scenario = scenario;
        setResizable(false);
        setWidth(320, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.edit_scenario_name"));
        initScenarioNameField();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(scenarioNameField, buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        scenarioNameField.focus();
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "edit-scenario-name-window");
    }

    private HorizontalLayout initButtonsLayout() {
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(listener -> onSaveButtonClicked());
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        HorizontalLayout layout = new HorizontalLayout(saveButton, Buttons.createCancelButton(this));
        layout.setSpacing(true);
        return layout;
    }

    private void initScenarioNameField() {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        scenarioNameField.setRequiredIndicatorVisible(true);
        scenarioNameField.setValue(scenario.getName());
        binder.forField(scenarioNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.scenarioExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
    }

    private boolean isValid() {
        return binder.isValid();
    }

    private void onSaveButtonClicked() {
        if (isValid()) {
            controller.editScenarioName(scenario.getId(), scenarioNameField.getValue());
            controller.getWidget().refresh();
            close();
        } else {
            Windows.showValidationErrorWindow(Set.of(scenarioNameField));
        }
    }
}
