package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AssigneeFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Window to apply additional filters for {@link UdmValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/22/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueFiltersWindow extends Window {

    private static final List<String> Y_N_ITEMS = Arrays.asList("Y", "N");
    private static final String NUMBER_VALIDATION_MESSAGE = "field.error.not_numeric";
    private static final String LENGTH_VALIDATION_MESSAGE = "field.error.length";

    private final StringLengthValidator numberStringLengthValidator =
        new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9);
    private final Binder<UdmValueFilter> filterBinder = new Binder<>();
    private AssigneeFilterWidget assigneeFilterWidget;
    private LastValuePeriodFilterWidget lastValuePeriodFilterWidget;
    private final TextField wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final TextField systemTitleField = new TextField(ForeignUi.getMessage("label.system_title"));
    private final ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox = buildOperatorComboBox();
    private final TextField systemStandardNumberField =
        new TextField(ForeignUi.getMessage("label.system_standard_number"));
    private final ComboBox<FilterOperatorEnum> systemStandardNumberOperatorComboBox = buildOperatorComboBox();
    private final TextField rhAccountNumberField = new TextField(ForeignUi.getMessage("label.rh_account_number"));
    private final TextField rhNameField = new TextField(ForeignUi.getMessage("label.rh_name"));
    private final ComboBox<FilterOperatorEnum> rhNameOperatorComboBox = buildOperatorComboBox();
    private final ComboBox<Currency> currencyComboBox = new ComboBox<>(ForeignUi.getMessage("label.currency"));
    private final TextField priceField = new TextField(ForeignUi.getMessage("label.price"));
    private final ComboBox<FilterOperatorEnum> priceOperatorComboBox = buildOperatorComboBox();
    private final TextField priceInUsdField = new TextField(ForeignUi.getMessage("label.price_in_usd"));
    private final ComboBox<FilterOperatorEnum> priceInUsdOperatorComboBox = buildOperatorComboBox();
    private final ComboBox<String> lastPriceFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.last_price_flag"));
    private final TextField lastPriceCommentField = new TextField(ForeignUi.getMessage("label.last_price_comment"));
    private final TextField contentField = new TextField(ForeignUi.getMessage("label.content"));
    private final ComboBox<FilterOperatorEnum> contentOperatorComboBox = buildOperatorComboBox();
    private final ComboBox<String> lastContentFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.last_content_flag"));
    private final TextField lastContentCommentField = new TextField(ForeignUi.getMessage("label.last_content_comment"));
    private final ComboBox<PublicationType> lastPubTypeComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.last_pub_type"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
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
        setWidth(560, Unit.PIXELS);
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
            initSystemStandardNumberLayout(), initRhAccountNumberLayout(), initRhNameLayout(), initCurrencyFilter(),
            initPriceLayout(), initPriceInUsdLayout(), initLastPriceFlagLastPriceCommentLayout(), initContentLayout(),
            initLastContentFlagLastContentCommentLayout(), initLastPubTypeLayout(), initCommentLayout());
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

    private TextField initWrWrkInstLayout() {
        filterBinder.forField(wrWrkInstField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), ForeignUi.getMessage(NUMBER_VALIDATION_MESSAGE))
            .withConverter(new StringToLongConverter(ForeignUi.getMessage(NUMBER_VALIDATION_MESSAGE)))
            .bind(UdmValueFilter::getWrWrkInst, UdmValueFilter::setWrWrkInst);
        wrWrkInstField.setValue(
            Objects.nonNull(valueFilter.getWrWrkInst()) ? valueFilter.getWrWrkInst().toString() : StringUtils.EMPTY);
        wrWrkInstField.setWidth(248, Unit.PIXELS);
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-value-wr-wrk-inst-filter");
        return wrWrkInstField;
    }

    private HorizontalLayout initSystemTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        systemTitleOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.CONTAINS);
        populateOperatorFilters(systemTitleField, systemTitleOperatorComboBox,
            UdmValueFilter::getSystemTitleExpression);
        filterBinder.forField(systemTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 2000), 0, 2000))
            .bind(filter -> filter.getSystemTitleExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSystemTitleExpression().setFieldFirstValue(value.trim()));
        systemTitleField.addValueChangeListener(event -> filterBinder.validate());
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(systemTitleField, event.getValue()));
        systemTitleField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(systemTitleField, "udm-value-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-value-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSystemStandardNumberLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemStandardNumberField,
            systemStandardNumberOperatorComboBox);
        systemStandardNumberOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.CONTAINS);
        populateOperatorFilters(systemStandardNumberField, systemStandardNumberOperatorComboBox,
            UdmValueFilter::getSystemStandardNumberExpression);
        filterBinder.forField(systemStandardNumberField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 1000), 0, 1000))
            .bind(filter -> filter.getSystemStandardNumberExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSystemStandardNumberExpression().setFieldFirstValue(value.trim()));
        systemStandardNumberField.addValueChangeListener(event -> filterBinder.validate());
        systemStandardNumberOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(systemStandardNumberField, event.getValue()));
        systemStandardNumberField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(systemStandardNumberField, "udm-value-system-standard-number-filter");
        VaadinUtils.addComponentStyle(systemStandardNumberOperatorComboBox,
            "udm-value-system-standard-number-operator-filter");
        return horizontalLayout;
    }

    private TextField initRhAccountNumberLayout() {
        filterBinder.forField(rhAccountNumberField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(getNumberValidator(), ForeignUi.getMessage(NUMBER_VALIDATION_MESSAGE))
            .withConverter(new StringToLongConverter(ForeignUi.getMessage(NUMBER_VALIDATION_MESSAGE)))
            .bind(UdmValueFilter::getRhAccountNumber, UdmValueFilter::setRhAccountNumber);
        rhAccountNumberField.setValue(Objects.nonNull(valueFilter.getRhAccountNumber())
            ? valueFilter.getRhAccountNumber().toString() : StringUtils.EMPTY);
        rhAccountNumberField.setWidth(248, Unit.PIXELS);
        VaadinUtils.addComponentStyle(rhAccountNumberField, "udm-value-rh-account-number-filter");
        return rhAccountNumberField;
    }

    private HorizontalLayout initRhNameLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(rhNameField, rhNameOperatorComboBox);
        rhNameOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.CONTAINS);
        filterBinder.forField(rhNameField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 255), 0, 255))
            .bind(filter -> filter.getRhNameExpression().getFieldFirstValue(),
                (filter, value) -> filter.getRhNameExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(rhNameField, rhNameOperatorComboBox, UdmValueFilter::getRhNameExpression);
        rhNameField.addValueChangeListener(event -> filterBinder.validate());
        rhNameOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(rhNameField, event.getValue()));
        rhNameField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(rhNameField, "udm-value-rh-name-filter");
        VaadinUtils.addComponentStyle(rhNameOperatorComboBox, "udm-value-rh-name-operator-filter");
        return horizontalLayout;
    }

    private ComboBox<Currency> initCurrencyFilter() {
        currencyComboBox.setItems(controller.getAllCurrencies());
        currencyComboBox.setPageLength(16);
        currencyComboBox.setItemCaptionGenerator(
            value -> String.format("%s - %s", value.getCode(), value.getDescription()));
        currencyComboBox.setSelectedItem(valueFilter.getCurrency());
        currencyComboBox.setWidth(248, Unit.PIXELS);
        VaadinUtils.addComponentStyle(currencyComboBox, "udm-value-currency-filter");
        return currencyComboBox;
    }

    private HorizontalLayout initPriceLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceField, priceOperatorComboBox);
        populateOperatorFilters(priceField, priceOperatorComboBox, UdmValueFilter::getPriceExpression);
        priceField.addValueChangeListener(event -> filterBinder.validate());
        priceOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(priceField, event.getValue()));
        filterBinder.forField(priceField)
            .withValidator(new AmountValidator())
            .bind(filter -> filter.getPriceExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getPriceExpression().setFieldFirstValue(new BigDecimal(value)));
        priceField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(priceField, "udm-value-price-filter");
        VaadinUtils.addComponentStyle(priceOperatorComboBox, "udm-value-price-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initPriceInUsdLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceInUsdField, priceInUsdOperatorComboBox);
        populateOperatorFilters(priceInUsdField, priceInUsdOperatorComboBox, UdmValueFilter::getPriceInUsdExpression);
        priceInUsdField.addValueChangeListener(event -> filterBinder.validate());
        priceInUsdOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(priceInUsdField, event.getValue()));
        filterBinder.forField(priceInUsdField)
            .withValidator(new AmountValidator())
            .bind(filter -> filter.getPriceInUsdExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getPriceInUsdExpression().setFieldFirstValue(new BigDecimal(value)));
        priceInUsdField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(priceInUsdField, "udm-value-price-in-usd-filter");
        VaadinUtils.addComponentStyle(priceInUsdOperatorComboBox, "udm-value-price-in-usd-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initLastPriceFlagLastPriceCommentLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(lastPriceFlagComboBox, lastPriceCommentField);
        lastPriceFlagComboBox.setItems(Y_N_ITEMS);
        lastPriceFlagComboBox.setSelectedItem(BooleanUtils.toYNString(valueFilter.getLastPriceFlag()));
        lastPriceFlagComboBox.setSizeFull();
        lastPriceCommentField.setValue(ObjectUtils.defaultIfNull(valueFilter.getLastPriceComment(), StringUtils.EMPTY));
        filterBinder.forField(lastPriceCommentField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 1024), 0, 1024))
            .bind(UdmValueFilter::getLastPriceComment, UdmValueFilter::setLastPriceComment);
        lastPriceCommentField.setSizeFull();
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(lastPriceFlagComboBox, "udm-value-last-price-flag-filter");
        VaadinUtils.addComponentStyle(lastPriceCommentField, "udm-value-last-price-comment-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(contentField, contentOperatorComboBox);
        populateOperatorFilters(contentField, contentOperatorComboBox, UdmValueFilter::getContentExpression);
        contentField.addValueChangeListener(event -> filterBinder.validate());
        contentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(contentField, event.getValue()));
        filterBinder.forField(contentField)
            .withValidator(new AmountZeroValidator())
            .bind(filter -> filter.getContentExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getContentExpression().setFieldFirstValue(new BigDecimal(value)));
        contentField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(contentField, "udm-value-content-filter");
        VaadinUtils.addComponentStyle(contentOperatorComboBox, "udm-value-content-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initLastContentFlagLastContentCommentLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(lastContentFlagComboBox, lastContentCommentField);
        lastContentFlagComboBox.setItems(Y_N_ITEMS);
        lastContentFlagComboBox.setSelectedItem(BooleanUtils.toYNString(valueFilter.getLastContentFlag()));
        lastContentFlagComboBox.setSizeFull();
        lastContentCommentField.setValue(
            ObjectUtils.defaultIfNull(valueFilter.getLastContentComment(), StringUtils.EMPTY));
        filterBinder.forField(lastContentCommentField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 1024), 0, 1024))
            .bind(UdmValueFilter::getLastContentComment, UdmValueFilter::setLastContentComment);
        lastContentCommentField.setSizeFull();
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(lastContentFlagComboBox, "udm-value-last-content-flag-filter");
        VaadinUtils.addComponentStyle(lastContentCommentField, "udm-value-last-content-comment-filter");
        return horizontalLayout;
    }

    private ComboBox<PublicationType> initLastPubTypeLayout() {
        List<PublicationType> publicationTypes = controller.getPublicationTypes();
        publicationTypes.add(0, new PublicationType());
        lastPubTypeComboBox.setItems(publicationTypes);
        lastPubTypeComboBox.setPageLength(12);
        lastPubTypeComboBox.setItemCaptionGenerator(value -> Objects.nonNull(value.getName())
            ? value.getNameAndDescription()
            : "NULL");
        lastPubTypeComboBox.setSelectedItem(valueFilter.getLastPubType());
        lastPubTypeComboBox.setWidth(248, Unit.PIXELS);
        VaadinUtils.addComponentStyle(lastPubTypeComboBox, "udm-value-last-pub-type-filter");
        return lastPubTypeComboBox;
    }

    private TextField initCommentLayout() {
        commentField.setValue(ObjectUtils.defaultIfNull(valueFilter.getComment(), StringUtils.EMPTY));
        filterBinder.forField(commentField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 1024), 0, 1024))
            .bind(UdmValueFilter::getComment, UdmValueFilter::setComment);
        commentField.setSizeFull();
        VaadinUtils.addComponentStyle(commentField, "udm-value-comment-filter");
        return commentField;
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox =
            new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, FilterOperatorEnum.IS_NULL);
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return filterOperatorComboBox;
    }

    private void populateOperatorFilters(TextField textField, ComboBox<FilterOperatorEnum> comboBox,
                                         Function<UdmValueFilter, FilterExpression<?>> expressionFunction) {
        FilterExpression<?> filterExpression = expressionFunction.apply(valueFilter);
        Object fieldValue = filterExpression.getFieldFirstValue();
        FilterOperatorEnum filterOperator = filterExpression.getOperator();
        if (Objects.nonNull(fieldValue)) {
            textField.setValue(fieldValue.toString());
            comboBox.setSelectedItem(filterOperator);
        } else if (Objects.nonNull(filterOperator) && 0 == filterOperator.getArgumentsNumber()) {
            textField.setEnabled(false);
            comboBox.setSelectedItem(filterOperator);
        }
    }

    private void updateOperatorField(TextField textField, FilterOperatorEnum filterOperator) {
        if (Objects.nonNull(filterOperator) && 0 == filterOperator.getArgumentsNumber()) {
            textField.clear();
            textField.setEnabled(false);
        } else {
            textField.setEnabled(true);
        }
        filterBinder.validate();
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
                    Arrays.asList(wrWrkInstField, systemTitleField, systemStandardNumberField, rhAccountNumberField,
                        rhNameField, priceField, priceInUsdField, lastPriceCommentField, contentField,
                        lastContentCommentField, commentField));
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
        wrWrkInstField.clear();
        clearOperatorLayout(systemTitleField, systemTitleOperatorComboBox);
        clearOperatorLayout(systemStandardNumberField, systemStandardNumberOperatorComboBox);
        rhAccountNumberField.clear();
        clearOperatorLayout(rhNameField, rhNameOperatorComboBox);
        currencyComboBox.clear();
        clearOperatorLayout(priceField, priceOperatorComboBox);
        clearOperatorLayout(priceInUsdField, priceInUsdOperatorComboBox);
        lastPriceFlagComboBox.clear();
        lastPriceCommentField.clear();
        clearOperatorLayout(contentField, contentOperatorComboBox);
        lastContentFlagComboBox.clear();
        lastContentCommentField.clear();
        lastPubTypeComboBox.clear();
        commentField.clear();
    }

    private void clearValueFilter() {
        valueFilter.setAssignees(new HashSet<>());
        valueFilter.setLastValuePeriods(new HashSet<>());
        valueFilter.setWrWrkInst(null);
        valueFilter.setSystemTitleExpression(new FilterExpression<>());
        valueFilter.setSystemStandardNumberExpression(new FilterExpression<>());
        valueFilter.setRhAccountNumber(null);
        valueFilter.setRhNameExpression(new FilterExpression<>());
        valueFilter.setCurrency(null);
        valueFilter.setPriceExpression(new FilterExpression<>());
        valueFilter.setPriceInUsdExpression(new FilterExpression<>());
        valueFilter.setLastPriceFlag(null);
        valueFilter.setLastPriceComment(null);
        valueFilter.setContentExpression(new FilterExpression<>());
        valueFilter.setLastContentFlag(null);
        valueFilter.setLastContentComment(null);
        valueFilter.setPubType(null);
        valueFilter.setLastPubType(null);
        valueFilter.setComment(null);
    }

    private void clearOperatorLayout(TextField textField, ComboBox<FilterOperatorEnum> comboBox) {
        textField.clear();
        comboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
    }

    private void populateValueFilter() {
        valueFilter.setWrWrkInst(getLongFromTextField(wrWrkInstField));
        valueFilter.setSystemTitleExpression(buildNumberFilterExpression(systemTitleField, systemTitleOperatorComboBox,
            Function.identity()));
        valueFilter.setSystemStandardNumberExpression(buildNumberFilterExpression(systemStandardNumberField,
            systemStandardNumberOperatorComboBox, Function.identity()));
        valueFilter.setRhAccountNumber(getLongFromTextField(rhAccountNumberField));
        valueFilter.setRhNameExpression(buildNumberFilterExpression(rhNameField, rhNameOperatorComboBox,
            Function.identity()));
        valueFilter.setCurrency(Objects.nonNull(currencyComboBox.getValue()) ? currencyComboBox.getValue() : null);
        valueFilter.setPriceExpression(buildAmountFilterExpression(priceField, priceOperatorComboBox,
            BigDecimal::new));
        valueFilter.setPriceInUsdExpression(buildAmountFilterExpression(priceInUsdField, priceInUsdOperatorComboBox,
            BigDecimal::new));
        valueFilter.setLastPriceFlag(Objects.isNull(lastPriceFlagComboBox.getValue())
            ? null : convertStringToBoolean(lastPriceFlagComboBox.getValue()));
        valueFilter.setLastPriceComment(getStringFromTextField(lastPriceCommentField));
        valueFilter.setContentExpression(buildAmountFilterExpression(contentField, contentOperatorComboBox,
            BigDecimal::new));
        valueFilter.setLastContentFlag(Objects.isNull(lastContentFlagComboBox.getValue())
            ? null : convertStringToBoolean(lastContentFlagComboBox.getValue()));
        valueFilter.setLastContentComment(getStringFromTextField(lastContentCommentField));
        valueFilter.setLastPubType(Objects.nonNull(lastPubTypeComboBox.getValue())
            ? lastPubTypeComboBox.getValue() : null);
        valueFilter.setComment(getStringFromTextField(commentField));
    }

    private Long getLongFromTextField(TextField textField) {
        return StringUtils.isNotEmpty(textField.getValue()) ? Long.valueOf(textField.getValue().trim()) : null;
    }

    private String getStringFromTextField(TextField textField) {
        return StringUtils.isNotEmpty(textField.getValue()) ? textField.getValue().trim() : null;
    }

    private <T> FilterExpression<T> buildNumberFilterExpression(TextField textField,
                                                                ComboBox<FilterOperatorEnum> comboBox,
                                                                Function<String, T> valueConverter) {
        return buildFilterExpression(textField, comboBox, valueConverter,
            field -> StringUtils.isNotEmpty(field.getValue()));
    }

    private <T> FilterExpression<T> buildAmountFilterExpression(TextField textField,
                                                                ComboBox<FilterOperatorEnum> comboBox,
                                                                Function<String, T> valueConverter) {
        return buildFilterExpression(textField, comboBox, valueConverter,
            field -> StringUtils.isNotBlank(field.getValue()));
    }

    private <T> FilterExpression<T> buildFilterExpression(TextField textField,
                                                          ComboBox<FilterOperatorEnum> comboBox,
                                                          Function<String, T> valueConverter,
                                                          Predicate<TextField> successPredicate) {
        FilterExpression<T> filterExpression = new FilterExpression<>();
        if (successPredicate.test(textField)) {
            filterExpression.setFieldFirstValue(valueConverter.apply(textField.getValue().trim()));
            filterExpression.setOperator(comboBox.getValue());
        } else if (0 == comboBox.getValue().getArgumentsNumber()) {
            filterExpression.setOperator(comboBox.getValue());
        }
        return filterExpression;
    }

    private SerializablePredicate<String> getNumberValidator() {
        return value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim());
    }

    private Boolean convertStringToBoolean(String value) {
        return "Y".equals(value);
    }
}
