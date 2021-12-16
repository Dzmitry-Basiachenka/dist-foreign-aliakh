package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents common logic for creating labels of applied filters.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Anton Azarenka
 */
public abstract class CommonUdmAppliedFilterPanel extends Panel {

    private static final String LIST_SEPARATOR = ", ";

    /**
     * Creates label for filter field with single value.
     *
     * @param filterValueFunction function to get value from filter
     * @param filter              filter
     * @param filterName          name of filter field
     * @param <T>                 type of filter
     * @return instance of {@link Label} if filter value is not null, otherwise <code>null</code>
     */
    protected <T> Label createLabelWithSingleValue(Function<T, ?> filterValueFunction, T filter, String filterName) {
        Label label = null;
        if (Objects.nonNull(filterValueFunction.apply(filter))) {
            label = new Label(StringUtils.EMPTY, ContentMode.HTML);
            label.setValue(formatLabel(filterName, filterValueFunction.apply(filter)));
        }
        return label;
    }

    /**
     * Creates label for filter field with multiple values.
     *
     * @param collection     collection of values
     * @param filterName     name of filter field
     * @param formatFunction function for format values
     * @param <T>            type of filter
     * @return instance of {@link Label} if filter value is not null, otherwise <code>null</code>
     */
    protected <T> Label createLabelWithMultipleValues(Collection<T> collection, String filterName,
                                                      Function<T, String> formatFunction) {
        Label label = null;
        if (!collection.isEmpty()) {
            label = new Label(StringUtils.EMPTY, ContentMode.HTML);
            label.setValue(formatLabel(filterName, formatMultipleSelectToString(collection, formatFunction)));
        }
        return label;
    }

    /**
     * Creates label for filter fields with operator.
     *
     * @param expression      filter expression
     * @param filterFromLabel filter from label name
     * @param filterToLabel   filter to label name
     * @return instance of {@link Label} if filter value is not null, otherwise <code>null</code>
     */
    protected Label createLabelWithOperator(FilterExpression<?> expression, String filterFromLabel,
                                            String filterToLabel) {
        Label label = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (!expression.isEmpty()) {
            stringBuilder.append(formatLabel(filterFromLabel, expression.getFieldFirstValue()));
            if (Objects.nonNull(expression.getFieldSecondValue())) {
                stringBuilder.append(formatLabel(filterToLabel, expression.getFieldSecondValue()));
            }
            stringBuilder.append(formatLabel("label.operator", expression.getOperator()));
            label = new Label(StringUtils.EMPTY, ContentMode.HTML);
            label.setValue(stringBuilder.toString());
        }
        return label;
    }

    private <T> String formatMultipleSelectToString(Collection<T> values, Function<T, String> formatFunction) {
        return values.stream().map(formatFunction).collect(Collectors.joining(LIST_SEPARATOR));
    }

    private String formatLabel(String filterName, Object values) {
        return String.format("<li><b><i>%s: </i></b>%s</li>", ForeignUi.getMessage(filterName), values);
    }
}