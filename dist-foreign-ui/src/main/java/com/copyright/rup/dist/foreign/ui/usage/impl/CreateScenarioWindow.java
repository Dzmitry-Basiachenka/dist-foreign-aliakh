package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.ui.component.validator.ScenarioNameUniqueValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;

import com.google.common.collect.Lists;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Modal window that provides functionality for creating {@link com.copyright.rup.dist.foreign.domain.Scenario}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/14/17
 *
 * @author Mikalai Bezmen
 */
public class CreateScenarioWindow extends Window {

    private TextField scenarioNameField;
    private TextArea descriptionArea;
    private IUsagesController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUsagesController}
     */
    CreateScenarioWindow(IUsagesController controller) {
        this.controller = controller;
        setResizable(false);
        setWidth(320, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_scenario"));
        initFields();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(scenarioNameField, descriptionArea, buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        scenarioNameField.focus();
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "create-scenario-window");
    }

    private HorizontalLayout initButtonsLayout() {
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(listener -> onConfirmButtonClicked());
        HorizontalLayout layout = new HorizontalLayout(confirmButton, Buttons.createCancelButton(this));
        layout.setSpacing(true);
        return layout;
    }

    private void initFields() {
        initScenarioNameField();
        initDescriptionArea();
    }

    private void initDescriptionArea() {
        descriptionArea = new TextArea(ForeignUi.getMessage("field.description"));
        descriptionArea.setValidationVisible(false);
        VaadinUtils.setMaxComponentsWidth(descriptionArea);
        descriptionArea.addValidator(
            new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000, false));
        VaadinUtils.addComponentStyle(descriptionArea, "scenario-description");
    }

    private void initScenarioNameField() {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        scenarioNameField.setValidationVisible(false);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
        scenarioNameField.setNullRepresentation(StringUtils.EMPTY);
        scenarioNameField.setValue(ForeignUi.getMessage("field.scenario_name.default", LocalDate.now().format(
            DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT))));
        scenarioNameField.setRequired(true);
        scenarioNameField.setRequiredError(ForeignUi.getMessage("field.error.empty"));
        scenarioNameField.addValidator(
            new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50, false));
        scenarioNameField.addValidator(new ScenarioNameUniqueValidator(controller.getScenarioService()));
    }

    private boolean isValid() {
        return scenarioNameField.isValid() && descriptionArea.isValid();
    }

    private void onConfirmButtonClicked() {
        if (isValid()) {
            String scenarioId = controller.createScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()),
                StringUtils.trimToEmpty(descriptionArea.getValue()));
            fireEvent(new ScenarioCreateEvent(this, scenarioId));
            close();
        } else {
            Windows.showValidationErrorWindow(Lists.newArrayList(scenarioNameField, descriptionArea));
        }
    }

    /**
     * Event that is thrown when new {@link com.copyright.rup.dist.foreign.domain.Scenario} is created.
     */
    public static class ScenarioCreateEvent extends Event {

        private String scenarioId;

        /**
         * Constructor.
         *
         * @param source     event source
         * @param scenarioId created scenarios id
         */
        private ScenarioCreateEvent(Component source, String scenarioId) {
            super(source);
            this.scenarioId = scenarioId;
        }

        /**
         * @return scenario id.
         */
        public String getScenarioId() {
            return scenarioId;
        }
    }
}
