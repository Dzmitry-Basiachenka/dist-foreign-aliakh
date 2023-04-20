package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.YearValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Window for creating ACL grant set.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Dzmitry Basiachenka
 */
public class CreateAclGrantSetWindow extends Window {

    private static final String EMPTY_PERIOD_STYLE = "empty-item-filter-widget";
    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";

    private final Binder<AclGrantSet> grantSetBinder = new Binder<>();
    private final Binder<String> binder = new Binder<>();
    private final IAclGrantDetailController aclGrantDetailController;
    private String sourceAclGrantSetId;

    private TextField grantSetNameFiled;
    private TextField grantPeriodYearField;
    private ComboBox<String> grantPeriodMonthComboBox;
    private TextField periodValidationField;
    private PeriodFilterWidget periodFilterWidget;
    private ComboBox<String> licenseTypeComboBox;
    private CheckBox editableCheckBox;
    private Set<Integer> selectedPeriods;
    private ComboBox<AclGrantSet> copyFromComboBox;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclGrantDetailController}
     */
    public CreateAclGrantSetWindow(IAclGrantDetailController controller) {
        this.aclGrantDetailController = controller;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.create_acl_grant_set"));
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setHeight(310, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "create-acl-grant-set-window");
    }

    /**
     * Initiates ACL grant set creating.
     */
    void onCreateClicked() {
        if (isValid()) {
            int grantDetailsCount;
            if (Objects.nonNull(copyFromComboBox.getValue())) {
                grantDetailsCount = aclGrantDetailController.copyAclGrantSet(buildAclGrantSet(), sourceAclGrantSetId);
            } else {
                grantDetailsCount = aclGrantDetailController.insertAclGrantSet(buildAclGrantSet());
            }
            close();
            Windows.showNotificationWindow(ForeignUi.getMessage("message.creation_completed", grantDetailsCount));
        } else {
            Windows.showValidationErrorWindow(List.of(grantSetNameFiled, grantPeriodYearField,
                grantPeriodMonthComboBox, periodValidationField, licenseTypeComboBox));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return grantSetBinder.isValid() && binder.isValid();
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initGrantSetNameField(), initPeriodYearAndPeriodMonthFields(),
            initPeriodFilterWidget(), initLicenseTypeComboBox(), initCopyFromComboBox(), initEditableCheckBox(),
            buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        binder.validate();
        return rootLayout;
    }

    private TextField initGrantSetNameField() {
        grantSetNameFiled = new TextField(ForeignUi.getMessage("label.grant_set_name"));
        grantSetNameFiled.setRequiredIndicatorVisible(true);
        grantSetBinder.forField(grantSetNameFiled)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 255), 0, 255))
            .withValidator(value -> !aclGrantDetailController.isGrantSetExist(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Grant Set"))
            .bind(AclGrantSet::getName, AclGrantSet::setName)
            .validate();
        grantSetNameFiled.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(grantSetNameFiled);
        VaadinUtils.addComponentStyle(grantSetNameFiled, "acl-grant-set-name-field");
        return grantSetNameFiled;
    }

    private HorizontalLayout initPeriodYearAndPeriodMonthFields() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(initGrantPeriodYearField(),
            initGrantPeriodMonthComboBox());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private TextField initGrantPeriodYearField() {
        grantPeriodYearField = new TextField(ForeignUi.getMessage("label.grant_period_year"));
        grantPeriodYearField.setSizeFull();
        grantPeriodYearField.setPlaceholder("YYYY");
        grantPeriodYearField.setRequiredIndicatorVisible(true);
        binder.forField(grantPeriodYearField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new YearValidator())
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue)
            .validate();
        VaadinUtils.addComponentStyle(grantPeriodYearField, "acl-grant-period-year-field");
        return grantPeriodYearField;
    }

    private ComboBox<String> initGrantPeriodMonthComboBox() {
        grantPeriodMonthComboBox = new ComboBox<>(ForeignUi.getMessage("label.grant_period_month"));
        grantPeriodMonthComboBox.setItems(FdaConstants.ACL_PERIOD_MONTHS);
        grantPeriodMonthComboBox.setRequiredIndicatorVisible(true);
        binder.forField(grantPeriodMonthComboBox)
            .withValidator(new RequiredValidator())
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue)
            .validate();
        grantPeriodMonthComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(grantPeriodMonthComboBox, "acl-grant-period-month-combo-box");
        return grantPeriodMonthComboBox;
    }

    private PeriodFilterWidget initPeriodFilterWidget() {
        periodValidationField = new TextField(ForeignUi.getMessage("label.periods"));
        binder.forField(periodValidationField)
            .withValidator(value -> Integer.parseInt(value) > 0, ForeignUi.getMessage("message.period.empty"))
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue)
            .validate();
        periodFilterWidget = new PeriodFilterWidget(aclGrantDetailController::getBaselinePeriods);
        VaadinUtils.addComponentStyle(periodFilterWidget, "acl-periods-filter-widget");
        periodFilterWidget.addStyleName(EMPTY_PERIOD_STYLE);
        periodFilterWidget.addFilterSaveListener(event -> {
            int size = event.getSelectedItemsIds().size();
            periodValidationField.setValue(String.valueOf(size));
            binder.validate();
            if (0 < size) {
                periodFilterWidget.removeStyleName(EMPTY_PERIOD_STYLE);
            } else {
                periodFilterWidget.addStyleName(EMPTY_PERIOD_STYLE);
            }
            selectedPeriods = event.getSelectedItemsIds();
        });
        return periodFilterWidget;
    }

    private ComboBox<String> initLicenseTypeComboBox() {
        licenseTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.license_type"));
        licenseTypeComboBox.setItems(FdaConstants.ACL_LICENSE_TYPES);
        licenseTypeComboBox.setRequiredIndicatorVisible(true);
        grantSetBinder.forField(licenseTypeComboBox)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(AclGrantSet::getLicenseType, AclGrantSet::setLicenseType)
            .validate();
        licenseTypeComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(licenseTypeComboBox, "acl-license-type-combo-box");
        return licenseTypeComboBox;
    }

    private ComboBox<AclGrantSet> initCopyFromComboBox() {
        copyFromComboBox = new ComboBox<>(ForeignUi.getMessage("label.copy_from"));
        copyFromComboBox.setItemCaptionGenerator(AclGrantSet::getName);
        copyFromComboBox.setItems(aclGrantDetailController.getAllAclGrantSets());
        copyFromComboBox.addValueChangeListener(event -> {
            if (Objects.nonNull(event.getValue())) {
                AclGrantSet aclGrantSet = aclGrantDetailController.getAclGrantSetById(event.getValue().getId());
                sourceAclGrantSetId = aclGrantSet.getId();
                populateCopiedGrantSetFields(aclGrantSet);
            } else {
                resetComponents();
            }
        });
        copyFromComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(copyFromComboBox, "acl-grant-set-copy-from-combo-box");
        return copyFromComboBox;
    }

    private CheckBox initEditableCheckBox() {
        editableCheckBox = new CheckBox();
        editableCheckBox.setValue(Boolean.TRUE);
        editableCheckBox.setCaption(ForeignUi.getMessage("label.editable"));
        VaadinUtils.addComponentStyle(editableCheckBox, "acl-editable-checkbox");
        return editableCheckBox;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button createButton = Buttons.createButton(ForeignUi.getMessage("button.create"));
        createButton.addClickListener(event -> onCreateClicked());
        VaadinUtils.setButtonsAutoDisabled(createButton);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(createButton, closeButton);
        return horizontalLayout;
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setName(StringUtils.trim(grantSetNameFiled.getValue()));
        aclGrantSet.setGrantPeriod(Integer.parseInt(String.format("%s%s",
            StringUtils.trim(grantPeriodYearField.getValue()), grantPeriodMonthComboBox.getValue())));
        aclGrantSet.setPeriods(selectedPeriods);
        aclGrantSet.setLicenseType(licenseTypeComboBox.getValue());
        aclGrantSet.setEditable(editableCheckBox.getValue());
        return aclGrantSet;
    }

    private void populateCopiedGrantSetFields(AclGrantSet aclGrantSet) {
        String period = String.valueOf(aclGrantSet.getGrantPeriod());
        grantPeriodYearField.setValue(StringUtils.left(period, 4));
        grantPeriodMonthComboBox.setValue(StringUtils.right(period, 2));
        selectedPeriods = aclGrantSet.getPeriods();
        periodFilterWidget.setLabelValue(selectedPeriods.size());
        periodValidationField.setValue(String.valueOf(selectedPeriods.size()));
        licenseTypeComboBox.setValue(aclGrantSet.getLicenseType());
        editableCheckBox.setValue(Boolean.TRUE);
        setEnabledComponents(false);
    }

    private void resetComponents() {
        grantPeriodYearField.clear();
        grantPeriodMonthComboBox.clear();
        periodFilterWidget.reset();
        licenseTypeComboBox.clear();
        setEnabledComponents(true);
    }

    private void setEnabledComponents(boolean isEnable) {
        grantPeriodYearField.setEnabled(isEnable);
        grantPeriodMonthComboBox.setEnabled(isEnable);
        if (!isEnable) {
            periodFilterWidget.removeStyleName(EMPTY_PERIOD_STYLE);
        } else {
            periodFilterWidget.addStyleName(EMPTY_PERIOD_STYLE);
        }
        periodFilterWidget.setEnabled(isEnable);
        licenseTypeComboBox.setEnabled(isEnable);
        editableCheckBox.setEnabled(isEnable);
    }
}
