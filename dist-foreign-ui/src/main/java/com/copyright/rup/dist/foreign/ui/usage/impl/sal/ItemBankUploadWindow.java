package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.SalFields;
import com.copyright.rup.dist.foreign.service.impl.csv.SalItemBankCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.converter.LocalDateConverter;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializablePredicate;
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
import java.util.List;
import java.util.Objects;

/**
 * Window for uploading a SAL Item Bank with usages.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/20
 *
 * @author Ihar Suvorau
 */
public class ItemBankUploadWindow extends Window {

    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;

    private final ISalUsageController usagesController;
    private final Binder<UsageBatch> binder = new Binder<>();
    private final Binder<String> uploadBinder = new Binder<>();
    private TextField accountNumberField;
    private TextField licenseeNameField;
    private TextField periodEndDateField;
    private TextField itemBankNameField;
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param usagesController usages controller
     */
    ItemBankUploadWindow(ISalUsageController usagesController) {
        this.usagesController = usagesController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_item_bank"));
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
                SalItemBankCsvProcessor processor = usagesController.getSalItemBankCsvProcessor();
                ProcessingResult<Usage> processingResult = processor.process(uploadField.getStreamToUploadedFile());
                if (processingResult.isSuccessful()) {
                    List<Usage> usages = processingResult.get();
                    usagesController.loadItemBank(buildItemBank(), usages);
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
                List.of(itemBankNameField, uploadField, accountNumberField, licenseeNameField, periodEndDateField));
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
        rootLayout.addComponents(initItemBankNameField(), initUploadField(), initLicenseeLayout(),
            initPeriodEndDateField(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
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
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue)
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
        VaadinUtils.setButtonsAutoDisabled(uploadButton);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(uploadButton, closeButton);
        return horizontalLayout;
    }

    private TextField initItemBankNameField() {
        itemBankNameField = new TextField(ForeignUi.getMessage("label.item_bank_name"));
        itemBankNameField.setRequiredIndicatorVisible(true);
        binder.forField(itemBankNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usagesController.usageBatchExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Item Bank"))
            .bind(UsageBatch::getName, UsageBatch::setName);
        itemBankNameField.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(itemBankNameField);
        VaadinUtils.addComponentStyle(itemBankNameField, "item-bank-name-field");
        return itemBankNameField;
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
        accountNumberField = new TextField(ForeignUi.getMessage("label.licensee_account_number"));
        accountNumberField.setRequiredIndicatorVisible(true);
        binder.forField(accountNumberField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .bind(usageBatch -> usageBatch.getSalFields().getLicenseeAccountNumber().toString(),
                (usageBatch, s) -> usageBatch.getSalFields().setLicenseeAccountNumber(Long.valueOf(s)));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        VaadinUtils.addComponentStyle(accountNumberField, "licensee-account-number-field");
        accountNumberField.addValueChangeListener(event -> licenseeNameField.setValue(StringUtils.EMPTY));
        return accountNumberField;
    }

    private TextField initPeriodEndDateField() {
        periodEndDateField = new TextField(ForeignUi.getMessage("label.distribution_period"));
        periodEndDateField.setRequiredIndicatorVisible(true);
        binder.forField(periodEndDateField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(getYearValidator(), ForeignUi.getMessage("field.error.number_not_in_range",
                MIN_YEAR, MAX_YEAR))
            .withConverter(new LocalDateConverter())
            .bind(UsageBatch::getPaymentDate, UsageBatch::setPaymentDate);
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
            .bind(usageBatch -> usageBatch.getSalFields().getLicenseeName(),
                (usageBatch, s) -> usageBatch.getSalFields().setLicenseeName(s));
        VaadinUtils.setMaxComponentsWidth(licenseeNameField);
        VaadinUtils.addComponentStyle(licenseeNameField, "licensee-name-field");
        return licenseeNameField;
    }

    private SerializablePredicate<String> getYearValidator() {
        return value -> Integer.parseInt(value) >= MIN_YEAR && Integer.parseInt(value) <= MAX_YEAR;
    }

    private Button initVerifyButton() {
        Button button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.addClickListener(event -> {
            if (Objects.isNull(accountNumberField.getErrorMessage())) {
                String licenseeName =
                    usagesController.getLicenseeName(Long.valueOf(StringUtils.trim(accountNumberField.getValue())));
                if (StringUtils.isNotBlank(licenseeName)) {
                    licenseeNameField.setValue(licenseeName);
                } else {
                    licenseeNameField.clear();
                }
            }
        });
        return button;
    }

    private UsageBatch buildItemBank() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(StringUtils.trim(itemBankNameField.getValue()));
        usageBatch.setProductFamily(FdaConstants.SAL_PRODUCT_FAMILY);
        usageBatch.setPaymentDate(LocalDate.of(Integer.parseInt(periodEndDateField.getValue()), 6, 30));
        SalFields salFields = new SalFields();
        salFields.setLicenseeAccountNumber(Long.valueOf(accountNumberField.getValue()));
        salFields.setLicenseeName(StringUtils.trim(licenseeNameField.getValue()));
        usageBatch.setSalFields(salFields);
        return usageBatch;
    }
}
