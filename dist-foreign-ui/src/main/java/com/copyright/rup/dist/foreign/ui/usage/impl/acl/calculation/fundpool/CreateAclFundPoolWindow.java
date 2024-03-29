package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.service.impl.csv.AclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.YearValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
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

import java.util.List;

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

    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";
    private static final long serialVersionUID = 2259064780804783110L;

    private final Binder<AclFundPool> fundPoolBinder = new Binder<>();
    private final Binder<String> binder = new Binder<>();
    private final UploadField uploadField = new UploadField();
    private final IAclFundPoolController fundPoolController;

    private TextField fundPoolNameField;
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
        super.setContent(initRootLayout());
        super.setCaption(ForeignUi.getMessage("window.create_acl_fund_pool"));
        super.setResizable(false);
        super.setWidth(400, Unit.PIXELS);
        super.setHeight(285, Unit.PIXELS);
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

    /**
     * Initiates ACL fund pool creating.
     */
    void onConfirmClicked() {
        if (isValid()) {
            if (ldmtCheckBox.getValue()) {
                onConfirmClickedLdmt();
            } else {
                onConfirmClickedManual();
            }
        } else {
            Windows.showValidationErrorWindow(
                List.of(fundPoolNameField, fundPoolPeriodYearField, fundPoolPeriodMonthComboBox,
                    licenseTypeComboBox, uploadField));
        }
    }

    private void onConfirmClickedLdmt() {
        if (fundPoolController.isLdmtDetailExist(licenseTypeComboBox.getValue())) {
            int fundPoolDetailsCount = fundPoolController.createLdmtFundPool(buildFundPool());
            close();
            Windows.showNotificationWindow(ForeignUi.getMessage("message.creation_completed", fundPoolDetailsCount));
        } else {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.error.empty_ldmt_detail"));
        }
    }

    private void onConfirmClickedManual() {
        try {
            AclFundPoolCsvProcessor processor = fundPoolController.getCsvProcessor();
            ProcessingResult<AclFundPoolDetail> result = processor.process(uploadField.getStreamToUploadedFile());
            if (result.isSuccessful()) {
                int fundPoolDetailsCount = fundPoolController.loadManualFundPool(buildFundPool(), result.get());
                close();
                Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", fundPoolDetailsCount));
            } else {
                Windows.showModalWindow(new ErrorUploadWindow(
                    fundPoolController.getErrorResultStreamSource(uploadField.getValue(), result),
                    ForeignUi.getMessage("message.error.upload.threshold.exceeded")));
            }
        } catch (ValidationException e) {
            Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
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
        fundPoolNameField = new TextField(ForeignUi.getMessage("label.fund_pool.name"));
        fundPoolNameField.setRequiredIndicatorVisible(true);
        fundPoolBinder.forField(fundPoolNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 255), 0, 255))
            .withValidator(value -> !fundPoolController.isFundPoolExist(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Fund Pool"))
            .bind(AclFundPool::getName, AclFundPool::setName)
            .validate();
        fundPoolNameField.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(fundPoolNameField);
        VaadinUtils.addComponentStyle(fundPoolNameField, "acl-fund-pool-name-field");
        return fundPoolNameField;
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
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue)
            .validate();
        VaadinUtils.addComponentStyle(fundPoolPeriodYearField, "acl-fund-pool-period-year-field");
        return fundPoolPeriodYearField;
    }

    private ComboBox<String> initFundPoolMonthComboBox() {
        fundPoolPeriodMonthComboBox = new ComboBox<>(ForeignUi.getMessage("label.fund_pool.period_month"));
        fundPoolPeriodMonthComboBox.setItems(FdaConstants.ACL_PERIOD_MONTHS);
        fundPoolPeriodMonthComboBox.setRequiredIndicatorVisible(true);
        binder.forField(fundPoolPeriodMonthComboBox)
            .withValidator(new RequiredValidator())
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue)
            .validate();
        fundPoolPeriodMonthComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(fundPoolPeriodMonthComboBox, "acl-fund-pool-period-month-combo-box");
        return fundPoolPeriodMonthComboBox;
    }

    private ComboBox<String> initLicenseTypeComboBox() {
        licenseTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.license_type"));
        licenseTypeComboBox.setItems(FdaConstants.ACL_LICENSE_TYPES);
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
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue);
        uploadField.addSucceededListener(event -> binder.validate());
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "fund-pool-details-upload-component");
        return uploadField;
    }

    private CheckBox initLdmtCheckBox() {
        ldmtCheckBox = new CheckBox();
        ldmtCheckBox.setValue(Boolean.FALSE);
        ldmtCheckBox.addValueChangeListener(event -> {
            uploadField.setEnabled(!event.getValue());
            binder.validate();
        });
        ldmtCheckBox.setCaption(ForeignUi.getMessage("label.ldmt"));
        VaadinUtils.addComponentStyle(ldmtCheckBox, "acl-ldmt-checkbox");
        return ldmtCheckBox;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(event -> onConfirmClicked());
        VaadinUtils.setButtonsAutoDisabled(confirmButton);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(confirmButton, closeButton);
        return horizontalLayout;
    }

    private AclFundPool buildFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setName(fundPoolNameField.getValue());
        aclFundPool.setPeriod(Integer.valueOf(String.format("%s%s",
            StringUtils.trim(fundPoolPeriodYearField.getValue()), fundPoolPeriodMonthComboBox.getValue())));
        aclFundPool.setLicenseType(licenseTypeComboBox.getValue());
        aclFundPool.setManualUploadFlag(!ldmtCheckBox.getValue());
        return aclFundPool;
    }
}
