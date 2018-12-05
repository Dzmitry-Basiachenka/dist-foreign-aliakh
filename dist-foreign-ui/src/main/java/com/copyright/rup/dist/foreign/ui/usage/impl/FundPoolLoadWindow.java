package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBigDecimalConverter;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

/**
 * Window for loading fund pool.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/28/2018
 *
 * @author Ihar Suvorau
 */
class FundPoolLoadWindow extends Window {

    private static final String EMPTY_MARKET_STYLE = "empty-selected-markets";
    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;

    private final IUsagesController usagesController;
    private final Binder<UsageBatch> binder = new Binder<>();
    private final Binder<String> stringBinder = new Binder<>();
    private Set<String> selectedMarkets;
    private TextField accountNumberField;
    private TextField accountNameField;
    private LocalDateWidget paymentDateWidget;
    private TextField stmAmountField;
    private TextField nonStmAmountField;
    private TextField stmMinAmountField;
    private TextField nonStmMinAmountField;
    private TextField usageBatchNameField;
    private TextField fundPoolPeriodToField;
    private TextField fundPoolPeriodFromField;
    private TextField marketValidationField;
    private Rightsholder rro;

    /**
     * Constructor.
     *
     * @param usagesController {@link IUsagesController} instance
     */
    FundPoolLoadWindow(IUsagesController usagesController) {
        this.usagesController = usagesController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_fund_pool"));
        setResizable(false);
        setWidth(440, Unit.PIXELS);
        setHeight(330, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "fund-pool-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            UsageBatch usageBatch = buildUsageBatch();
            int usagesCount = usagesController.getUsagesCountForNtsBatch(usageBatch);
            if (0 < usagesCount) {
                usagesController.loadNtsBatch(usageBatch);
                close();
                Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usagesCount));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.load_fund_pool.no_usages"));
            }
        } else {
            Windows.showValidationErrorWindow(
                Arrays.asList(usageBatchNameField, accountNumberField, accountNameField, paymentDateWidget,
                    fundPoolPeriodToField, fundPoolPeriodFromField, marketValidationField, stmAmountField,
                    nonStmAmountField, stmMinAmountField, nonStmMinAmountField));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return binder.isValid() && stringBinder.isValid();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(StringUtils.trim(usageBatchNameField.getValue()));
        usageBatch.setRro(rro);
        usageBatch.setPaymentDate(paymentDateWidget.getValue());
        FundPool fundPool = new FundPool();
        fundPool.setMarkets(selectedMarkets);
        fundPool.setFundPoolPeriodFrom(Integer.parseInt(fundPoolPeriodFromField.getValue()));
        fundPool.setFundPoolPeriodTo(Integer.parseInt(fundPoolPeriodToField.getValue()));
        fundPool.setStmAmount(new BigDecimal(stmAmountField.getValue()));
        fundPool.setNonStmAmount(new BigDecimal(nonStmAmountField.getValue()));
        fundPool.setStmMinimumAmount(new BigDecimal(stmMinAmountField.getValue()));
        fundPool.setNonStmMinimumAmount(new BigDecimal(nonStmMinAmountField.getValue()));
        usageBatch.setFundPool(fundPool);
        return usageBatch;
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initUsageBatchNameField(), initRightsholderLayout(), initDateLayout(),
            initMarketFilterWidget(), initAmountsLayout(), initMinAmountsLayout(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        binder.validate();
        return rootLayout;
    }

    private MarketFilterWidget initMarketFilterWidget() {
        marketValidationField = new TextField(ForeignUi.getMessage("label.markets"));
        stringBinder.forField(marketValidationField)
            .withValidator(value -> Integer.parseInt(value) > 0, "Please select at least one market")
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
        MarketFilterWidget marketFilterWidget = new MarketFilterWidget(usagesController::getMarkets);
        VaadinUtils.addComponentStyle(marketFilterWidget, "market-filter-widget");
        marketFilterWidget.addStyleName(EMPTY_MARKET_STYLE);
        marketFilterWidget.addFilterSaveListener(event -> {
            int size = event.getSelectedItemsIds().size();
            marketValidationField.setValue(String.valueOf(size));
            stringBinder.validate();
            if (0 < size) {
                marketFilterWidget.removeStyleName(EMPTY_MARKET_STYLE);
            } else {
                marketFilterWidget.addStyleName(EMPTY_MARKET_STYLE);
            }
            selectedMarkets = (Set<String>) event.getSelectedItemsIds();
        });
        return marketFilterWidget;
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

    private HorizontalLayout initRightsholderLayout() {
        Button verifyButton = initVerifyButton();
        verifyButton.setWidth(60, Unit.PIXELS);
        TextField accountNumber = initRightsholderAccountNumberField();
        TextField accountName = initRightsholderAccountNameField();
        HorizontalLayout horizontalLayout = new HorizontalLayout(accountNumber, accountName, verifyButton);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(accountNumber, 0.3f);
        horizontalLayout.setExpandRatio(accountName, 0.7f);
        horizontalLayout.setComponentAlignment(verifyButton, Alignment.BOTTOM_RIGHT);
        return horizontalLayout;
    }

    private HorizontalLayout initDateLayout() {
        initPeriodFromField();
        initPeriodToField();
        LocalDateWidget paymentDate = initPaymentDateWidget();
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(paymentDate, fundPoolPeriodFromField, fundPoolPeriodToField);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(paymentDate, 0.3f);
        horizontalLayout.setExpandRatio(fundPoolPeriodFromField, 0.35f);
        horizontalLayout.setExpandRatio(fundPoolPeriodToField, 0.35f);
        return horizontalLayout;
    }

    private void initPeriodFromField() {
        fundPoolPeriodFromField = new TextField(ForeignUi.getMessage("label.fund.pool.period.from"));
        fundPoolPeriodFromField.setRequiredIndicatorVisible(true);
        fundPoolPeriodFromField.setSizeFull();
        fundPoolPeriodFromField.addValueChangeListener(event -> stringBinder.validate());
        VaadinUtils.addComponentStyle(fundPoolPeriodFromField, "fund-pool-period-from-field");
        stringBinder.forField(fundPoolPeriodFromField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(getNumericValidator(), "Field value should contain numeric values only")
            .withValidator(getYearValidator(), "Field value should be in range from 1950 to 2099")
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
    }

    private void initPeriodToField() {
        fundPoolPeriodToField = new TextField(ForeignUi.getMessage("label.fund.pool.period.to"));
        fundPoolPeriodToField.setRequiredIndicatorVisible(true);
        fundPoolPeriodToField.setSizeFull();
        VaadinUtils.addComponentStyle(fundPoolPeriodToField, "fund-pool-period-to-field");
        stringBinder.forField(fundPoolPeriodToField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(getNumericValidator(), "Field value should contain numeric values only")
            .withValidator(getYearValidator(), "Field value should be in range from 1950 to 2099")
            .withValidator(value -> {
                String periodFrom = fundPoolPeriodFromField.getValue();
                return StringUtils.isEmpty(periodFrom)
                    || !getNumericValidator().test(periodFrom)
                    || !getYearValidator().test(periodFrom)
                    || 0 <= fundPoolPeriodToField.getValue().compareTo(periodFrom);
            }, "Field value should be greater or equal to Fund pool period from")
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
    }

    private HorizontalLayout initAmountsLayout() {
        stmAmountField = initAmountField(ForeignUi.getMessage("label.stm.amount"));
        VaadinUtils.addComponentStyle(stmAmountField, "stm-amount-field");
        nonStmAmountField = initAmountField(ForeignUi.getMessage("label.non.stm.amount"));
        VaadinUtils.addComponentStyle(nonStmAmountField, "non-stm-amount-field");
        HorizontalLayout amountLayout = new HorizontalLayout(stmAmountField, nonStmAmountField);
        amountLayout.setSizeFull();
        return amountLayout;
    }

    private HorizontalLayout initMinAmountsLayout() {
        stmMinAmountField = initAmountField(ForeignUi.getMessage("label.stm.minimum.amount"));
        stmMinAmountField.setValue("50");
        VaadinUtils.addComponentStyle(stmMinAmountField, "stm-minimum-amount-field");
        nonStmMinAmountField = initAmountField(ForeignUi.getMessage("label.non.stm.minimum.amount"));
        nonStmMinAmountField.setValue("7");
        VaadinUtils.addComponentStyle(nonStmMinAmountField, "non-stm-minimum-amount-field");
        HorizontalLayout minAmountLayout = new HorizontalLayout(stmMinAmountField, nonStmMinAmountField);
        minAmountLayout.setSizeFull();
        return minAmountLayout;
    }

    private TextField initRightsholderAccountNumberField() {
        accountNumberField = new TextField(ForeignUi.getMessage("label.rro_account_number"));
        accountNumberField.setRequiredIndicatorVisible(true);
        binder.forField(accountNumberField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(getNumericValidator(), "Field value should contain numeric values only")
            .bind(usageBatch -> usageBatch.getRro().getAccountNumber().toString(),
                (usageBatch, s) -> usageBatch.getRro().setAccountNumber(Long.valueOf(s)));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        VaadinUtils.addComponentStyle(accountNumberField, "rro-account-number-field");
        accountNumberField.addValueChangeListener(event -> accountNameField.setValue(StringUtils.EMPTY));
        return accountNumberField;
    }

    private LocalDateWidget initPaymentDateWidget() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date"));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
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

    private TextField initAmountField(String label) {
        TextField textField = new TextField(label);
        textField.setRequiredIndicatorVisible(true);
        binder.forField(textField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> new AmountValidator().isValid(StringUtils.trimToEmpty(value)),
                "Field value should be positive number and not exceed 10 digits")
            .withConverter(new StringToBigDecimalConverter("Field should be numeric"))
            .bind(UsageBatch::getGrossAmount, UsageBatch::setGrossAmount);
        VaadinUtils.setMaxComponentsWidth(textField);
        return textField;
    }

    private Button initVerifyButton() {
        Button button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.addClickListener(event -> {
            if (null == accountNumberField.getErrorMessage()) {
                rro = usagesController.getRro(Long.valueOf(StringUtils.trim(accountNumberField.getValue())));
                if (StringUtils.isNotBlank(rro.getName())) {
                    accountNameField.setValue(rro.getName());
                } else {
                    accountNameField.clear();
                }
            }
        });
        return button;
    }

    private SerializablePredicate<String> getNumericValidator() {
        return value -> StringUtils.isNumeric(StringUtils.trim(value));
    }

    private SerializablePredicate<String> getYearValidator() {
        return value -> Integer.parseInt(value) >= MIN_YEAR && Integer.parseInt(value) <= MAX_YEAR;
    }
}
