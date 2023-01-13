package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.NumericValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents common logic for creating additional ACL filters.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 14/01/2022
 *
 * @author Aliaksandr Liakh
 */
public abstract class CommonAclFiltersWindow extends Window {

    private static final List<FilterOperatorEnum> FLAG_ITEMS = List.of(FilterOperatorEnum.Y, FilterOperatorEnum.N);
    private static final List<FilterOperatorEnum> LAST_VALUE_FLAG_ITEMS =
        List.of(FilterOperatorEnum.Y, FilterOperatorEnum.N, FilterOperatorEnum.IS_NULL);
    private static final int ONE = 1;

    /**
     * Builds text operator combobox.
     *
     * @return text operator combobox
     */
    protected final ComboBox<FilterOperatorEnum> buildTextOperatorComboBox() {
        return buildOperatorComboBox(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.CONTAINS, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
    }

    /**
     * Builds text operator combobox.
     *
     * @param items {@link FilterOperatorEnum} items
     * @return text operator combobox
     */
    protected ComboBox<FilterOperatorEnum> buildTextOperatorComboBox(FilterOperatorEnum... items) {
        return buildOperatorComboBox(items);
    }

    /**
     * Builds numeric operator combobox.
     *
     * @return numeric operator combobox
     */
    protected final ComboBox<FilterOperatorEnum> buildNumericOperatorComboBox() {
        return buildOperatorComboBox(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.BETWEEN, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
    }

    /**
     * Builds numeric operator combobox.
     *
     * @param items {@link FilterOperatorEnum} items
     * @return numeric operator combobox
     */
    protected ComboBox<FilterOperatorEnum> buildNumericOperatorComboBox(FilterOperatorEnum... items) {
        return buildOperatorComboBox(items);
    }

    /**
     * Populates comboBox with specific values for flags.
     *
     * @param comboBox     comboBox to be populated
     * @param selectedItem selected item
     * @param componentId  comboBox id
     */
    protected void populateFlagComboBox(ComboBox<FilterOperatorEnum> comboBox, FilterOperatorEnum selectedItem,
                                        String componentId) {
        populateComboBox(comboBox, selectedItem, componentId, FLAG_ITEMS);
    }

    /**
     * Populates comboBox with specific values for last value flags.
     *
     * @param comboBox     comboBox to be populated
     * @param selectedItem selected item
     * @param componentId  comboBox id
     */
    protected void populateLastValueFlagComboBox(ComboBox<FilterOperatorEnum> comboBox, FilterOperatorEnum selectedItem,
                                                 String componentId) {
        populateComboBox(comboBox, selectedItem, componentId, LAST_VALUE_FLAG_ITEMS);
    }

    /**
     * Populates operation filters.
     *
     * @param textField        text field
     * @param operatorComboBox operator combobox
     * @param filterExpression filter expression
     */
    protected void populateOperatorFilters(TextField textField, ComboBox<FilterOperatorEnum> operatorComboBox,
                                           FilterExpression<?> filterExpression) {
        Object fieldValue = filterExpression.getFieldFirstValue();
        FilterOperatorEnum filterOperator = filterExpression.getOperator();
        if (Objects.nonNull(fieldValue)) {
            textField.setValue(fieldValue.toString());
            operatorComboBox.setSelectedItem(filterOperator);
        } else if (Objects.nonNull(filterOperator) && 0 == filterOperator.getArgumentsNumber()) {
            textField.setEnabled(false);
            operatorComboBox.setSelectedItem(filterOperator);
        }
    }

    /**
     * Populates operation filters.
     *
     * @param fromField        'from' text field
     * @param toField          'to' text field
     * @param operatorComboBox operator combobox
     * @param filterExpression filter expression
     */
    protected void populateOperatorFilters(TextField fromField, TextField toField,
                                           ComboBox<FilterOperatorEnum> operatorComboBox,
                                           FilterExpression<Number> filterExpression) {
        Number firstFieldValue = filterExpression.getFieldFirstValue();
        Number secondFieldValue = filterExpression.getFieldSecondValue();
        FilterOperatorEnum filterOperator = filterExpression.getOperator();
        toField.setEnabled(Objects.nonNull(secondFieldValue));
        if (Objects.nonNull(firstFieldValue)) {
            fromField.setValue(firstFieldValue.toString());
            toField.setValue(Objects.nonNull(secondFieldValue) ? secondFieldValue.toString() : StringUtils.EMPTY);
            operatorComboBox.setSelectedItem(filterOperator);
        } else if (Objects.nonNull(filterOperator) && 0 == filterOperator.getArgumentsNumber()) {
            fromField.setEnabled(false);
            toField.setEnabled(false);
            operatorComboBox.setSelectedItem(filterOperator);
        }
    }

    /**
     * Updates operator field.
     *
     * @param filterBinder   filter binder
     * @param textField      text field
     * @param filterOperator filter operator
     */
    protected void updateOperatorField(Binder<?> filterBinder, TextField textField, FilterOperatorEnum filterOperator) {
        if (0 == filterOperator.getArgumentsNumber()) {
            textField.clear();
            textField.setEnabled(false);
        } else {
            textField.setEnabled(true);
        }
        filterBinder.validate();
    }

    /**
     * Updates operator field.
     *
     * @param filterBinder   filter binder
     * @param fromField      'from' text field
     * @param toField        'to' text field
     * @param filterOperator filter operator
     */
    protected void updateOperatorField(Binder<?> filterBinder, TextField fromField, TextField toField,
                                       FilterOperatorEnum filterOperator) {
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

    /**
     * Clears operator layout.
     *
     * @param textField        text field
     * @param operatorComboBox operator combobox
     */
    protected void clearOperatorLayout(TextField textField, ComboBox<FilterOperatorEnum> operatorComboBox) {
        textField.clear();
        operatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
    }

    /**
     * Clears operator layout.
     *
     * @param fromField        'from' text field
     * @param toField          'to' text field
     * @param operatorComboBox operator combobox
     */
    protected void clearOperatorLayout(TextField fromField, TextField toField,
                                       ComboBox<FilterOperatorEnum> operatorComboBox) {
        fromField.clear();
        toField.clear();
        toField.setEnabled(false);
        operatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
    }

    /**
     * Builds text filter expression.
     *
     * @param textField        text field
     * @param operatorComboBox operator combobox
     * @param valueConverter   value converter
     * @param <T>              type of value converter
     * @return text filter expression
     */
    protected <T> FilterExpression<T> buildTextFilterExpression(TextField textField,
                                                                ComboBox<FilterOperatorEnum> operatorComboBox,
                                                                Function<String, T> valueConverter) {
        return buildFilterExpression(textField, operatorComboBox, valueConverter,
            field -> StringUtils.isNotEmpty(field.getValue()));
    }

    /**
     * Builds number filter expression.
     *
     * @param fromField        'from' text field
     * @param toField          'to' text field
     * @param operatorComboBox operator combobox
     * @param valueConverter   value converter
     * @return number filter expression
     */
    protected FilterExpression<Number> buildNumberFilterExpression(TextField fromField, TextField toField,
                                                                   ComboBox<FilterOperatorEnum> operatorComboBox,
                                                                   Function<String, Number> valueConverter) {
        return buildFilterExpression(fromField, toField, operatorComboBox, valueConverter,
            field -> StringUtils.isNotEmpty(field.getValue()));
    }

    /**
     * Builds amount filter expression.
     *
     * @param fromField        'from' text field
     * @param toField          'to' text field
     * @param operatorComboBox operator combobox
     * @param valueConverter   value converter
     * @return amount filter expression
     */
    protected FilterExpression<Number> buildAmountFilterExpression(TextField fromField, TextField toField,
                                                                   ComboBox<FilterOperatorEnum> operatorComboBox,
                                                                   Function<String, Number> valueConverter) {
        return buildFilterExpression(fromField, toField, operatorComboBox, valueConverter,
            field -> StringUtils.isNotBlank(field.getValue()));
    }

    /**
     * Builds filter expression.
     *
     * @param textField        text field
     * @param operatorComboBox operator combobox
     * @param valueConverter   value converter
     * @param successPredicate success predicate
     * @param <T>              type of value converter
     * @return filter expression
     */
    protected <T> FilterExpression<T> buildFilterExpression(TextField textField,
                                                            ComboBox<FilterOperatorEnum> operatorComboBox,
                                                            Function<String, T> valueConverter,
                                                            Predicate<TextField> successPredicate) {
        FilterExpression<T> filterExpression = new FilterExpression<>();
        if (successPredicate.test(textField)) {
            filterExpression.setFieldFirstValue(valueConverter.apply(textField.getValue().trim()));
            filterExpression.setOperator(operatorComboBox.getValue());
        } else if (0 == operatorComboBox.getValue().getArgumentsNumber()) {
            filterExpression.setOperator(operatorComboBox.getValue());
        }
        return filterExpression;
    }

    /**
     * Applies common formatting for HorizontalLayout with one component.
     *
     * @param mainLayout main horizontal layout
     * @param component  component to expand
     */
    protected void applyCommonTextFieldFormatting(HorizontalLayout mainLayout, Component component) {
        mainLayout.setSizeFull();
        component.setSizeFull();
        mainLayout.setExpandRatio(component, 1f);
    }

    /**
     * Applies common formatting for HorizontalLayout with two components.
     *
     * @param mainLayout      main horizontal layout
     * @param firstComponent  first component to expand
     * @param secondComponent second component to expand
     */
    protected void applyCommonNumericFieldFormatting(HorizontalLayout mainLayout, Component firstComponent,
                                                     Component secondComponent) {
        mainLayout.setSizeFull();
        firstComponent.setSizeFull();
        mainLayout.setExpandRatio(firstComponent, 0.5f);
        secondComponent.setSizeFull();
        mainLayout.setExpandRatio(secondComponent, 0.5f);
    }

    /**
     * Builds filter expression.
     *
     * @param fromField        'from' text field
     * @param toField          'to' text field
     * @param operatorComboBox operator combobox
     * @param valueConverter   value converter
     * @param successPredicate success predicate
     * @param <T>              type of value converter
     * @return filter expression
     */
    protected <T> FilterExpression<T> buildFilterExpression(TextField fromField, TextField toField,
                                                            ComboBox<FilterOperatorEnum> operatorComboBox,
                                                            Function<String, T> valueConverter,
                                                            Predicate<TextField> successPredicate) {
        FilterExpression<T> filterExpression = new FilterExpression<>();
        if (successPredicate.test(fromField)) {
            filterExpression.setFieldFirstValue(valueConverter.apply(fromField.getValue().trim()));
            filterExpression.setFieldSecondValue(
                successPredicate.test(toField) ? valueConverter.apply(toField.getValue().trim()) : null);
            filterExpression.setOperator(operatorComboBox.getValue());
        } else if (0 == operatorComboBox.getValue().getArgumentsNumber()) {
            filterExpression.setOperator(operatorComboBox.getValue());
        }
        return filterExpression;
    }

    /**
     * Gets text length validator.
     *
     * @param maxLength maximum length
     * @return length validator
     */
    protected Validator<String> getTextStringLengthValidator(int maxLength) {
        return new StringLengthValidator(ForeignUi.getMessage("field.error.length", maxLength), 0, maxLength);
    }

    /**
     * Gets number length validator.
     *
     * @param maxLength maximum length
     * @return length validator
     */
    protected Validator<String> getNumberStringLengthValidator(int maxLength) {
        return new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", maxLength), 0, maxLength);
    }

    /**
     * Gets 'between' operation validator.
     *
     * @param textField        text field
     * @param operatorComboBox operator combobox
     * @return 'between' operation validator
     */
    protected SerializablePredicate<String> getBetweenOperatorValidator(TextField textField,
                                                                        ComboBox<FilterOperatorEnum> operatorComboBox) {
        return value -> FilterOperatorEnum.BETWEEN != operatorComboBox.getValue()
            || StringUtils.isNotBlank(textField.getValue());
    }

    /**
     * Validates numbers in 'from' and 'to' text fields.
     *
     * @param fromField 'from' text field
     * @param toField   'to' text field
     * @return {@code true} is the text fields contains valid numbers, {@code false} otherwise
     */
    protected boolean validateIntegerFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        NumericValidator numericValidator = new NumericValidator();
        return StringUtils.isEmpty(fromValue)
            || StringUtils.isEmpty(toValue)
            || !numericValidator.isValid(fromValue)
            || !numericValidator.isValid(toValue)
            || 0 <= Long.valueOf(toValue.trim()).compareTo(Long.valueOf(fromValue.trim()));
    }

    /**
     * Validates BigDecimal values in 'from' and 'to' text fields.
     *
     * @param fromField 'from' text field
     * @param toField   'to' text field
     * @return {@code true} is the text fields contains valid numbers, {@code false} otherwise
     */
    protected boolean validateBigDecimalFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        AmountValidator amountValidator = new AmountValidator();
        return StringUtils.isBlank(fromValue)
            || StringUtils.isBlank(toValue)
            || !amountValidator.isValid(fromValue)
            || !amountValidator.isValid(toValue)
            || 0 <= new BigDecimal(toValue.trim()).compareTo(new BigDecimal(fromValue.trim()));
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox(FilterOperatorEnum... items) {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setWidth(230, Unit.PIXELS);
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setItems(items);
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return filterOperatorComboBox;
    }

    private void populateComboBox(ComboBox<FilterOperatorEnum> comboBox, FilterOperatorEnum selectedItem,
                                  String componentId, List<FilterOperatorEnum> items) {
        comboBox.setItems(items);
        comboBox.setSizeFull();
        comboBox.setSelectedItem(selectedItem);
        VaadinUtils.addComponentStyle(comboBox, componentId);
    }
}
