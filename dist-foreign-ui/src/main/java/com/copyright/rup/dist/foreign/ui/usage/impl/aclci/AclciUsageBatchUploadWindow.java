package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.AclciFields;
import com.copyright.rup.dist.foreign.service.impl.csv.AclciUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.YearValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Window for uploading ACLCI usage batch with usages.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/19/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageBatchUploadWindow extends Window {

    private final IAclciUsageController usagesController;
    private final Binder<UsageBatch> binder = new Binder<>();
    private final Binder<String> uploadBinder = new Binder<>();
    private TextField usageBatchNameField;
    private UploadField uploadField;
    private TextField licenseeAccountNumberField;
    private TextField licenseeNameField;
    private TextField periodEndDateField;

    /**
     * Constructor.
     *
     * @param usagesController usages controller
     */
    AclciUsageBatchUploadWindow(IAclciUsageController usagesController) {
        this.usagesController = usagesController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_usage_batch"));
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setHeight(305, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "usage-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                AclciUsageCsvProcessor processor = usagesController.getAclciUsageCsvProcessor();
                ProcessingResult<Usage> processingResult = processor.process(uploadField.getStreamToUploadedFile());
                if (processingResult.isSuccessful()) {
                    List<Usage> usages = processingResult.get();
                    usagesController.loadAclciUsageBatch(binder.getBean(), usages);
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usages.size()));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(
                            usagesController.getErrorResultStreamSource(uploadField.getValue(), processingResult),
                            ForeignUi.getMessage("message.error.upload")));
                }
            } catch (ThresholdExceededException e) {
                Windows.showModalWindow(
                    new ErrorUploadWindow(
                        usagesController.getErrorResultStreamSource(uploadField.getValue(), e.getProcessingResult()),
                        String.format("%s%s", e.getMessage(),
                            ForeignUi.getMessage("message.error.upload.threshold.exceeded"))));
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            }
        } else {
            Windows.showValidationErrorWindow(
                Arrays.asList(usageBatchNameField, uploadField, licenseeAccountNumberField, licenseeNameField,
                    periodEndDateField));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return binder.isValid() && uploadBinder.isValid();
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initUsageBatchNameField(), initUploadField(), initLicenseeLayout(),
            initPeriodEndDateField(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setProductFamily(FdaConstants.ACLCI_PRODUCT_FAMILY);
        usageBatch.setAclciFields(new AclciFields());
        binder.setBean(usageBatch);
        binder.validate();
        return rootLayout;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setSizeFull();
        uploadField.setRequiredIndicatorVisible(true);
        uploadBinder.forField(uploadField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
        uploadField.addSucceededListener(event -> uploadBinder.validate());
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "usage-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(uploadButton, closeButton);
        return horizontalLayout;
    }

    private TextField initUsageBatchNameField() {
        usageBatchNameField = new TextField(ForeignUi.getMessage("label.usage_batch_name"));
        usageBatchNameField.setRequiredIndicatorVisible(true);
        binder.forField(usageBatchNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usagesController.usageBatchExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Usage Batch"))
            .bind(UsageBatch::getName, UsageBatch::setName);
        usageBatchNameField.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(usageBatchNameField);
        VaadinUtils.addComponentStyle(usageBatchNameField, "usage-batch-name-field");
        return usageBatchNameField;
    }

    private VerticalLayout initLicenseeLayout() {
        VerticalLayout licenseeLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        TextField accountNumber = initLicenseeAccountNumberField();
        TextField licenseeName = initLicenseeNameField();
        Button verifyButton = initVerifyButton();
        verifyButton.setWidth(72, Unit.PIXELS);
        horizontalLayout.addComponents(accountNumber, verifyButton);
        horizontalLayout.setComponentAlignment(verifyButton, Alignment.BOTTOM_RIGHT);
        horizontalLayout.setExpandRatio(accountNumber, 1);
        horizontalLayout.setSizeFull();
        licenseeLayout.addComponents(horizontalLayout, licenseeName);
        licenseeLayout.setMargin(false);
        licenseeLayout.setSizeFull();
        return licenseeLayout;
    }

    private TextField initLicenseeAccountNumberField() {
        licenseeAccountNumberField = new TextField(ForeignUi.getMessage("label.licensee_account_number"));
        licenseeAccountNumberField.setRequiredIndicatorVisible(true);
        binder.forField(licenseeAccountNumberField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .bind(batch -> Objects.toString(batch.getAclciFields().getLicenseeAccountNumber(), StringUtils.EMPTY),
                (batch, value) ->
                    batch.getAclciFields().setLicenseeAccountNumber(Long.valueOf(StringUtils.trim(value))));
        VaadinUtils.setMaxComponentsWidth(licenseeAccountNumberField);
        VaadinUtils.addComponentStyle(licenseeAccountNumberField, "licensee-account-number-field");
        licenseeAccountNumberField.addValueChangeListener(event -> licenseeNameField.setValue(StringUtils.EMPTY));
        return licenseeAccountNumberField;
    }

    private TextField initPeriodEndDateField() {
        periodEndDateField = new TextField(ForeignUi.getMessage("label.distribution_period"));
        periodEndDateField.setRequiredIndicatorVisible(true);
        binder.forField(periodEndDateField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new YearValidator())
            .bind(batch -> Objects.toString(batch.getPaymentDate(), StringUtils.EMPTY),
                (batch, value) -> batch.setPaymentDate(LocalDate.of(Integer.parseInt(StringUtils.trim(value)), 6, 30)));
        VaadinUtils.addComponentStyle(periodEndDateField, "distribution-period-field");
        periodEndDateField.setWidth(40, Unit.PERCENTAGE);
        return periodEndDateField;
    }

    private TextField initLicenseeNameField() {
        licenseeNameField = new TextField(ForeignUi.getMessage("label.licensee_name"));
        licenseeNameField.setRequiredIndicatorVisible(true);
        licenseeNameField.setReadOnly(true);
        binder.forField(licenseeNameField)
            .asRequired(ForeignUi.getMessage("field.error.licensee_name.empty"))
            .bind(batch -> batch.getAclciFields().getLicenseeName(),
                (batch, value) -> batch.getAclciFields().setLicenseeName(value));
        VaadinUtils.setMaxComponentsWidth(licenseeNameField);
        VaadinUtils.addComponentStyle(licenseeNameField, "licensee-name-field");
        return licenseeNameField;
    }

    private Button initVerifyButton() {
        Button button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.addClickListener(event -> {
            if (Objects.isNull(licenseeAccountNumberField.getErrorMessage())) {
                Long licenseeAccountNumber = Long.valueOf(StringUtils.trim(licenseeAccountNumberField.getValue()));
                String licenseeName = usagesController.getLicenseeName(licenseeAccountNumber);
                if (StringUtils.isNotBlank(licenseeName)) {
                    licenseeNameField.setValue(licenseeName);
                } else {
                    licenseeNameField.clear();
                }
            }
        });
        return button;
    }
}
