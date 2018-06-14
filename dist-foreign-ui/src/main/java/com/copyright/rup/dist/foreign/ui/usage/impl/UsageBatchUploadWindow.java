package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

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

    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";

    private final IUsagesController usagesController;
    private final Binder<UsageBatch> binder = new Binder<>();
    private final Binder<String> uploadBinder = new Binder<>();
    private TextField accountNumberField;
    private TextField accountNameField;
    private TextField productFamilyField;
    private LocalDateWidget paymentDateWidget;
    private TextField grossAmountField;
    private TextField usageBatchNameField;
    private Rightsholder rro;
    private UploadField uploadField;
    private TextField fiscalYearField;

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
            try {
                UsageCsvProcessor processor = usagesController.getCsvProcessor(productFamilyField.getValue());
                ProcessingResult<Usage> processingResult = processor.process(uploadField.getStreamToUploadedFile());
                if (processingResult.isSuccessful()) {
                    int usagesCount = usagesController.loadUsageBatch(buildUsageBatch(), processingResult.get());
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usagesCount));
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
                        e.getMessage() + "<br>Press Download button to see detailed list of errors"));
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
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
        return binder.isValid() && uploadBinder.isValid();
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
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(s -> s, (s, v) -> s = v).validate();
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
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usagesController.usageBatchExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Usage Batch"))
            .bind(UsageBatch::getName, UsageBatch::setName);
        usageBatchNameField.setSizeFull();
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
        rroAccountLayout.addComponents(accountNumber, productFamily);
        rroAccountLayout.setSizeFull();
        rroAccountLayout.setExpandRatio(accountNumber, 0.62f);
        rroAccountLayout.setExpandRatio(productFamily, 0.38f);
        HorizontalLayout horizontalLayout = new HorizontalLayout(rroAccountLayout, verifyButton);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(rroAccountLayout, 1);
        horizontalLayout.setComponentAlignment(verifyButton, Alignment.BOTTOM_RIGHT);
        TextField accountName = initRightsholderAccountNameField();
        accountName.setSizeFull();
        verticalLayout.addComponents(horizontalLayout, accountName);
        verticalLayout.setMargin(false);
        verticalLayout.setSizeFull();
        return verticalLayout;
    }

    private HorizontalLayout initPaymentDataLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(initPaymentDateWidget(), initFiscalYearField());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private HorizontalLayout initGrossAmountLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(initGrossAmountField(), new Label());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private TextField initRightsholderAccountNumberField() {
        accountNumberField = new TextField(ForeignUi.getMessage("label.rro_account_number"));
        accountNumberField.setRequiredIndicatorVisible(true);
        binder.forField(accountNumberField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                "Field value should contain numeric values only")
            .bind(usageBatch -> usageBatch.getRro().getAccountNumber().toString(),
                (usageBatch, s) -> usageBatch.getRro().setAccountNumber(Long.valueOf(s)));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        VaadinUtils.addComponentStyle(accountNumberField, "rro-account-number-field");
        accountNumberField.addValueChangeListener(event -> {
            accountNameField.setValue(StringUtils.EMPTY);
            productFamilyField.setValue(StringUtils.EMPTY);
        });
        return accountNumberField;
    }

    private LocalDateWidget initPaymentDateWidget() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date"));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        paymentDateWidget.addValueChangeListener(event ->
            fiscalYearField.setValue(UsageBatchUtils.getFiscalYear(paymentDateWidget.getValue())));
        binder.forField(paymentDateWidget)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(UsageBatch::getPaymentDate, UsageBatch::setPaymentDate);
        VaadinUtils.addComponentStyle(paymentDateWidget, "payment-date-field");
        return paymentDateWidget;
    }

    private TextField initRightsholderAccountNameField() {
        accountNameField = new TextField(ForeignUi.getMessage("label.rro_account_name"));
        accountNameField.setRequiredIndicatorVisible(true);
        accountNameField.setReadOnly(true);
        binder.forField(accountNameField)
            .asRequired(ForeignUi.getMessage("field.error.rro_name.empty"))
            .bind(usageBatch -> usageBatch.getRro().getName(), (usageBatch, s) -> usageBatch.getRro().setName(s));
        VaadinUtils.setMaxComponentsWidth(accountNameField);
        VaadinUtils.addComponentStyle(accountNameField, "rro-account-name-field");
        return accountNameField;
    }

    private TextField initFiscalYearField() {
        fiscalYearField = new TextField(ForeignUi.getMessage("label.fiscal_year"));
        fiscalYearField.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(fiscalYearField);
        VaadinUtils.addComponentStyle(fiscalYearField, "fiscal-year-field");
        return fiscalYearField;
    }

    private TextField initGrossAmountField() {
        grossAmountField = new TextField(ForeignUi.getMessage("label.gross_amount_usd"));
        grossAmountField.setRequiredIndicatorVisible(true);
        binder.forField(grossAmountField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> new AmountValidator().isValid(StringUtils.trimToEmpty(value)),
                "Field value should be positive number and not exceed 10 digits")
            .withConverter(new StringToBigDecimalConverter("Field should be numeric"))
            .bind(UsageBatch::getGrossAmount, UsageBatch::setGrossAmount);
        VaadinUtils.setMaxComponentsWidth(grossAmountField);
        VaadinUtils.addComponentStyle(grossAmountField, "gross-amount-field");
        return grossAmountField;
    }

    private TextField initProductFamilyField() {
        productFamilyField = new TextField(ForeignUi.getMessage("label.product_family"));
        productFamilyField.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(productFamilyField);
        VaadinUtils.addComponentStyle(productFamilyField, "product-family-field");
        return productFamilyField;
    }

    private Button initVerifyButton() {
        Button button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.addClickListener(event -> {
            if (null == accountNumberField.getErrorMessage()) {
                rro = usagesController.getRro(Long.valueOf(StringUtils.trim(accountNumberField.getValue())));
                if (StringUtils.isNotBlank(rro.getName())) {
                    accountNameField.setValue(rro.getName());
                    productFamilyField.setValue(
                        FdaConstants.CLA_ACCOUNT_NUMBER.equals(rro.getAccountNumber())
                            ? FdaConstants.CLA_FAS_PRODUCT_FAMILY : FdaConstants.FAS_PRODUCT_FAMILY);
                } else {
                    accountNameField.clear();
                    productFamilyField.clear();
                }
            }
        });
        return button;
    }
}
