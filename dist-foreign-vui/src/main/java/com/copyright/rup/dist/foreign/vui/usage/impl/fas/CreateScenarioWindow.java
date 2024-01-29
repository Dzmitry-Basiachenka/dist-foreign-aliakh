package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

/**
 * Modal window that provides functionality for creating {@link Scenario}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/14/2017
 *
 * @author Mikalai Bezmen
 */
class CreateScenarioWindow extends CommonDialog {

    private static final long serialVersionUID = -4515391344997139909L;

    private final ICommonUsageController controller;
    private final Binder<Scenario> binder = new Binder<>();
    private TextField scenarioNameField;
    private TextArea descriptionArea;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonUsageController}
     */
    CreateScenarioWindow(ICommonUsageController controller) {
        this.controller = controller;
        super.setWidth("400px");
        super.setHeaderTitle(ForeignUi.getMessage("window.create_scenario"));
        super.add(initContent());
        super.getFooter().add(initButtonsLayout());
        setModalWindowProperties("create-scenario-window", false);
    }

    private HorizontalLayout initButtonsLayout() {
        var confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(listener -> onConfirmButtonClicked());
        VaadinUtils.setButtonsAutoDisabled(confirmButton);
        return new HorizontalLayout(confirmButton, Buttons.createCancelButton(this));
    }

    private VerticalLayout initContent() {
        initScenarioNameField();
        initDescriptionArea();
        scenarioNameField.focus();
        var content = new VerticalLayout(scenarioNameField, descriptionArea);
        content.setSpacing(false);
        content.setSizeFull();
        return content;
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
        scenarioNameField.setRequiredIndicatorVisible(true);
        binder.forField(scenarioNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.scenarioExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .bind(Scenario::getName, Scenario::setName);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
        scenarioNameField.setValue(
            ForeignUi.getMessage("field.scenario_name.default", controller.getSelectedProductFamily(),
                CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
    }

    private void onConfirmButtonClicked() {
        if (binder.isValid()) {
            fireEvent(new ScenarioCreateEvent(this,
                controller.createScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()),
                    StringUtils.trimToEmpty(descriptionArea.getValue()))));
            close();
        } else {
            Windows.showValidationErrorWindow();
        }
    }
}
