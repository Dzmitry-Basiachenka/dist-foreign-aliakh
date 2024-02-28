package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.vui.common.validator.AmountRangeValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredNumberValidator;
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
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.validator.LongRangeValidator;
import com.vaadin.flow.data.validator.RangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;
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
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;
    private static final String YEAR_ERROR_MESSAGE =
        ForeignUi.getMessage("field.error.number_not_in_range", MIN_YEAR, MAX_YEAR);

    private final INtsUsageController usageController;
    private final Binder<UsageBatch> binder = new Binder<>();
    private final Div errorDiv = new Div();

    private VerticalLayout marketLayout;
    private LongField accountNumberField;
    private Binder.Binding<UsageBatch, Long> accountNumberBinding;
    private TextField accountNameField;
    private LocalDateWidget paymentDateWidget;
    private IntegerField fundPoolPeriodFromField;
    private IntegerField fundPoolPeriodToField;
    private Set<String> selectedMarkets;
    private BigDecimalField stmAmountField;

    /**
     * Constructor.
     *
     * @param usageController instance of {@link INtsUsageController}
     */
    FundPoolLoadWindow(INtsUsageController usageController) {
        this.usageController = usageController;
        super.setWidth("520px");
        super.setHeight("510px");
        super.setHeaderTitle(ForeignUi.getMessage("window.load_fund_pool"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("fund-pool-upload-window", false);
        initBinder();
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            var usageBatch = binder.getBean();
            usageBatch.setFiscalYear(UsageBatchUtils.calculateFiscalYear(paymentDateWidget.getValue()));
            usageBatch.getNtsFields().setMarkets(selectedMarkets);
            int usagesCount = usageController.getUsagesCountForNtsBatch(usageBatch);
            if (0 < usagesCount) {
                int insertedUsages = usageController.loadNtsBatch(usageBatch);
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
        var isBinderValid = binder.validate().isOk();
        var areMarketsValid = validateMarkets();
        return areMarketsValid && isBinderValid;
    }

    private VerticalLayout initRootLayout() {
        var rootLayout = VaadinUtils.initCommonVerticalLayout();
        rootLayout.add(initUsageBatchNameField(), initRightsholderLayout(), initPaymentDataLayout(),
            initMarketFilterWidget(), initAmountsLayout(), initMinAmountsLayout(), initExcludeStmCheckBox());
        VaadinUtils.setPadding(rootLayout, 10, 10, 0, 10);
        return rootLayout;
    }

    private void initBinder() {
        var usageBatch = new UsageBatch();
        usageBatch.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
        usageBatch.setRro(new Rightsholder());
        var ntsFields = new UsageBatch.NtsFields();
        ntsFields.setStmMinimumAmount(new BigDecimal("50"));
        ntsFields.setNonStmMinimumAmount(new BigDecimal("7"));
        usageBatch.setNtsFields(ntsFields);
        binder.setBean(usageBatch);
    }

    private TextField initUsageBatchNameField() {
        var usageBatchNameField = new TextField(ForeignUi.getMessage("label.usage_batch_name"));
        usageBatchNameField.setRequiredIndicatorVisible(true);
        usageBatchNameField.setWidthFull();
        binder.forField(usageBatchNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usageController.usageBatchExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Usage Batch"))
            .bind(UsageBatch::getName, UsageBatch::setName);
        VaadinUtils.addComponentStyle(usageBatchNameField, "usage-batch-name-field");
        return usageBatchNameField;
    }

    private HorizontalLayout initButtonsLayout() {
        var uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        return new HorizontalLayout(uploadButton, Buttons.createCloseButton(this));
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
            .withValidator(new RequiredNumberValidator())
            .withValidator(
                new LongRangeValidator(ForeignUi.getMessage("field.error.number_length", 10), 1L, 9999999999L))
            .bind(batch -> batch.getRro().getAccountNumber(),
                (batch, value) -> batch.getRro().setAccountNumber(value));
        VaadinUtils.addComponentStyle(accountNumberField, "rro-account-number-field");
        return accountNumberField;
    }

    private TextField initRightsholderAccountNameField() {
        accountNameField = new TextField(ForeignUi.getMessage("label.rro_account_name"));
        accountNameField.setWidth("50%");
        accountNameField.setRequiredIndicatorVisible(true);
        accountNameField.setReadOnly(true);
        binder.forField(accountNameField)
            .asRequired(ForeignUi.getMessage("field.error.rro_name.empty"))
            .bind(batch -> batch.getRro().getName(),
                (batch, value) -> batch.getRro().setName(value));
        VaadinUtils.addComponentStyle(accountNameField, "rro-account-name-field");
        return accountNameField;
    }

    private Button initVerifyButton() {
        var button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.setWidth("72px");
        button.addClickListener(event -> {
            if (!accountNumberBinding.validate().isError()) {
                var rro = usageController.getRightsholder(accountNumberField.getValue());
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
        var horizontalLayout =
            new HorizontalLayout(initPaymentDateWidget(), initFundPoolPeriodFromField(), initFundPoolPeriodToField());
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

    private IntegerField initFundPoolPeriodFromField() {
        fundPoolPeriodFromField = new IntegerField(ForeignUi.getMessage("label.fund.pool.period.from"));
        fundPoolPeriodFromField.setRequiredIndicatorVisible(true);
        fundPoolPeriodFromField.setWidth("33%");
        VaadinUtils.addComponentStyle(fundPoolPeriodFromField, "fund-pool-period-from-field");
        binder.forField(fundPoolPeriodFromField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(RangeValidator.of(YEAR_ERROR_MESSAGE, MIN_YEAR, MAX_YEAR))
            .bind(batch -> batch.getNtsFields().getFundPoolPeriodFrom(),
                (batch, value) -> batch.getNtsFields().setFundPoolPeriodFrom(value));
        return fundPoolPeriodFromField;
    }

    private IntegerField initFundPoolPeriodToField() {
        fundPoolPeriodToField = new IntegerField(ForeignUi.getMessage("label.fund.pool.period.to"));
        fundPoolPeriodToField.setRequiredIndicatorVisible(true);
        fundPoolPeriodToField.setSizeFull();
        fundPoolPeriodToField.setWidth("30%");
        VaadinUtils.addComponentStyle(fundPoolPeriodToField, "fund-pool-period-to-field");
        binder.forField(fundPoolPeriodToField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(RangeValidator.of(YEAR_ERROR_MESSAGE, MIN_YEAR, MAX_YEAR))
            .withValidator(value -> 0 <= fundPoolPeriodToField.getValue().compareTo(fundPoolPeriodFromField.getValue()),
                ForeignUi.getMessage("field.error.greater_or_equal_to",
                    ForeignUi.getMessage("label.fund.pool.period.from")))
            .bind(batch -> batch.getNtsFields().getFundPoolPeriodTo(),
                (batch, value) -> batch.getNtsFields().setFundPoolPeriodTo(value));
        return fundPoolPeriodToField;
    }

    private VerticalLayout initMarketFilterWidget() {
        var marketFilterWidget = new MarketFilterWidget(usageController::getMarkets);
        marketFilterWidget.addFilterSaveListener(event -> {
            selectedMarkets = event.getSelectedItemsIds();
            validateMarkets();
        });
        VaadinUtils.addComponentStyle(marketFilterWidget, "market-filter-widget");
        initErrorMarketDiv();
        marketLayout = VaadinUtils.initCommonVerticalLayout(marketFilterWidget);
        return marketLayout;
    }

    private void initErrorMarketDiv() {
        errorDiv.getElement().setProperty("innerHTML", ForeignUi.getMessage("message.market.empty"));
        errorDiv.setClassName(EMPTY_MARKET_STYLE);
    }

    private HorizontalLayout initAmountsLayout() {
        stmAmountField = initAmountField(ForeignUi.getMessage("label.stm.amount"),
            batch -> batch.getNtsFields().getStmAmount(),
            (batch, value) -> batch.getNtsFields().setStmAmount(value));
        VaadinUtils.addComponentStyle(stmAmountField, "stm-amount-field");
        var nonStmAmountField = initNonStmFundPoolAmountField(ForeignUi.getMessage("label.non.stm.amount"));
        VaadinUtils.addComponentStyle(nonStmAmountField, "non-stm-amount-field");
        var amountLayout = new HorizontalLayout(stmAmountField, nonStmAmountField);
        amountLayout.setSizeFull();
        return amountLayout;
    }

    private HorizontalLayout initMinAmountsLayout() {
        var stmMinAmountField = initAmountField(ForeignUi.getMessage("label.stm.minimum.amount"),
            batch -> batch.getNtsFields().getStmMinimumAmount(),
            (batch, value) -> batch.getNtsFields().setStmMinimumAmount(value));
        VaadinUtils.addComponentStyle(stmMinAmountField, "stm-minimum-amount-field");
        var nonStmMinAmountField = initAmountField(ForeignUi.getMessage("label.non.stm.minimum.amount"),
            batch -> batch.getNtsFields().getNonStmMinimumAmount(),
            (batch, value) -> batch.getNtsFields().setNonStmMinimumAmount(value));
        VaadinUtils.addComponentStyle(nonStmMinAmountField, "non-stm-minimum-amount-field");
        var minAmountLayout = new HorizontalLayout(stmMinAmountField, nonStmMinAmountField);
        minAmountLayout.setSizeFull();
        return minAmountLayout;
    }

    private BigDecimalField initAmountField(String label, ValueProvider<UsageBatch, BigDecimal> getter,
                                            Setter<UsageBatch, BigDecimal> setter) {
        var field = new BigDecimalField(label);
        field.setWidthFull();
        field.setPrefixComponent(VaadinIcon.DOLLAR.create());
        field.setRequiredIndicatorVisible(true);
        binder.forField(field)
            .withValidator(new RequiredNumberValidator())
            .withValidator(AmountRangeValidator.zeroAmountValidator())
            .bind(getter, setter);
        return field;
    }

    private BigDecimalField initNonStmFundPoolAmountField(String label) {
        var field = new BigDecimalField(label);
        field.setWidthFull();
        field.setPrefixComponent(VaadinIcon.DOLLAR.create());
        field.setRequiredIndicatorVisible(true);
        binder.forField(field)
            .withValidator(new RequiredNumberValidator())
            .withValidator(AmountRangeValidator.zeroAmountValidator())
            .withValidator(getFundPoolAmountValidator(),
                ForeignUi.getMessage("message.error.invalid_stm_or_non_stm_amount"))
            .bind(batch -> batch.getNtsFields().getNonStmAmount(),
                (batch, value) -> batch.getNtsFields().setNonStmAmount(value));
        return field;
    }

    private Checkbox initExcludeStmCheckBox() {
        var excludeStmCheckbox = new Checkbox();
        excludeStmCheckbox.setLabel(ForeignUi.getMessage("label.exclude.stm"));
        VaadinUtils.addComponentStyle(excludeStmCheckbox, "exclude-stm-rhs-checkbox");
        binder.forField(excludeStmCheckbox)
            .bind(batch -> batch.getNtsFields().isExcludingStm(),
                (batch, value) -> batch.getNtsFields().setExcludingStm(value));
        return excludeStmCheckbox;
    }

    private SerializablePredicate<BigDecimal> getFundPoolAmountValidator() {
        return value -> 0 > BigDecimal.ZERO.compareTo(value) || 0 > BigDecimal.ZERO.compareTo(
            stmAmountField.getValue());
    }

    private boolean validateMarkets() {
        boolean isSelected = CollectionUtils.isNotEmpty(selectedMarkets);
        if (isSelected) {
            marketLayout.remove(errorDiv);
            marketLayout.removeClassName(EMPTY_MARKET_STYLE);
        } else {
            marketLayout.add(errorDiv);
            marketLayout.addClassName(EMPTY_MARKET_STYLE);
        }
        return isSelected;
    }
}
