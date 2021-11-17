package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.ExchangeRate;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.YearValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.Range;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Setter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Window to edit UDM value.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/13/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmEditValueWindow extends Window {

    private static final String NUMBER_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
    private static final Range<Integer> DECIMAL_SCALE_RANGE = Range.closed(0, 10);
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT, Locale.US);
    private static final DecimalFormat MONEY_FORMATTER = new DecimalFormat("0.00########",
        CurrencyUtils.getParameterizedDecimalFormatSymbols());

    private final Binder<UdmValueDto> binder = new Binder<>();
    private final IUdmValueController controller;
    private final UdmValueDto udmValue;
    private final Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
    private final ClickListener saveButtonClickListener;
    private final ComboBox<UdmValueStatusEnum> valueStatusComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.value_status"));
    private final ComboBox<PublicationType> pubTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.pub_type"));
    private final TextField priceField = new TextField(ForeignUi.getMessage("label.price"));
    private final ComboBox<Currency> currencyComboBox = new ComboBox<>(ForeignUi.getMessage("label.currency"));
    private final TextField priceInUsdField = new TextField();
    private final TextField currencyExchangeRateField = new TextField();
    private final TextField currencyExchangeRateDateField = new TextField();
    private final ComboBox<String> priceTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.price_type"));
    private final ComboBox<String> priceAccessTypeComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.price_access_type"));
    private final TextField priceYearField = new TextField(ForeignUi.getMessage("label.price_year"));
    private final TextField priceSourceField = new TextField(ForeignUi.getMessage("label.price_source"));
    private final TextField priceCommentField = new TextField(ForeignUi.getMessage("label.price_comment"));
    private final TextField priceFlagField = new TextField();
    private final TextField contentField = new TextField(ForeignUi.getMessage("label.content"));
    private final TextField contentSourceField = new TextField(ForeignUi.getMessage("label.content_source"));
    private final TextField contentCommentField = new TextField(ForeignUi.getMessage("label.content_comment"));
    private final TextField contentFlagField = new TextField();
    private final TextField contentUnitPriceField = new TextField();
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));

    /**
     * Constructor.
     *
     * @param valueController  instance of {@link IUdmValueController}
     * @param selectedUdmValue UDM value to be displayed on the window
     * @param clickListener    action that should be performed after Save button was clicked
     */
    public UdmEditValueWindow(IUdmValueController valueController, UdmValueDto selectedUdmValue,
                              ClickListener clickListener) {
        controller = valueController;
        udmValue = selectedUdmValue;
        saveButtonClickListener = clickListener;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.edit_udm_value"));
        setResizable(false);
        setWidth(960, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "edit-udm-value-window");
    }

    /**
     * Constructor.
     *
     * @param valueController  instance of {@link IUdmValueController}
     * @param selectedUdmValue UDM value to be displayed on the window
     */
    public UdmEditValueWindow(IUdmValueController valueController, UdmValueDto selectedUdmValue) {
        this(valueController, selectedUdmValue, null);
        saveButton.setVisible(false);
        setCaption(ForeignUi.getMessage("window.view_udm_value"));
    }

    /**
     * Recalculates price in USD as the product of price and currency exchange rate
     * and sets currency exchange rate and currency exchange rate date.
     */
    void recalculatePriceInUsd() {
        if (Objects.isNull(priceField.getErrorMessage()) && StringUtils.isNotBlank(priceField.getValue())
            && Objects.isNull(currencyComboBox.getErrorMessage()) && Objects.nonNull(currencyComboBox.getValue())) {
            ExchangeRate exchangeRate = controller.getExchangeRate(currencyComboBox.getValue().getCode(),
                LocalDate.now());
            BigDecimal price = NumberUtils.createBigDecimal(priceField.getValue().trim());
            BigDecimal priceInUsd = price.multiply(exchangeRate.getInverseExchangeRateValue())
                .setScale(10, BigDecimal.ROUND_HALF_UP);
            currencyExchangeRateField.setValue(exchangeRate.getInverseExchangeRateValue().toString());
            currencyExchangeRateDateField.setValue(DateUtils.format(exchangeRate.getExchangeRateUpdateDate()));
            priceInUsdField.setValue(CurrencyUtils.format(priceInUsd, MONEY_FORMATTER));
        } else {
            currencyExchangeRateField.clear();
            currencyExchangeRateDateField.clear();
            priceInUsdField.clear();
        }
        recalculateContentUnitPrice();
    }

    /**
     * Recalculates the flag field: clears it if the value field is not valid,
     * sets it to "Y" if the value field is valid and not blank,
     * sets it to "N" if the value field is valid and blank.
     *
     * @param valueField the value field
     * @param flagField  the flag field
     */
    void recalculateFlag(TextField valueField, TextField flagField) {
        if (Objects.isNull(valueField.getErrorMessage())) {
            flagField.setValue(StringUtils.isNotBlank(valueField.getValue()) ? "Y" : "N");
        } else {
            flagField.clear();
        }
    }

    /**
     * Recalculates content unit price as the quotient of price in usd and content.
     */
    void recalculateContentUnitPrice() {
        if (Objects.isNull(priceInUsdField.getErrorMessage()) && StringUtils.isNotBlank(priceInUsdField.getValue())
            && Objects.isNull(contentField.getErrorMessage()) && StringUtils.isNotBlank(contentField.getValue())) {
            BigDecimal priceInUsd = NumberUtils.createBigDecimal(priceInUsdField.getValue().trim());
            BigDecimal content = NumberUtils.createBigDecimal(contentField.getValue().trim());
            BigDecimal contentUnitPrice = priceInUsd.divide(content, 10, BigDecimal.ROUND_HALF_UP);
            contentUnitPriceField.setValue(CurrencyUtils.format(contentUnitPrice, MONEY_FORMATTER));
        } else {
            contentUnitPriceField.clear();
        }
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout editFieldsLayout = new VerticalLayout();
        editFieldsLayout.addComponents(
            new HorizontalLayout(
                initLeftColumn(),
                initRightColumn()
            )
        );
        Panel panel = new Panel(editFieldsLayout);
        panel.setSizeFull();
        editFieldsLayout.setMargin(false);
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        binder.validate();
        binder.readBean(udmValue);
        binder.addValueChangeListener(event -> saveButton.setEnabled(binder.hasChanges()));
        return rootLayout;
    }

    private VerticalLayout initLeftColumn() {
        return buildVerticalLayoutWithFixedWidth(
            new Panel(ForeignUi.getMessage("label.work_information"), new VerticalLayout(
                buildReadOnlyLayout("label.system_title", UdmValueDto::getSystemTitle),
                buildReadOnlyLayout("label.wr_wrk_inst", bean -> Objects.toString(bean.getWrWrkInst())),
                buildReadOnlyLayout("label.system_standard_number", UdmValueDto::getSystemStandardNumber),
                buildReadOnlyLayout("label.rh_name", UdmValueDto::getRhName),
                buildReadOnlyLayout("label.rh_account_number",
                    bean -> Objects.toString(bean.getRhAccountNumber()))
            )),
            new Panel(ForeignUi.getMessage("label.price"), new VerticalLayout(
                buildPriceLayout(),
                buildCurrencyLayout(),
                buildReadOnlyLayout(currencyExchangeRateField, "label.currency_exchange_rate",
                    fromBigDecimalToString(UdmValueDto::getCurrencyExchangeRate),
                    fromStringToBigDecimal(UdmValueDto::setCurrencyExchangeRate)),
                buildReadOnlyLayout(currencyExchangeRateDateField, "label.currency_exchange_rate_date",
                    fromLocalDateToString(UdmValueDto::getCurrencyExchangeRateDate),
                    fromStringToLocalDate(UdmValueDto::setCurrencyExchangeRateDate)),
                buildReadOnlyLayout(priceInUsdField, "label.price_in_usd",
                    fromBigDecimalToMoneyString(UdmValueDto::getPriceInUsd),
                    fromStringToBigDecimal(UdmValueDto::setPriceInUsd)),
                buildPriceTypeLayout(),
                buildPriceAccessTypeLayout(),
                buildPriceYearLayout(),
                buildPriceSourceLayout(),
                buildEditableStringLayout(priceCommentField, "label.price_comment", 1000,
                    UdmValueDto::getPriceComment, UdmValueDto::setPriceComment,
                    "udm-value-edit-price-comment-field"),
                buildReadOnlyLayout(priceFlagField, "label.price_flag",
                    fromBooleanToYNString(UdmValueDto::isPriceFlag),
                    fromYNStringToBoolean(UdmValueDto::setPriceFlag)),
                buildReadOnlyLayout("label.last_price_in_usd",
                    fromBigDecimalToMoneyString(UdmValueDto::getLastPriceInUsd)),
                buildReadOnlyLayout("label.last_price_source", UdmValueDto::getLastPriceSource),
                buildReadOnlyLayout("label.last_price_comment", UdmValueDto::getLastPriceComment),
                buildReadOnlyLayout("label.last_price_flag",
                    bean -> BooleanUtils.toYNString(bean.isLastPriceFlag()))
            ))
        );
    }

    private VerticalLayout initRightColumn() {
        return buildVerticalLayoutWithFixedWidth(
            new Panel(ForeignUi.getMessage("label.general"), new VerticalLayout(
                buildReadOnlyLayout("label.value_period", bean -> Objects.toString(bean.getValuePeriod())),
                buildReadOnlyLayout("label.last_value_period",
                    bean -> Objects.toString(bean.getLastValuePeriod(), StringUtils.EMPTY)),
                buildReadOnlyLayout("label.assignee", UdmValueDto::getAssignee),
                initValueStatusLayout()
            )),
            new Panel(ForeignUi.getMessage("label.publication_type"), new VerticalLayout(
                buildPubTypeLayout(),
                buildReadOnlyLayout("label.last_pub_type", UdmValueDto::getLastPubType)
            )),
            new Panel(ForeignUi.getMessage("label.content"), new VerticalLayout(
                buildContentLayout(),
                buildContentSourceLayout(),
                buildEditableStringLayout(contentCommentField, "label.content_comment", 1000,
                    UdmValueDto::getContentComment, UdmValueDto::setContentComment,
                    "udm-value-edit-content-comment-field"),
                buildReadOnlyLayout(contentFlagField, "label.content_flag",
                    fromBooleanToYNString(UdmValueDto::isContentFlag),
                    fromYNStringToBoolean(UdmValueDto::setContentFlag)),
                buildReadOnlyLayout("label.last_content",
                    fromBigDecimalToMoneyString(UdmValueDto::getLastContent)),
                buildReadOnlyLayout("label.last_content_source", UdmValueDto::getLastContentSource),
                buildReadOnlyLayout("label.last_content_comment", UdmValueDto::getLastContentComment),
                buildReadOnlyLayout("label.last_content_flag",
                    bean -> BooleanUtils.toYNString(bean.isLastContentFlag())),
                buildReadOnlyLayout(contentUnitPriceField, "label.content_unit_price",
                    fromBigDecimalToMoneyString(UdmValueDto::getContentUnitPrice),
                    fromStringToBigDecimal(UdmValueDto::setContentUnitPrice))
            )),
            new Panel(ForeignUi.getMessage("label.comment"), new VerticalLayout(
                buildEditableStringLayout(commentField, "label.comment", 1000,
                    UdmValueDto::getComment, UdmValueDto::setComment,
                    "udm-value-edit-comment-field")
            )),
            new Panel(new VerticalLayout(
                buildReadOnlyLayout("label.updated_by", UdmValueDto::getUpdateUser),
                buildReadOnlyLayout("label.updated_date",
                    bean -> DateUtils.format(bean.getUpdateDate()))
            ))
        );
    }

    private VerticalLayout buildVerticalLayoutWithFixedWidth(Component... children) {
        VerticalLayout verticalLayout = new VerticalLayout(children);
        verticalLayout.setWidth(450, Unit.PIXELS);
        return verticalLayout;
    }

    private HorizontalLayout buildReadOnlyLayout(TextField textField, String caption,
                                                 ValueProvider<UdmValueDto, String> getter,
                                                 Setter<UdmValueDto, String> setter) {
        textField.setReadOnly(true);
        textField.setSizeFull();
        binder.forField(textField).bind(getter, setter);
        return buildCommonLayout(textField, ForeignUi.getMessage(caption));
    }

    private HorizontalLayout buildReadOnlyLayout(String caption, ValueProvider<UdmValueDto, String> getter) {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setSizeFull();
        binder.forField(textField).bind(getter, null);
        return buildCommonLayout(textField, ForeignUi.getMessage(caption));
    }

    private HorizontalLayout buildEditableStringLayout(TextField textField, String caption, int maxLength,
                                                       ValueProvider<UdmValueDto, String> getter,
                                                       Setter<UdmValueDto, String> setter, String styleName) {
        textField.setSizeFull();
        binder.forField(textField)
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", maxLength), 0, maxLength))
            .bind(getter, setter);
        VaadinUtils.addComponentStyle(textField, styleName);
        return buildCommonLayout(textField, ForeignUi.getMessage(caption));
    }

    private HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(labelCaption);
        label.addStyleName(Cornerstone.LABEL_BOLD);
        label.setWidth(175, Unit.PIXELS);
        HorizontalLayout layout = new HorizontalLayout(label, component);
        layout.setSizeFull();
        layout.setExpandRatio(component, 1);
        return layout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        saveButton.setEnabled(false);
        saveButton.addClickListener(event -> {
            try {
                binder.writeBean(udmValue);
                controller.updateValue(udmValue);
                saveButtonClickListener.buttonClick(event);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(Arrays.asList(valueStatusComboBox, pubTypeComboBox, priceField,
                    currencyComboBox, priceTypeComboBox, priceAccessTypeComboBox, priceYearField, priceSourceField,
                    priceCommentField, contentField, contentSourceField, contentCommentField, commentField));
            }
        });
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> binder.readBean(udmValue));
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private HorizontalLayout initValueStatusLayout() {
        valueStatusComboBox.setSizeFull();
        valueStatusComboBox.setEmptySelectionAllowed(false);
        valueStatusComboBox.setItems(UdmValueStatusEnum.NEW,
            UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD,
            UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE,
            UdmValueStatusEnum.NEEDS_FURTHER_REVIEW,
            UdmValueStatusEnum.RESEARCH_COMPLETE);
        valueStatusComboBox.setSelectedItem(udmValue.getStatus());
        valueStatusComboBox.addValueChangeListener(event -> binder.validate());
        binder.forField(valueStatusComboBox).bind(UdmValueDto::getStatus, UdmValueDto::setStatus);
        VaadinUtils.addComponentStyle(valueStatusComboBox, "udm-value-edit-value-status-combo-box");
        return buildCommonLayout(valueStatusComboBox, ForeignUi.getMessage("label.value_status"));
    }

    private HorizontalLayout buildPubTypeLayout() {
        pubTypeComboBox.setSizeFull();
        List<PublicationType> pubTypes = controller.getAllPublicationTypes();
        pubTypeComboBox.setItems(pubTypes);
        pubTypeComboBox.setPageLength(12);
        pubTypeComboBox.setItemCaptionGenerator(value -> Objects.nonNull(value.getName())
            ? String.format("%s - %s", value.getName(), value.getDescription())
            : StringUtils.EMPTY);
        pubTypeComboBox.setSelectedItem(udmValue.getPublicationType());
        pubTypeComboBox.addValueChangeListener(event -> binder.validate());
        binder.forField(pubTypeComboBox).bind(UdmValueDto::getPublicationType, UdmValueDto::setPublicationType);
        VaadinUtils.addComponentStyle(pubTypeComboBox, "udm-value-edit-pub-type-combo-box");
        return buildCommonLayout(pubTypeComboBox, ForeignUi.getMessage("label.pub_type"));
    }

    private HorizontalLayout buildPriceLayout() {
        priceField.setSizeFull();
        binder.forField(priceField)
            .withValidator(value -> StringUtils.isBlank(value) || NumberUtils.isNumber(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new AmountValidator())
            .withValidator(value -> StringUtils.isBlank(value) ||
                    DECIMAL_SCALE_RANGE.contains(NumberUtils.createBigDecimal(value.trim()).scale()),
                ForeignUi.getMessage("field.error.number_scale", DECIMAL_SCALE_RANGE.upperEndpoint()))
            .bind(bean -> CurrencyUtils.format(bean.getPrice(), MONEY_FORMATTER),
                (bean, value) -> bean.setPrice(StringUtils.isNotBlank(value)
                    ? NumberUtils.createBigDecimal(value.trim()) : null));
        priceField.addValueChangeListener(event -> {
            if (event.isUserOriginated()) {
                recalculatePriceInUsd();
                recalculateFlag(priceField, priceFlagField);
            }
        });
        VaadinUtils.addComponentStyle(priceField, "udm-value-edit-price-field");
        return buildCommonLayout(priceField, ForeignUi.getMessage("label.price"));
    }

    private HorizontalLayout buildCurrencyLayout() {
        currencyComboBox.setSizeFull();
        List<Currency> currencies = controller.getAllCurrencies();
        currencyComboBox.setItems(currencies);
        currencyComboBox.setItemCaptionGenerator(currency -> Objects.nonNull(currency)
            ? String.format("%s - %s", currency.getCode(), currency.getDescription())
            : StringUtils.EMPTY);
        currencyComboBox.setSelectedItem(currencies
            .stream()
            .filter(currency -> currency.getCode().equals(udmValue.getCurrency()))
            .findFirst()
            .orElse(null));
        currencyComboBox.addValueChangeListener(event -> {
            binder.validate();
            if (event.isUserOriginated()) {
                recalculatePriceInUsd();
            }
        });
        binder.forField(currencyComboBox)
            .withValidator(value -> Objects.nonNull(value) || StringUtils.isBlank(priceField.getValue()),
                ForeignUi.getMessage("field.error.empty_if_price_specified"))
            .bind(bean -> currencies
                    .stream()
                    .filter(currency -> currency.getCode().equals(bean.getCurrency()))
                    .findFirst()
                    .orElse(null),
                (bean, value) -> bean.setCurrency(Objects.nonNull(value) ? value.getCode() : null));
        VaadinUtils.addComponentStyle(currencyComboBox, "udm-value-edit-currency-combo-box");
        return buildCommonLayout(currencyComboBox, ForeignUi.getMessage("label.currency"));
    }

    private HorizontalLayout buildPriceTypeLayout() {
        priceTypeComboBox.setSizeFull();
        priceTypeComboBox.setItems(controller.getAllPriceTypes());
        priceTypeComboBox.setSelectedItem(udmValue.getPriceType());
        priceTypeComboBox.addValueChangeListener(event -> binder.validate());
        binder.forField(priceTypeComboBox).bind(UdmValueDto::getPriceType, UdmValueDto::setPriceType);
        VaadinUtils.addComponentStyle(priceTypeComboBox, "udm-value-edit-price-type-combo-box");
        return buildCommonLayout(priceTypeComboBox, ForeignUi.getMessage("label.price_type"));
    }

    private HorizontalLayout buildPriceAccessTypeLayout() {
        priceAccessTypeComboBox.setSizeFull();
        priceAccessTypeComboBox.setItems(controller.getAllPriceAccessTypes());
        priceAccessTypeComboBox.setSelectedItem(udmValue.getPriceAccessType());
        priceAccessTypeComboBox.addValueChangeListener(event -> binder.validate());
        binder.forField(priceAccessTypeComboBox).bind(UdmValueDto::getPriceAccessType, UdmValueDto::setPriceAccessType);
        VaadinUtils.addComponentStyle(priceAccessTypeComboBox, "udm-value-edit-price-access-type-combo-box");
        return buildCommonLayout(priceAccessTypeComboBox, ForeignUi.getMessage("label.price_access_type"));
    }

    private HorizontalLayout buildPriceYearLayout() {
        priceYearField.setSizeFull();
        binder.forField(priceYearField)
            .withValidator(value -> StringUtils.isBlank(value) || StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new YearValidator())
            .bind(bean -> Objects.toString(bean.getPriceYear(), StringUtils.EMPTY),
                (bean, value) -> bean.setPriceYear(NumberUtils.createInteger(StringUtils.trimToNull(value))));
        VaadinUtils.addComponentStyle(priceYearField, "udm-value-edit-price-year-field");
        return buildCommonLayout(priceYearField, ForeignUi.getMessage("label.price_year"));
    }

    private HorizontalLayout buildPriceSourceLayout() {
        priceSourceField.setSizeFull();
        binder.forField(priceSourceField)
            .withValidator(value -> StringUtils.isNotBlank(value) || StringUtils.isBlank(priceField.getValue()),
                ForeignUi.getMessage("field.error.empty_if_price_specified"))
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1000), 0, 1000))
            .bind(UdmValueDto::getPriceSource, UdmValueDto::setPriceSource);
        VaadinUtils.addComponentStyle(priceSourceField, "udm-value-edit-price-source-field");
        return buildCommonLayout(priceSourceField, ForeignUi.getMessage("label.price_source"));
    }

    private HorizontalLayout buildContentLayout() {
        contentField.setSizeFull();
        binder.forField(contentField)
            .withValidator(value -> StringUtils.isBlank(value) || NumberUtils.isNumber(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new AmountZeroValidator())
            .withValidator(value -> StringUtils.isBlank(value) ||
                    DECIMAL_SCALE_RANGE.contains(NumberUtils.createBigDecimal(value.trim()).scale()),
                ForeignUi.getMessage("field.error.number_scale", DECIMAL_SCALE_RANGE.upperEndpoint()))
            .bind(bean -> CurrencyUtils.format(bean.getContent(), MONEY_FORMATTER),
                (bean, value) -> bean.setContent(StringUtils.isNotBlank(value)
                    ? NumberUtils.createBigDecimal(value.trim()) : null));
        contentField.addValueChangeListener(event -> {
            if (event.isUserOriginated()) {
                recalculateContentUnitPrice();
                recalculateFlag(contentField, contentFlagField);
            }
        });
        VaadinUtils.addComponentStyle(contentField, "udm-value-edit-content-field");
        return buildCommonLayout(contentField, ForeignUi.getMessage("label.content"));
    }

    private HorizontalLayout buildContentSourceLayout() {
        contentSourceField.setSizeFull();
        binder.forField(contentSourceField)
            .withValidator(value -> StringUtils.isNotBlank(value) || StringUtils.isBlank(contentField.getValue()),
                ForeignUi.getMessage("field.error.empty_if_content_specified"))
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1000), 0, 1000))
            .bind(UdmValueDto::getContentSource, UdmValueDto::setContentSource);
        VaadinUtils.addComponentStyle(contentSourceField, "udm-value-edit-content-source-field");
        return buildCommonLayout(contentSourceField, ForeignUi.getMessage("label.content_source"));
    }

    private ValueProvider<UdmValueDto, String> fromLocalDateToString(ValueProvider<UdmValueDto, LocalDate> getter) {
        return bean -> DateUtils.format(getter.apply(bean));
    }

    private Setter<UdmValueDto, String> fromStringToLocalDate(Setter<UdmValueDto, LocalDate> setter) {
        return (bean, value) -> setter.accept(bean, StringUtils.isNotEmpty(value)
            ? LocalDate.parse(value, DATE_TIME_FORMATTER) : null);
    }

    private ValueProvider<UdmValueDto, String> fromBooleanToYNString(ValueProvider<UdmValueDto, Boolean> getter) {
        return bean -> BooleanUtils.toYNString(getter.apply(bean));
    }

    private Setter<UdmValueDto, String> fromYNStringToBoolean(Setter<UdmValueDto, Boolean> setter) {
        return (bean, value) -> setter.accept(bean, Objects.nonNull(value) ? "Y".equals(value) : null);
    }

    private ValueProvider<UdmValueDto, String> fromBigDecimalToString(ValueProvider<UdmValueDto, BigDecimal> getter) {
        return bean -> Objects.toString(getter.apply(bean), StringUtils.EMPTY);
    }

    private ValueProvider<UdmValueDto, String> fromBigDecimalToMoneyString(
        ValueProvider<UdmValueDto, BigDecimal> getter) {
        return bean -> CurrencyUtils.format(getter.apply(bean), MONEY_FORMATTER);
    }

    private Setter<UdmValueDto, String> fromStringToBigDecimal(Setter<UdmValueDto, BigDecimal> setter) {
        return (bean, value) -> setter.accept(bean,
            StringUtils.isNotBlank(value) ? NumberUtils.createBigDecimal(value.trim()) : null);
    }
}