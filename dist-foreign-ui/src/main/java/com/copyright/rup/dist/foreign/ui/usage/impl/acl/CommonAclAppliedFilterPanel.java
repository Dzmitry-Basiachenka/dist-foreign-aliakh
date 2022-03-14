package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

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
public abstract class CommonAclAppliedFilterPanel extends Panel {

    private static final String LIST_SEPARATOR = ", ";

    /**
     * Constructor.
     */
    public CommonAclAppliedFilterPanel() {
        setWidth(265, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        setSizeFull();
    }

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
            stringBuilder.append(
                Objects.isNull(expression.getFieldFirstValue())
                    ? formatLabel(filterFromLabel, expression.getOperator())
                    : formatLabel(filterFromLabel, expression.getFieldFirstValue()));
            if (Objects.nonNull(expression.getFieldSecondValue())) {
                stringBuilder.append(formatLabel(filterToLabel, expression.getFieldSecondValue()));
            }
            if (Objects.nonNull(expression.getFieldFirstValue())) {
                stringBuilder.append(formatLabel("label.operator", expression.getOperator()));
            }
            label = new Label(StringUtils.EMPTY, ContentMode.HTML);
            label.setValue(stringBuilder.toString());
        }
        return label;
    }

    /**
     * Adds label to layout.
     *
     * @param label          instance of {@link Label}
     * @param verticalLayout instance of {@link VerticalLayout}
     */
    protected void addLabel(Label label, VerticalLayout verticalLayout) {
        if (Objects.nonNull(label)) {
            label.setStyleName("v-label-white-space-normal");
            verticalLayout.addComponent(label);
        }
    }

    /**
     * Initialize layout.
     *
     * @return instance of {@link VerticalLayout}
     */
    protected VerticalLayout initLayout() {
        VerticalLayout filtersPanelLayout = new VerticalLayout();
        filtersPanelLayout.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(filtersPanelLayout);
        return filtersPanelLayout;
    }

    /**
     * Converts {@link FilterOperatorEnum} to string.
     *
     * @param value instance of {@link FilterOperatorEnum}
     * @return value represented as string
     */
    protected String convertFilterOperatorToString(FilterOperatorEnum value) {
        return Objects.nonNull(value) ? value.name() : null;
    }

    private <T> String formatMultipleSelectToString(Collection<T> values, Function<T, String> formatFunction) {
        return values.stream().map(formatFunction).collect(Collectors.joining(LIST_SEPARATOR));
    }

    private String formatLabel(String filterName, Object values) {
        return String.format("<li><b><i>%s: </i></b>%s</li>", ForeignUi.getMessage(filterName), values);
    }
}
