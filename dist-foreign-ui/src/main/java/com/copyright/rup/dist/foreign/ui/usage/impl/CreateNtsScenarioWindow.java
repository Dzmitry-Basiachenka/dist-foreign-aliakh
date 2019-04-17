package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Setter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Window to create NTS scenario.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/15/2019
 *
 * @author Aliaksandr Liakh
 */
public class CreateNtsScenarioWindow extends Window {

    private final IUsagesController controller;
    private final Binder<Scenario> binder = new Binder<>();
    private TextField scenarioNameField;
    private TextField rhMinimumAmountField;
    private TextArea descriptionArea;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUsagesController}
     */
    CreateNtsScenarioWindow(IUsagesController controller) {
        this.controller = controller;
        setResizable(false);
        setWidth(320, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_scenario"));
        initFields();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(scenarioNameField, rhMinimumAmountField, descriptionArea,
            buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        scenarioNameField.focus();
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "create-scenario-window");
    }

    private void initFields() {
        initScenarioNameField();
        initMinimumRhAmountField();
        initDescriptionArea();
    }

    private void initScenarioNameField() {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        binder.forField(scenarioNameField)
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.getScenarioService().scenarioExists(value),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .bind(Scenario::getName, Scenario::setName);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
        scenarioNameField.setValue(
            ForeignUi.getMessage("field.scenario_name.default", controller.getSelectedProductFamily(),
                CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
    }

    private void initMinimumRhAmountField() {
        rhMinimumAmountField = new TextField(ForeignUi.getMessage("field.rh_minimum_amount"));
        rhMinimumAmountField.setRequiredIndicatorVisible(true);
        binder.forField(rhMinimumAmountField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withConverter(new StringToBigDecimalConverter(ForeignUi.getMessage("field.error.not_numeric")))
            .withValidator(value -> new AmountValidator(true).isValid(value.toString()),
                ForeignUi.getMessage("field.error.positive_number_or_zero_length", 10))
            .bind(scenario -> scenario.getNtsFields().getRhMinimumAmount(),
                (Setter<Scenario, BigDecimal>) (scenario, rhMinimumAmount) ->
                    scenario.getNtsFields().setRhMinimumAmount(rhMinimumAmount));
        rhMinimumAmountField.setValue(ForeignUi.getMessage("field.rh_minimum_amount.default"));
        VaadinUtils.setMaxComponentsWidth(rhMinimumAmountField);
    }

    private void initDescriptionArea() {
        descriptionArea = new TextArea(ForeignUi.getMessage("field.description"));
        binder.forField(descriptionArea)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(Scenario::getDescription, Scenario::setDescription);
        VaadinUtils.setMaxComponentsWidth(descriptionArea);
        VaadinUtils.addComponentStyle(descriptionArea, "scenario-description");
    }

    private HorizontalLayout initButtonsLayout() {
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(listener -> onConfirmButtonClicked());
        HorizontalLayout layout = new HorizontalLayout(confirmButton, Buttons.createCancelButton(this));
        layout.setSpacing(true);
        return layout;
    }

    private void onConfirmButtonClicked() {
        if (binder.isValid()) {
            fireEvent(new ScenarioCreateEvent(this,
                controller.createNtsScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()),
                    new BigDecimal(StringUtils.trimToEmpty(rhMinimumAmountField.getValue())),
                    StringUtils.trimToEmpty(descriptionArea.getValue()))));
            close();
        } else {
            Windows.showValidationErrorWindow(
                Lists.newArrayList(scenarioNameField, rhMinimumAmountField, descriptionArea));
        }
    }
}
