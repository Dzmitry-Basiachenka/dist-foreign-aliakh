package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.CurrencyUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Contains common functionality for UDM value window.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Dzmitry Basiachenka
 */
public abstract class CommonUdmValueWindow extends Window {

    private static final DecimalFormat MONEY_FORMATTER = new DecimalFormat("0.00########",
        CurrencyUtils.getParameterizedDecimalFormatSymbols());
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT, Locale.US);

    /**
     * Builds read only horizontal layout.
     *
     * @param caption caption value
     * @param getter  value provider
     * @param binder  binder
     * @return instance of {@link HorizontalLayout}
     */
    protected HorizontalLayout buildReadOnlyLayout(String caption, ValueProvider<UdmValueDto, String> getter,
                                                   Binder<UdmValueDto> binder) {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setSizeFull();
        binder.forField(textField).bind(getter, null);
        return buildCommonLayout(textField, ForeignUi.getMessage(caption));
    }

    /**
     * Builds common horizontal layout.
     *
     * @param component    instance of {@link Component}
     * @param labelCaption label caption value
     * @return instance of {@link HorizontalLayout}
     */
    protected HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(labelCaption);
        label.addStyleName(Cornerstone.LABEL_BOLD);
        label.setWidth(175, Unit.PIXELS);
        HorizontalLayout layout = new HorizontalLayout(label, component);
        layout.setSizeFull();
        layout.setExpandRatio(component, 1);
        return layout;
    }

    /**
     * Converts value from {@link LocalDate} to String.
     *
     * @param getter value provider getter
     * @return value provider with converted value
     */
    protected ValueProvider<UdmValueDto, String> fromLocalDateToString(ValueProvider<UdmValueDto, LocalDate> getter) {
        return bean -> DateUtils.format(getter.apply(bean));
    }

    /**
     * Converts value from String to {@link LocalDate}.
     *
     * @param setter value provider setter
     * @return value provider with converted value
     */
    protected Setter<UdmValueDto, String> fromStringToLocalDate(Setter<UdmValueDto, LocalDate> setter) {
        return (bean, value) -> setter.accept(bean, StringUtils.isNotEmpty(value)
            ? LocalDate.parse(value, DATE_TIME_FORMATTER) : null);
    }

    /**
     * Converts value from Boolean to String with YN format.
     *
     * @param getter value provider getter
     * @return value provider with converted value
     */
    protected ValueProvider<UdmValueDto, String> fromBooleanToYNString(ValueProvider<UdmValueDto, Boolean> getter) {
        return bean -> BooleanUtils.toYNString(getter.apply(bean));
    }

    /**
     * Converts value from String in YN format to Boolean.
     *
     * @param setter value provider setter
     * @return value provider with converted value
     */
    protected Setter<UdmValueDto, String> fromYNStringToBoolean(Setter<UdmValueDto, Boolean> setter) {
        return (bean, value) -> setter.accept(bean, Objects.nonNull(value) ? "Y".equals(value) : null);
    }

    /**
     * Converts value from {@link BigDecimal} to String.
     *
     * @param getter value provider getter
     * @return value provider with converted value
     */
    protected ValueProvider<UdmValueDto, String> fromBigDecimalToString(ValueProvider<UdmValueDto, BigDecimal> getter) {
        return bean -> Objects.toString(getter.apply(bean), StringUtils.EMPTY);
    }

    /**
     * Converts value from {@link BigDecimal} to String in money format.
     *
     * @param getter value provider getter
     * @return value provider with converted value
     */
    protected ValueProvider<UdmValueDto, String> fromBigDecimalToMoneyString(
        ValueProvider<UdmValueDto, BigDecimal> getter) {
        return bean -> CurrencyUtils.format(getter.apply(bean), MONEY_FORMATTER);
    }

    /**
     * Converts value from String to {@link BigDecimal}.
     *
     * @param setter value provider setter
     * @return value provider with converted value
     */
    protected Setter<UdmValueDto, String> fromStringToBigDecimal(Setter<UdmValueDto, BigDecimal> setter) {
        return (bean, value) -> setter.accept(bean,
            StringUtils.isNotBlank(value) ? NumberUtils.createBigDecimal(value.trim()) : null);
    }

    /**
     * Converts value from {@link BigDecimal} to String in money format.
     *
     * @param value instance of {@link BigDecimal}
     * @return converted value
     */
    protected String convertFromBigDecimalToMoneyString(BigDecimal value) {
        return CurrencyUtils.format(value, MONEY_FORMATTER);
    }
}
