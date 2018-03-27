package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
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
 * Modal window that provides functionality for creating {@link Scenario}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/14/17
 *
 * @author Mikalai Bezmen
 */
public class CreateScenarioWindow extends Window {

    private final IUsagesController controller;
    private final Binder<Scenario> binder = new Binder<>();
    private TextField scenarioNameField;
    private TextArea descriptionArea;

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
        binder.forField(descriptionArea)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(Scenario::getDescription, Scenario::setDescription);
        VaadinUtils.setMaxComponentsWidth(descriptionArea);
        VaadinUtils.addComponentStyle(descriptionArea, "scenario-description");
    }

    private void initScenarioNameField() {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        binder.forField(scenarioNameField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.getScenarioService().scenarioExists(value),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .bind(Scenario::getName, Scenario::setName);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
        scenarioNameField.setValue(
            ForeignUi.getMessage("field.scenario_name.default", controller.getSelectedProductFamily(),
                LocalDate.now().format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT))));
    }

    private boolean isValid() {
        return binder.isValid();
    }

    private void onConfirmButtonClicked() {
        if (isValid()) {
            fireEvent(new ScenarioCreateEvent(this,
                controller.createScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()),
                    StringUtils.trimToEmpty(descriptionArea.getValue()))));
            close();
        } else {
            Windows.showValidationErrorWindow(Lists.newArrayList(scenarioNameField, descriptionArea));
        }
    }

    /**
     * Event that is thrown when new {@link Scenario} is created.
     */
    public static class ScenarioCreateEvent extends Event {

        private final Scenario scenario;

        /**
         * Constructor.
         *
         * @param source   event source
         * @param scenario created {@link Scenario}
         */
        ScenarioCreateEvent(Component source, Scenario scenario) {
            super(source);
            this.scenario = scenario;
        }

        /**
         * @return {@link Scenario}.
         */
        public Scenario getScenarioId() {
            return scenario;
        }
    }
}
