package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

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
        result = new CsvProcessingResult<>(Collections.emptyList());
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
        String originalRow = "originalRow";
        String errorMessage = "Error message";
        int line = 1;
        result.logError(line, originalRow, errorMessage);
        assertFalse(result.isSuccessful());
        assertFalse(result.isEmpty());
        assertTrue(result.getResult().isEmpty());
        assertEquals(1, result.getErrors().size());
        CsvProcessingResult.ErrorRow errorRow = result.getErrors().get(1);
        assertEquals(line, errorRow.getLine());
        assertEquals(originalRow, errorRow.getOriginalRow());
        assertEquals(errorMessage, errorRow.getErrorMessages().get(0));
    }
}
