package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.domain.Currency;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.component.CsvUploadComponent;
import com.copyright.rup.dist.foreign.ui.component.LocalDateWidget;
import com.copyright.rup.dist.foreign.ui.component.validator.GrossAmountValidator;
import com.copyright.rup.dist.foreign.ui.component.validator.NumberValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;

import com.google.common.collect.Lists;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Window for uploading a usage batch with usages.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/18/2017
 *
 * @author Mikita Hladkikh
 * @author Mikalai Bezmen
 */
class UsageBatchUploadWindow extends Window {

    private static final int DEFAULT_WIDTH = 157;
    private TextField accountNumberField;
    private LocalDateWidget paymentDateWidget;
    private TextField grossAmountField;
    private TextField usageBatchNameField;
    private ComboBox reportedCurrencyBox;
    private Property<String> rightsholderNameProperty;
    private Property<String> fiscalYearProperty;
    private CsvUploadComponent csvUploadComponent;
    private IUsagesController usagesController;

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
        setHeight(305, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "usage-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                UsageCsvProcessor processor = new UsageCsvProcessor();
                CsvProcessingResult<Usage> processingResult =
                    processor.process(csvUploadComponent.getStreamToUploadedFile());
                if (processingResult.isSuccessful()) {
                    int usagesCount = usagesController.loadUsageBatch(buildUsageBatch(), processingResult.getResult(),
                        SecurityUtils.getUserName());
                    close();
                    Windows.showNotificationWindow(
                        String.format(ForeignUi.getMessage("message.upload_completed"), usagesCount));
                }
            } catch (RupRuntimeException e) {
                Windows.showNotificationWindow(e.getMessage());
            }
        } else {
            // TODO {mhladkikh} use csvUploadComponent, but not it's internal state for error window
            Windows.showValidationErrorWindow(
                Lists.newArrayList(usageBatchNameField, csvUploadComponent.getFileNameField(),
                    accountNumberField, paymentDateWidget, grossAmountField, reportedCurrencyBox));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return usageBatchNameField.isValid()
            && csvUploadComponent.isValid()
            && accountNumberField.isValid()
            && paymentDateWidget.isValid()
            && grossAmountField.isValid()
            && reportedCurrencyBox.isValid();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(StringUtils.trim(usageBatchNameField.getValue()));
        Rightsholder rro = new Rightsholder();
        rro.setAccountNumber(Long.valueOf(StringUtils.trim(accountNumberField.getValue())));
        usageBatch.setRro(rro);
        usageBatch.setPaymentDate(paymentDateWidget.getValue());
        usageBatch.setFiscalYear(UsageBatchUtils.calculateFiscalYear(paymentDateWidget.getValue()));
        usageBatch.setGrossAmount(new BigDecimal(StringUtils.trim(grossAmountField.getValue())));
        return usageBatch;
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initUsageBatchNameField(), initCsvUploadComponent(), initRightsholderLayout(),
            initPaymentDataLayout(), initGrossAmountLayout(), buttonsLayout);
        rootLayout.setSpacing(true);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
    }

    private CsvUploadComponent initCsvUploadComponent() {
        csvUploadComponent = new CsvUploadComponent();
        HorizontalLayout csvUploadLayout = (HorizontalLayout) csvUploadComponent.getComponent(0);
        Upload upload = (Upload) csvUploadLayout.getComponent(1);
        upload.setSizeFull();
        csvUploadLayout.setSizeFull();
        csvUploadLayout.setComponentAlignment(upload, Alignment.BOTTOM_RIGHT);
        csvUploadLayout.setExpandRatio(csvUploadLayout.getComponent(0), 0.8f);
        csvUploadLayout.setExpandRatio(upload, 0.2f);
        VaadinUtils.setMaxComponentsWidth(csvUploadComponent);
        VaadinUtils.addComponentStyle(csvUploadComponent, "usage-upload-component");
        setRequired(csvUploadComponent.getFileNameField());
        return csvUploadComponent;
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
        usageBatchNameField.addValidator(new StringLengthValidator(String.format(
            ForeignUi.getMessage("field.error.length"), 50), 0, 50, false));
        usageBatchNameField.addValidator(new UsageBatchNameUniqueValidator());
        setRequired(usageBatchNameField);
        usageBatchNameField.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(usageBatchNameField);
        VaadinUtils.addComponentStyle(usageBatchNameField, "usage-batch-name-field");
        return usageBatchNameField;
    }

    private HorizontalLayout initRightsholderLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        TextField accountNumber = initRighsholderAccountNumberField();
        TextField accountName = initRightsholderAccountNameField();
        Button verifyButton = initVerifyButton();
        horizontalLayout.addComponents(accountNumber, accountName, verifyButton);
        horizontalLayout.setSpacing(true);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(accountNumber, 0.25f);
        horizontalLayout.setExpandRatio(accountName, 0.55f);
        horizontalLayout.setExpandRatio(verifyButton, 0.2f);
        horizontalLayout.setComponentAlignment(verifyButton, Alignment.BOTTOM_RIGHT);
        return horizontalLayout;
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
        horizontalLayout.addComponents(initReportedCurrency(), initGrossAmountField());
        horizontalLayout.setSpacing(true);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private TextField initRighsholderAccountNumberField() {
        accountNumberField = new TextField(ForeignUi.getMessage("label.rro_account_number"));
        setRequired(accountNumberField);
        accountNumberField.setNullRepresentation(StringUtils.EMPTY);
        accountNumberField.setImmediate(true);
        accountNumberField.addValidator(new NumberValidator());
        accountNumberField.addValidator(
            new StringLengthValidator(String.format(ForeignUi.getMessage("field.error.number_length"), 10), 0, 10,
                false));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        VaadinUtils.addComponentStyle(accountNumberField, "rro-account-number-field");
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
        TextField nameField = new TextField(ForeignUi.getMessage("label.rro_account_name"), rightsholderNameProperty);
        nameField.setReadOnly(true);
        nameField.setNullRepresentation(StringUtils.EMPTY);
        VaadinUtils.setMaxComponentsWidth(nameField);
        VaadinUtils.addComponentStyle(nameField, "rro-account-name-field");
        return nameField;
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
        grossAmountField.addValidator(new GrossAmountValidator());
        VaadinUtils.setMaxComponentsWidth(grossAmountField);
        VaadinUtils.addComponentStyle(grossAmountField, "gross-amount-field");
        return grossAmountField;
    }

    private ComboBox initReportedCurrency() {
        BeanItemContainer<Currency> container =
            new BeanItemContainer<>(Currency.class, usagesController.getCurrencies());
        reportedCurrencyBox = new ComboBox(ForeignUi.getMessage("label.reported_currency"), container);
        reportedCurrencyBox.setWidth(DEFAULT_WIDTH, Unit.PIXELS);
        for (Object itemId : reportedCurrencyBox.getItemIds()) {
            reportedCurrencyBox.setItemCaption(itemId, getCurrencyItemCaption(container, itemId));
        }
        container.sort(new Object[]{"code"}, new boolean[]{true});
        setRequired(reportedCurrencyBox);
        VaadinUtils.setMaxComponentsWidth(reportedCurrencyBox);
        VaadinUtils.addComponentStyle(reportedCurrencyBox, "reported-currency-field");
        return reportedCurrencyBox;
    }

    private Button initVerifyButton() {
        Button button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.setSizeFull();
        button.addClickListener(event -> {
            String rroAccountNumber = StringUtils.trim(accountNumberField.getValue());
            rightsholderNameProperty.setValue(StringUtils.isNotEmpty(rroAccountNumber)
                ? usagesController.getRroName(Long.valueOf(rroAccountNumber))
                : StringUtils.EMPTY);
        });
        return button;
    }

    private void setRequired(AbstractField field) {
        field.setRequired(true);
        field.setRequiredError(ForeignUi.getMessage("field.error.empty"));
    }

    private String getCurrencyItemCaption(BeanItemContainer<Currency> container, Object itemId) {
        BeanItem<Currency> currencyBean = container.getItem(itemId);
        String caption = StringUtils.EMPTY;
        if (null != currencyBean) {
            Currency currency = currencyBean.getBean();
            caption = String.format("%s - %s", currency.getCode(), currency.getName());
        }
        return caption;
    }

    /**
     * Validator for Usage Batch uniqueness. Checks whether usage batch with provided name already exists or not.
     */
    class UsageBatchNameUniqueValidator extends AbstractStringValidator {

        /**
         * Constructs a validator for Usage Batch name.
         */
        UsageBatchNameUniqueValidator() {
            super("Usage Batch with such name already exists");
        }

        @Override
        protected boolean isValidValue(String value) {
            return !usagesController.usageBatchExists(StringUtils.trim(value));
        }
    }
}
