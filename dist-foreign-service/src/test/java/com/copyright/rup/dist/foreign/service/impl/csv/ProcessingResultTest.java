package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Verifies {@link DistCsvProcessor.ProcessingResult}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/22/17
 *
 * @author Aliaksei Pchelnikau
 */
public class ProcessingResultTest {

    private DistCsvProcessor.ProcessingResult<Integer> result;

    @Before
    public void setUp() {
        result = new DistCsvProcessor.ProcessingResult<>(new ArrayList<>());
    }

    @Test
    public void testInit() {
        assertTrue(result.isSuccessful());
        assertTrue(result.isEmpty());
        assertTrue(result.get().isEmpty());
    }

    @Test
    public void testAddRecord() {
        Integer item = 10;
        result.addRecord(item);
        assertTrue(result.isSuccessful());
        assertFalse(result.isEmpty());
        assertEquals(item, result.get().iterator().next());
    }

    @Test
    public void testLogError() {
        String[] originalRow = {"originalRow"};
        String errorMessage = "Error message";
        Integer line = 1;
        result.logError(line, originalRow, errorMessage);
        assertFalse(result.isSuccessful());
        assertFalse(result.isEmpty());
        assertTrue(result.get().isEmpty());
    }
}
