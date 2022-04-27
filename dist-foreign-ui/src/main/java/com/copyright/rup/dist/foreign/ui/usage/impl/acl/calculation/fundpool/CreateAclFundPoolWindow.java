package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.YearValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
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

/**
 * Window for creating ACL fund pool.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class CreateAclFundPoolWindow extends Window {

    private static final String[] MONTHS = new String[]{"06", "12"};
    private static final String[] LICENSE_TYPES = new String[]{"ACL", "MACL", "VGW", "JACDCL"};
    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";

    private final Binder<AclFundPool> fundPoolBinder = new Binder<>();
    private final Binder<String> binder = new Binder<>();
    private final UploadField uploadField = new UploadField();
    private final IAclFundPoolController fundPoolController;

    private TextField fundPoolNameFiled;
    private TextField fundPoolPeriodYearField;
    private ComboBox<String> fundPoolPeriodMonthComboBox;
    private ComboBox<String> licenseTypeComboBox;
    private CheckBox ldmtCheckBox;

    /**
     * Constructor.
     *
     * @param fundPoolController instance of {@link IAclFundPoolController}
     */
    public CreateAclFundPoolWindow(IAclFundPoolController fundPoolController) {
        this.fundPoolController = fundPoolController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.create_acl_fund_pool"));
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setHeight(285, Unit.PIXELS);
        binder.validate();
        VaadinUtils.addComponentStyle(this, "create-acl-fund-pool-window");
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return binder.isValid() && fundPoolBinder.isValid();
    }

    private void manualUpload() {
        if (isValid()) {
            //TODO will be implement later
            close();
        } else {
            Windows.showValidationErrorWindow(
                Arrays.asList(fundPoolNameFiled, fundPoolPeriodYearField, fundPoolPeriodMonthComboBox,
                    licenseTypeComboBox, uploadField));
        }
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initFundPoolNameField(), initPeriodYearAndPeriodMonthFields(),
            initLicenseTypeComboBox(), initUploadField(), initLdmtCheckBox(), buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
    }

    private TextField initFundPoolNameField() {
        fundPoolNameFiled = new TextField(ForeignUi.getMessage("label.fund_pool.name"));
        fundPoolNameFiled.setRequiredIndicatorVisible(true);
        fundPoolBinder.forField(fundPoolNameFiled)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 255), 0, 255))
            .withValidator(value -> !fundPoolController.isFundPoolExist(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Fund Pool"))
            .bind(AclFundPool::getName, AclFundPool::setName)
            .validate();
        fundPoolNameFiled.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(fundPoolNameFiled);
        VaadinUtils.addComponentStyle(fundPoolNameFiled, "acl-fund-pool-name-field");
        return fundPoolNameFiled;
    }

    private HorizontalLayout initPeriodYearAndPeriodMonthFields() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(initFundPoolYearField(),
            initFundPoolMonthComboBox());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private TextField initFundPoolYearField() {
        fundPoolPeriodYearField = new TextField(ForeignUi.getMessage("label.fund_pool.period_year"));
        fundPoolPeriodYearField.setSizeFull();
        fundPoolPeriodYearField.setPlaceholder("YYYY");
        fundPoolPeriodYearField.setRequiredIndicatorVisible(true);
        binder.forField(fundPoolPeriodYearField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new YearValidator())
            .bind(s -> s, (s, v) -> s = v)
            .validate();
        VaadinUtils.addComponentStyle(fundPoolPeriodYearField, "acl-fund-pool-period-year-field");
        return fundPoolPeriodYearField;
    }

    private ComboBox<String> initFundPoolMonthComboBox() {
        fundPoolPeriodMonthComboBox = new ComboBox<>(ForeignUi.getMessage("label.fund_pool.period_month"));
        fundPoolPeriodMonthComboBox.setItems(MONTHS);
        fundPoolPeriodMonthComboBox.setRequiredIndicatorVisible(true);
        binder.forField(fundPoolPeriodMonthComboBox)
            .withValidator(new RequiredValidator())
            .bind(s -> s, (s, v) -> s = v)
            .validate();
        fundPoolPeriodMonthComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(fundPoolPeriodMonthComboBox, "acl-fund-pool-period-month-combo-box");
        return fundPoolPeriodMonthComboBox;
    }

    private ComboBox<String> initLicenseTypeComboBox() {
        licenseTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.license_type"));
        licenseTypeComboBox.setItems(LICENSE_TYPES);
        licenseTypeComboBox.setRequiredIndicatorVisible(true);
        fundPoolBinder.forField(licenseTypeComboBox)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(AclFundPool::getLicenseType, AclFundPool::setLicenseType)
            .validate();
        licenseTypeComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(licenseTypeComboBox, "acl-license-type-combo-box");
        return licenseTypeComboBox;
    }

    private UploadField initUploadField() {
        uploadField.setSizeFull();
        uploadField.setRequiredIndicatorVisible(true);
        binder.forField(uploadField)
            .withValidator(value -> ldmtCheckBox.getValue() || StringUtils.isNotBlank(value),
                ForeignUi.getMessage("field.error.empty"))
            .withValidator(value -> ldmtCheckBox.getValue() || StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue);
        uploadField.addSucceededListener(event -> binder.validate());
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "fund-pool-details-upload-component");
        return uploadField;
    }

    private CheckBox initLdmtCheckBox() {
        ldmtCheckBox = new CheckBox();
        ldmtCheckBox.setValue(false);
        ldmtCheckBox.addValueChangeListener(event -> uploadField.setEnabled(!event.getValue()));
        ldmtCheckBox.setCaption(ForeignUi.getMessage("label.ldmt"));
        VaadinUtils.addComponentStyle(ldmtCheckBox, "acl-ldmt-checkbox");
        return ldmtCheckBox;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(event -> {
            if (ldmtCheckBox.getValue()) {
                //TODO will implemented with B-57768 FDA: Integrate with LDMT for ACL fund pools in Oracle - technical
                close();
            } else {
                manualUpload();
            }
        });
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(confirmButton, closeButton);
        return horizontalLayout;
    }
}