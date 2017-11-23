package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception.ThresholdExceededException;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

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
        result = new CsvProcessingResult<>(Collections.emptyList(), "fileName");
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
        result.addRecord(1, item);
        assertTrue(result.isSuccessful());
        assertFalse(result.isEmpty());
        assertEquals(item, result.getResult().get(0));
    }

    @Test
    public void testLogError() throws ThresholdExceededException {
        List<String> originalRow = Lists.newArrayList("originalRow");
        String errorMessage = "Error message";
        Integer line = 1;
        result.logError(line, originalRow, errorMessage);
        assertFalse(result.isSuccessful());
        assertFalse(result.isEmpty());
        assertTrue(result.getResult().isEmpty());
        assertEquals(1, result.getErrors().size());
        CsvProcessingResult.ErrorRow errorRow = result.getErrors().get(0);
        assertEquals(line, errorRow.getLineNumber());
        assertEquals(originalRow, errorRow.getOriginalLine());
        assertEquals(errorMessage, errorRow.getErrorMessages().get(0));
    }
}
