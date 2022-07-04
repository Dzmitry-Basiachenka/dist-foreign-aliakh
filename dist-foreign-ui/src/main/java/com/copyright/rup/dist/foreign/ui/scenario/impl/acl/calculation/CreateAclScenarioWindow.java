package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Window to create ACL scenario.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/21/2022
 *
 * @author Anton Azarenka
 */
public class CreateAclScenarioWindow extends Window {

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

    //TODO these fields will be reimplemented. It is stubs for now
    private Button usageAgeWeightWidget;
    private Button publicationTypeWeightWidget;
    private Button licenseeClassMappingWidget;

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
                DateUtils.format(LocalDate.now())));
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
        VaadinUtils.addComponentStyle(licenseTypeComboBox, "acl-scenario-license-type-combo-box");
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
        VaadinUtils.setMaxComponentsWidth(aclCopyFromScenarioComboBox);
    }

    private void initEditableCheckBox() {
        editableCheckBox = new CheckBox();
        editableCheckBox.setCaption(ForeignUi.getMessage("label.editable"));
        editableCheckBox.setEnabled(hasSpecialistPermission);
        editableCheckBox.setValue(true);
        scenarioBinder.forField(editableCheckBox)
            .bind(AclScenario::isEditableFlag, AclScenario::setEditableFlag);
        VaadinUtils.addComponentStyle(editableCheckBox, "acl-scenario-editable-check-box");
    }

    private void initUsageAgeWeightsWidget() {
        //TODO will be reimplemented later
        usageAgeWeightWidget = Buttons.createButton(ForeignUi.getMessage("button.usage_age_weights"));
        usageAgeWeightWidget.addStyleName(ValoTheme.BUTTON_LINK);
    }

    private void initPubTypeWeightsWidget() {
        //TODO will be reimplemented later
        publicationTypeWeightWidget = Buttons.createButton(ForeignUi.getMessage("button.publication_type_weights"));
        publicationTypeWeightWidget.addStyleName(ValoTheme.BUTTON_LINK);
    }

    private void initLicenseeClassesMappingWidget() {
        //TODO will be reimplemented later
        licenseeClassMappingWidget = Buttons.createButton(ForeignUi.getMessage("button.licensee_class_mapping"));
        licenseeClassMappingWidget.addStyleName(ValoTheme.BUTTON_LINK);
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
        usageBatchComboBox.setEnabled(periodAndLicenseTypeFilled);
        fundPoolComboBox.setEnabled(periodAndLicenseTypeFilled);
        grantSetComboBox.setEnabled(periodAndLicenseTypeFilled);
        usageBatchComboBox.setSelectedItem(null);
        fundPoolComboBox.setSelectedItem(null);
        grantSetComboBox.setSelectedItem(null);
        if (periodAndLicenseTypeFilled) {
            String licenseType = licenseTypeComboBox.getValue();
            Integer period = periodComboBox.getValue();
            boolean editableFlag = editableCheckBox.getValue();
            usageBatchComboBox.setItems(controller.getUsageBatchesByPeriod(period, editableFlag));
            fundPoolComboBox.setItems(controller.getFundPoolsByLicenseTypeAndPeriod(licenseType, period));
            grantSetComboBox.setItems(controller.getGrantSetsByLicenseTypeAndPeriod(licenseType, period, editableFlag));
        }
    }

    private void onConfirmButtonClicked() {
        if (isValid()) {
            AclScenario aclScenario = new AclScenario();
            try {
                scenarioBinder.writeBean(aclScenario);
                fundPoolComboBox.getSelectedItem().ifPresent(fundPool -> aclScenario.setFundPoolId(fundPool.getId()));
                usageBatchComboBox.getSelectedItem()
                    .ifPresent(usageBatch -> aclScenario.setUsageBatchId(usageBatch.getId()));
                grantSetComboBox.getSelectedItem().ifPresent(grantSet -> aclScenario.setGrantSetId(grantSet.getId()));
                controller.createAclScenario(aclScenario);
                close();
            } catch (ValidationException e) {
                ExceptionUtils.printRootCauseStackTrace(e);
            }
        } else {
            Windows.showValidationErrorWindow(Arrays.asList(scenarioNameField, periodComboBox, licenseTypeComboBox,
                usageBatchComboBox, grantSetComboBox, fundPoolComboBox));
        }
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
}
