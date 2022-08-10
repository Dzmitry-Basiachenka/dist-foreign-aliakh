package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.YearValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
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

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * Window for creating ACL usage batch.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/05/2022
 *
 * @author Aliaksandr Liakh
 */
public class CreateAclUsageBatchWindow extends Window {

    private static final String EMPTY_PERIOD_STYLE = "empty-selected-periods";
    private static final String[] MONTHS = new String[]{"06", "12"};

    private final Binder<AclUsageBatch> usageBatchBinder = new Binder<>();
    private final Binder<String> binder = new Binder<>();
    private final IAclUsageController aclUsageController;

    private TextField usageBatchNameFiled;
    private TextField distributionPeriodYearField;
    private ComboBox<String> distributionPeriodMonthComboBox;
    private TextField periodValidationField;
    private PeriodFilterWidget periodFilterWidget;
    private ComboBox<AclUsageBatch> copyFromComboBox;
    private CheckBox editableCheckBox;
    private Set<Integer> selectedPeriods;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclUsageController}
     */
    public CreateAclUsageBatchWindow(IAclUsageController controller) {
        this.aclUsageController = controller;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.create_acl_usage_batch"));
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setHeight(265, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "create-acl-usage-batch-window");
    }

    /**
     * Initiates ACL usage batch creating.
     */
    void onCreateClicked() {
        if (isValid()) {
            int usagesCount;
            if (Objects.nonNull(copyFromComboBox.getValue())) {
                usagesCount = aclUsageController.copyAclUsageBatch(copyFromComboBox.getValue().getId(),
                    buildAclUsageBatch());
            } else {
                usagesCount = aclUsageController.insertAclUsageBatch(buildAclUsageBatch());
            }
            close();
            Windows.showNotificationWindow(ForeignUi.getMessage("message.creation_completed", usagesCount));
        } else {
            Windows.showValidationErrorWindow(Arrays.asList(usageBatchNameFiled, distributionPeriodYearField,
                distributionPeriodMonthComboBox, periodValidationField));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return usageBatchBinder.isValid() && binder.isValid();
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initUsageBatchNameField(), initDistributionPeriodYearAndPeriodMonthFields(),
            initPeriodFilterWidget(), initCopyFromComboBox(), initEditableCheckBox(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        binder.validate();
        return rootLayout;
    }

    private TextField initUsageBatchNameField() {
        usageBatchNameFiled = new TextField(ForeignUi.getMessage("label.usage_batch_name"));
        usageBatchNameFiled.setRequiredIndicatorVisible(true);
        usageBatchBinder.forField(usageBatchNameFiled)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 255), 0, 255))
            .withValidator(value -> !aclUsageController.isAclUsageBatchExist(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Usage Batch"))
            .bind(AclUsageBatch::getName, AclUsageBatch::setName)
            .validate();
        usageBatchNameFiled.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(usageBatchNameFiled);
        VaadinUtils.addComponentStyle(usageBatchNameFiled, "acl-usage-batch-name-field");
        return usageBatchNameFiled;
    }

    private HorizontalLayout initDistributionPeriodYearAndPeriodMonthFields() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(initDistributionPeriodYearField(),
            initDistributionPeriodMonthComboBox());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private TextField initDistributionPeriodYearField() {
        distributionPeriodYearField = new TextField(ForeignUi.getMessage("label.distribution_period_year"));
        distributionPeriodYearField.setSizeFull();
        distributionPeriodYearField.setPlaceholder("YYYY");
        distributionPeriodYearField.setRequiredIndicatorVisible(true);
        binder.forField(distributionPeriodYearField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new YearValidator())
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
        VaadinUtils.addComponentStyle(distributionPeriodYearField, "acl-distribution-period-year-field");
        return distributionPeriodYearField;
    }

    private ComboBox<String> initDistributionPeriodMonthComboBox() {
        distributionPeriodMonthComboBox = new ComboBox<>(ForeignUi.getMessage("label.distribution_period_month"));
        distributionPeriodMonthComboBox.setItems(MONTHS);
        distributionPeriodMonthComboBox.setRequiredIndicatorVisible(true);
        binder.forField(distributionPeriodMonthComboBox)
            .withValidator(new RequiredValidator())
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
        distributionPeriodMonthComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(distributionPeriodMonthComboBox, "acl-distribution-period-month-combo-box");
        return distributionPeriodMonthComboBox;
    }

    private PeriodFilterWidget initPeriodFilterWidget() {
        periodValidationField = new TextField(ForeignUi.getMessage("label.periods"));
        binder.forField(periodValidationField)
            .withValidator(value -> Integer.parseInt(value) > 0, ForeignUi.getMessage("message.period.empty"))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
        periodFilterWidget = new PeriodFilterWidget(aclUsageController::getAllPeriods);
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

    private ComboBox<AclUsageBatch> initCopyFromComboBox() {
        copyFromComboBox = new ComboBox<>(ForeignUi.getMessage("label.copy_from"));
        copyFromComboBox.setItemCaptionGenerator(AclUsageBatch::getName);
        copyFromComboBox.setItems(aclUsageController.getAllAclUsageBatches());
        copyFromComboBox.addValueChangeListener(event -> {
            if (Objects.nonNull(event.getValue())) {
                populateCopiedUsageBatchFields(event.getValue());
            } else {
                resetComponents();
            }
        });
        copyFromComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(copyFromComboBox, "acl-copy-from-combo-box");
        return copyFromComboBox;
    }

    private CheckBox initEditableCheckBox() {
        editableCheckBox = new CheckBox();
        editableCheckBox.setValue(true);
        editableCheckBox.setCaption(ForeignUi.getMessage("label.editable"));
        VaadinUtils.addComponentStyle(editableCheckBox, "acl-editable-checkbox");
        return editableCheckBox;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button createButton = Buttons.createButton(ForeignUi.getMessage("button.create"));
        createButton.addClickListener(event -> onCreateClicked());
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(createButton, closeButton);
        return horizontalLayout;
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName(StringUtils.trim(usageBatchNameFiled.getValue()));
        usageBatch.setDistributionPeriod(Integer.parseInt(String.format("%s%s",
            StringUtils.trim(distributionPeriodYearField.getValue()), distributionPeriodMonthComboBox.getValue())));
        usageBatch.setPeriods(selectedPeriods);
        usageBatch.setEditable(editableCheckBox.getValue());
        return usageBatch;
    }

    private void populateCopiedUsageBatchFields(AclUsageBatch aclUsageBatch) {
        String period = String.valueOf(aclUsageBatch.getDistributionPeriod());
        distributionPeriodYearField.setValue(StringUtils.left(period, 4));
        distributionPeriodMonthComboBox.setValue(StringUtils.right(period, 2));
        selectedPeriods = aclUsageBatch.getPeriods();
        periodFilterWidget.setLabelValue(selectedPeriods.size());
        periodValidationField.setValue(String.valueOf(selectedPeriods.size()));
        setEnabledComponents(false);
    }

    private void resetComponents() {
        distributionPeriodYearField.clear();
        distributionPeriodMonthComboBox.clear();
        periodFilterWidget.reset();
        periodValidationField.clear();
        setEnabledComponents(true);
    }

    private void setEnabledComponents(boolean isEnable) {
        distributionPeriodYearField.setEnabled(isEnable);
        distributionPeriodMonthComboBox.setEnabled(isEnable);
        if (!isEnable) {
            periodFilterWidget.removeStyleName(EMPTY_PERIOD_STYLE);
        } else {
            periodFilterWidget.addStyleName(EMPTY_PERIOD_STYLE);
        }
        periodFilterWidget.setEnabled(isEnable);
        editableCheckBox.setValue(true);
        editableCheckBox.setEnabled(isEnable);
    }
}
