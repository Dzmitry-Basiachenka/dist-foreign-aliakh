package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.repository.impl.UsageCsvReportHandler.DateCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.UsageCsvReportHandler.FiscalYearCellProcessor;

import org.junit.Test;

import java.time.LocalDate;

/**
 * Verifies {@link UsageCsvReportHandler}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/09/17
 *
 * @author Darya Baraukova
 */
public class UsageCsvReportHandlerTest {

    @Test
    public void testFiscalYearCellProcessor() {
        FiscalYearCellProcessor processor = new FiscalYearCellProcessor();
        assertEquals("FY2017", processor.execute(2017, null));
    }

    @Test
    public void testFiscalYearCellProcessorNullValue() {
        FiscalYearCellProcessor processor = new FiscalYearCellProcessor();
        assertNull(processor.execute(null, null));
    }

    @Test
    public void testDateCellProcessor() {
        DateCellProcessor processor = new DateCellProcessor();
        LocalDate date = LocalDate.of(2017, 3, 22);
        assertEquals("03/22/2017", processor.execute(date, null));
    }

    @Test
    public void testDateCellProcessorNullValue() {
        DateCellProcessor processor = new DateCellProcessor();
        assertNull(processor.execute(null, null));
    }
}
