package com.copyright.rup.dist.foreign.domain.filter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    private final FilterOperatorEnum operatorEnum;
    private final T fieldFirstValue;
    private final T fieldSecondValue;

    /**
     * @param operatorEnum     instance of {@link FilterOperatorEnum}
     * @param fieldFirstValue  first value of a field
     * @param fieldSecondValue second value of a field
     */
    public FilterExpression(FilterOperatorEnum operatorEnum, T fieldFirstValue, T fieldSecondValue) {
        this.operatorEnum = operatorEnum;
        this.fieldFirstValue = fieldFirstValue;
        this.fieldSecondValue = fieldSecondValue;
    }

    public FilterOperatorEnum getOperatorEnum() {
        return operatorEnum;
    }

    public T getFieldFirstValue() {
        return fieldFirstValue;
    }

    public T getFieldSecondValue() {
        return fieldSecondValue;
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
            .append(operatorEnum, that.operatorEnum)
            .append(fieldFirstValue, that.fieldFirstValue)
            .append(fieldSecondValue, that.fieldSecondValue)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(operatorEnum)
            .append(fieldFirstValue)
            .append(fieldSecondValue)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("operatorEnum", operatorEnum)
            .append("fieldFirstValue", fieldFirstValue)
            .append("fieldSecondValue", fieldSecondValue)
            .toString();
    }
}
