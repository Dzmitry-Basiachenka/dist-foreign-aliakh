package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AggregateLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.DetailLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.TypeOfUseFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Window to apply additional filters for UDM baseline.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmBaselineFiltersWindow extends Window {

    private static final int ONE = 1;
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String NOT_NUMERIC_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
    private static final String GRATER_OR_EQUAL_VALIDATION_MESSAGE = "field.error.greater_or_equal_to";

    private final StringLengthValidator numberStringLengthValidator =
        new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9);
    private final TextField systemTitleField = new TextField(ForeignUi.getMessage("label.system_title"));
    private final ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox = buildTextOperatorComboBox();
    private final TextField surveyCountryField = new TextField(ForeignUi.getMessage("label.survey_country"));
    private final ComboBox<FilterOperatorEnum> surveyCountryOperatorComboBox = buildTextOperatorComboBox();
    private final TextField usageDetailIdField = new TextField(ForeignUi.getMessage("label.usage_detail_id"));
    private final ComboBox<FilterOperatorEnum> usageDetailIdOperatorComboBox = buildTextOperatorComboBox();
    private final TextField wrWrkInstFromField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_from"));
    private final TextField wrWrkInstToField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_to"));
    private final ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField annualizedCopiesFrom = new TextField(ForeignUi.getMessage("label.annualized_copies_from"));
    private final TextField annualizedCopiesTo = new TextField(ForeignUi.getMessage("label.annualized_copies_to"));
    private final ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox = buildNumericOperatorComboBox();
    private final Binder<UdmBaselineFilter> filterBinder = new Binder<>();
    private DetailLicenseeClassFilterWidget detailLicenseeClassFilterWidget;
    private AggregateLicenseeClassFilterWidget aggregateLicenseeClassFilterWidget;
    private TypeOfUseFilterWidget typeOfUseFilterWidget;
    private UdmBaselineFilter appliedBaselineFilter;
    private final UdmBaselineFilter baselineFilter;
    private final IUdmBaselineFilterController controller;

    /**
     * Constructor.
     *
     * @param controller        instance of {@link IUdmBaselineFilterController}
     * @param udmBaselineFilter instance of {@link UdmBaselineFilter} to be displayed on window
     */
    public UdmBaselineFiltersWindow(IUdmBaselineFilterController controller, UdmBaselineFilter udmBaselineFilter) {
        this.controller = controller;
        baselineFilter = new UdmBaselineFilter(udmBaselineFilter);
        appliedBaselineFilter = udmBaselineFilter;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.udm_baseline_additional_filters"));
        setResizable(false);
        setWidth(750, Unit.PIXELS);
        setHeight(365, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-baseline-additional-filters-window");
    }

    /**
     * @return applied UDM baseline filter.
     */
    public UdmBaselineFilter getAppliedBaselineFilter() {
        return appliedBaselineFilter;
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        buttonsLayout.setMargin(new MarginInfo(true, false, false, false));
        initDetailLicenseeClassFilterWidget();
        initAggregateLicenseeClassFilterWidget();
        initTypeOfUseFilterWidget();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initLicenseeClassesLayout(), typeOfUseFilterWidget, initWrWrkInstLayout(),
            initSystemTitleLayout(), initUsageDetailIdLayout(), initSurveyCountryLayout(), initAnnualizedCopiesLayout(),
            buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, true, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        filterBinder.validate();
        return rootLayout;
    }

    private void initDetailLicenseeClassFilterWidget() {
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(controller::getDetailLicenseeClasses,
            baselineFilter.getDetailLicenseeClasses());
        detailLicenseeClassFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<DetailLicenseeClass>) saveEvent ->
                baselineFilter.setDetailLicenseeClasses(saveEvent.getSelectedItemsIds()));
    }

    private void initAggregateLicenseeClassFilterWidget() {
        aggregateLicenseeClassFilterWidget =
            new AggregateLicenseeClassFilterWidget(controller::getAggregateLicenseeClasses,
                baselineFilter.getAggregateLicenseeClasses());
        aggregateLicenseeClassFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<AggregateLicenseeClass>) saveEvent ->
                baselineFilter.setAggregateLicenseeClasses(saveEvent.getSelectedItemsIds()));
    }

    private void initTypeOfUseFilterWidget() {
        typeOfUseFilterWidget =
            new TypeOfUseFilterWidget(controller::getTypeOfUses, baselineFilter.getReportedTypeOfUses());
        typeOfUseFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            baselineFilter.setReportedTypeOfUses(saveEvent.getSelectedItemsIds()));
    }

    private HorizontalLayout initLicenseeClassesLayout() {
        HorizontalLayout licenseeClassesLayout =
            new HorizontalLayout(detailLicenseeClassFilterWidget, aggregateLicenseeClassFilterWidget);
        licenseeClassesLayout.setSizeFull();
        licenseeClassesLayout.setSpacing(true);
        return licenseeClassesLayout;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        HorizontalLayout wrWrkInstLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        populateOperatorFilters(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            UdmBaselineFilter::getWrWrkInstExpression);
        wrWrkInstFromField.addValueChangeListener(event -> filterBinder.validate());
        wrWrkInstOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(wrWrkInstFromField, wrWrkInstToField, event.getValue()));
        filterBinder.forField(wrWrkInstFromField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NOT_NUMERIC_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(wrWrkInstFromField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getWrWrkInstExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getWrWrkInstExpression().setFieldFirstValue(Long.valueOf(value)));
        filterBinder.forField(wrWrkInstToField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NOT_NUMERIC_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(wrWrkInstToField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(wrWrkInstFromField, wrWrkInstToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.wr_wrk_inst_from")))
            .bind(filter -> filter.getWrWrkInstExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getWrWrkInstExpression().setFieldFirstValue(Long.valueOf(value)));
        wrWrkInstFromField.setSizeFull();
        wrWrkInstToField.setSizeFull();
        wrWrkInstLayout.setSizeFull();
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "udm-baseline-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "udm-baseline-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "udm-baseline-wr-wrk-inst-operator-filter");
        return wrWrkInstLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        populateOperatorFilters(systemTitleField, systemTitleOperatorComboBox,
            UdmBaselineFilter::getSystemTitleExpression);
        filterBinder.forField(systemTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(filter -> filter.getSystemTitleExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSystemTitleExpression().setFieldFirstValue(value.trim()));
        systemTitleField.addValueChangeListener(event -> filterBinder.validate());
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(systemTitleField, event.getValue()));
        systemTitleField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(systemTitleField, "udm-baseline-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-baseline-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        HorizontalLayout annualizedCopiesLayout =
            new HorizontalLayout(annualizedCopiesFrom, annualizedCopiesTo, annualizedCopiesOperatorComboBox);
        populateOperatorFilters(annualizedCopiesFrom, annualizedCopiesTo, annualizedCopiesOperatorComboBox,
            UdmBaselineFilter::getAnnualizedCopiesExpression);
        annualizedCopiesFrom.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(annualizedCopiesFrom)
            .withValidator(new AmountZeroValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesFrom, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(annualizedCopiesTo)
            .withValidator(new AmountZeroValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesTo, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(annualizedCopiesFrom, annualizedCopiesTo),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.annualized_copies_from")))
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldSecondValue(new BigDecimal(value)));
        annualizedCopiesOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(annualizedCopiesFrom, annualizedCopiesTo, event.getValue()));
        annualizedCopiesFrom.setSizeFull();
        annualizedCopiesTo.setSizeFull();
        annualizedCopiesLayout.setSizeFull();
        VaadinUtils.addComponentStyle(annualizedCopiesFrom, "udm-baseline-annualized-copies-from-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesTo, "udm-baseline-annualized-copies-to-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesOperatorComboBox,
            "udm-baseline-annualized-copies-operator-filter");
        return annualizedCopiesLayout;
    }

    private HorizontalLayout initUsageDetailIdLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        populateOperatorFilters(usageDetailIdField, usageDetailIdOperatorComboBox,
            UdmBaselineFilter::getUsageDetailIdExpression);
        filterBinder.forField(usageDetailIdField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .bind(filter -> filter.getUsageDetailIdExpression().getFieldFirstValue(),
                (filter, value) -> filter.getUsageDetailIdExpression().setFieldFirstValue(value.trim()));
        usageDetailIdField.addValueChangeListener(event -> filterBinder.validate());
        usageDetailIdOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(usageDetailIdField, event.getValue()));
        usageDetailIdField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(usageDetailIdField, "udm-baseline-usage-detail-id-filter");
        VaadinUtils.addComponentStyle(usageDetailIdOperatorComboBox, "udm-baseline-usage-detail-id-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSurveyCountryLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(surveyCountryField, surveyCountryOperatorComboBox);
        populateOperatorFilters(surveyCountryField, surveyCountryOperatorComboBox,
            UdmBaselineFilter::getSurveyCountryExpression);
        filterBinder.forField(surveyCountryField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 100), 0, 100))
            .bind(filter -> filter.getSurveyCountryExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSurveyCountryExpression().setFieldFirstValue(value.trim()));
        surveyCountryField.addValueChangeListener(event -> filterBinder.validate());
        surveyCountryOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(surveyCountryField, event.getValue()));
        surveyCountryField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(surveyCountryField, "udm-baseline-survey-country-filter");
        VaadinUtils.addComponentStyle(surveyCountryOperatorComboBox, "udm-baseline-survey-country-operator-filter");
        return horizontalLayout;
    }

    private ComboBox<FilterOperatorEnum> buildNumericOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = buildOperatorComboBox();
        filterOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, FilterOperatorEnum.BETWEEN,
            FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
        return filterOperatorComboBox;
    }

    private ComboBox<FilterOperatorEnum> buildTextOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = buildOperatorComboBox();
        filterOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.CONTAINS, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
        return filterOperatorComboBox;
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return filterOperatorComboBox;
    }

    private void populateOperatorFilters(TextField textField, ComboBox<FilterOperatorEnum> comboBox,
                                         Function<UdmBaselineFilter, FilterExpression<?>> expressionFunction) {
        FilterExpression<?> filterExpression = expressionFunction.apply(baselineFilter);
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

    private void populateOperatorFilters(TextField fromField, TextField toField, ComboBox<FilterOperatorEnum> comboBox,
                                         Function<UdmBaselineFilter, FilterExpression<Number>> getExpressionFunction) {
        FilterExpression<Number> filterExpression = getExpressionFunction.apply(baselineFilter);
        Number firstFieldValue = filterExpression.getFieldFirstValue();
        Number secondFieldValue = filterExpression.getFieldSecondValue();
        FilterOperatorEnum filterOperator = filterExpression.getOperator();
        toField.setEnabled(Objects.nonNull(secondFieldValue));
        if (Objects.nonNull(firstFieldValue)) {
            fromField.setValue(firstFieldValue.toString());
            toField.setValue(Objects.nonNull(secondFieldValue) ? secondFieldValue.toString() : StringUtils.EMPTY);
            comboBox.setSelectedItem(filterOperator);
        } else if (Objects.nonNull(filterOperator) && 0 == filterOperator.getArgumentsNumber()) {
            fromField.setEnabled(false);
            toField.setEnabled(false);
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

    private void updateOperatorField(TextField fromField, TextField toField, FilterOperatorEnum filterOperator) {
        if (0 == filterOperator.getArgumentsNumber()) {
            fromField.clear();
            fromField.setEnabled(false);
            toField.clear();
            toField.setEnabled(false);
        } else if (ONE == filterOperator.getArgumentsNumber()) {
            fromField.setEnabled(true);
            toField.clear();
            toField.setEnabled(false);
        } else {
            fromField.setEnabled(true);
            toField.setEnabled(true);
        }
        filterBinder.validate();
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            if (filterBinder.isValid()) {
                populateBaselineFilter();
                appliedBaselineFilter = baselineFilter;
                close();
            } else {
                Windows.showValidationErrorWindow(Arrays.asList(wrWrkInstFromField, wrWrkInstToField, systemTitleField,
                    usageDetailIdField, surveyCountryField, annualizedCopiesFrom, annualizedCopiesTo));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void populateBaselineFilter() {
        baselineFilter.setWrWrkInstExpression(buildNumberFilterExpression(wrWrkInstFromField, wrWrkInstToField,
            wrWrkInstOperatorComboBox, Integer::valueOf));
        baselineFilter.setSystemTitleExpression(buildFilterExpression(systemTitleField, systemTitleOperatorComboBox,
            Function.identity()));
        baselineFilter.setUsageDetailIdExpression(buildFilterExpression(usageDetailIdField,
            usageDetailIdOperatorComboBox,
            Function.identity()));
        baselineFilter.setSurveyCountryExpression(buildFilterExpression(surveyCountryField,
            surveyCountryOperatorComboBox, Function.identity()));
        baselineFilter.setAnnualizedCopiesExpression(buildAmountFilterExpression(annualizedCopiesFrom,
            annualizedCopiesTo, annualizedCopiesOperatorComboBox, BigDecimal::new));
    }

    private void clearFilters() {
        baselineFilter.setDetailLicenseeClasses(new HashSet<>());
        baselineFilter.setAggregateLicenseeClasses(new HashSet<>());
        baselineFilter.setReportedTypeOfUses(new HashSet<>());
        baselineFilter.setWrWrkInstExpression(new FilterExpression<>());
        baselineFilter.setSystemTitleExpression(new FilterExpression<>());
        baselineFilter.setUsageDetailIdExpression(new FilterExpression<>());
        baselineFilter.setSurveyCountryExpression(new FilterExpression<>());
        baselineFilter.setAnnualizedCopiesExpression(new FilterExpression<>());
        detailLicenseeClassFilterWidget.reset();
        aggregateLicenseeClassFilterWidget.reset();
        typeOfUseFilterWidget.reset();
        clearOperatorLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        clearOperatorLayout(systemTitleField, systemTitleOperatorComboBox);
        clearOperatorLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        clearOperatorLayout(surveyCountryField, surveyCountryOperatorComboBox);
        clearOperatorLayout(annualizedCopiesFrom, annualizedCopiesTo, annualizedCopiesOperatorComboBox);
    }

    private void clearOperatorLayout(TextField textField, ComboBox<FilterOperatorEnum> comboBox) {
        textField.clear();
        comboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
    }

    private void clearOperatorLayout(TextField fromField, TextField toField, ComboBox<FilterOperatorEnum> comboBox) {
        fromField.clear();
        toField.clear();
        toField.setEnabled(false);
        comboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
    }

    private <T> FilterExpression<T> buildFilterExpression(TextField textField,
                                                          ComboBox<FilterOperatorEnum> comboBox,
                                                          Function<String, T> valueConverter) {
        return buildFilterExpression(textField, comboBox, valueConverter,
            field -> StringUtils.isNotEmpty(textField.getValue()));
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

    private FilterExpression<Number> buildNumberFilterExpression(TextField fromField, TextField toField,
                                                                 ComboBox<FilterOperatorEnum> comboBox,
                                                                 Function<String, Number> valueConverter) {
        return buildFilterExpression(fromField, toField, comboBox, valueConverter,
            field -> StringUtils.isNotEmpty(field.getValue()));
    }

    private FilterExpression<Number> buildAmountFilterExpression(TextField fromField, TextField toField,
                                                                 ComboBox<FilterOperatorEnum> comboBox,
                                                                 Function<String, Number> valueConverter) {
        return buildFilterExpression(fromField, toField, comboBox, valueConverter,
            field -> StringUtils.isNotBlank(field.getValue()));
    }

    private FilterExpression<Number> buildFilterExpression(TextField fromField, TextField toField,
                                                           ComboBox<FilterOperatorEnum> comboBox,
                                                           Function<String, Number> valueConverter,
                                                           Predicate<TextField> successPredicate) {
        FilterExpression<Number> filterExpression = new FilterExpression<>();
        if (successPredicate.test(fromField)) {
            filterExpression.setFieldFirstValue(valueConverter.apply(fromField.getValue().trim()));
            filterExpression.setFieldSecondValue(
                successPredicate.test(toField) ? valueConverter.apply(toField.getValue().trim()) : null);
            filterExpression.setOperator(comboBox.getValue());
        } else if (0 == comboBox.getValue().getArgumentsNumber()) {
            filterExpression.setOperator(comboBox.getValue());
        }
        return filterExpression;
    }


    private SerializablePredicate<String> getNumberValidator() {
        return value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim());
    }

    private SerializablePredicate<String> getBetweenOperatorValidator(TextField fieldToValidate,
                                                                      ComboBox<FilterOperatorEnum> comboBox) {
        return value -> FilterOperatorEnum.BETWEEN != comboBox.getValue()
            || StringUtils.isNotBlank(fieldToValidate.getValue());
    }

    private boolean validateBigDecimalFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        AmountZeroValidator amountZeroValidator = new AmountZeroValidator();
        return StringUtils.isBlank(fromValue)
            || StringUtils.isBlank(toValue)
            || !amountZeroValidator.isValid(fromValue)
            || !amountZeroValidator.isValid(toValue)
            || 0 <= new BigDecimal(toValue.trim()).compareTo(new BigDecimal(fromValue.trim()));
    }

    private boolean validateIntegerFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        return StringUtils.isEmpty(fromValue)
            || StringUtils.isEmpty(toValue)
            || !getNumberValidator().test(fromValue)
            || !getNumberValidator().test(toValue)
            || 0 <= Integer.valueOf(toValue.trim()).compareTo(Integer.valueOf(fromValue.trim()));
    }
}
