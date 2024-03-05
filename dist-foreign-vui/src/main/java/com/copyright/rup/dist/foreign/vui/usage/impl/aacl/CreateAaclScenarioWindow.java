package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.AggregateLicenseeClassMappingWindow;
import com.copyright.rup.dist.foreign.vui.usage.impl.PublicationTypeWeightsWindow;
import com.copyright.rup.dist.foreign.vui.usage.impl.ScenarioParameterWidget;
import com.copyright.rup.dist.foreign.vui.usage.impl.UsageAgeWeightWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
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
class CreateAaclScenarioWindow extends CommonDialog {

    private static final long serialVersionUID = -8332553041135466984L;

    private final IAaclUsageController controller;
    private final Binder<Scenario> scenarioBinder = new Binder<>();
    private final Binder<AtomicReference<FundPool>> fundPoolBinder = new Binder<>();

    private TextField scenarioNameField;
    private ComboBox<FundPool> fundPoolComboBox;
    private ScenarioParameterWidget<List<UsageAge>> usageAgeWeightWidget;
    private ScenarioParameterWidget<List<PublicationType>> publicationTypeWeightWidget;
    private ScenarioParameterWidget<List<DetailLicenseeClass>> licenseeClassMappingWidget;
    private TextArea descriptionArea;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAaclUsageController}
     */
    CreateAaclScenarioWindow(IAaclUsageController controller) {
        this.controller = controller;
        super.setWidth("500px");
        super.setHeaderTitle(ForeignUi.getMessage("window.create_scenario"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("create-scenario-window", false);
        scenarioNameField.focus();
        scenarioBinder.validate();
        fundPoolBinder.validate();
    }

    private VerticalLayout initRootLayout() {
        var rootLayout = new VerticalLayout(initScenarioNameField(), initFundPoolComboBox(),
            initUsageAgeWeightsWidget(), initPubTypeWeightsWidget(), initLicenseeClassesMappingWidget(),
            initDescriptionArea());
        VaadinUtils.setPadding(rootLayout, 10, 10, 10, 10);
        return rootLayout;
    }

    private TextField initScenarioNameField() {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        scenarioNameField.setValue(
            ForeignUi.getMessage("field.scenario_name.default", controller.getSelectedProductFamily(),
                CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
        scenarioNameField.setRequiredIndicatorVisible(true);
        scenarioNameField.setWidthFull();
        scenarioBinder.forField(scenarioNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.scenarioExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .bind(Scenario::getName, Scenario::setName);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
        return scenarioNameField;
    }

    private ComboBox<FundPool> initFundPoolComboBox() {
        fundPoolComboBox = new ComboBox<>(ForeignUi.getMessage("label.fund_pool"));
        fundPoolComboBox.setItems(controller.getFundPoolsNotAttachedToScenario());
        fundPoolComboBox.setItemLabelGenerator(FundPool::getName);
        fundPoolComboBox.setRequiredIndicatorVisible(true);
        fundPoolComboBox.setWidthFull();
        fundPoolBinder.forField(fundPoolComboBox)
            .asRequired(ForeignUi.getMessage("field.error.fund_pool.empty"))
            .bind(AtomicReference::get, AtomicReference::set);
        VaadinUtils.addComponentStyle(fundPoolComboBox, "fund-pools-filter");
        return fundPoolComboBox;
    }

    private ScenarioParameterWidget<List<UsageAge>> initUsageAgeWeightsWidget() {
        usageAgeWeightWidget =
            new ScenarioParameterWidget<>(ForeignUi.getMessage("button.usage_age_weights"),
                controller.getUsageAges(), () -> new UsageAgeWeightWindow(true));
        return usageAgeWeightWidget;
    }

    private ScenarioParameterWidget<List<PublicationType>> initPubTypeWeightsWidget() {
        publicationTypeWeightWidget =
            new ScenarioParameterWidget<>(ForeignUi.getMessage("button.publication_type_weights"),
                controller.getPublicationTypes(), () -> new PublicationTypeWeightsWindow(true));
        return publicationTypeWeightWidget;
    }

    private ScenarioParameterWidget<List<DetailLicenseeClass>> initLicenseeClassesMappingWidget() {
        licenseeClassMappingWidget =
            new ScenarioParameterWidget<>(ForeignUi.getMessage("button.licensee_class_mapping"),
                controller.getDetailLicenseeClasses(), () -> new AggregateLicenseeClassMappingWindow(true));
        return licenseeClassMappingWidget;
    }

    private TextArea initDescriptionArea() {
        descriptionArea = new TextArea(ForeignUi.getMessage("field.description"));
        descriptionArea.setWidthFull();
        scenarioBinder.forField(descriptionArea)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(Scenario::getDescription, Scenario::setDescription);
        VaadinUtils.addComponentStyle(descriptionArea, "scenario-description");
        return descriptionArea;
    }

    private HorizontalLayout initButtonsLayout() {
        var confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(listener -> onConfirmButtonClicked());
        return new HorizontalLayout(confirmButton, Buttons.createCancelButton(this));
    }

    private void onConfirmButtonClicked() {
        if (scenarioBinder.isValid() && fundPoolBinder.isValid()) {
            var aaclFields = new Scenario.AaclFields();
            aaclFields.setFundPoolId(fundPoolComboBox.getValue().getId());
            aaclFields.setUsageAges(usageAgeWeightWidget.getAppliedParameters());
            aaclFields.setPublicationTypes(publicationTypeWeightWidget.getAppliedParameters());
            aaclFields.setDetailLicenseeClasses(licenseeClassMappingWidget.getAppliedParameters());
            var aggregateClassesWithoutUsages =
                controller.getAggregateClassesNotToBeDistributed(aaclFields.getFundPoolId(),
                    aaclFields.getDetailLicenseeClasses());
            if (CollectionUtils.isEmpty(aggregateClassesWithoutUsages)) {
                fireEvent(new ScenarioCreateEvent(this,
                    controller.createAaclScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()), aaclFields,
                        StringUtils.trimToEmpty(descriptionArea.getValue()))));
                close();
            } else {
                var formattedAggregateClasses = aggregateClassesWithoutUsages.stream()
                    .map(aggregateClass -> String.format("%s (%s - %s)", aggregateClass.getId(),
                        aggregateClass.getEnrollmentProfile(), aggregateClass.getDiscipline()))
                    .collect(Collectors.toList());
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.aggregate_licensee_classes_without_usages",
                        String.join("<br><li>", formattedAggregateClasses)));
            }
        } else {
            Windows.showValidationErrorWindow();
        }
    }
}
