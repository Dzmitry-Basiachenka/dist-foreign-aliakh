package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.NumericValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.CommonAclFiltersWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AssigneeFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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
public class UdmValueFiltersWindow extends CommonAclFiltersWindow {

    private static final long serialVersionUID = -2270422945892329477L;
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
    private final TextField contentUnitPriceFromField =
        new TextField(ForeignUi.getMessage("label.content_unit_price_from"));
    private final TextField contentUnitPriceToField =
        new TextField(ForeignUi.getMessage("label.content_unit_price_to"));
    private final ComboBox<FilterOperatorEnum> contentUnitPriceOperatorComboBox = buildNumericOperatorComboBox();
    private final ComboBox<FilterOperatorEnum> contentUnitPriceFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.content_unit_price_flag"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
    private final ComboBox<FilterOperatorEnum> commentOperatorComboBox = buildTextOperatorComboBox();
    private final TextField lastCommentField = new TextField(ForeignUi.getMessage("label.last_comment"));
    private final ComboBox<FilterOperatorEnum> lastCommentOperatorComboBox = buildTextOperatorComboBox();
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
        super.setContent(initRootLayout());
        super.setCaption(ForeignUi.getMessage("window.udm_values_additional_filters"));
        super.setResizable(false);
        super.setWidth(600, Unit.PIXELS);
        super.setHeight(650, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-values-additional-filters-window");
    }

    /**
     * @return applied UDM value filter.
     */
    public UdmValueFilter getAppliedValueFilter() {
        return valueFilter;
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.addComponents(initAssigneeLastValuePeriodLayout(), initWrWrkInstLayout(), initSystemTitleLayout(),
            initSystemStandardNumberLayout(), initRhAccountNumberLayout(), initRhNameLayout(),
            initCurrencyLastPubTypeLayout(), initPriceLayout(), initPriceInUsdLayout(), initPriceFlagsLayout(),
            initPriceCommentLayout(), initLastPriceCommentLayout(), initContentLayout(), initContentFlagsLayout(),
            initContentCommentLayout(), initLastContentCommentLayout(), initContentUnitPriceLayout(),
            initContentUnitPriceFlagComboBox(), initCommentLayout(), initLastCommentLayout());
        Panel panel = new Panel(fieldsLayout);
        panel.setSizeFull();
        fieldsLayout.setMargin(new MarginInfo(true));
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        filterBinder.readBean(valueFilter);
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
        wrWrkInstToField.setEnabled(false);
        bindBetweenLongFields(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            UdmValueFilter::getWrWrkInstExpression, ForeignUi.getMessage("label.wr_wrk_inst_from"), 9);
        wrWrkInstOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, wrWrkInstFromField, wrWrkInstToField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        applyCommonNumericFieldFormatting(horizontalLayout, wrWrkInstFromField, wrWrkInstToField);
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "udm-value-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "udm-value-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "udm-value-wr-wrk-inst-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        bindStringField(systemTitleField, systemTitleOperatorComboBox, UdmValueFilter::getSystemTitleExpression, 2000);
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, systemTitleField, event.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, systemTitleField);
        VaadinUtils.addComponentStyle(systemTitleField, "udm-value-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-value-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSystemStandardNumberLayout() {
        bindStringField(systemStandardNumberField, systemStandardNumberOperatorComboBox,
            UdmValueFilter::getSystemStandardNumberExpression, 1000);
        systemStandardNumberOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, systemStandardNumberField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(systemStandardNumberField, systemStandardNumberOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, systemStandardNumberField);
        VaadinUtils.addComponentStyle(systemStandardNumberField, "udm-value-system-standard-number-filter");
        VaadinUtils.addComponentStyle(systemStandardNumberOperatorComboBox,
            "udm-value-system-standard-number-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initRhAccountNumberLayout() {
        rhAccountNumberToField.setEnabled(false);
        bindBetweenLongFields(rhAccountNumberFromField, rhAccountNumberToField, rhAccountNumberOperatorComboBox,
            UdmValueFilter::getRhAccountNumberExpression, ForeignUi.getMessage("label.rh_account_number_from"), 10);
        rhAccountNumberOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, rhAccountNumberFromField, rhAccountNumberToField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(rhAccountNumberFromField, rhAccountNumberToField, rhAccountNumberOperatorComboBox);
        applyCommonNumericFieldFormatting(horizontalLayout, rhAccountNumberFromField, rhAccountNumberToField);
        VaadinUtils.addComponentStyle(rhAccountNumberFromField, "udm-value-rh-account-number-from-filter");
        VaadinUtils.addComponentStyle(rhAccountNumberToField, "udm-value-rh-account-number-to-filter");
        VaadinUtils.addComponentStyle(rhAccountNumberOperatorComboBox, "udm-value-rh-account-number-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initRhNameLayout() {
        bindStringField(rhNameField, rhNameOperatorComboBox, UdmValueFilter::getRhNameExpression, 255);
        rhNameOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, rhNameField, event.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(rhNameField, rhNameOperatorComboBox);
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
        filterBinder.forField(currencyComboBox)
            .bind(UdmValueFilter::getCurrency, UdmValueFilter::setCurrency);
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
        filterBinder.forField(lastPubTypeComboBox)
            .bind(UdmValueFilter::getLastPubType, UdmValueFilter::setLastPubType);
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
        priceToField.setEnabled(false);
        bindBetweenBigDecimalFields(priceFromField, priceToField, priceOperatorComboBox,
            UdmValueFilter::getPriceExpression, ForeignUi.getMessage("label.price_from"));
        priceOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, priceFromField, priceToField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(priceFromField, priceToField, priceOperatorComboBox);
        applyCommonNumericFieldFormatting(horizontalLayout, priceFromField, priceToField);
        VaadinUtils.addComponentStyle(priceFromField, "udm-value-price-from-filter");
        VaadinUtils.addComponentStyle(priceToField, "udm-value-price-to-filter");
        VaadinUtils.addComponentStyle(priceOperatorComboBox, "udm-value-price-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initPriceInUsdLayout() {
        priceInUsdToField.setEnabled(false);
        bindBetweenBigDecimalFields(priceInUsdFromField, priceInUsdToField, priceInUsdOperatorComboBox,
            UdmValueFilter::getPriceInUsdExpression, ForeignUi.getMessage("label.price_in_usd_from"));
        priceInUsdOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, priceInUsdFromField, priceInUsdToField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(priceInUsdFromField, priceInUsdToField, priceInUsdOperatorComboBox);
        applyCommonNumericFieldFormatting(horizontalLayout, priceInUsdFromField, priceInUsdToField);
        VaadinUtils.addComponentStyle(priceInUsdFromField, "udm-value-price-in-usd-from-filter");
        VaadinUtils.addComponentStyle(priceInUsdToField, "udm-value-price-in-usd-to-filter");
        VaadinUtils.addComponentStyle(priceInUsdOperatorComboBox, "udm-value-price-in-usd-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initPriceFlagsLayout() {
        filterBinder.forField(priceFlagComboBox)
            .bind(filter -> filter.getPriceFlagExpression().getOperator(),
                (filter, value) -> filter.getPriceFlagExpression().setOperator(value));
        filterBinder.forField(lastPriceFlagComboBox)
            .bind(filter -> filter.getLastPriceFlagExpression().getOperator(),
                (filter, value) -> filter.getLastPriceFlagExpression().setOperator(value));
        populateFlagComboBox(priceFlagComboBox, valueFilter.getPriceFlagExpression().getOperator(),
            "udm-value-price-flag-filter");
        populateLastValueFlagComboBox(lastPriceFlagComboBox, valueFilter.getLastPriceFlagExpression().getOperator(),
            "udm-value-last-price-flag-filter");
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceFlagComboBox, lastPriceFlagComboBox);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private HorizontalLayout initPriceCommentLayout() {
        bindStringField(priceCommentField, priceCommentOperatorComboBox,
            UdmValueFilter::getPriceCommentExpression, 1024);
        priceCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, priceCommentField, event.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceCommentField, priceCommentOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, priceCommentField);
        VaadinUtils.addComponentStyle(priceCommentField, "udm-value-price-comment-filter");
        VaadinUtils.addComponentStyle(priceCommentOperatorComboBox, "udm-value-price-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initLastPriceCommentLayout() {
        bindStringField(lastPriceCommentField, lastPriceCommentOperatorComboBox,
            UdmValueFilter::getLastPriceCommentExpression, 1024);
        lastPriceCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, lastPriceCommentField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(lastPriceCommentField, lastPriceCommentOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, lastPriceCommentField);
        VaadinUtils.addComponentStyle(lastPriceCommentField, "udm-value-last-price-comment-filter");
        VaadinUtils.addComponentStyle(lastPriceCommentOperatorComboBox,
            "udm-value-last-price-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentLayout() {
        contentToField.setEnabled(false);
        bindBetweenBigDecimalFields(contentFromField, contentToField, contentOperatorComboBox,
            UdmValueFilter::getContentExpression, ForeignUi.getMessage("label.content_from"));
        contentOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, contentFromField, contentToField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentFromField, contentToField, contentOperatorComboBox);
        applyCommonNumericFieldFormatting(horizontalLayout, contentFromField, contentToField);
        VaadinUtils.addComponentStyle(contentFromField, "udm-value-content-from-filter");
        VaadinUtils.addComponentStyle(contentToField, "udm-value-content-to-filter");
        VaadinUtils.addComponentStyle(contentOperatorComboBox, "udm-value-content-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentCommentLayout() {
        bindStringField(contentCommentField, contentCommentOperatorComboBox,
            UdmValueFilter::getContentCommentExpression, 1024);
        contentCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, contentCommentField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentCommentField, contentCommentOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, contentCommentField);
        VaadinUtils.addComponentStyle(contentCommentField, "udm-value-content-comment-filter");
        VaadinUtils.addComponentStyle(contentCommentOperatorComboBox, "udm-value-content-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentFlagsLayout() {
        filterBinder.forField(contentFlagComboBox)
            .bind(filter -> filter.getContentFlagExpression().getOperator(),
                (filter, value) -> filter.getContentFlagExpression().setOperator(value));
        filterBinder.forField(lastContentFlagComboBox)
            .bind(filter -> filter.getLastContentFlagExpression().getOperator(),
                (filter, value) -> filter.getLastContentFlagExpression().setOperator(value));
        populateFlagComboBox(contentFlagComboBox, valueFilter.getContentFlagExpression().getOperator(),
            "udm-value-content-flag-filter");
        populateLastValueFlagComboBox(lastContentFlagComboBox, valueFilter.getLastContentFlagExpression().getOperator(),
            "udm-value-last-content-flag-filter");
        HorizontalLayout horizontalLayout = new HorizontalLayout(contentFlagComboBox, lastContentFlagComboBox);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private HorizontalLayout initLastContentCommentLayout() {
        bindStringField(lastContentCommentField, lastContentCommentOperatorComboBox,
            UdmValueFilter::getLastContentCommentExpression, 1024);
        lastContentCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, lastContentCommentField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(lastContentCommentField, lastContentCommentOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, lastContentCommentField);
        VaadinUtils.addComponentStyle(lastContentCommentField, "udm-value-last-content-comment-filter");
        VaadinUtils.addComponentStyle(lastContentCommentOperatorComboBox,
            "udm-value-last-content-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentUnitPriceLayout() {
        contentUnitPriceToField.setEnabled(false);
        bindBetweenBigDecimalFields(contentUnitPriceFromField, contentUnitPriceToField,
            contentUnitPriceOperatorComboBox, UdmValueFilter::getContentUnitPriceExpression,
            ForeignUi.getMessage("label.content_unit_price_from"));
        contentUnitPriceOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, contentUnitPriceFromField, contentUnitPriceToField,
                event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentUnitPriceFromField, contentUnitPriceToField, contentUnitPriceOperatorComboBox);
        applyCommonNumericFieldFormatting(horizontalLayout, contentUnitPriceFromField, contentUnitPriceToField);
        VaadinUtils.addComponentStyle(contentUnitPriceFromField, "udm-value-content-unit-price-from-filter");
        VaadinUtils.addComponentStyle(contentUnitPriceToField, "udm-value-content-unit-price-to-filter");
        VaadinUtils.addComponentStyle(contentUnitPriceOperatorComboBox, "udm-value-content-unit-price-operator-filter");
        return horizontalLayout;
    }

    private ComboBox<FilterOperatorEnum> initContentUnitPriceFlagComboBox() {
        filterBinder.forField(contentUnitPriceFlagComboBox)
            .bind(filter -> filter.getContentUnitPriceFlagExpression().getOperator(),
                (filter, value) -> filter.getContentUnitPriceFlagExpression().setOperator(value));
        populateFlagComboBox(contentUnitPriceFlagComboBox,
            valueFilter.getContentUnitPriceFlagExpression().getOperator(), "udm-value-content-unit-price-flag-filter");
        contentUnitPriceFlagComboBox.setWidth(50, Unit.PERCENTAGE);
        return contentUnitPriceFlagComboBox;
    }

    private HorizontalLayout initCommentLayout() {
        bindStringField(commentField, commentOperatorComboBox, UdmValueFilter::getCommentExpression, 1024);
        commentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, commentField, event.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(commentField, commentOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, commentField);
        VaadinUtils.addComponentStyle(commentField, "udm-value-comment-filter");
        VaadinUtils.addComponentStyle(commentOperatorComboBox, "udm-value-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initLastCommentLayout() {
        bindStringField(lastCommentField, lastCommentOperatorComboBox,
            UdmValueFilter::getLastCommentExpression, 1024);
        lastCommentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, lastCommentField, event.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(lastCommentField, lastCommentOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, lastCommentField);
        VaadinUtils.addComponentStyle(lastCommentField, "udm-value-last-comment-filter");
        VaadinUtils.addComponentStyle(lastCommentOperatorComboBox, "udm-value-last-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            try {
                filterBinder.writeBean(valueFilter);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(
                    List.of(wrWrkInstFromField, wrWrkInstToField, systemTitleField, systemStandardNumberField,
                        rhAccountNumberFromField, rhAccountNumberToField, rhNameField, priceFromField, priceToField,
                        priceInUsdFromField, priceInUsdToField, priceCommentField, lastPriceCommentField,
                        contentFromField, contentToField, contentCommentField, lastContentCommentField,
                        contentUnitPriceFromField, contentUnitPriceToField, commentField, lastCommentField));
            }
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearFilters() {
        valueFilter.setAssignees(new HashSet<>());
        valueFilter.setLastValuePeriods(new HashSet<>());
        assigneeFilterWidget.reset();
        lastValuePeriodFilterWidget.reset();
        filterBinder.readBean(new UdmValueFilter());
    }

    private void bindStringField(TextField textField, ComboBox<FilterOperatorEnum> operatorComboBox,
                                 Function<UdmValueFilter, FilterExpression<String>> getter, int maxLength) {
        filterBinder.forField(textField)
            .withValidator(getTextStringLengthValidator(maxLength))
            .bind(filter -> Objects.toString(getter.apply(filter).getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> getter.apply(filter).setFieldFirstValue(StringUtils.trimToNull(value)));
        filterBinder.forField(operatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(getter.apply(filter).getOperator(), FilterOperatorEnum.EQUALS),
                (filter, value) -> getter.apply(filter).setOperator(value));
    }

    private void bindBetweenLongFields(TextField fromField, TextField toField,
                                       ComboBox<FilterOperatorEnum> operatorComboBox,
                                       Function<UdmValueFilter, FilterExpression<Number>> getter,
                                       String fromFieldName, int maxLength) {
        filterBinder.forField(fromField)
            .withValidator(getNumberStringLengthValidator(maxLength))
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(fromField, operatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(getter.apply(filter).getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> getter.apply(filter)
                    .setFieldFirstValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(toField)
            .withValidator(getNumberStringLengthValidator(maxLength))
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(toField, operatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(fromField, toField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE, fromFieldName))
            .bind(filter -> Objects.toString(getter.apply(filter).getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> getter.apply(filter)
                    .setFieldSecondValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(operatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(getter.apply(filter).getOperator(), FilterOperatorEnum.EQUALS),
                (filter, value) -> getter.apply(filter).setOperator(value));
    }

    private void bindBetweenBigDecimalFields(TextField fromField, TextField toField,
                                             ComboBox<FilterOperatorEnum> operatorComboBox,
                                             Function<UdmValueFilter, FilterExpression<Number>> getter,
                                             String fromFieldName) {
        filterBinder.forField(fromField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(fromField, operatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(getter.apply(filter).getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> getter.apply(filter)
                    .setFieldFirstValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(toField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(toField, operatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(fromField, toField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE, fromFieldName))
            .bind(filter -> Objects.toString(getter.apply(filter).getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> getter.apply(filter)
                    .setFieldSecondValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(operatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(getter.apply(filter).getOperator(), FilterOperatorEnum.EQUALS),
                (filter, value) -> getter.apply(filter).setOperator(value));
    }
}
