package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
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
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
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
public abstract class CommonAppliedFilterPanel extends Panel {

    private static final String LIST_SEPARATOR = ", ";

    /**
     * Constructor.
     */
    public CommonAppliedFilterPanel() {
        initLayout();
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
     * Initialize filter panel.
     *
     * @return instance of {@link VerticalLayout}
     */
    protected VerticalLayout initFilterPanel() {
        VerticalLayout filterPanel = new VerticalLayout();
        filterPanel.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(filterPanel);
        return filterPanel;
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

    /**
     * Sorts string values by natural order.
     *
     * @param values collection of values
     * @return sorted set of values
     */
    protected Set<String> sortStringValuesByNaturalOrder(Collection<String> values) {
        return values.stream()
            .sorted(String::compareToIgnoreCase)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Sorts integer values by descending.
     *
     * @param values collection of values
     * @return sorted set of values
     */
    protected Set<Integer> sortIntegerValuesByDesc(Collection<Integer> values) {
        return values.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Sorts detail licensee classes by id ascending.
     *
     * @param classes collection of {@link DetailLicenseeClass}es
     * @return set of sorted {@link DetailLicenseeClass}es
     */
    protected Set<DetailLicenseeClass> sortDetailLicenseeClasses(Collection<DetailLicenseeClass> classes) {
        return classes.stream()
            .sorted(Comparator.comparing(DetailLicenseeClass::getId))
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Sorts aggregate licensee classes by id ascending.
     *
     * @param classes collection of {@link AggregateLicenseeClass}es
     * @return set of sorted {@link AggregateLicenseeClass}es
     */
    protected Set<AggregateLicenseeClass> sortAggregateLicenseeClasses(Collection<AggregateLicenseeClass> classes) {
        return classes.stream()
            .sorted(Comparator.comparing(AggregateLicenseeClass::getId))
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Sorts publication types by name and description ascending.
     *
     * @param publicationTypes collection of {@link PublicationType}s
     * @return set of sorted {@link PublicationType}s
     */
    protected Set<PublicationType> sortPublicationTypes(Collection<PublicationType> publicationTypes) {
        return publicationTypes.stream()
            .sorted((p1, p2) -> p1.getNameAndDescription().compareToIgnoreCase(p2.getNameAndDescription()))
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void initLayout() {
        setWidth(265, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        setSizeFull();
    }

    private <T> String formatMultipleSelectToString(Collection<T> values, Function<T, String> formatFunction) {
        return values.stream().map(formatFunction).collect(Collectors.joining(LIST_SEPARATOR));
    }

    private String formatLabel(String filterName, Object values) {
        return String.format("<li><b><i>%s: </i></b>%s</li>", ForeignUi.getMessage(filterName), values);
    }
}
