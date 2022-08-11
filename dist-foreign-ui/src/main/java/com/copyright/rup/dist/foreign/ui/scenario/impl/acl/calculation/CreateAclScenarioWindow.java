package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.impl.AclAggregateLicenseeClassMappingWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.AclUsageAgeWeightWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.AclPublicationTypeWeightsParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.AclPublicationTypeWeightsWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Window to create ACL scenario.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/21/2022
 *
 * @author Anton Azarenka
 */
public class CreateAclScenarioWindow extends Window implements IDateFormatter {

    //TODO move it in FdaConstants
    private static final String[] LICENSE_TYPES = new String[]{"ACL", "MACL", "VGW", "JACDCL"};
    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";

    private final Binder<AclScenario> scenarioBinder = new Binder<>();
    private final Binder<AtomicReference<AclFundPool>> fundPoolBinder = new Binder<>();
    private final Binder<AtomicReference<AclGrantSet>> grantSetBinder = new Binder<>();
    private final Binder<AtomicReference<AclUsageBatch>> usageBatchBinder = new Binder<>();
    private final ClickListener createButtonClickListener;
    private final boolean hasSpecialistPermission = ForeignSecurityUtils.hasSpecialistPermission();

    private final IAclScenariosController controller;
    private TextField scenarioNameField;
    private ComboBox<String> licenseTypeComboBox;
    private ComboBox<AclFundPool> fundPoolComboBox;
    private ComboBox<AclGrantSet> grantSetComboBox;
    private ComboBox<AclUsageBatch> usageBatchComboBox;
    private ComboBox<AclScenario> aclCopyFromScenarioComboBox;
    private ComboBox<Integer> periodComboBox;
    private CheckBox editableCheckBox;

    private ScenarioParameterWidget<List<UsageAge>> usageAgeWeightWidget;
    private AclPublicationTypeWeightsParameterWidget publicationTypeWeightWidget;
    private ScenarioParameterWidget<List<DetailLicenseeClass>> licenseeClassMappingWidget;
    private List<AclUsageBatch> usageBatches = new ArrayList<>();
    private List<AclGrantSet> grantSets = new ArrayList<>();
    private List<AclFundPool> fundPools = new ArrayList<>();
    private List<AclPublicationType> historicalPubTypes = new ArrayList<>();
    private List<UsageAge> defaultUsageAges = new ArrayList<>();
    private List<DetailLicenseeClass> defaultLicenseeClasses = new ArrayList<>();

    private TextArea descriptionArea;

    /**
     * Constructor.
     *
     * @param controller    instance of {@link IAclScenariosController}
     * @param clickListener action that should be performed after Create button was clicked
     */
    public CreateAclScenarioWindow(IAclScenariosController controller, ClickListener clickListener) {
        this.controller = controller;
        this.createButtonClickListener = clickListener;
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_scenario"));
        initFields();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout =
            new VerticalLayout(scenarioNameField, periodComboBox, licenseTypeComboBox, usageBatchComboBox,
                fundPoolComboBox, grantSetComboBox, aclCopyFromScenarioComboBox, usageAgeWeightWidget,
                publicationTypeWeightWidget, licenseeClassMappingWidget, editableCheckBox, descriptionArea,
                buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        scenarioNameField.focus();
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "create-acl-scenario-window");
        validate();
    }

    private void initFields() {
        initScenarioNameField();
        initPeriodComboBox();
        initCopyFromScenarioComboBox();
        initFundPoolComboBox();
        initGrantSetComboBox();
        initLicenseTypeComboBox();
        initUsageBatchComboBox();
        initUsageAgeWeightsWidget();
        initPubTypeWeightsWidget();
        initLicenseeClassesMappingWidget();
        initEditableCheckBox();
        initDescriptionArea();
    }

    private void initScenarioNameField() {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        scenarioNameField.setValue(
            ForeignUi.getMessage("field.scenario_name.default", FdaConstants.ACL_PRODUCT_FAMILY,
                toShortFormat(LocalDate.now())));
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        scenarioNameField.setRequiredIndicatorVisible(true);
        scenarioBinder.forField(scenarioNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.aclScenarioExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "ACL Scenario"))
            .bind(AclScenario::getName, AclScenario::setName);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "acl-scenario-name");
    }

    private void initPeriodComboBox() {
        periodComboBox = new ComboBox<>(ForeignUi.getMessage("label.period"));
        VaadinUtils.setMaxComponentsWidth(periodComboBox);
        periodComboBox.setItems(controller.getAllPeriods());
        periodComboBox.addValueChangeListener(event -> populateComboBoxes());
        periodComboBox.setRequiredIndicatorVisible(true);
        scenarioBinder.forField(periodComboBox)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(AclScenario::getPeriodEndDate, AclScenario::setPeriodEndDate);
        periodComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(periodComboBox, "acl-scenario-period-combo-box");
    }

    private void initLicenseTypeComboBox() {
        licenseTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.license_type"));
        licenseTypeComboBox.setItems(LICENSE_TYPES);
        licenseTypeComboBox.addValueChangeListener(event -> populateComboBoxes());
        licenseTypeComboBox.setRequiredIndicatorVisible(true);
        scenarioBinder.forField(licenseTypeComboBox)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(AclScenario::getLicenseType, AclScenario::setLicenseType);
        VaadinUtils.setMaxComponentsWidth(licenseTypeComboBox);
        VaadinUtils.addComponentStyle(licenseTypeComboBox, "acl-scenario-license-type-combo-box");
    }

    private void initUsageBatchComboBox() {
        usageBatchComboBox = new ComboBox<>(ForeignUi.getMessage("label.usage_batch"));
        VaadinUtils.setMaxComponentsWidth(usageBatchComboBox);
        usageBatchComboBox.setEnabled(false);
        usageBatchComboBox.setRequiredIndicatorVisible(true);
        usageBatchComboBox.setItemCaptionGenerator(AclUsageBatch::getName);
        usageBatchBinder.forField(usageBatchComboBox)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(AtomicReference::get, AtomicReference::set);
        VaadinUtils.setMaxComponentsWidth(usageBatchComboBox);
        VaadinUtils.addComponentStyle(usageBatchComboBox, "acl-scenario-usage-batch-combo-box");
    }

    private void initFundPoolComboBox() {
        fundPoolComboBox = new ComboBox<>(ForeignUi.getMessage("label.fund_pool"));
        VaadinUtils.setMaxComponentsWidth(fundPoolComboBox);
        fundPoolComboBox.setEnabled(false);
        fundPoolComboBox.setRequiredIndicatorVisible(true);
        fundPoolComboBox.setItemCaptionGenerator(AclFundPool::getName);
        fundPoolBinder.forField(fundPoolComboBox)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(AtomicReference::get, AtomicReference::set).validate();
        VaadinUtils.setMaxComponentsWidth(fundPoolComboBox);
        VaadinUtils.addComponentStyle(fundPoolComboBox, "acl-scenario-fund-pool-combo-box");
    }

    private void initGrantSetComboBox() {
        grantSetComboBox = new ComboBox<>(ForeignUi.getMessage("label.grant_set"));
        grantSetComboBox.setEnabled(false);
        grantSetComboBox.setRequiredIndicatorVisible(true);
        grantSetComboBox.setItemCaptionGenerator(AclGrantSet::getName);
        grantSetBinder.forField(grantSetComboBox)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(AtomicReference::get, AtomicReference::set)
            .validate();
        VaadinUtils.setMaxComponentsWidth(grantSetComboBox);
        VaadinUtils.addComponentStyle(grantSetComboBox, "acl-scenario-grant-set-combo-box");
    }

    private void initCopyFromScenarioComboBox() {
        aclCopyFromScenarioComboBox = new ComboBox<>(ForeignUi.getMessage("label.copy_from"));
        aclCopyFromScenarioComboBox.setItemCaptionGenerator(AclScenario::getName);
        aclCopyFromScenarioComboBox.setItems(controller.getScenarios());
        scenarioBinder.forField(aclCopyFromScenarioComboBox).bind(scenario -> scenario, (scenario, value) ->
            scenario.setCopiedFrom(Objects.nonNull(value) ? value.getName() : null));
        aclCopyFromScenarioComboBox.addValueChangeListener(event -> {
            if (Objects.nonNull(event.getValue())) {
                AclScenario scenario = controller.getScenarioById(event.getValue().getId());
                populateCopiedScenarioFields(scenario);
            } else {
                resetFieldsToDefaultValues();
            }
        });
        VaadinUtils.setMaxComponentsWidth(aclCopyFromScenarioComboBox);
        VaadinUtils.addComponentStyle(aclCopyFromScenarioComboBox, "acl-scenario-copy-from-combo-box");
    }

    private void initEditableCheckBox() {
        editableCheckBox = new CheckBox();
        editableCheckBox.setCaption(ForeignUi.getMessage("label.editable"));
        editableCheckBox.setEnabled(hasSpecialistPermission);
        editableCheckBox.addValueChangeListener(event -> {
            if (!periodComboBox.isEmpty() && !licenseTypeComboBox.isEmpty()) {
                updateUsageBatchFundPoolAndGrantSetComboBoxes();
            }
            if (!editableCheckBox.getValue()) {
                publicationTypeWeightWidget.setAppliedParameters(controller.getAclHistoricalPublicationTypes());
            }
        });
        editableCheckBox.setValue(true);
        scenarioBinder.forField(editableCheckBox)
            .bind(AclScenario::isEditableFlag, AclScenario::setEditableFlag);
        VaadinUtils.addComponentStyle(editableCheckBox, "acl-scenario-editable-check-box");
    }

    private void initUsageAgeWeightsWidget() {
        defaultUsageAges = controller.getUsageAgeWeights();
        usageAgeWeightWidget = new ScenarioParameterWidget<>(ForeignUi.getMessage("button.usage_age_weights"),
            defaultUsageAges, () -> new AclUsageAgeWeightWindow(true));
        usageAgeWeightWidget.addStyleName(ValoTheme.BUTTON_LINK);
    }

    private void initPubTypeWeightsWidget() {
        historicalPubTypes = controller.getAclHistoricalPublicationTypes();
        publicationTypeWeightWidget =
            new AclPublicationTypeWeightsParameterWidget(ForeignUi.getMessage("button.publication_type_weights"),
                historicalPubTypes, () -> new AclPublicationTypeWeightsWindow(controller, editableCheckBox.getValue()));
    }

    private void initLicenseeClassesMappingWidget() {
        defaultLicenseeClasses = controller.getDetailLicenseeClasses();
        licenseeClassMappingWidget =
            new ScenarioParameterWidget<>(ForeignUi.getMessage("button.licensee_class_mapping"),
                defaultLicenseeClasses,
                () -> new AclAggregateLicenseeClassMappingWindow(true, controller.getAggregateLicenseeClasses()));
    }

    private void initDescriptionArea() {
        descriptionArea = new TextArea(ForeignUi.getMessage("field.description"));
        scenarioBinder.forField(descriptionArea)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(AclScenario::getDescription,
                (scenario, description) -> scenario.setDescription(StringUtils.trimToEmpty(description)))
            .validate();
        VaadinUtils.setMaxComponentsWidth(descriptionArea);
        VaadinUtils.addComponentStyle(descriptionArea, "scenario-description");
    }

    private HorizontalLayout initButtonsLayout() {
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(event -> {
            onConfirmButtonClicked();
            createButtonClickListener.buttonClick(event);
        });
        HorizontalLayout layout = new HorizontalLayout(confirmButton, Buttons.createCancelButton(this));
        layout.setSpacing(true);
        return layout;
    }

    private void populateComboBoxes() {
        boolean periodAndLicenseTypeFilled = !periodComboBox.isEmpty() && !licenseTypeComboBox.isEmpty();
        setEnableUsageBatchFundPoolAndGrantSetComboBoxes(periodAndLicenseTypeFilled);
        usageBatchComboBox.setSelectedItem(null);
        fundPoolComboBox.setSelectedItem(null);
        grantSetComboBox.setSelectedItem(null);
        if (periodAndLicenseTypeFilled) {
            updateUsageBatchFundPoolAndGrantSetComboBoxes();
        }
    }

    private void updateUsageBatchFundPoolAndGrantSetComboBoxes() {
        String licenseType = licenseTypeComboBox.getValue();
        Integer period = periodComboBox.getValue();
        boolean editableFlag = editableCheckBox.getValue();
        this.usageBatches = controller.getUsageBatchesByPeriod(period, editableFlag);
        this.fundPools = controller.getFundPoolsByLicenseTypeAndPeriod(licenseType, period);
        this.grantSets = controller.getGrantSetsByLicenseTypeAndPeriod(licenseType, period, editableFlag);
        usageBatchComboBox.setItems(usageBatches);
        fundPoolComboBox.setItems(fundPools);
        grantSetComboBox.setItems(grantSets);
    }

    private void onConfirmButtonClicked() {
        if (isValid()) {
            if (isValidUsageBatch()) {
                AclScenario aclScenario = new AclScenario();
                try {
                    scenarioBinder.writeBean(aclScenario);
                    fundPoolComboBox.getSelectedItem()
                        .ifPresent(fundPool -> aclScenario.setFundPoolId(fundPool.getId()));
                    usageBatchComboBox.getSelectedItem()
                        .ifPresent(usageBatch -> aclScenario.setUsageBatchId(usageBatch.getId()));
                    grantSetComboBox.getSelectedItem()
                        .ifPresent(grantSet -> aclScenario.setGrantSetId(grantSet.getId()));
                    aclScenario.setUsageAges(usageAgeWeightWidget.getAppliedParameters());
                    aclScenario.setPublicationTypes(publicationTypeWeightWidget.getAppliedParameters());
                    aclScenario.setDetailLicenseeClasses(licenseeClassMappingWidget.getAppliedParameters());
                    if (isValidAggregateClassMapping(aclScenario)) {
                        controller.createAclScenario(aclScenario);
                        close();
                    }
                } catch (ValidationException e) {
                    ExceptionUtils.printRootCauseStackTrace(e);
                }
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.error.create_acl_scenario"));
            }
        } else {
            Windows.showValidationErrorWindow(Arrays.asList(scenarioNameField, periodComboBox, licenseTypeComboBox,
                usageBatchComboBox, grantSetComboBox, fundPoolComboBox, descriptionArea));
        }
    }

    private void resetFieldsToDefaultValues() {
        setEnableFieldsForCopyScenario(true);
        periodComboBox.setValue(null);
        licenseTypeComboBox.setValue(null);
        descriptionArea.setValue(StringUtils.EMPTY);
        this.usageBatches.clear();
        this.fundPools.clear();
        this.grantSets.clear();
        usageAgeWeightWidget.setAppliedParameters(defaultUsageAges);
        licenseeClassMappingWidget.setAppliedParameters(defaultLicenseeClasses);
        publicationTypeWeightWidget.setAppliedParameters(historicalPubTypes);
        editableCheckBox.setEnabled(hasSpecialistPermission);
        populateComboBoxes();
    }

    private void setEnableUsageBatchFundPoolAndGrantSetComboBoxes(boolean isEnable) {
        usageBatchComboBox.setEnabled(isEnable);
        fundPoolComboBox.setEnabled(isEnable);
        grantSetComboBox.setEnabled(isEnable);
    }

    private void setEnableFieldsForCopyScenario(boolean isEnable) {
        editableCheckBox.setEnabled(isEnable);
        periodComboBox.setEnabled(isEnable);
        licenseTypeComboBox.setEnabled(isEnable);
    }

    private void populateCopiedScenarioFields(AclScenario scenario) {
        editableCheckBox.setValue(true);
        licenseTypeComboBox.setValue(scenario.getLicenseType());
        periodComboBox.setValue(scenario.getPeriodEndDate());
        setEnableUsageBatchFundPoolAndGrantSetComboBoxes(true);
        updateUsageBatchFundPoolAndGrantSetComboBoxes();
        usageBatches.stream()
            .filter(usageBatch -> usageBatch.getId().equals(scenario.getUsageBatchId()))
            .findFirst()
            .ifPresent(batch -> usageBatchComboBox.setSelectedItem(batch));
        fundPools.stream()
            .filter(fundPool -> fundPool.getId().equals(scenario.getFundPoolId()))
            .findFirst()
            .ifPresent(aclFundPool -> fundPoolComboBox.setSelectedItem(aclFundPool));
        grantSets.stream()
            .filter(grantSet -> grantSet.getId().equals(scenario.getGrantSetId()))
            .findFirst()
            .ifPresent(aclGrantSet -> grantSetComboBox.setSelectedItem(aclGrantSet));
        usageAgeWeightWidget.setAppliedParameters(scenario.getUsageAges());
        licenseeClassMappingWidget.setAppliedParameters(scenario.getDetailLicenseeClasses());
        publicationTypeWeightWidget.setAppliedParameters(scenario.getPublicationTypes());
        descriptionArea.setValue(scenario.getDescription());
        setEnableFieldsForCopyScenario(false);
    }

    private boolean isValid() {
        return scenarioBinder.isValid()
            && usageBatchBinder.isValid()
            && grantSetBinder.isValid()
            && fundPoolBinder.isValid();
    }

    private void validate() {
        scenarioBinder.validate();
        usageBatchBinder.validate();
        grantSetBinder.validate();
        fundPoolBinder.validate();
    }

    private boolean isValidAggregateClassMapping(AclScenario scenario) {
        Set<AclFundPoolDetailDto> fundPoolDetailsWithoutUsages =
            controller.getFundPoolDetailsNotToBeDistributed(scenario.getUsageBatchId(), scenario.getFundPoolId(),
                scenario.getGrantSetId(), scenario.getDetailLicenseeClasses());
        if (CollectionUtils.isNotEmpty(fundPoolDetailsWithoutUsages)) {
            List<String> formattedAggregateClasses = fundPoolDetailsWithoutUsages.stream()
                .map(detail -> String.format("%s - %s (%s)", detail.getAggregateLicenseeClass().getId(),
                    detail.getAggregateLicenseeClass().getDescription(), detail.getTypeOfUse()))
                .collect(Collectors.toList());
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.error.aggregate_licensee_classes_without_usages",
                    String.join("<br><li>", formattedAggregateClasses)));
        }
        return fundPoolDetailsWithoutUsages.isEmpty();
    }

    private boolean isValidUsageBatch() {
        return controller.isValidUsageBatch(usageBatchComboBox.getValue().getId(), grantSetComboBox.getValue().getId(),
            periodComboBox.getValue(), getPeriodPriorsWithWeightAboveZero());
    }

    private List<Integer> getPeriodPriorsWithWeightAboveZero() {
        return usageAgeWeightWidget.getAppliedParameters().stream()
            .filter(usageAge -> usageAge.getWeight().compareTo(BigDecimal.ZERO) > 0)
            .map(UsageAge::getPeriod)
            .collect(Collectors.toList());
    }
}
