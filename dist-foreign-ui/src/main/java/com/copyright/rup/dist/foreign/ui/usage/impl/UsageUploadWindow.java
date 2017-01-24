package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.CurrencyEnum;
import com.copyright.rup.dist.foreign.ui.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.component.CsvUploadComponent;
import com.copyright.rup.dist.foreign.ui.component.LocalDateWidget;
import com.copyright.rup.dist.foreign.ui.component.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.component.validator.NumberValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;

import com.google.common.collect.Lists;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

/**
 * Window for uploading usages.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/18/2017
 *
 * @author Mikita Hladkikh
 */
class UsageUploadWindow extends Window {

    private static final int DEFAULT_WIDTH = 157;
    private TextField accountNumberField;
    private LocalDateWidget paymentDateWidget;
    private TextField grossAmountField;
    private ComboBox reportedCurrencyBox;
    private Property<String> rightsholderNameProperty;
    private Property<String> fiscalYearProperty;
    private CsvUploadComponent csvUploadComponent;

    /**
     * Constructor.
     */
    UsageUploadWindow() {
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_usage_batch"));
        setResizable(false);
        setWidth(600, Unit.PIXELS);
        setHeight(310, Unit.PIXELS);
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            // TODO {mhladkikh} implement logic
            Windows.showNotificationWindow("Uploading is started");
        } else {
            // TODO {mhladkikh} use csvUploadComponent, but not it's internal state for error window
            Windows.showValidationErrorWindow(Lists.newArrayList(csvUploadComponent.getFileNameField(),
                accountNumberField, paymentDateWidget, grossAmountField, reportedCurrencyBox));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return csvUploadComponent.isValid()
            && accountNumberField.isValid()
            && paymentDateWidget.isValid()
            && grossAmountField.isValid()
            && reportedCurrencyBox.isValid();
    }

    private Component initComponentsLayout() {
        GridLayout gridlayout = new GridLayout(3, 4);
        gridlayout.addComponent(initRighsholderAccountNumberField(), 0, 0);
        gridlayout.addComponent(initRightsholderAccountNameField(), 1, 0);
        gridlayout.addComponent(initVerifyButton(), 2, 0);
        gridlayout.addComponent(initPaymentDateWidget(), 0, 1);
        gridlayout.addComponent(initFiscalYearField(), 1, 1);
        gridlayout.addComponent(initGrossAmountField(), 0, 2);
        gridlayout.addComponent(initReportedCurrency(), 0, 3);
        gridlayout.setSpacing(true);
        gridlayout.setComponentAlignment(gridlayout.getComponent(2, 0), Alignment.BOTTOM_RIGHT);
        gridlayout.setSizeFull();
        gridlayout.setColumnExpandRatio(0, 0.3f);
        gridlayout.setColumnExpandRatio(1, 0.6f);
        return gridlayout;
    }

    private ComponentContainer initRootLayout() {
        csvUploadComponent = new CsvUploadComponent();
        VaadinUtils.setMaxComponentsWidth(csvUploadComponent);
        setRequired(csvUploadComponent.getFileNameField());
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(csvUploadComponent, initComponentsLayout(), buttonsLayout);
        rootLayout.setSpacing(true);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
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

    private TextField initRighsholderAccountNumberField() {
        accountNumberField = new TextField(ForeignUi.getMessage("label.rro_account_number"));
        setRequired(accountNumberField);
        accountNumberField.setNullRepresentation(StringUtils.EMPTY);
        accountNumberField.setImmediate(true);
        accountNumberField.addValidator(new NumberValidator());
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        return accountNumberField;
    }

    private LocalDateWidget initPaymentDateWidget() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date"),
            new ObjectProperty<>(null, LocalDate.class));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        setRequired(paymentDateWidget);
        paymentDateWidget.addValueChangeListener(event ->
            fiscalYearProperty.setValue(UsageBatchUtils.getFiscalYear(paymentDateWidget.getValue())));
        return paymentDateWidget;
    }

    private TextField initRightsholderAccountNameField() {
        rightsholderNameProperty = new ObjectProperty<>(StringUtils.EMPTY);
        TextField nameField = new TextField(ForeignUi.getMessage("label.rro_account_name"), rightsholderNameProperty);
        nameField.setReadOnly(true);
        nameField.setNullRepresentation(StringUtils.EMPTY);
        VaadinUtils.setMaxComponentsWidth(nameField);
        return nameField;
    }

    private TextField initFiscalYearField() {
        fiscalYearProperty = new ObjectProperty<>(StringUtils.EMPTY);
        TextField fiscalYearField = new TextField(ForeignUi.getMessage("label.fiscal_year"), fiscalYearProperty);
        fiscalYearField.setReadOnly(true);
        fiscalYearField.setNullRepresentation(StringUtils.EMPTY);
        VaadinUtils.setMaxComponentsWidth(fiscalYearField);
        return fiscalYearField;
    }

    private TextField initGrossAmountField() {
        grossAmountField = new TextField(ForeignUi.getMessage("label.gross_amount_usd"));
        setRequired(grossAmountField);
        grossAmountField.setNullRepresentation(StringUtils.EMPTY);
        grossAmountField.setConverter(new StringToBigDecimalConverter());
        grossAmountField.setConversionError(ForeignUi.getMessage("field.error.number_format"));
        grossAmountField.addValidator(new AmountValidator());
        VaadinUtils.setMaxComponentsWidth(grossAmountField);
        return grossAmountField;
    }

    private ComboBox initReportedCurrency() {
        reportedCurrencyBox = new ComboBox(ForeignUi.getMessage("label.reported_currency"));
        reportedCurrencyBox.setWidth(DEFAULT_WIDTH, Unit.PIXELS);
        setRequired(reportedCurrencyBox);
        reportedCurrencyBox.addItems((Object[]) CurrencyEnum.values());
        VaadinUtils.setMaxComponentsWidth(reportedCurrencyBox);
        return reportedCurrencyBox;
    }

    private Button initVerifyButton() {
        Button button = new Button(ForeignUi.getMessage("button.verify"));
        button.addClickListener(event -> rightsholderNameProperty.setValue(accountNumberField.getValue()));
        return button;
    }

    private void setRequired(AbstractField field) {
        field.setRequired(true);
        field.setRequiredError(ForeignUi.getMessage("field.error.empty"));
    }
}
