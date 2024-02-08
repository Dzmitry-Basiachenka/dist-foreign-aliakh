package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.common.validator.AmountRangeValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredNumberValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
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
class CreateNtsScenarioWindow extends CommonDialog {

    private static final long serialVersionUID = 931915979919885932L;

    private final INtsUsageController controller;
    private final Binder<Scenario> scenarioBinder = new Binder<>();
    private final Binder<FundPool> fundBinder = new Binder<>();

    private TextField scenarioNameField;
    private BigDecimalField rhMinimumAmountField;
    private BigDecimalField preServiceFeeAmountField;
    private BigDecimalField postServiceFeeAmountField;
    private ComboBox<FundPool> fundsComboBox;
    private TextArea descriptionArea;

    /**
     * Constructor.
     *
     * @param controller instance of {@link INtsUsageController}
     */
    CreateNtsScenarioWindow(INtsUsageController controller) {
        this.controller = controller;
        super.setWidth("320px");
        super.setHeaderTitle(ForeignUi.getMessage("window.create_scenario"));
        super.add(initContent());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("create-scenario-window", false);
    }

    private VerticalLayout initContent() {
        initScenarioNameField();
        initRhMinimumAmountField();
        initPreServiceFeeAmountField();
        initPostServiceFeeAmountField();
        initPreServiceFeeFundsCombobox();
        initDescriptionArea();
        scenarioNameField.focus();
        var content = new VerticalLayout(scenarioNameField, rhMinimumAmountField, preServiceFeeAmountField,
            postServiceFeeAmountField, fundsComboBox, descriptionArea);
        content.setSpacing(false);
        content.setSizeFull();
        return content;
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
        scenarioNameField.setWidthFull();
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
        scenarioNameField.setValue(
            ForeignUi.getMessage("field.scenario_name.default", controller.getSelectedProductFamily(),
                CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
    }

    private void initRhMinimumAmountField() {
        rhMinimumAmountField = new BigDecimalField(ForeignUi.getMessage("field.rh_minimum_amount"));
        rhMinimumAmountField.setValue(new BigDecimal("300"));
        rhMinimumAmountField.setPrefixComponent(VaadinIcon.DOLLAR.create());
        rhMinimumAmountField.setRequiredIndicatorVisible(true);
        rhMinimumAmountField.setWidthFull();
        scenarioBinder.forField(rhMinimumAmountField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(AmountRangeValidator.zeroAmountValidator())
            .bind(scenario -> scenario.getNtsFields().getRhMinimumAmount(),
                (Setter<Scenario, BigDecimal>) (scenario, rhMinimumAmount) ->
                    scenario.getNtsFields().setRhMinimumAmount(rhMinimumAmount));
        VaadinUtils.addComponentStyle(rhMinimumAmountField, "rh-minimum-amount-field");
    }

    private void initPreServiceFeeAmountField() {
        preServiceFeeAmountField = new BigDecimalField(ForeignUi.getMessage("field.pre_service_fee_amount"));
        initFundAmountField(preServiceFeeAmountField, "pre-service-fee-amount-field",
            (ValueProvider<FundPool, BigDecimal>) FundPool::getTotalAmount,
            (Setter<FundPool, BigDecimal>) FundPool::setTotalAmount);
    }

    private void initPostServiceFeeAmountField() {
        postServiceFeeAmountField = new BigDecimalField(ForeignUi.getMessage("field.post_service_fee_amount"));
        initFundAmountField(postServiceFeeAmountField, "post-service-fee-amount-field",
            (ValueProvider<FundPool, BigDecimal>) FundPool::getTotalAmount,
            (Setter<FundPool, BigDecimal>) FundPool::setTotalAmount);
    }

    private void initFundAmountField(BigDecimalField amountField, String fieldId,
                                     ValueProvider<FundPool, BigDecimal> getter,
                                     Setter<FundPool, BigDecimal> setter) {
        amountField.setValue(BigDecimal.ZERO);
        amountField.setPrefixComponent(VaadinIcon.DOLLAR.create());
        amountField.setRequiredIndicatorVisible(true);
        amountField.setWidthFull();
        fundBinder.forField(amountField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(AmountRangeValidator.zeroAmountValidator())
            .bind(getter, setter);
        VaadinUtils.addComponentStyle(amountField, fieldId);
    }

    private void initPreServiceFeeFundsCombobox() {
        fundsComboBox = new ComboBox<>(ForeignUi.getMessage("label.pre_service_fee_funds"));
        fundsComboBox.setItems(controller.getAdditionalFundsNotAttachedToScenario());
        fundsComboBox.setItemLabelGenerator(FundPool::getName);
        fundsComboBox.setWidthFull();
        VaadinUtils.addComponentStyle(fundsComboBox, "pre-service-fee-funds-filter");
    }

    private void initDescriptionArea() {
        descriptionArea = new TextArea(ForeignUi.getMessage("field.description"));
        scenarioBinder.forField(descriptionArea)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(Scenario::getDescription, Scenario::setDescription);
        descriptionArea.setWidthFull();
        VaadinUtils.addComponentStyle(descriptionArea, "scenario-description");
    }

    private HorizontalLayout initButtonsLayout() {
        var confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(listener -> onConfirmButtonClicked());
        VaadinUtils.setButtonsAutoDisabled(confirmButton);
        return new HorizontalLayout(confirmButton, Buttons.createCancelButton(this));
    }

    private void onConfirmButtonClicked() {
        if (scenarioBinder.isValid() && fundBinder.isValid()) {
            var ntsFields = new Scenario.NtsFields();
            ntsFields.setRhMinimumAmount(rhMinimumAmountField.getValue());
            ntsFields.setPreServiceFeeAmount(preServiceFeeAmountField.getValue());
            ntsFields.setPostServiceFeeAmount(postServiceFeeAmountField.getValue());
            var selectedFund = fundsComboBox.getValue();
            if (Objects.nonNull(selectedFund)) {
                ntsFields.setPreServiceFeeFundId(selectedFund.getId());
                ntsFields.setPreServiceFeeFundName(selectedFund.getName());
            }
            fireEvent(new ScenarioCreateEvent(this,
                controller.createNtsScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()),
                    ntsFields, StringUtils.trimToEmpty(descriptionArea.getValue()))));
            close();
        } else {
            Windows.showValidationErrorWindow();
        }
    }
}
