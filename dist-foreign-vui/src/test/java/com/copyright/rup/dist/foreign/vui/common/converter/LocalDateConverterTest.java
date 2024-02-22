package com.copyright.rup.dist.foreign.vui.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.vaadin.flow.data.binder.ValueContext;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Verifies {@link LocalDateConverter}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Ihar Suvorau
 */
public class LocalDateConverterTest {

    private ValueContext context;
    private LocalDateConverter converter;

    @Before
    public void setUp() {
        context = new ValueContext();
        converter = new LocalDateConverter();
    }

    @Test
    public void testConvertToModel() {
        AssertionError exception = new AssertionError();
        assertEquals(LocalDate.of(2001, 6, 30), converter.convertToModel(2001, context).getOrThrow(s -> exception));
        assertEquals(LocalDate.of(1999, 6, 30), converter.convertToModel(1999, context).getOrThrow(s -> exception));
    }

    @Test
    public void testConvertToPresentation() {
        assertEquals(2001, converter.convertToPresentation(LocalDate.of(2001, 1, 1), context).intValue());
        assertNull(converter.convertToPresentation(null, context));
    }
}
