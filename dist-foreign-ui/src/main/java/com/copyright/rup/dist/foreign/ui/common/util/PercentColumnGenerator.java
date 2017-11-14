package com.copyright.rup.dist.foreign.ui.common.util;

import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.ui.Table;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Percent column generator.
 * Multiplies by 100 and formats to a value with one decimal place
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/09/17
 *
 * @author Aliaksandra Bayanouskaya
 */
//TODO {abayanouskaya} add unit test for column generator
public class PercentColumnGenerator implements Table.ColumnGenerator {

    private static final BigDecimal PERCENT_100 = new BigDecimal("100");

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        BigDecimal value = VaadinUtils.getContainerPropertyValue(source, itemId, columnId, BigDecimal.class);
        return null != value
            ? Objects.toString(value.multiply(PERCENT_100).setScale(1, BigDecimal.ROUND_HALF_UP))
            : StringUtils.EMPTY;
    }
}
