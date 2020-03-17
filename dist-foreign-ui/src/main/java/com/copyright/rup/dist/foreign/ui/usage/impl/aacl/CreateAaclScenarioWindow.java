package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Window to create AACL scenario.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/2020
 *
 * @author Stanislau Rudak
 */
class CreateAaclScenarioWindow extends Window {

    private final IAaclUsageController controller;
    private final Binder<Scenario> scenarioBinder = new Binder<>();
    private final Binder<AtomicReference<FundPool>> fundPoolBinder = new Binder<>();
    private TextField scenarioNameField;
    private TextField titleCutoffAmountField;
    private ComboBox<FundPool> fundPoolComboBox;
    private AaclScenarioParameterWidget<List<UsageAge>> usageAgeWeightWidget;
    private AaclScenarioParameterWidget<List<PublicationType>> publicationTypeWeightWidget;
    private AaclScenarioParameterWidget<List<DetailLicenseeClass>> licenseeClassMappingWidget;
    private TextArea descriptionArea;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAaclUsageController}
     */
    CreateAaclScenarioWindow(IAaclUsageController controller) {
        this.controller = controller;
        setResizable(false);
        setWidth(320, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_scenario"));
        initFields();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout =
            new VerticalLayout(scenarioNameField, titleCutoffAmountField, fundPoolComboBox, usageAgeWeightWidget,
                publicationTypeWeightWidget, licenseeClassMappingWidget, descriptionArea, buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        scenarioNameField.focus();
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "create-scenario-window");
        scenarioBinder.validate();
        fundPoolBinder.validate();
    }

    private void initFields() {
        initScenarioNameField();
        initTitleCutoffAmountField();
        initFundPoolCombobox();
        initUsageAgeWeightsWidget();
        initPubTypeWeightsWidget();
        initLicenseeClassesMappingWidget();
        initDescriptionArea();
    }

    private void initScenarioNameField() {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        scenarioNameField.setValue(
            ForeignUi.getMessage("field.scenario_name.default", controller.getSelectedProductFamily(),
                CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
        scenarioNameField.setRequiredIndicatorVisible(true);
        scenarioBinder.forField(scenarioNameField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.scenarioExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .bind(Scenario::getName, Scenario::setName);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
    }

    private void initTitleCutoffAmountField() {
        titleCutoffAmountField = new TextField(ForeignUi.getMessage("field.title_cutoff_amount"));
        titleCutoffAmountField.setValue("50");
        titleCutoffAmountField.setRequiredIndicatorVisible(true);
        scenarioBinder.forField(titleCutoffAmountField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withConverter(new StringToBigDecimalConverter(ForeignUi.getMessage("field.error.not_numeric")))
            .withValidator(value -> new AmountValidator(true).isValid(value.toString()),
                ForeignUi.getMessage("field.error.positive_number_or_zero_and_length", 10))
            .bind(scenario -> scenario.getAaclFields().getTitleCutoffAmount(),
                (Setter<Scenario, BigDecimal>) (scenario, cutoffAmount) ->
                    scenario.getAaclFields().setTitleCutoffAmount(cutoffAmount));
        VaadinUtils.setMaxComponentsWidth(titleCutoffAmountField);
        VaadinUtils.addComponentStyle(titleCutoffAmountField, "title-cutoff-amount-field");
    }

    private void initFundPoolCombobox() {
        fundPoolComboBox = new ComboBox<>(ForeignUi.getMessage("label.fund_pool"));
        fundPoolComboBox.setItems(controller.getFundPools());
        fundPoolComboBox.setItemCaptionGenerator(FundPool::getName);
        fundPoolComboBox.setRequiredIndicatorVisible(true);
        fundPoolBinder.forField(fundPoolComboBox)
            .asRequired(ForeignUi.getMessage("field.error.fund_pool.empty"))
            .bind(AtomicReference::get, AtomicReference::set);
        VaadinUtils.setMaxComponentsWidth(fundPoolComboBox);
        VaadinUtils.addComponentStyle(fundPoolComboBox, "fund-pools-filter");
    }

    private void initUsageAgeWeightsWidget() {
        usageAgeWeightWidget =
            new AaclScenarioParameterWidget<>(ForeignUi.getMessage("button.usage_age_weights"),
                controller::getUsageAges, AaclUsageAgeWeightWindow::new);
    }

    private void initPubTypeWeightsWidget() {
        publicationTypeWeightWidget =
            new AaclScenarioParameterWidget<>(ForeignUi.getMessage("button.publication_type_weights"),
                controller::getPublicationTypes, PublicationTypeWeightsWindow::new);
    }

    private void initLicenseeClassesMappingWidget() {
        licenseeClassMappingWidget =
            new AaclScenarioParameterWidget<>(ForeignUi.getMessage("button.licensee_class_mapping"),
                controller::getDetailLicenseeClasses, AggregateLicenseeClassMappingWindow::new);
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
        // TODO {srudak} validate licensee class mapping
        if (scenarioBinder.isValid() && fundPoolBinder.isValid()) {
            AaclFields aaclFields = new AaclFields();
            fundPoolComboBox.getSelectedItem().ifPresent(fundPool -> aaclFields.setFundPoolId(fundPool.getId()));
            aaclFields.setPublicationTypes(publicationTypeWeightWidget.getAppliedParameters());
            // TODO {srudak} create scenario and fire ScenarioCreatedEvent
            controller.createAaclScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()), aaclFields,
                StringUtils.trimToEmpty(descriptionArea.getValue()));
            close();
        } else {
            Windows.showValidationErrorWindow(
                Arrays.asList(scenarioNameField, titleCutoffAmountField, fundPoolComboBox, descriptionArea));
        }
    }
}
