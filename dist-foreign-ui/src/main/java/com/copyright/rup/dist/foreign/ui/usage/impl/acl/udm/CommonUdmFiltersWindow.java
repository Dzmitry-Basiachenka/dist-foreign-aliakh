package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents common logic for creating additional UDM filters.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 14/01/2022
 *
 * @author Aliaksandr Liakh
 */
public abstract class CommonUdmFiltersWindow extends Window {

    private static final int ONE = 1;

    /**
     * Builds text operator combobox.
     *
     * @return text operator combobox
     */
    protected ComboBox<FilterOperatorEnum> buildTextOperatorComboBox() {
        ComboBox<FilterOperatorEnum> operatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        operatorComboBox.setEmptySelectionAllowed(false);
        operatorComboBox.setSizeFull();
        operatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.CONTAINS, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
        operatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return operatorComboBox;
    }

    /**
     * Builds numeric operator combobox.
     *
     * @return numeric operator combobox
     */
    protected ComboBox<FilterOperatorEnum> buildNumericOperatorComboBox() {
        ComboBox<FilterOperatorEnum> operatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        operatorComboBox.setEmptySelectionAllowed(false);
        operatorComboBox.setSizeFull();
        operatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.BETWEEN, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
        operatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return operatorComboBox;
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
     * @param textField        text field
     * @param operatorComboBox operator combobox
     * @param valueConverter   value converter
     * @param <T>              type of value converter
     * @return amount filter expression
     */
    protected <T> FilterExpression<T> buildAmountFilterExpression(TextField textField,
                                                                  ComboBox<FilterOperatorEnum> operatorComboBox,
                                                                  Function<String, T> valueConverter) {
        return buildFilterExpression(textField, operatorComboBox, valueConverter,
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
    private <T> FilterExpression<T> buildFilterExpression(TextField fromField, TextField toField,
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
     * Gets number validator.
     *
     * @return number validator
     */
    protected SerializablePredicate<String> getNumberValidator() {
        return value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim());
    }

    /**
     * Gets text length validator.
     *
     * @param maxLength maximum length
     * @return length validator
     */
    protected StringLengthValidator getTextStringLengthValidator(int maxLength) {
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
        return StringUtils.isEmpty(fromValue)
            || StringUtils.isEmpty(toValue)
            || !getNumberValidator().test(fromValue)
            || !getNumberValidator().test(toValue)
            || 0 <= Integer.valueOf(toValue.trim()).compareTo(Integer.valueOf(fromValue.trim()));
    }
}
