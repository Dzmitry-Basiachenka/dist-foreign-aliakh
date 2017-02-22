package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link CsvProcessingResult}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/22/17
 *
 * @author Aliaksei Pchelnikau
 */
public class CsvProcessingResultTest {

    private CsvProcessingResult<Integer> result;

    @Before
    public void setUp() {
        result = new CsvProcessingResult<>();
    }

    @Test
    public void testInit() {
        assertTrue(result.isSuccessful());
        assertTrue(result.isEmpty());
        assertTrue(result.getResult().isEmpty());
    }

    @Test
    public void testAddRecord() {
        Integer item = 10;
        result.addRecord(item);
        assertTrue(result.isSuccessful());
        assertFalse(result.isEmpty());
        assertEquals(item, result.getResult().get(0));
    }

    @Test
    public void testLogError() {
        result.logError(1, "Error message");
        assertFalse(result.isSuccessful());
        assertFalse(result.isEmpty());
        assertTrue(result.getResult().isEmpty());
    }
}
