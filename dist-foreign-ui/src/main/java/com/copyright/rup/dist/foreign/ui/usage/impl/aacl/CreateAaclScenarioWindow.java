package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
        setWidth(300, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_scenario"));
        initFields();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout =
            new VerticalLayout(scenarioNameField, fundPoolComboBox, usageAgeWeightWidget, publicationTypeWeightWidget,
                licenseeClassMappingWidget, descriptionArea, buttonsLayout);
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
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.scenarioExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .bind(Scenario::getName, Scenario::setName);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
    }

    private void initFundPoolCombobox() {
        fundPoolComboBox = new ComboBox<>(ForeignUi.getMessage("label.fund_pool"));
        fundPoolComboBox.setItems(controller.getFundPoolsNotAttachedToScenario());
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
                controller.getUsageAges(), () -> new AaclUsageAgeWeightWindow(true));
    }

    private void initPubTypeWeightsWidget() {
        publicationTypeWeightWidget =
            new AaclScenarioParameterWidget<>(ForeignUi.getMessage("button.publication_type_weights"),
                controller.getPublicationTypes(), () -> new PublicationTypeWeightsWindow(true));
    }

    private void initLicenseeClassesMappingWidget() {
        licenseeClassMappingWidget =
            new AaclScenarioParameterWidget<>(ForeignUi.getMessage("button.licensee_class_mapping"),
                controller.getDetailLicenseeClasses(), () -> new AggregateLicenseeClassMappingWindow(true));
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
        if (scenarioBinder.isValid() && fundPoolBinder.isValid()) {
            AaclFields aaclFields = new AaclFields();
            fundPoolComboBox.getSelectedItem().ifPresent(fundPool -> aaclFields.setFundPoolId(fundPool.getId()));
            aaclFields.setUsageAges(usageAgeWeightWidget.getAppliedParameters());
            aaclFields.setPublicationTypes(publicationTypeWeightWidget.getAppliedParameters());
            aaclFields.setDetailLicenseeClasses(licenseeClassMappingWidget.getAppliedParameters());
            List<AggregateLicenseeClass> aggregateClassesWithoutUsages =
                controller.getAggregateClassesNotToBeDistributed(aaclFields.getFundPoolId(),
                    aaclFields.getDetailLicenseeClasses());
            if (CollectionUtils.isEmpty(aggregateClassesWithoutUsages)) {
                fireEvent(new ScenarioCreateEvent(this,
                    controller.createAaclScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()), aaclFields,
                        StringUtils.trimToEmpty(descriptionArea.getValue()))));
                close();
            } else {
                List<String> formattedAggregateClasses = aggregateClassesWithoutUsages.stream()
                    .map(aggregateClass -> String.format("%s (%s - %s)", aggregateClass.getId(),
                        aggregateClass.getEnrollmentProfile(), aggregateClass.getDiscipline()))
                    .collect(Collectors.toList());
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.aggregate_licensee_classes_without_usages",
                        String.join("<br><li>", formattedAggregateClasses)));
            }
        } else {
            Windows.showValidationErrorWindow(Arrays.asList(scenarioNameField, fundPoolComboBox, descriptionArea));
        }
    }
}
