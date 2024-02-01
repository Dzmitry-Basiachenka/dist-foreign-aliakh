package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.vui.common.validator.AmountRangeValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredBigDecimalValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredLongValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.MarketFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.LongField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.LongRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
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
class FundPoolLoadWindow extends CommonDialog {

    private static final long serialVersionUID = -3082206348453157583L;
    private static final String EMPTY_MARKET_STYLE = "empty-item-filter-widget";
    private static final String NOT_NUMERIC_MESSAGE = "field.error.not_numeric";
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;

    private final INtsUsageController usagesController;
    private final Binder<UsageBatch> binder = new Binder<>();
    private final Binder<String> stringBinder = new Binder<>();

    private TextField usageBatchNameField;
    private LongField accountNumberField;
    private Binder.Binding<UsageBatch, Long> accountNumberBinding;
    private TextField accountNameField;
    private LocalDateWidget paymentDateWidget;
    private TextField fundPoolPeriodFromField;
    private TextField fundPoolPeriodToField;
    private TextField marketValidationField;
    private Set<String> selectedMarkets;
    private BigDecimalField stmAmountField;
    private BigDecimalField nonStmAmountField;
    private BigDecimalField stmMinAmountField;
    private BigDecimalField nonStmMinAmountField;
    private Checkbox excludeStmCheckbox;
    private Rightsholder rro;

    /**
     * Constructor.
     *
     * @param usagesController instance of {@link INtsUsageController}
     */
    FundPoolLoadWindow(INtsUsageController usagesController) {
        this.usagesController = usagesController;
        super.add(initRootLayout());
        super.setHeaderTitle(ForeignUi.getMessage("window.load_fund_pool"));
        super.setWidth("700px");
        super.setHeight("580px");
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("fund-pool-upload-window", false);
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            var usageBatch = buildUsageBatch();
            int usagesCount = usagesController.getUsagesCountForNtsBatch(usageBatch);
            if (0 < usagesCount) {
                int insertedUsages = usagesController.loadNtsBatch(usageBatch);
                close();
                Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", insertedUsages));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.load_fund_pool.no_usages"));
            }
        } else {
            Windows.showValidationErrorWindow();
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
        var usageBatch = new UsageBatch();
        usageBatch.setName(StringUtils.trim(usageBatchNameField.getValue()));
        usageBatch.setRro(rro);
        usageBatch.setProductFamily("NTS");
        usageBatch.setPaymentDate(paymentDateWidget.getValue());
        usageBatch.setFiscalYear(UsageBatchUtils.calculateFiscalYear(paymentDateWidget.getValue()));
        var ntsFields = new UsageBatch.NtsFields();
        ntsFields.setMarkets(selectedMarkets);
        ntsFields.setFundPoolPeriodFrom(Integer.valueOf(fundPoolPeriodFromField.getValue()));
        ntsFields.setFundPoolPeriodTo(Integer.valueOf(fundPoolPeriodToField.getValue()));
        ntsFields.setStmAmount(stmAmountField.getValue());
        ntsFields.setNonStmAmount(nonStmAmountField.getValue());
        ntsFields.setStmMinimumAmount(stmMinAmountField.getValue());
        ntsFields.setNonStmMinimumAmount(nonStmMinAmountField.getValue());
        ntsFields.setExcludingStm(excludeStmCheckbox.getValue());
        usageBatch.setNtsFields(ntsFields);
        return usageBatch;
    }

    private VerticalLayout initRootLayout() {
        var rootLayout = VaadinUtils.initCommonVerticalLayout();
        rootLayout.add(initUsageBatchNameField(), initRightsholderLayout(), initPaymentDataLayout(),
            initMarketFilterWidget(), initAmountsLayout(), initMinAmountsLayout(), initExcludeStmCheckBox());
        VaadinUtils.setPadding(rootLayout, 10, 10, 0, 10);
        binder.validate();
        return rootLayout;
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
        VaadinUtils.setMaxComponentsWidth(usageBatchNameField);
        VaadinUtils.addComponentStyle(usageBatchNameField, "usage-batch-name-field");
        return usageBatchNameField;
    }

    private HorizontalLayout initButtonsLayout() {
        var uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        VaadinUtils.setButtonsAutoDisabled(uploadButton);
        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(uploadButton, Buttons.createCloseButton(this));
        return horizontalLayout;
    }

    private HorizontalLayout initRightsholderLayout() {
        var horizontalLayout = new HorizontalLayout(initRightsholderAccountNumberField(),
            initRightsholderAccountNameField(), initVerifyButton());
        horizontalLayout.setWidthFull();
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return horizontalLayout;
    }

    private LongField initRightsholderAccountNumberField() {
        accountNumberField = new LongField(ForeignUi.getMessage("label.rro_account_number"));
        accountNumberField.setWidth("30%");
        accountNumberField.setRequiredIndicatorVisible(true);
        accountNumberField.addValueChangeListener(event -> accountNameField.setValue(StringUtils.EMPTY));
        accountNumberBinding = binder.forField(accountNumberField)
            .withValidator(new RequiredLongValidator())
            .withValidator(
                new LongRangeValidator(ForeignUi.getMessage("field.error.number_length", 10), 1L, 9999999999L))
            .bind(usageBatch -> usageBatch.getRro().getAccountNumber(),
                (usageBatch, value) -> usageBatch.getRro().setAccountNumber(value));
        VaadinUtils.addComponentStyle(accountNumberField, "rro-account-number-field");
        return accountNumberField;
    }

    private TextField initRightsholderAccountNameField() {
        accountNameField = new TextField(ForeignUi.getMessage("label.rro_account_name"));
        accountNameField.setRequiredIndicatorVisible(true);
        accountNameField.setReadOnly(true);
        accountNameField.setWidthFull();
        binder.forField(accountNameField)
            .asRequired(ForeignUi.getMessage("field.error.rro_name.empty"))
            .bind(usageBatch -> usageBatch.getRro().getName(),
                (usageBatch, value) -> usageBatch.getRro().setName(value));
        VaadinUtils.addComponentStyle(accountNameField, "rro-account-name-field");
        return accountNameField;
    }

    private Button initVerifyButton() {
        var button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.setWidth("72px"); //TODO {aliakh} check
        button.addClickListener(event -> {
            if (BindingValidationStatus.Status.OK == accountNumberBinding.validate().getStatus()) {
                rro = usagesController.getRightsholder(accountNumberField.getValue());
                if (StringUtils.isNotBlank(rro.getName())) {
                    accountNameField.setValue(rro.getName());
                } else {
                    accountNameField.clear();
                }
            }
        });
        return button;
    }

    private HorizontalLayout initPaymentDataLayout() {
        var horizontalLayout = new HorizontalLayout(initPaymentDateWidget(), initFundPoolPeriodFromField(),
            initFundPoolPeriodToField());
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        horizontalLayout.setWidthFull();
        return horizontalLayout;
    }

    private LocalDateWidget initPaymentDateWidget() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date"));
        paymentDateWidget.setWidth("30%");
        binder.forField(paymentDateWidget)
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .bind(UsageBatch::getPaymentDate, UsageBatch::setPaymentDate);
        VaadinUtils.addComponentStyle(paymentDateWidget, "payment-date-field");
        return paymentDateWidget;
    }

    private TextField initFundPoolPeriodFromField() {
        fundPoolPeriodFromField = new TextField(ForeignUi.getMessage("label.fund.pool.period.from"));
        fundPoolPeriodFromField.setRequiredIndicatorVisible(true);
        fundPoolPeriodFromField.setSizeFull();
        fundPoolPeriodFromField.addValueChangeListener(event -> stringBinder.validate());
        VaadinUtils.addComponentStyle(fundPoolPeriodFromField, "fund-pool-period-from-field");
        stringBinder.forField(fundPoolPeriodFromField)
            .withValidator(new RequiredValidator())
            .withValidator(getNumericValidator(), ForeignUi.getMessage(NOT_NUMERIC_MESSAGE))
            .withValidator(getYearValidator(), ForeignUi.getMessage("field.error.number_not_in_range",
                MIN_YEAR, MAX_YEAR))
            .bind(ValueProvider.identity(), (bean, value) -> bean = value)
            .validate();
        return fundPoolPeriodFromField;
    }

    private TextField initFundPoolPeriodToField() {
        fundPoolPeriodToField = new TextField(ForeignUi.getMessage("label.fund.pool.period.to"));
        fundPoolPeriodToField.setRequiredIndicatorVisible(true);
        fundPoolPeriodToField.setSizeFull();
        VaadinUtils.addComponentStyle(fundPoolPeriodToField, "fund-pool-period-to-field");
        stringBinder.forField(fundPoolPeriodToField)
            .withValidator(new RequiredValidator())
            .withValidator(getNumericValidator(), ForeignUi.getMessage(NOT_NUMERIC_MESSAGE))
            .withValidator(getYearValidator(), ForeignUi.getMessage("field.error.number_not_in_range",
                MIN_YEAR, MAX_YEAR))
            .withValidator(value -> {
                String periodFrom = fundPoolPeriodFromField.getValue();
                return StringUtils.isEmpty(periodFrom)
                    || !getNumericValidator().test(periodFrom)
                    || !getYearValidator().test(periodFrom)
                    || 0 <= fundPoolPeriodToField.getValue().compareTo(periodFrom);
            }, ForeignUi.getMessage("field.error.greater_or_equal_to",
                ForeignUi.getMessage("label.fund.pool.period.from")))
            .bind(ValueProvider.identity(), (bean, value) -> bean = value)
            .validate();
        return fundPoolPeriodToField;
    }

    private MarketFilterWidget initMarketFilterWidget() {
        marketValidationField = new TextField(ForeignUi.getMessage("label.markets"));
        stringBinder.forField(marketValidationField)
            .withValidator(value -> Integer.parseInt(value) > 0, ForeignUi.getMessage("message.market.empty"))
            .bind(ValueProvider.identity(), (bean, value) -> bean = value)
            .validate();
        var marketFilterWidget = new MarketFilterWidget(usagesController::getMarkets);
        VaadinUtils.addComponentStyle(marketFilterWidget, "market-filter-widget");
        marketFilterWidget.addClassName(EMPTY_MARKET_STYLE);
        marketFilterWidget.addFilterSaveListener(event -> {
            int size = event.getSelectedItemsIds().size();
            marketValidationField.setValue(String.valueOf(size));
            stringBinder.validate();
            if (0 < size) {
                marketFilterWidget.removeClassName(EMPTY_MARKET_STYLE);
            } else {
                marketFilterWidget.addClassName(EMPTY_MARKET_STYLE);
            }
            selectedMarkets = event.getSelectedItemsIds();
        });
        return marketFilterWidget;
    }

    private HorizontalLayout initAmountsLayout() {
        stmAmountField = initAmountField(ForeignUi.getMessage("label.stm.amount"));
        VaadinUtils.addComponentStyle(stmAmountField, "stm-amount-field");
        nonStmAmountField = initNonStmFundPoolAmountField(ForeignUi.getMessage("label.non.stm.amount"));
        VaadinUtils.addComponentStyle(nonStmAmountField, "non-stm-amount-field");
        HorizontalLayout amountLayout = new HorizontalLayout(stmAmountField, nonStmAmountField);
        amountLayout.setSizeFull();
        return amountLayout;
    }

    private HorizontalLayout initMinAmountsLayout() {
        stmMinAmountField = initAmountField(ForeignUi.getMessage("label.stm.minimum.amount"));
        stmMinAmountField.setValue(new BigDecimal("50"));
        VaadinUtils.addComponentStyle(stmMinAmountField, "stm-minimum-amount-field");
        nonStmMinAmountField = initAmountField(ForeignUi.getMessage("label.non.stm.minimum.amount"));
        nonStmMinAmountField.setValue(new BigDecimal("7"));
        VaadinUtils.addComponentStyle(nonStmMinAmountField, "non-stm-minimum-amount-field");
        HorizontalLayout minAmountLayout = new HorizontalLayout(stmMinAmountField, nonStmMinAmountField);
        minAmountLayout.setSizeFull();
        return minAmountLayout;
    }

    private BigDecimalField initAmountField(String label) {
        var field = new BigDecimalField(label);
        field.setPrefixComponent(VaadinIcon.DOLLAR.create());
        field.setRequiredIndicatorVisible(true);
        binder.forField(field)
            .withValidator(new RequiredBigDecimalValidator())
            .withValidator(AmountRangeValidator.zeroAmountValidator())
            .bind(UsageBatch::getGrossAmount, UsageBatch::setGrossAmount);
        VaadinUtils.setMaxComponentsWidth(field);
        return field;
    }

    private BigDecimalField initNonStmFundPoolAmountField(String label) {
        var field = new BigDecimalField(label);
        field.setPrefixComponent(VaadinIcon.DOLLAR.create());
        field.setRequiredIndicatorVisible(true);
        binder.forField(field)
            .withValidator(new RequiredBigDecimalValidator())
            .withValidator(AmountRangeValidator.zeroAmountValidator())
            .withValidator(getFundPoolAmountValidator(),
                ForeignUi.getMessage("message.error.invalid_stm_or_non_stm_amount"))
            .bind(UsageBatch::getGrossAmount, UsageBatch::setGrossAmount);
        binder.addValueChangeListener(event -> binder.validate());
        VaadinUtils.setMaxComponentsWidth(field);
        return field;
    }

    private Checkbox initExcludeStmCheckBox() {
        excludeStmCheckbox = new Checkbox();
        excludeStmCheckbox.setLabel(ForeignUi.getMessage("label.exclude.stm"));
        VaadinUtils.addComponentStyle(excludeStmCheckbox, "exclude-stm-rhs-checkbox");
        return excludeStmCheckbox;
    }

    private SerializablePredicate<String> getNumericValidator() {
        return value -> StringUtils.isNumeric(StringUtils.trim(value));
    }

    //TODO {aliakh} reuse YearValidator instead
    private SerializablePredicate<String> getYearValidator() {
        return value -> Integer.parseInt(value) >= MIN_YEAR && Integer.parseInt(value) <= MAX_YEAR;
    }

    private SerializablePredicate<BigDecimal> getFundPoolAmountValidator() {
        return value -> 0 > BigDecimal.ZERO.compareTo(value)
            || 0 > BigDecimal.ZERO.compareTo(stmAmountField.getValue());
    }
}
