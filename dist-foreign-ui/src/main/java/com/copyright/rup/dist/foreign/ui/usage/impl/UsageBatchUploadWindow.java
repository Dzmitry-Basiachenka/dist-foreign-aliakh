package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.component.validator.GrossAmountValidator;
import com.copyright.rup.dist.foreign.ui.component.validator.NumberValidator;
import com.copyright.rup.dist.foreign.ui.component.validator.UsageBatchNameUniqueValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.Lists;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;

import java.math.BigDecimal;

/**
 * Window for uploading a usage batch with usages.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/18/17
 *
 * @author Mikita Hladkikh
 * @author Mikalai Bezmen
 */
class UsageBatchUploadWindow extends Window {

    private final IUsagesController usagesController;
    private TextField accountNumberField;
    private TextField accountNameField;
    private LocalDateWidget paymentDateWidget;
    private TextField grossAmountField;
    private TextField usageBatchNameField;
    private Property<String> rightsholderNameProperty;
    private Property<String> fiscalYearProperty;
    private Property<String> productFamilyProperty;
    private Rightsholder rro;
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param usagesController {@link IUsagesController} instance
     */
    UsageBatchUploadWindow(IUsagesController usagesController) {
        this.usagesController = usagesController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_usage_batch"));
        setResizable(false);
        setWidth(440, Unit.PIXELS);
        setHeight(350, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "usage-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            StopWatch stopWatch = new Slf4JStopWatch();
            try {
                UsageCsvProcessor processor = usagesController.getCsvProcessor(productFamilyProperty.getValue());
                ProcessingResult<Usage> processingResult = processor.process(uploadField.getStreamToUploadedFile());
                stopWatch.lap("usageBatch.load_fileProcessed");
                if (processingResult.isSuccessful()) {
                    int usagesCount = usagesController.loadUsageBatch(buildUsageBatch(), processingResult.get());
                    stopWatch.lap("usageBatch.load_stored");
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usagesCount));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(
                            usagesController.getErrorResultStreamSource(uploadField.getFileName(), processingResult),
                            ForeignUi.getMessage("message.error.upload")));
                }
            } catch (ThresholdExceededException e) {
                Windows.showModalWindow(
                    new ErrorUploadWindow(
                        usagesController.getErrorResultStreamSource(uploadField.getFileName(), e.getProcessingResult()),
                        e.getMessage() + "<br>Press Download button to see detailed list of errors"));
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            } finally {
                stopWatch.stop();
            }
        } else {
            Windows.showValidationErrorWindow(
                Lists.newArrayList(usageBatchNameField, uploadField,
                    accountNumberField, accountNameField, paymentDateWidget, grossAmountField));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return usageBatchNameField.isValid()
            && uploadField.isValid()
            && accountNumberField.isValid()
            && accountNameField.isValid()
            && paymentDateWidget.isValid()
            && grossAmountField.isValid();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(StringUtils.trim(usageBatchNameField.getValue()));
        usageBatch.setRro(rro);
        usageBatch.setPaymentDate(paymentDateWidget.getValue());
        usageBatch.setFiscalYear(UsageBatchUtils.calculateFiscalYear(paymentDateWidget.getValue()));
        usageBatch.setGrossAmount(
            new BigDecimal(StringUtils.trim(grossAmountField.getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
        return usageBatch;
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initUsageBatchNameField(), initUploadField(), initRightsholderLayout(),
            initPaymentDataLayout(), initGrossAmountLayout(), buttonsLayout);
        rootLayout.setSpacing(true);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setSizeFull();
        uploadField.addValidator(new CsvFileExtensionValidator());
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
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }

    private TextField initUsageBatchNameField() {
        usageBatchNameField = new TextField(ForeignUi.getMessage("label.usage_batch_name"));
        usageBatchNameField.addValidator(
            new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50, false));
        usageBatchNameField.addValidator(new UsageBatchNameUniqueValidator(usagesController));
        setRequired(usageBatchNameField);
        usageBatchNameField.setSizeFull();
        usageBatchNameField.setImmediate(true);
        VaadinUtils.setMaxComponentsWidth(usageBatchNameField);
        VaadinUtils.addComponentStyle(usageBatchNameField, "usage-batch-name-field");
        return usageBatchNameField;
    }

    private VerticalLayout initRightsholderLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout rroAccountLayout = new HorizontalLayout();
        TextField accountNumber = initRightsholderAccountNumberField();
        TextField productFamily = initProductFamilyField();
        Button verifyButton = initVerifyButton();
        verifyButton.setWidth(72, Unit.PIXELS);
        rroAccountLayout.addComponents(accountNumber, productFamily, verifyButton);
        rroAccountLayout.setSpacing(true);
        rroAccountLayout.setSizeFull();
        rroAccountLayout.setExpandRatio(accountNumber, 0.62f);
        rroAccountLayout.setExpandRatio(productFamily, 0.38f);
        rroAccountLayout.setComponentAlignment(verifyButton, Alignment.BOTTOM_RIGHT);
        TextField accountName = initRightsholderAccountNameField();
        accountName.setSizeFull();
        verticalLayout.addComponents(rroAccountLayout, accountName);
        verticalLayout.setSpacing(true);
        return verticalLayout;
    }

    private HorizontalLayout initPaymentDataLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(initPaymentDateWidget(), initFiscalYearField());
        horizontalLayout.setSpacing(true);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private HorizontalLayout initGrossAmountLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(initGrossAmountField(), new Label());
        horizontalLayout.setSpacing(true);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private TextField initRightsholderAccountNumberField() {
        accountNumberField = new TextField(ForeignUi.getMessage("label.rro_account_number"));
        setRequired(accountNumberField);
        accountNumberField.setNullRepresentation(StringUtils.EMPTY);
        accountNumberField.setImmediate(true);
        accountNumberField.addValidator(new NumberValidator());
        accountNumberField.addValidator(
            new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10, false));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        VaadinUtils.addComponentStyle(accountNumberField, "rro-account-number-field");
        accountNumberField.addValueChangeListener(
            (ValueChangeListener) event -> {
                rightsholderNameProperty.setValue(StringUtils.EMPTY);
                productFamilyProperty.setValue(StringUtils.EMPTY);
            });
        return accountNumberField;
    }

    private LocalDateWidget initPaymentDateWidget() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date"));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        setRequired(paymentDateWidget);
        paymentDateWidget.addValueChangeListener(event ->
            fiscalYearProperty.setValue(UsageBatchUtils.getFiscalYear(paymentDateWidget.getValue())));
        VaadinUtils.addComponentStyle(paymentDateWidget, "payment-date-field");
        return paymentDateWidget;
    }

    private TextField initRightsholderAccountNameField() {
        rightsholderNameProperty = new ObjectProperty<>(StringUtils.EMPTY);
        accountNameField = new TextField(ForeignUi.getMessage("label.rro_account_name"), rightsholderNameProperty);
        accountNameField.setRequired(true);
        accountNameField.setRequiredError(ForeignUi.getMessage("field.error.rro_name.empty"));
        accountNameField.setReadOnly(true);
        accountNameField.setNullRepresentation(StringUtils.EMPTY);
        VaadinUtils.setMaxComponentsWidth(accountNameField);
        VaadinUtils.addComponentStyle(accountNameField, "rro-account-name-field");
        return accountNameField;
    }

    private TextField initFiscalYearField() {
        fiscalYearProperty = new ObjectProperty<>(StringUtils.EMPTY);
        TextField fiscalYearField = new TextField(ForeignUi.getMessage("label.fiscal_year"), fiscalYearProperty);
        fiscalYearField.setReadOnly(true);
        fiscalYearField.setNullRepresentation(StringUtils.EMPTY);
        VaadinUtils.setMaxComponentsWidth(fiscalYearField);
        VaadinUtils.addComponentStyle(fiscalYearField, "fiscal-year-field");
        return fiscalYearField;
    }

    private TextField initGrossAmountField() {
        grossAmountField = new TextField(ForeignUi.getMessage("label.gross_amount_usd"));
        setRequired(grossAmountField);
        grossAmountField.setNullRepresentation(StringUtils.EMPTY);
        grossAmountField.setImmediate(true);
        grossAmountField.addValidator(new GrossAmountValidator());
        VaadinUtils.setMaxComponentsWidth(grossAmountField);
        VaadinUtils.addComponentStyle(grossAmountField, "gross-amount-field");
        return grossAmountField;
    }

    private TextField initProductFamilyField() {
        productFamilyProperty = new ObjectProperty<>(StringUtils.EMPTY);
        TextField productFamilyField =
            new TextField(ForeignUi.getMessage("label.product_family"), productFamilyProperty);
        productFamilyField.setReadOnly(true);
        productFamilyField.setNullRepresentation(StringUtils.EMPTY);
        VaadinUtils.setMaxComponentsWidth(productFamilyField);
        VaadinUtils.addComponentStyle(productFamilyField, "product-family-field");
        return productFamilyField;
    }

    private Button initVerifyButton() {
        Button button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.setSizeFull();
        button.addClickListener(event -> {
            if (accountNumberField.isValid()) {
                rro = usagesController.getRro(Long.valueOf(StringUtils.trim(accountNumberField.getValue())));
                rightsholderNameProperty.setValue(rro.getName());
                if (StringUtils.isNotBlank(rro.getName())) {
                    productFamilyProperty.setValue(
                        Long.valueOf(2000017000).equals(rro.getAccountNumber()) ? "CLA_FAS" : "FAS");
                }
            }
        });
        return button;
    }

    private void setRequired(AbstractField field) {
        field.setRequired(true);
        field.setRequiredError(ForeignUi.getMessage("field.error.empty"));
    }

    /**
     * Validator for CSV file extension.
     */
    static class CsvFileExtensionValidator extends AbstractStringValidator {

        /**
         * Constructor.
         */
        CsvFileExtensionValidator() {
            super(ForeignUi.getMessage("error.upload_file.invalid_extension"));
        }

        @Override
        protected boolean isValidValue(String value) {
            return StringUtils.endsWith(value, ".csv");
        }
    }
}
