package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AssigneeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmFiltersWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Window to apply additional filters for {@link UdmValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/22/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueFiltersWindow extends CommonUdmFiltersWindow {

    private static final String NUMBER_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String GRATER_OR_EQUAL_VALIDATION_MESSAGE = "field.error.greater_or_equal_to";

    private final Binder<UdmValueFilter> filterBinder = new Binder<>();
    private AssigneeFilterWidget assigneeFilterWidget;
    private LastValuePeriodFilterWidget lastValuePeriodFilterWidget;
    private final TextField wrWrkInstFromField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_from"));
    private final TextField wrWrkInstToField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_to"));
    private final ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField systemTitleField = new TextField(ForeignUi.getMessage("label.system_title"));
    private final ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox = buildTextOperatorComboBox();
    private final TextField systemStandardNumberField =
        new TextField(ForeignUi.getMessage("label.system_standard_number"));
    private final ComboBox<FilterOperatorEnum> systemStandardNumberOperatorComboBox = buildTextOperatorComboBox();
    private final TextField rhAccountNumberFromField =
        new TextField(ForeignUi.getMessage("label.rh_account_number_from"));
    private final TextField rhAccountNumberToField = new TextField(ForeignUi.getMessage("label.rh_account_number_to"));
    private final ComboBox<FilterOperatorEnum> rhAccountNumberOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField rhNameField = new TextField(ForeignUi.getMessage("label.rh_name"));
    private final ComboBox<FilterOperatorEnum> rhNameOperatorComboBox = buildTextOperatorComboBox();
    private final ComboBox<Currency> currencyComboBox = new ComboBox<>(ForeignUi.getMessage("label.currency"));
    private final TextField priceFromField = new TextField(ForeignUi.getMessage("label.price_from"));
    private final TextField priceToField = new TextField(ForeignUi.getMessage("label.price_to"));
    private final ComboBox<FilterOperatorEnum> priceOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField priceInUsdFromField = new TextField(ForeignUi.getMessage("label.price_in_usd_from"));
    private final TextField priceInUsdToField = new TextField(ForeignUi.getMessage("label.price_in_usd_to"));
    private final ComboBox<FilterOperatorEnum> priceInUsdOperatorComboBox = buildNumericOperatorComboBox();
    private final ComboBox<FilterOperatorEnum> priceFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.price_flag"));
    private final TextField priceCommentField = new TextField(ForeignUi.getMessage("label.price_comment"));
    private final ComboBox<FilterOperatorEnum> priceCommentOperatorComboBox = buildTextOperatorComboBox();
    private final ComboBox<FilterOperatorEnum> lastPriceFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.last_price_flag"));
    private final TextField lastPriceCommentField = new TextField(ForeignUi.getMessage("label.last_price_comment"));
    private final ComboBox<FilterOperatorEnum> lastPriceCommentOperatorComboBox = buildTextOperatorComboBox();
    private final TextField contentFromField = new TextField(ForeignUi.getMessage("label.content_from"));
    private final TextField contentToField = new TextField(ForeignUi.getMessage("label.content_to"));
    private final ComboBox<FilterOperatorEnum> contentOperatorComboBox = buildNumericOperatorComboBox();
    private final ComboBox<FilterOperatorEnum> contentFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.content_flag"));
    private final TextField contentCommentField = new TextField(ForeignUi.getMessage("label.content_comment"));
    private final ComboBox<FilterOperatorEnum> contentCommentOperatorComboBox = buildTextOperatorComboBox();
    private final ComboBox<FilterOperatorEnum> lastContentFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.last_content_flag"));
    private final TextField lastContentCommentField = new TextField(ForeignUi.getMessage("label.last_content_comment"));
    private final ComboBox<FilterOperatorEnum> lastContentCommentOperatorComboBox = buildTextOperatorComboBox();
    private final ComboBox<PublicationType> lastPubTypeComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.last_pub_type"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
    private final ComboBox<FilterOperatorEnum> commentOperatorComboBox = buildTextOperatorComboBox();
    private final TextField lastCommentField = new TextField(ForeignUi.getMessage("label.last_comment"));
    private final ComboBox<FilterOperatorEnum> lastCommentOperatorComboBox = buildTextOperatorComboBox();
    private UdmValueFilter appliedValueFilter;
    private final UdmValueFilter valueFilter;
    private final IUdmValueFilterController controller;

    /**
     * Constructor.
     *
     * @param controller     instance of {@link IUdmValueFilterController}
     * @param udmValueFilter instance of {@link UdmValueFilter} to be displayed on window
     */
    public UdmValueFiltersWindow(IUdmValueFilterController controller, UdmValueFilter udmValueFilter) {
        this.controller = controller;
        valueFilter = new UdmValueFilter(udmValueFilter);
        appliedValueFilter = udmValueFilter;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.udm_values_additional_filters"));
        setResizable(false);
        setWidth(600, Unit.PIXELS);
        setHeight(650, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-values-additional-filters-window");
    }

    /**
     * @return applied UDM value filter.
     */
    public UdmValueFilter getAppliedValueFilter() {
        return appliedValueFilter;
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.addComponents(initAssigneeLastValuePeriodLayout(), initWrWrkInstLayout(), initSystemTitleLayout(),
            initSystemStandardNumberLayout(), initRhAccountNumberLayout(), initRhNameLayout(),
            initCurrencyLastPubTypeLayout(), initPriceLayout(), initPriceInUsdLayout(), initPriceFlagsLayout(),
            initPriceCommentLayout(), initLastPriceCommentLayout(), initContentLayout(), initContentFlagsLayout(),
            initContentCommentLayout(), initLastContentCommentLayout(), initCommentLayout(), initLastCommentLayout());
        Panel panel = new Panel(fieldsLayout);
        panel.setSizeFull();
        fieldsLayout.setMargin(new MarginInfo(true));
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        filterBinder.validate();
        return rootLayout;
    }

    private HorizontalLayout initAssigneeLastValuePeriodLayout() {
        assigneeFilterWidget = new AssigneeFilterWidget(controller::getAssignees, valueFilter.getAssignees());
        assigneeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            valueFilter.setAssignees(saveEvent.getSelectedItemsIds()));
        lastValuePeriodFilterWidget = new LastValuePeriodFilterWidget(controller::getLastValuePeriods,
            valueFilter.getLastValuePeriods());
        lastValuePeriodFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            valueFilter.setLastValuePeriods(saveEvent.getSelectedItemsIds()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(assigneeFilterWidget, lastValuePeriodFilterWidget);
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        populateOperatorFilters(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            valueFilter.getWrWrkInstExpression());
        wrWrkInstFromField.addValueChangeListener(event -> filterBinder.validate());
        wrWrkInstOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, wrWrkInstFromField, wrWrkInstToField, event.getValue()));
        filterBinder.forField(wrWrkInstFromField)
            .withValidator(getNumberStringLengthValidator(9))
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(wrWrkInstFromField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getWrWrkInstExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getWrWrkInstExpression().setFieldFirstValue(Long.valueOf(value)));
        filterBinder.forField(wrWrkInstToField)
            .withValidator(getNumberStringLengthValidator(9))
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(wrWrkInstToField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(wrWrkInstFromField, wrWrkInstToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.wr_wrk_inst_from")))
            .bind(filter -> filter.getWrWrkInstExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getWrWrkInstExpression().setFieldSecondValue(Long.valueOf(value)));
        applyCommonNumericFieldFormatting(horizontalLayout, wrWrkInstFromField, wrWrkInstToField);
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "udm-value-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "udm-value-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "udm-value-wr-wrk-inst-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        populateOperatorFilters(systemTitleField, systemTitleOperatorComboBox,
            valueFilter.getSystemTitleExpression());
        filterBinder.forField(systemTitleField)
            .withValidator(getTextStringLengthValidator(2000))
            .bind(filter -> filter.getSystemTitleExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSystemTitleExpression().setFieldFirstValue(value.trim()));
        systemTitleField.addValueChangeListener(event -> filterBinder.validate());
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, systemTitleField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, systemTitleField);
        VaadinUtils.addComponentStyle(systemTitleField, "udm-value-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-value-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSystemStandardNumberLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(systemStandardNumberField, systemStandardNumberOperatorComboBox);
        populateOperatorFilters(systemStandardNumberField, systemStandardNumberOperatorComboBox,
            valueFilter.getSystemStandardNumberExpression());
        filterBinder.forField(systemStandardNumberField)
            .withValidator(getTextStringLengthValidator(1000))
            .bind(filter -> filter.getSystemStandardNumberExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSystemStandardNumberExpression().setFieldFirstValue(value.trim()));
        systemStandardNumberField.addValueChangeListener(event -> filterBinder.validate());
        systemStandardNumberOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, systemStandardNumberField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, systemStandardNumberField);
        VaadinUtils.addComponentStyle(systemStandardNumberField, "udm-value-system-standard-number-filter");
        VaadinUtils.addComponentStyle(systemStandardNumberOperatorComboBox,
            "udm-value-system-standard-number-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initRhAccountNumberLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(rhAccountNumberFromField, rhAccountNumberToField, rhAccountNumberOperatorComboBox);
        populateOperatorFilters(rhAccountNumberFromField, rhAccountNumberToField, rhAccountNumberOperatorComboBox,
            valueFilter.getRhAccountNumberExpression());
        rhAccountNumberFromField.addValueChangeListener(event -> filterBinder.validate());
        rhAccountNumberOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, rhAccountNumberFromField, rhAccountNumberToField, event.getValue()));
        filterBinder.forField(rhAccountNumberFromField)
            .withValidator(getNumberStringLengthValidator(10))
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(rhAccountNumberFromField, rhAccountNumberOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getRhAccountNumberExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getRhAccountNumberExpression().setFieldFirstValue(Long.valueOf(value)));
        filterBinder.forField(rhAccountNumberToField)
            .withValidator(getNumberStringLengthValidator(10))
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(rhAccountNumberToField, rhAccountNumberOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(rhAccountNumberFromField, rhAccountNumberToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.rh_account_number_from")))
            .bind(filter -> filter.getRhAccountNumberExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getRhAccountNumberExpression().setFieldSecondValue(Long.valueOf(value)));
        applyCommonNumericFieldFormatting(horizontalLayout, rhAccountNumberFromField, rhAccountNumberToField);
        VaadinUtils.addComponentStyle(rhAccountNumberFromField, "udm-value-rh-account-number-from-filter");
        VaadinUtils.addComponentStyle(rhAccountNumberToField, "udm-value-rh-account-number-to-filter");
        VaadinUtils.addComponentStyle(rhAccountNumberOperatorComboBox, "udm-value-rh-account-number-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initRhNameLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(rhNameField, rhNameOperatorComboBox);
        populateOperatorFilters(rhNameField, rhNameOperatorComboBox, valueFilter.getRhNameExpression());
        filterBinder.forField(rhNameField)
            .withValidator(getTextStringLengthValidator(255))
            .bind(filter -> filter.getRhNameExpression().getFieldFirstValue(),
                (filter, value) -> filter.getRhNameExpression().setFieldFirstValue(value.trim()));
        rhNameField.addValueChangeListener(event -> filterBinder.validate());
        rhNameOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, rhNameField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, rhNameField);
        VaadinUtils.addComponentStyle(rhNameField, "udm-value-rh-name-filter");
        VaadinUtils.addComponentStyle(rhNameOperatorComboBox, "udm-value-rh-name-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initCurrencyLastPubTypeLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(initCurrencyComboBox(), initLastPubTypeCombobox());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private ComboBox<Currency> initCurrencyComboBox() {
        currencyComboBox.setItems(controller.getAllCurrencies());
        currencyComboBox.setPageLength(16);
        currencyComboBox.setItemCaptionGenerator(
            value -> String.format("%s - %s", value.getCode(), value.getDescription()));
        currencyComboBox.setSelectedItem(valueFilter.getCurrency());
        currencyComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(currencyComboBox, "udm-value-currency-filter");
        return currencyComboBox;
    }

    private ComboBox<PublicationType> initLastPubTypeCombobox() {
        List<PublicationType> publicationTypes = controller.getPublicationTypes();
        publicationTypes.add(0, new PublicationType());
        lastPubTypeComboBox.setItems(publicationTypes);
        lastPubTypeComboBox.setPageLength(12);
        lastPubTypeComboBox.setItemCaptionGenerator(value -> Objects.nonNull(value.getName())
            ? value.getNameAndDescription()
            : "NULL");
        lastPubTypeComboBox.setSelectedItem(valueFilter.getLastPubType());
        lastPubTypeComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(lastPubTypeComboBox, "udm-value-last-pub-type-filter");
        return lastPubTypeComboBox;
    }

    private HorizontalLayout initPriceLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(priceFromField, priceToField, priceOperatorComboBox);
        populateOperatorFilters(priceFromField, priceToField, priceOperatorComboBox,
            valueFilter.getPriceExpression());
        priceFromField.addValueChangeListener(event -> filterBinder.validate());
        priceOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, priceFromField, priceToField, event.getValue()));
        filterBinder.forField(priceFromField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(priceFromField, priceOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getPriceExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getPriceExpression().setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(priceToField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(priceToField, priceOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(priceFromField, priceToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.price_from")))
            .bind(filter -> filter.getPriceExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getPriceExpression().setFieldSecondValue(new BigDecimal(value)));
        applyCommonNumericFieldFormatting(horizontalLayout, priceFromField, priceToField);
        VaadinUtils.addComponentStyle(priceFromField, "udm-value-price-from-filter");
        VaadinUtils.addComponentStyle(priceToField, "udm-value-price-to-filter");
        VaadinUtils.addComponentStyle(priceOperatorComboBox, "udm-value-price-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initPriceInUsdLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(priceInUsdFromField, priceInUsdToField, priceInUsdOperatorComboBox);
        populateOperatorFilters(priceInUsdFromField, priceInUsdToField, priceInUsdOperatorComboBox,
            valueFilter.getPriceInUsdExpression());
        priceInUsdFromField.addValueChangeListener(event -> filterBinder.validate());
        priceInUsdOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, priceInUsdFromField, priceInUsdToField, event.getValue()));
        filterBinder.forField(priceInUsdFromField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(priceInUsdFromField, priceInUsdOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getPriceInUsdExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getPriceInUsdExpression().setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(priceInUsdToField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(priceInUsdToField, priceInUsdOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(priceInUsdFromField, priceInUsdToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.price_in_usd_from")))
            .bind(filter -> filter.getPriceInUsdExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getPriceInUsdExpression().setFieldSecondValue(new BigDecimal(value)));
        applyCommonNumericFieldFormatting(horizontalLayout, priceInUsdFromField, priceInUsdToField);
        VaadinUtils.addComponentStyle(priceInUsdFromField, "udm-value-price-in-usd-from-filter");
        VaadinUtils.addComponentStyle(priceInUsdToField, "udm-value-price-in-usd-to-filter");
        VaadinUtils.addComponentStyle(priceInUsdOperatorComboBox, "udm-value-price-in-usd-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initPriceFlagsLayout() {
        populateFlagComboBox(priceFlagComboBox, valueFilter.getPriceFlagExpression().getOperator(),
            "udm-value-price-flag-filter");
        populateFlagComboBox(lastPriceFlagComboBox, valueFilter.getLastPriceFlagExpression().getOperator(),
            "udm-value-last-price-flag-filter");
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceFlagComboBox, lastPriceFlagComboBox);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private HorizontalLayout initPriceCommentLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceCommentField, priceCommentOperatorComboBox);
        populateOperatorFilters(priceCommentField, priceCommentOperatorComboBox,
            valueFilter.getPriceCommentExpression());
        filterBinder.forField(priceCommentField)
            .withValidator(getTextStringLengthValidator(1024))
            .bind(filter -> filter.getPriceCommentExpression().getFieldFirstValue(),
                (filter, value) -> filter.getPriceCommentExpression().setFieldFirstValue(value.trim()));
        priceCommentField.addValueChangeListener(event -> filterBinder.validate());
        priceCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, priceCommentField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, priceCommentField);
        VaadinUtils.addComponentStyle(priceCommentField, "udm-value-price-comment-filter");
        VaadinUtils.addComponentStyle(priceCommentOperatorComboBox, "udm-value-price-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initLastPriceCommentLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(lastPriceCommentField, lastPriceCommentOperatorComboBox);
        populateOperatorFilters(lastPriceCommentField, lastPriceCommentOperatorComboBox,
            valueFilter.getLastPriceCommentExpression());
        filterBinder.forField(lastPriceCommentField)
            .withValidator(getTextStringLengthValidator(1024))
            .bind(filter -> filter.getLastPriceCommentExpression().getFieldFirstValue(),
                (filter, value) -> filter.getLastPriceCommentExpression().setFieldFirstValue(value.trim()));
        lastPriceCommentField.addValueChangeListener(event -> filterBinder.validate());
        lastPriceCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, lastPriceCommentField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, lastPriceCommentField);
        VaadinUtils.addComponentStyle(lastPriceCommentField, "udm-value-last-price-comment-filter");
        VaadinUtils.addComponentStyle(lastPriceCommentOperatorComboBox,
            "udm-value-last-price-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentFromField, contentToField, contentOperatorComboBox);
        populateOperatorFilters(contentFromField, contentToField, contentOperatorComboBox,
            valueFilter.getContentExpression());
        contentFromField.addValueChangeListener(event -> filterBinder.validate());
        contentOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, contentFromField, contentToField, event.getValue()));
        filterBinder.forField(contentFromField)
            .withValidator(new AmountZeroValidator())
            .withValidator(getBetweenOperatorValidator(contentFromField, contentOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getContentExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getContentExpression().setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(contentToField)
            .withValidator(new AmountZeroValidator())
            .withValidator(getBetweenOperatorValidator(contentToField, contentOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(contentFromField, contentToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.content_from")))
            .bind(filter -> filter.getContentExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getContentExpression().setFieldSecondValue(new BigDecimal(value)));
        applyCommonNumericFieldFormatting(horizontalLayout, contentFromField, contentToField);
        VaadinUtils.addComponentStyle(contentFromField, "udm-value-content-from-filter");
        VaadinUtils.addComponentStyle(contentToField, "udm-value-content-to-filter");
        VaadinUtils.addComponentStyle(contentOperatorComboBox, "udm-value-content-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentCommentLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentCommentField, contentCommentOperatorComboBox);
        populateOperatorFilters(contentCommentField, contentCommentOperatorComboBox,
            valueFilter.getContentCommentExpression());
        filterBinder.forField(contentCommentField)
            .withValidator(getTextStringLengthValidator(1024))
            .bind(filter -> filter.getContentCommentExpression().getFieldFirstValue(),
                (filter, value) -> filter.getContentCommentExpression().setFieldFirstValue(value.trim()));
        contentCommentField.addValueChangeListener(event -> filterBinder.validate());
        contentCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, contentCommentField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, contentCommentField);
        VaadinUtils.addComponentStyle(contentCommentField, "udm-value-content-comment-filter");
        VaadinUtils.addComponentStyle(contentCommentOperatorComboBox, "udm-value-content-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentFlagsLayout() {
        populateFlagComboBox(contentFlagComboBox, valueFilter.getContentFlagExpression().getOperator(),
            "udm-value-content-flag-filter");
        populateFlagComboBox(lastContentFlagComboBox, valueFilter.getLastContentFlagExpression().getOperator(),
            "udm-value-last-content-flag-filter");
        HorizontalLayout horizontalLayout = new HorizontalLayout(contentFlagComboBox, lastContentFlagComboBox);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private HorizontalLayout initLastContentCommentLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(lastContentCommentField, lastContentCommentOperatorComboBox);
        populateOperatorFilters(lastContentCommentField, lastContentCommentOperatorComboBox,
            valueFilter.getLastContentCommentExpression());
        filterBinder.forField(lastContentCommentField)
            .withValidator(getTextStringLengthValidator(1024))
            .bind(filter -> filter.getLastContentCommentExpression().getFieldFirstValue(),
                (filter, value) -> filter.getLastContentCommentExpression().setFieldFirstValue(value.trim()));
        lastContentCommentField.addValueChangeListener(event -> filterBinder.validate());
        lastContentCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, lastContentCommentField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, lastContentCommentField);
        VaadinUtils.addComponentStyle(lastContentCommentField, "udm-value-last-content-comment-filter");
        VaadinUtils.addComponentStyle(lastContentCommentOperatorComboBox,
            "udm-value-last-content-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initCommentLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(commentField, commentOperatorComboBox);
        populateOperatorFilters(commentField, commentOperatorComboBox, valueFilter.getCommentExpression());
        filterBinder.forField(commentField)
            .withValidator(getTextStringLengthValidator(1024))
            .bind(filter -> filter.getCommentExpression().getFieldFirstValue(),
                (filter, value) -> filter.getCommentExpression().setFieldFirstValue(value.trim()));
        commentField.addValueChangeListener(event -> filterBinder.validate());
        commentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, commentField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, commentField);
        VaadinUtils.addComponentStyle(commentField, "udm-value-comment-filter");
        VaadinUtils.addComponentStyle(commentOperatorComboBox, "udm-value-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initLastCommentLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(lastCommentField, lastCommentOperatorComboBox);
        populateOperatorFilters(lastCommentField, lastCommentOperatorComboBox,
            valueFilter.getLastCommentExpression());
        filterBinder.forField(lastCommentField)
            .withValidator(getTextStringLengthValidator(1024))
            .bind(filter -> filter.getLastCommentExpression().getFieldFirstValue(),
                (filter, value) -> filter.getLastCommentExpression().setFieldFirstValue(value.trim()));
        lastCommentField.addValueChangeListener(event -> filterBinder.validate());
        lastCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, lastCommentField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, lastCommentField);
        VaadinUtils.addComponentStyle(lastCommentField, "udm-value-last-comment-filter");
        VaadinUtils.addComponentStyle(lastCommentOperatorComboBox, "udm-value-last-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            // TODO {aliakh} rewrite using binder.writeBean
            if (filterBinder.isValid()) {
                populateValueFilter();
                appliedValueFilter = valueFilter;
                close();
            } else {
                Windows.showValidationErrorWindow(
                    Arrays.asList(wrWrkInstFromField, wrWrkInstToField, systemTitleField, systemStandardNumberField,
                        rhAccountNumberFromField, rhAccountNumberToField, rhNameField, priceFromField, priceToField,
                        priceInUsdFromField, priceInUsdToField, priceCommentField, lastPriceCommentField,
                        contentFromField, contentToField, contentCommentField, lastContentCommentField, commentField,
                        lastCommentField));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearFilters() {
        clearValueFilter();
        assigneeFilterWidget.reset();
        lastValuePeriodFilterWidget.reset();
        clearOperatorLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        clearOperatorLayout(systemTitleField, systemTitleOperatorComboBox);
        clearOperatorLayout(systemStandardNumberField, systemStandardNumberOperatorComboBox);
        clearOperatorLayout(rhAccountNumberFromField, rhAccountNumberToField, rhAccountNumberOperatorComboBox);
        clearOperatorLayout(rhNameField, rhNameOperatorComboBox);
        currencyComboBox.clear();
        clearOperatorLayout(priceFromField, priceToField, priceOperatorComboBox);
        clearOperatorLayout(priceInUsdFromField, priceInUsdToField, priceInUsdOperatorComboBox);
        priceFlagComboBox.clear();
        clearOperatorLayout(priceCommentField, priceCommentOperatorComboBox);
        lastPriceFlagComboBox.clear();
        clearOperatorLayout(lastPriceCommentField, lastPriceCommentOperatorComboBox);
        clearOperatorLayout(contentFromField, contentToField, contentOperatorComboBox);
        contentFlagComboBox.clear();
        clearOperatorLayout(contentCommentField, contentCommentOperatorComboBox);
        lastContentFlagComboBox.clear();
        clearOperatorLayout(lastContentCommentField, lastContentCommentOperatorComboBox);
        lastPubTypeComboBox.clear();
        clearOperatorLayout(commentField, commentOperatorComboBox);
        clearOperatorLayout(lastCommentField, lastCommentOperatorComboBox);
    }

    private void clearValueFilter() {
        valueFilter.setAssignees(new HashSet<>());
        valueFilter.setLastValuePeriods(new HashSet<>());
        valueFilter.setWrWrkInstExpression(new FilterExpression<>());
        valueFilter.setSystemTitleExpression(new FilterExpression<>());
        valueFilter.setSystemStandardNumberExpression(new FilterExpression<>());
        valueFilter.setRhAccountNumberExpression(new FilterExpression<>());
        valueFilter.setRhNameExpression(new FilterExpression<>());
        valueFilter.setCurrency(null);
        valueFilter.setPriceExpression(new FilterExpression<>());
        valueFilter.setPriceInUsdExpression(new FilterExpression<>());
        valueFilter.setPriceFlagExpression(new FilterExpression<>());
        valueFilter.setPriceCommentExpression(new FilterExpression<>());
        valueFilter.setLastPriceFlagExpression(new FilterExpression<>());
        valueFilter.setLastPriceCommentExpression(new FilterExpression<>());
        valueFilter.setContentExpression(new FilterExpression<>());
        valueFilter.setContentFlagExpression(new FilterExpression<>());
        valueFilter.setContentCommentExpression(new FilterExpression<>());
        valueFilter.setLastContentFlagExpression(new FilterExpression<>());
        valueFilter.setLastContentCommentExpression(new FilterExpression<>());
        valueFilter.setLastPubType(null);
        valueFilter.setCommentExpression(new FilterExpression<>());
        valueFilter.setLastCommentExpression(new FilterExpression<>());
    }

    private void populateValueFilter() {
        valueFilter.setWrWrkInstExpression(buildNumberFilterExpression(wrWrkInstFromField, wrWrkInstToField,
            wrWrkInstOperatorComboBox, Integer::valueOf));
        valueFilter.setSystemTitleExpression(buildTextFilterExpression(systemTitleField, systemTitleOperatorComboBox,
            Function.identity()));
        valueFilter.setSystemStandardNumberExpression(buildTextFilterExpression(systemStandardNumberField,
            systemStandardNumberOperatorComboBox, Function.identity()));
        valueFilter.setRhAccountNumberExpression(buildNumberFilterExpression(rhAccountNumberFromField,
            rhAccountNumberToField, rhAccountNumberOperatorComboBox, Long::valueOf));
        valueFilter.setRhNameExpression(buildTextFilterExpression(rhNameField, rhNameOperatorComboBox,
            Function.identity()));
        valueFilter.setCurrency(Objects.nonNull(currencyComboBox.getValue()) ? currencyComboBox.getValue() : null);
        valueFilter.setPriceExpression(buildAmountFilterExpression(priceFromField, priceToField,
            priceOperatorComboBox, BigDecimal::new));
        valueFilter.setPriceInUsdExpression(buildAmountFilterExpression(priceInUsdFromField, priceInUsdToField,
            priceInUsdOperatorComboBox, BigDecimal::new));
        valueFilter.getPriceFlagExpression().setOperator(priceFlagComboBox.getValue());
        valueFilter.setPriceCommentExpression(buildTextFilterExpression(priceCommentField, priceCommentOperatorComboBox,
            Function.identity()));
        valueFilter.getLastPriceFlagExpression().setOperator(lastPriceFlagComboBox.getValue());
        valueFilter.setLastPriceCommentExpression(buildTextFilterExpression(lastPriceCommentField,
            lastPriceCommentOperatorComboBox, Function.identity()));
        valueFilter.setContentExpression(buildAmountFilterExpression(contentFromField, contentToField,
            contentOperatorComboBox, BigDecimal::new));
        valueFilter.getContentFlagExpression().setOperator(contentFlagComboBox.getValue());
        valueFilter.setContentCommentExpression(buildTextFilterExpression(contentCommentField,
            contentCommentOperatorComboBox, Function.identity()));
        valueFilter.getLastContentFlagExpression().setOperator(lastContentFlagComboBox.getValue());
        valueFilter.setLastContentCommentExpression(buildTextFilterExpression(lastContentCommentField,
            lastContentCommentOperatorComboBox, Function.identity()));
        valueFilter.setLastPubType(Objects.nonNull(lastPubTypeComboBox.getValue())
            ? lastPubTypeComboBox.getValue() : null);
        valueFilter.setCommentExpression(buildTextFilterExpression(commentField, commentOperatorComboBox,
            Function.identity()));
        valueFilter.setLastCommentExpression(buildTextFilterExpression(lastCommentField,
            lastCommentOperatorComboBox, Function.identity()));
    }
}
