package com.copyright.rup.dist.foreign.domain.filter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Abstract class for filter expressions. Contains field values and operator.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/21
 *
 * @param <T> type of field values
 * @author Uladzislau Shalamitski
 */
public class FilterExpression<T> {

    private FilterOperatorEnum operator;
    private T fieldFirstValue;
    private T fieldSecondValue;

    /**
     * Default constructor.
     */
    public FilterExpression() {
    }

    /**
     * @param operator         instance of {@link FilterOperatorEnum}
     * @param fieldFirstValue  first value of a field
     * @param fieldSecondValue second value of a field
     */
    public FilterExpression(FilterOperatorEnum operator, T fieldFirstValue, T fieldSecondValue) {
        this.operator = operator;
        this.fieldFirstValue = fieldFirstValue;
        this.fieldSecondValue = fieldSecondValue;
    }

    public FilterOperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(FilterOperatorEnum operator) {
        this.operator = operator;
    }

    public T getFieldFirstValue() {
        return fieldFirstValue;
    }

    public void setFieldFirstValue(T fieldFirstValue) {
        this.fieldFirstValue = fieldFirstValue;
    }

    public T getFieldSecondValue() {
        return fieldSecondValue;
    }

    public void setFieldSecondValue(T fieldSecondValue) {
        this.fieldSecondValue = fieldSecondValue;
    }

    public boolean isEmpty() {
        return Objects.isNull(fieldFirstValue) && Objects.isNull(fieldSecondValue)
            && FilterOperatorEnum.IS_NULL != operator && FilterOperatorEnum.IS_NOT_NULL != operator;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        FilterExpression<?> that = (FilterExpression<?>) obj;
        return new EqualsBuilder()
            .append(operator, that.operator)
            .append(fieldFirstValue, that.fieldFirstValue)
            .append(fieldSecondValue, that.fieldSecondValue)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(operator)
            .append(fieldFirstValue)
            .append(fieldSecondValue)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("operator", operator)
            .append("fieldFirstValue", fieldFirstValue)
            .append("fieldSecondValue", fieldSecondValue)
            .toString();
    }
}
