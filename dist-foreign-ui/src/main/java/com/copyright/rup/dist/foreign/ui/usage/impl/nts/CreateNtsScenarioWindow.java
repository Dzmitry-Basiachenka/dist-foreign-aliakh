package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Setter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Window to create NTS scenario.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/15/2019
 *
 * @author Aliaksandr Liakh
 */
class CreateNtsScenarioWindow extends Window {

    private final INtsUsageController controller;
    private final Binder<Scenario> scenarioBinder = new Binder<>();
    private final Binder<FundPool> fundBinder = new Binder<>();
    private TextField scenarioNameField;
    private TextField rhMinimumAmountField;
    private TextField preServiceFeeAmountField;
    private TextField postServiceFeeAmountField;
    private ComboBox<FundPool> fundsComboBox;
    private TextArea descriptionArea;

    /**
     * Constructor.
     *
     * @param controller instance of {@link INtsUsageController}
     */
    CreateNtsScenarioWindow(INtsUsageController controller) {
        this.controller = controller;
        setResizable(false);
        setWidth(320, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_scenario"));
        initFields();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(scenarioNameField, rhMinimumAmountField, preServiceFeeAmountField,
            postServiceFeeAmountField, fundsComboBox, descriptionArea, buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        scenarioNameField.focus();
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "create-scenario-window");
    }

    private void initFields() {
        initScenarioNameField();
        initRhMinimumAmountField();
        initPreServiceFeeAmountField();
        initPostServiceFeeAmountField();
        initPreServiceFeeFundsCombobox();
        initDescriptionArea();
    }

    private void initScenarioNameField() {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        scenarioNameField.setRequiredIndicatorVisible(true);
        scenarioBinder.forField(scenarioNameField)
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

    private void initRhMinimumAmountField() {
        rhMinimumAmountField = new TextField(ForeignUi.getMessage("field.rh_minimum_amount"));
        rhMinimumAmountField.setValue("300");
        rhMinimumAmountField.setRequiredIndicatorVisible(true);
        scenarioBinder.forField(rhMinimumAmountField)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator())
            .withConverter(new StringToBigDecimalConverter(ForeignUi.getMessage("field.error.not_numeric")))
            .bind(scenario -> scenario.getNtsFields().getRhMinimumAmount(),
                (Setter<Scenario, BigDecimal>) (scenario, rhMinimumAmount) ->
                    scenario.getNtsFields().setRhMinimumAmount(rhMinimumAmount));
        VaadinUtils.setMaxComponentsWidth(rhMinimumAmountField);
        VaadinUtils.addComponentStyle(rhMinimumAmountField, "rh-minimum-amount-field");
    }

    private void initPreServiceFeeAmountField() {
        preServiceFeeAmountField = new TextField(ForeignUi.getMessage("field.pre_service_fee_amount"));
        initFundAmountField(preServiceFeeAmountField, "pre-service-fee-amount-field",
            (ValueProvider<FundPool, BigDecimal>) FundPool::getTotalAmount,
            (Setter<FundPool, BigDecimal>) FundPool::setTotalAmount);
    }

    private void initPostServiceFeeAmountField() {
        postServiceFeeAmountField = new TextField(ForeignUi.getMessage("field.post_service_fee_amount"));
        initFundAmountField(postServiceFeeAmountField, "post-service-fee-amount-field",
            (ValueProvider<FundPool, BigDecimal>) FundPool::getTotalAmount,
            (Setter<FundPool, BigDecimal>) FundPool::setTotalAmount);
    }

    private void initFundAmountField(TextField amountField, String fieldId,
                                     ValueProvider<FundPool, BigDecimal> getter,
                                     Setter<FundPool, BigDecimal> setter) {
        amountField.setRequiredIndicatorVisible(true);
        amountField.setValue("0");
        fundBinder.forField(amountField)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator())
            .withConverter(new StringToBigDecimalConverter(ForeignUi.getMessage("field.error.not_numeric")))
            .bind(getter, setter);
        VaadinUtils.setMaxComponentsWidth(amountField);
        VaadinUtils.addComponentStyle(amountField, fieldId);
    }

    private void initPreServiceFeeFundsCombobox() {
        fundsComboBox = new ComboBox<>(ForeignUi.getMessage("label.pre_service_fee_funds"));
        fundsComboBox.setItems(controller.getAdditionalFundsNotAttachedToScenario());
        fundsComboBox.setItemCaptionGenerator(FundPool::getName);
        VaadinUtils.setMaxComponentsWidth(fundsComboBox);
        VaadinUtils.addComponentStyle(fundsComboBox, "pre-service-fee-funds-filter");
    }

    private void initDescriptionArea() {
        descriptionArea = new TextArea(ForeignUi.getMessage("field.description"));
        scenarioBinder.forField(descriptionArea)
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
        if (scenarioBinder.isValid() && fundBinder.isValid()) {
            NtsFields ntsFields = new NtsFields();
            ntsFields.setRhMinimumAmount(getAmountFromTextField(rhMinimumAmountField));
            ntsFields.setPreServiceFeeAmount(getAmountFromTextField(preServiceFeeAmountField));
            ntsFields.setPostServiceFeeAmount(getAmountFromTextField(postServiceFeeAmountField));
            FundPool selectedFund = fundsComboBox.getValue();
            if (Objects.nonNull(selectedFund)) {
                ntsFields.setPreServiceFeeFundId(selectedFund.getId());
                ntsFields.setPreServiceFeeFundName(selectedFund.getName());
            }
            fireEvent(new ScenarioCreateEvent(this,
                controller.createNtsScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()),
                    ntsFields, StringUtils.trimToEmpty(descriptionArea.getValue()))));
            close();
        } else {
            Windows.showValidationErrorWindow(
                List.of(scenarioNameField, rhMinimumAmountField, preServiceFeeAmountField,
                    postServiceFeeAmountField, descriptionArea));
        }
    }

    private BigDecimal getAmountFromTextField(TextField textField) {
        return new BigDecimal(StringUtils.trimToEmpty(textField.getValue()));
    }
}
