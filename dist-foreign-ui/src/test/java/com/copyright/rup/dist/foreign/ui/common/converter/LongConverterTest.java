package com.copyright.rup.dist.foreign.ui.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link LongConverter}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 01/13/2023
 *
 * @author Aliaksandr Liakh
 */
public class LongConverterTest {

    private ValueContext context;
    private LongConverter converter;

    @Before
    public void setUp() {
        context = new ValueContext();
        converter = new LongConverter("error message");
    }

    @Test
    public void testConvertToModelSuccess() {
        AssertionError exception = new AssertionError();
        assertEquals(Long.valueOf(0), converter.convertToModel("0", context).getOrThrow(s -> exception));
        assertEquals(Long.valueOf(0), converter.convertToModel(" 0 ", context).getOrThrow(s -> exception));
        assertNull(converter.convertToModel("", context).getOrThrow(s -> exception));
        assertNull(converter.convertToModel(" ", context).getOrThrow(s -> exception));
    }

    @Test
    public void testConvertToModelError() {
        Result<Long> result = converter.convertToModel("not_a_number", context);
        assertTrue(result.isError());
        assertEquals("error message", result.getMessage().get());
    }

    @Test
    public void testConvertToPresentation() {
        assertEquals("0", converter.convertToPresentation(Long.valueOf(0), context));
        assertEquals("", converter.convertToPresentation(null, context));
    }
}
