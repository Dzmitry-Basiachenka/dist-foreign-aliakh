package com.copyright.rup.dist.foreign.ui.common.utils;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Verifies {@link BigDecimalUtils}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/09/2021
 *
 * @author Aliaksandr Liakh
 */
public class BigDecimalUtilsTest {

    private static final String PLUS_1000_WITH_GROUPS = "1,000.00";
    private static final String MINUS_1000_WITH_GROUPS = "-1,000.00";
    private static final String PLUS_1000_WITHOUT_GROUPS = "1000.00";
    private static final String MINUS_1000_WITHOUT_GROUPS = "-1000.00";

    @Test
    public void testConstructor() {
        TestUtils.validatePrivateConstructor(BigDecimalUtils.class);
    }

    @Test
    public void testFormatCurrencyForGrid() {
        assertEquals(StringUtils.EMPTY, BigDecimalUtils.formatCurrencyForGrid(null));
        assertEquals(PLUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000")));
        assertEquals(PLUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.")));
        assertEquals(PLUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.0")));
        assertEquals(PLUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.00")));
        assertEquals(PLUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.000")));
        assertEquals("1,000.10", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.100")));
        assertEquals("1,000.01", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.010")));
        assertEquals("1,000.001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.001")));
        assertEquals("1,000.0001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.0001")));
        assertEquals("1,000.00001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.00001")));
        assertEquals("1,000.000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.000001")));
        assertEquals("1,000.0000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.0000001")));
        assertEquals("1,000.00000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.00000001")));
        assertEquals("1,000.000000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.000000001")));
        assertEquals("1,000.0000000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("1000.0000000001")));
        assertEquals(MINUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000")));
        assertEquals(MINUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.")));
        assertEquals(MINUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.0")));
        assertEquals(MINUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.00")));
        assertEquals(MINUS_1000_WITH_GROUPS, BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.000")));
        assertEquals("-1,000.10", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.100")));
        assertEquals("-1,000.01", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.010")));
        assertEquals("-1,000.001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.001")));
        assertEquals("-1,000.0001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.0001")));
        assertEquals("-1,000.00001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.00001")));
        assertEquals("-1,000.000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.000001")));
        assertEquals("-1,000.0000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.0000001")));
        assertEquals("-1,000.00000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.00000001")));
        assertEquals("-1,000.000000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.000000001")));
        assertEquals("-1,000.0000000001", BigDecimalUtils.formatCurrencyForGrid(new BigDecimal("-1000.0000000001")));
    }

    @Test
    public void testFormatCurrencyForDialog() {
        assertEquals(StringUtils.EMPTY, BigDecimalUtils.formatCurrencyForDialog(null));
        assertEquals(PLUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000")));
        assertEquals(PLUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.")));
        assertEquals(PLUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.0")));
        assertEquals(PLUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.00")));
        assertEquals(PLUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.000")));
        assertEquals("1000.10", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.100")));
        assertEquals("1000.01", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.010")));
        assertEquals("1000.001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.001")));
        assertEquals("1000.0001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.0001")));
        assertEquals("1000.00001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.00001")));
        assertEquals("1000.000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.000001")));
        assertEquals("1000.0000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.0000001")));
        assertEquals("1000.00000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.00000001")));
        assertEquals("1000.000000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.000000001")));
        assertEquals("1000.0000000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("1000.0000000001")));
        assertEquals(MINUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000")));
        assertEquals(MINUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.")));
        assertEquals(MINUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.0")));
        assertEquals(MINUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.00")));
        assertEquals(MINUS_1000_WITHOUT_GROUPS, BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.000")));
        assertEquals("-1000.10", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.100")));
        assertEquals("-1000.01", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.010")));
        assertEquals("-1000.001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.001")));
        assertEquals("-1000.0001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.0001")));
        assertEquals("-1000.00001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.00001")));
        assertEquals("-1000.000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.000001")));
        assertEquals("-1000.0000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.0000001")));
        assertEquals("-1000.00000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.00000001")));
        assertEquals("-1000.000000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.000000001")));
        assertEquals("-1000.0000000001", BigDecimalUtils.formatCurrencyForDialog(new BigDecimal("-1000.0000000001")));
    }
}
