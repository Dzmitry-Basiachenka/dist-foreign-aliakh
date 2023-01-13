package com.copyright.rup.dist.foreign.ui.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.vaadin.data.ValueContext;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Verifies {@link BigDecimalConverter}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/18/2020
 *
 * @author Uladzislau_Shalamitski
 */
public class BigDecimalConverterTest {

    private ValueContext context;
    private BigDecimalConverter converter;

    @Before
    public void setUp() {
        context = new ValueContext();
        converter = new BigDecimalConverter();
    }

    @Test
    public void testConvertToModel() {
        NumberFormatException exception = new NumberFormatException();
        assertEquals(new BigDecimal("0.75"), converter.convertToModel("0.75", context).getOrThrow(s -> exception));
        assertEquals(new BigDecimal("0.50"), converter.convertToModel("0.4999", context).getOrThrow(s -> exception));
        assertEquals(new BigDecimal("0.25"), converter.convertToModel(" 0.2501 ", context).getOrThrow(s -> exception));
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertToModelEmptyData() {
        assertTrue(converter.convertToModel("", context).isError());
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertToModelInvalidData() {
        assertTrue(converter.convertToModel(",50", context).isError());
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertToModelNonNumericData() {
        assertTrue(converter.convertToModel("abc", context).isError());
    }

    @Test
    public void testConvertToPresentation() {
        assertEquals("1.00", converter.convertToPresentation(new BigDecimal("1.00"), context));
    }
}
