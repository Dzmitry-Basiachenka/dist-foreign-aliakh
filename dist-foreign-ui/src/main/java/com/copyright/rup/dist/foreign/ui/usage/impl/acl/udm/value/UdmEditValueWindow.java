package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.ExchangeRate;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.impl.UdmValueAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.YearValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmValueWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Setter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
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
public class UdmEditValueWindow extends CommonUdmValueWindow {

    private static final String NUMBER_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
    private static final String YES = "Y";
    private static final String NO = "N";

    private final Binder<UdmValueDto> binder = new Binder<>();
    private final IUdmValueController controller;
    private final UdmValueDto udmValue;
    private final Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
    private final Button editButton = Buttons.createButton(ForeignUi.getMessage("button.edit_cup"));
    private final Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
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
    private final TextField contentUnitPriceField = new TextField(ForeignUi.getMessage("label.content_unit_price"));
    private final TextField contentUnitPriceFlagField = new TextField(
        ForeignUi.getMessage("label.content_unit_price_flag"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
    private final UdmValueAuditFieldToValuesMap fieldToValueChangesMap;

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
        fieldToValueChangesMap = new UdmValueAuditFieldToValuesMap(udmValue);
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.edit_udm_value"));
        setResizable(false);
        setWidth(960, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "edit-udm-value-window");
    }

    /**
     * Recalculates price in USD as the product of price and currency exchange rate
     * and sets currency exchange rate and currency exchange rate date.
     */
    void recalculatePriceInUsd() {
        if (Objects.isNull(priceField.getErrorMessage()) && StringUtils.isNotBlank(priceField.getValue())
            && Objects.isNull(currencyComboBox.getErrorMessage()) && Objects.nonNull(currencyComboBox.getValue())) {
            ExchangeRate exchangeRate =
                controller.getExchangeRate(currencyComboBox.getValue().getCode(), LocalDate.now());
            BigDecimal price = NumberUtils.createBigDecimal(priceField.getValue().trim());
            BigDecimal currencyExchangeRate =
                exchangeRate.getInverseExchangeRateValue().setScale(10, RoundingMode.HALF_UP);
            BigDecimal priceInUsd = price.multiply(currencyExchangeRate).setScale(10, RoundingMode.HALF_UP);
            currencyExchangeRateField.setValue(currencyExchangeRate.toString());
            currencyExchangeRateDateField.setValue(toShortFormat(exchangeRate.getExchangeRateUpdateDate()));
            priceInUsdField.setValue(convertFromBigDecimalToMoneyString(priceInUsd));
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
            flagField.setValue(StringUtils.isNotBlank(valueField.getValue()) ? YES : NO);
        } else {
            flagField.clear();
        }
    }

    /**
     * Recalculates CUP Flag field: clears if Price Flag or/and Content Flag is empty,
     * set it to "Y" if Price and Content flags are equal "Y", otherwise "N".
     */
    void recalculateContentUnitPriceFlag() {
        String priceFlag = priceFlagField.getValue();
        String contentFlag = contentFlagField.getValue();
        if (StringUtils.isEmpty(priceFlag) || StringUtils.isEmpty(contentFlag)) {
            contentUnitPriceFlagField.clear();
        } else {
            contentUnitPriceFlagField.setValue(YES.equals(priceFlag) && YES.equals(contentFlag) ? YES : NO);
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
            BigDecimal contentUnitPrice = priceInUsd.divide(content, 10, RoundingMode.HALF_UP);
            contentUnitPriceField.setValue(convertFromBigDecimalToMoneyString(contentUnitPrice));
        } else {
            contentUnitPriceField.clear();
        }
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout editFieldsLayout = new VerticalLayout();
        editFieldsLayout.addComponents(new HorizontalLayout(initLeftColumn(), initRightColumn()));
        Panel panel = new Panel(editFieldsLayout);
        panel.setSizeFull();
        editFieldsLayout.setMargin(false);
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        binder.readBean(udmValue);
        binder.validate();
        binder.addValueChangeListener(event -> saveButton.setEnabled(binder.hasChanges()));
        return rootLayout;
    }

    private VerticalLayout initLeftColumn() {
        return buildVerticalLayoutWithFixedWidth(
            new Panel(ForeignUi.getMessage("label.work_information"), new VerticalLayout(
                buildReadOnlyLayout("label.system_title", UdmValueDto::getSystemTitle, binder),
                buildReadOnlyLayout("label.wr_wrk_inst", bean -> Objects.toString(bean.getWrWrkInst()), binder),
                buildReadOnlyLayout("label.system_standard_number", UdmValueDto::getSystemStandardNumber, binder),
                buildReadOnlyLayout("label.rh_name", UdmValueDto::getRhName, binder),
                buildReadOnlyLayout("label.rh_account_number",
                    bean -> Objects.toString(bean.getRhAccountNumber()), binder)
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
                    fromBigDecimalToMoneyString(UdmValueDto::getLastPriceInUsd), binder),
                buildReadOnlyLayout("label.last_price_source", UdmValueDto::getLastPriceSource, binder),
                buildReadOnlyLayout("label.last_price_comment", UdmValueDto::getLastPriceComment, binder),
                buildReadOnlyLayout("label.last_price_flag",
                    bean -> BooleanUtils.toYNString(bean.isLastPriceFlag()), binder)
            ))
        );
    }

    private VerticalLayout initRightColumn() {
        return buildVerticalLayoutWithFixedWidth(
            new Panel(ForeignUi.getMessage("label.general"), new VerticalLayout(
                buildReadOnlyLayout("label.value_period", bean -> Objects.toString(bean.getValuePeriod()), binder),
                buildReadOnlyLayout("label.last_value_period",
                    bean -> Objects.toString(bean.getLastValuePeriod(), StringUtils.EMPTY), binder),
                buildReadOnlyLayout("label.assignee", UdmValueDto::getAssignee, binder),
                initValueStatusLayout()
            )),
            new Panel(ForeignUi.getMessage("label.publication_type"), new VerticalLayout(
                buildPubTypeLayout(),
                buildReadOnlyLayout("label.last_pub_type", UdmValueDto::getLastPubType, binder)
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
                    fromBigDecimalToMoneyString(UdmValueDto::getLastContent), binder),
                buildReadOnlyLayout("label.last_content_source", UdmValueDto::getLastContentSource, binder),
                buildReadOnlyLayout("label.last_content_comment", UdmValueDto::getLastContentComment, binder),
                buildReadOnlyLayout("label.last_content_flag",
                    bean -> BooleanUtils.toYNString(bean.isLastContentFlag()), binder),
                buildContentUnitPriceLayout(contentUnitPriceField,
                    fromBigDecimalToMoneyString(UdmValueDto::getContentUnitPrice),
                    fromStringToBigDecimal(UdmValueDto::setContentUnitPrice)),
                buildContentUnitPriceFlagLayout(contentUnitPriceFlagField,
                    fromBooleanToYNString(UdmValueDto::isContentUnitPriceFlag),
                    fromYNStringToBoolean(UdmValueDto::setContentUnitPriceFlag)),
                initContentButtonsLayout()
            )),
            new Panel(ForeignUi.getMessage("label.comment"), new VerticalLayout(
                buildEditableStringLayout(commentField, "label.comment", 1000,
                    UdmValueDto::getComment, UdmValueDto::setComment,
                    "udm-value-edit-comment-field"),
                buildReadOnlyLayout("label.last_comment", UdmValueDto::getLastComment, binder)
            )),
            new Panel(new VerticalLayout(
                buildReadOnlyLayout("label.updated_by", UdmValueDto::getUpdateUser, binder),
                buildReadOnlyLayout("label.updated_date",
                    bean -> toShortFormat(bean.getUpdateDate()), binder)
            ))
        );
    }

    private VerticalLayout initContentButtonsLayout() {
        var buttonsLayout = new HorizontalLayout(editButton, clearButton);
        editButton.addClickListener(event -> {
            contentUnitPriceField.setReadOnly(false);
            contentUnitPriceFlagField.setReadOnly(false);
        });
        var editClearContentFieldsLayout = new VerticalLayout(buttonsLayout);
        editClearContentFieldsLayout.setMargin(new MarginInfo(false));
        editClearContentFieldsLayout.setSpacing(false);
        editClearContentFieldsLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        return editClearContentFieldsLayout;
    }

    private VerticalLayout buildVerticalLayoutWithFixedWidth(Component... children) {
        VerticalLayout verticalLayout = new VerticalLayout(children);
        verticalLayout.setWidth(450, Unit.PIXELS);
        return verticalLayout;
    }

    private HorizontalLayout buildReadOnlyLayout(TextField textField, String caption,
                                                 ValueProvider<UdmValueDto, String> getter,
                                                 Setter<UdmValueDto, String> setter) {
        String fieldName = ForeignUi.getMessage(caption);
        textField.setReadOnly(true);
        textField.setSizeFull();
        binder.forField(textField).bind(getter, setter);
        textField.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim()));
        return buildCommonLayout(textField, fieldName);
    }

    private HorizontalLayout buildContentUnitPriceLayout(TextField textField, ValueProvider<UdmValueDto, String> getter,
                                                         Setter<UdmValueDto, String> setter) {
        binder.forField(textField)
            .withValidator(value -> StringUtils.isBlank(value) || NumberUtils.isNumber(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new AmountValidator())
            .bind(getter, setter);
        return buildContentUnitPriceLayout(textField, "label.content_unit_price");
    }

    private HorizontalLayout buildContentUnitPriceFlagLayout(TextField textField,
                                                             ValueProvider<UdmValueDto, String> getter,
                                                             Setter<UdmValueDto, String> setter) {
        binder.forField(textField)
            .withValidator(value -> "Y".equals(value.trim()) || "N".equals(value.trim()),
                ForeignUi.getMessage("field.error.flag"))
            .bind(getter, setter);
        return buildContentUnitPriceLayout(textField, "label.content_unit_price_flag");
    }

    private HorizontalLayout buildContentUnitPriceLayout(TextField textField, String caption) {
        String fieldName = ForeignUi.getMessage(caption);
        textField.setReadOnly(true);
        textField.setSizeFull();
        textField.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim()));
        return buildCommonLayout(textField, fieldName);
    }

    private HorizontalLayout buildEditableStringLayout(TextField textField, String caption, int maxLength,
                                                       ValueProvider<UdmValueDto, String> getter,
                                                       Setter<UdmValueDto, String> setter, String styleName) {
        String fieldName = ForeignUi.getMessage(caption);
        textField.setSizeFull();
        binder.forField(textField)
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", maxLength), 0, maxLength))
            .bind(getter, (value, fieldValue) -> setter.accept(value, StringUtils.trimToNull(fieldValue)));
        textField.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim()));
        VaadinUtils.addComponentStyle(textField, styleName);
        return buildCommonLayout(textField, fieldName);
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        saveButton.setEnabled(false);
        saveButton.addClickListener(event -> {
            if (binder.isValid()) {
                if (isContentUnitPriceFieldsChanged()) {
                    Windows.showConfirmDialogWithReason(
                        ForeignUi.getMessage("window.confirm"),
                        ForeignUi.getMessage("message.confirm.update_values_cup"),
                        ForeignUi.getMessage("button.yes"),
                        ForeignUi.getMessage("button.cancel"),
                        reason -> {
                            onClickSaveButton(event, reason);
                            this.close();
                        },
                        new StringLengthValidator(ForeignUi.getMessage("field.error.empty.length", 1024), 1, 1024));
                } else {
                    onClickSaveButton(event, StringUtils.EMPTY);
                }
            } else {
                showValidationMessage();
            }
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> {
            binder.readBean(udmValue);
            binder.validate();
            saveButton.setEnabled(false);
        });
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private void onClickSaveButton(ClickEvent event, String reason) {
        try {
            binder.writeBean(udmValue);
            if (StringUtils.isNotBlank(reason)) {
                fieldToValueChangesMap.setContentUnitPriceReason(reason);
            }
            controller.updateValue(udmValue, fieldToValueChangesMap.getActionReasons());
            saveButtonClickListener.buttonClick(event);
            close();
        } catch (ValidationException e) {
            showValidationMessage();
        }
    }

    private void showValidationMessage() {
        Windows.showValidationErrorWindow(List.of(valueStatusComboBox, pubTypeComboBox, priceField,
            currencyComboBox, priceTypeComboBox, priceAccessTypeComboBox, priceYearField, priceSourceField,
            priceCommentField, contentField, contentSourceField, contentCommentField, commentField,
            contentUnitPriceField, contentUnitPriceFlagField)
        );
    }

    private boolean isContentUnitPriceFieldsChanged() {
        return !contentUnitPriceField.isReadOnly() && isPriceAndContentFieldsNotChanged()
            && (0 != (udmValue.getContentUnitPrice().compareTo(new BigDecimal(contentUnitPriceField.getValue())))
            || convertFromYNStringToBoolean(contentUnitPriceFlagField.getValue()) != udmValue.isContentUnitPriceFlag());
    }

    private boolean isPriceAndContentFieldsNotChanged() {
        return Objects.nonNull(udmValue.getPrice())
            && Objects.nonNull(udmValue.getContent())
            && 0 == udmValue.getPrice().compareTo(new BigDecimal(priceField.getValue()))
            && 0 == udmValue.getContent().compareTo(new BigDecimal(contentField.getValue()));
    }

    private HorizontalLayout initValueStatusLayout() {
        String fieldName = ForeignUi.getMessage("label.value_status");
        valueStatusComboBox.setSizeFull();
        valueStatusComboBox.setEmptySelectionAllowed(false);
        if (ForeignSecurityUtils.hasResearcherPermission()) {
            valueStatusComboBox.setItems(UdmValueStatusEnum.NEW,
                UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE);
        } else {
            valueStatusComboBox.setItems(UdmValueStatusEnum.NEW,
                UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD,
                UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE,
                UdmValueStatusEnum.NEEDS_FURTHER_REVIEW,
                UdmValueStatusEnum.RESEARCH_COMPLETE);
        }
        valueStatusComboBox.setSelectedItem(udmValue.getStatus());
        valueStatusComboBox.addValueChangeListener(
            event -> fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().name()));
        binder.forField(valueStatusComboBox).bind(UdmValueDto::getStatus, UdmValueDto::setStatus);
        VaadinUtils.addComponentStyle(valueStatusComboBox, "udm-value-edit-value-status-combo-box");
        return buildCommonLayout(valueStatusComboBox, fieldName);
    }

    private HorizontalLayout buildPubTypeLayout() {
        String fieldName = ForeignUi.getMessage("label.pub_type");
        pubTypeComboBox.setSizeFull();
        List<PublicationType> pubTypes = controller.getAllPublicationTypes();
        pubTypeComboBox.setItems(pubTypes);
        pubTypeComboBox.setPageLength(12);
        pubTypeComboBox.setItemCaptionGenerator(value -> Objects.nonNull(value.getName())
            ? value.getNameAndDescription()
            : StringUtils.EMPTY);
        pubTypeComboBox.setSelectedItem(udmValue.getPublicationType());
        pubTypeComboBox.addValueChangeListener(event -> fieldToValueChangesMap.updateFieldValue(fieldName,
            Objects.nonNull(event.getValue()) ? event.getValue().getNameAndDescription() : null));
        binder.forField(pubTypeComboBox).bind(UdmValueDto::getPublicationType, UdmValueDto::setPublicationType);
        VaadinUtils.addComponentStyle(pubTypeComboBox, "udm-value-edit-pub-type-combo-box");
        return buildCommonLayout(pubTypeComboBox, fieldName);
    }

    private HorizontalLayout buildPriceLayout() {
        String fieldName = ForeignUi.getMessage("label.price");
        priceField.setSizeFull();
        binder.forField(priceField)
            .withValidator(value -> StringUtils.isBlank(value) || NumberUtils.isNumber(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new AmountValidator())
            .bind(fromBigDecimalToMoneyString(UdmValueDto::getPrice),
                (bean, value) -> bean.setPrice(StringUtils.isNotBlank(value)
                    ? NumberUtils.createBigDecimal(value.trim()) : null));
        priceField.addValueChangeListener(event -> {
            binder.validate();
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim());
            if (event.isUserOriginated()) {
                recalculatePriceInUsd();
                recalculateFlag(priceField, priceFlagField);
                recalculateContentUnitPriceFlag();
            }
        });
        VaadinUtils.addComponentStyle(priceField, "udm-value-edit-price-field");
        return buildCommonLayout(priceField, fieldName);
    }

    private HorizontalLayout buildCurrencyLayout() {
        String fieldName = ForeignUi.getMessage("label.currency");
        currencyComboBox.setSizeFull();
        List<Currency> currencies = controller.getAllCurrencies();
        currencyComboBox.setItems(currencies);
        currencyComboBox.setItemCaptionGenerator(currency -> Objects.nonNull(currency)
            ? currency.getCodeAndDescription()
            : StringUtils.EMPTY);
        currencyComboBox.setSelectedItem(currencies
            .stream()
            .filter(currency -> currency.getCode().equals(udmValue.getCurrency()))
            .findFirst()
            .orElse(null));
        currencyComboBox.addValueChangeListener(event -> {
            binder.validate();
            fieldToValueChangesMap.updateFieldValue(fieldName, Objects.nonNull(event.getValue())
                ? event.getValue().getCodeAndDescription() : null);
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
        return buildCommonLayout(currencyComboBox, fieldName);
    }

    private HorizontalLayout buildPriceTypeLayout() {
        String fieldName = ForeignUi.getMessage("label.price_type");
        priceTypeComboBox.setSizeFull();
        priceTypeComboBox.setItems(controller.getAllPriceTypes());
        priceTypeComboBox.setSelectedItem(udmValue.getPriceType());
        priceTypeComboBox.addValueChangeListener(
            event -> fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue()));
        binder.forField(priceTypeComboBox).bind(UdmValueDto::getPriceType, UdmValueDto::setPriceType);
        VaadinUtils.addComponentStyle(priceTypeComboBox, "udm-value-edit-price-type-combo-box");
        return buildCommonLayout(priceTypeComboBox, fieldName);
    }

    private HorizontalLayout buildPriceAccessTypeLayout() {
        String fieldName = ForeignUi.getMessage("label.price_access_type");
        priceAccessTypeComboBox.setSizeFull();
        priceAccessTypeComboBox.setItems(controller.getAllPriceAccessTypes());
        priceAccessTypeComboBox.setSelectedItem(udmValue.getPriceAccessType());
        priceAccessTypeComboBox.addValueChangeListener(
            event -> fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue()));
        binder.forField(priceAccessTypeComboBox).bind(UdmValueDto::getPriceAccessType, UdmValueDto::setPriceAccessType);
        VaadinUtils.addComponentStyle(priceAccessTypeComboBox, "udm-value-edit-price-access-type-combo-box");
        return buildCommonLayout(priceAccessTypeComboBox, fieldName);
    }

    private HorizontalLayout buildPriceYearLayout() {
        String fieldName = ForeignUi.getMessage("label.price_year");
        priceYearField.setSizeFull();
        priceYearField.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim()));
        binder.forField(priceYearField)
            .withValidator(value -> StringUtils.isBlank(value) || StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new YearValidator())
            .bind(bean -> Objects.toString(bean.getPriceYear(), StringUtils.EMPTY),
                (bean, value) -> bean.setPriceYear(NumberUtils.createInteger(StringUtils.trimToNull(value))));
        VaadinUtils.addComponentStyle(priceYearField, "udm-value-edit-price-year-field");
        return buildCommonLayout(priceYearField, fieldName);
    }

    private HorizontalLayout buildPriceSourceLayout() {
        String fieldName = ForeignUi.getMessage("label.price_source");
        priceSourceField.setSizeFull();
        priceSourceField.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim()));
        binder.forField(priceSourceField)
            .withValidator(value -> StringUtils.isNotBlank(value) || StringUtils.isBlank(priceField.getValue()),
                ForeignUi.getMessage("field.error.empty_if_price_specified"))
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1000), 0, 1000))
            .bind(UdmValueDto::getPriceSource,
                (value, fieldValue) -> value.setPriceSource(StringUtils.trimToNull(fieldValue)));
        VaadinUtils.addComponentStyle(priceSourceField, "udm-value-edit-price-source-field");
        return buildCommonLayout(priceSourceField, fieldName);
    }

    private HorizontalLayout buildContentLayout() {
        String fieldName = ForeignUi.getMessage("label.content");
        contentField.setSizeFull();
        binder.forField(contentField)
            .withValidator(value -> StringUtils.isBlank(value) || NumberUtils.isNumber(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new AmountZeroValidator())
            .bind(fromBigDecimalToMoneyString(UdmValueDto::getContent),
                (bean, value) -> bean.setContent(StringUtils.isNotBlank(value)
                    ? NumberUtils.createBigDecimal(value.trim()) : null));
        contentField.addValueChangeListener(event -> {
            binder.validate();
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim());
            if (event.isUserOriginated()) {
                recalculateContentUnitPrice();
                recalculateFlag(contentField, contentFlagField);
                recalculateContentUnitPriceFlag();
            }
        });
        VaadinUtils.addComponentStyle(contentField, "udm-value-edit-content-field");
        return buildCommonLayout(contentField, fieldName);
    }

    private HorizontalLayout buildContentSourceLayout() {
        String fieldName = ForeignUi.getMessage("label.content_source");
        contentSourceField.setSizeFull();
        contentSourceField.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim()));
        binder.forField(contentSourceField)
            .withValidator(value -> StringUtils.isNotBlank(value) || StringUtils.isBlank(contentField.getValue()),
                ForeignUi.getMessage("field.error.empty_if_content_specified"))
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1000), 0, 1000))
            .bind(UdmValueDto::getContentSource,
                (value, fieldValue) -> value.setContentSource(StringUtils.trimToNull(fieldValue)));
        VaadinUtils.addComponentStyle(contentSourceField, "udm-value-edit-content-source-field");
        return buildCommonLayout(contentSourceField, fieldName);
    }
}
