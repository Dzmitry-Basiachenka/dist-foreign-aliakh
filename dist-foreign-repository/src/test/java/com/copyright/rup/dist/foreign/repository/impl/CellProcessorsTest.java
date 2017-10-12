package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.DateCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.FiscalYearCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.ServiceFeePercentCellProcessor;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Verifies cell processors defined in {@link BaseCsvReportHandler}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class CellProcessorsTest {

    @Test
    public void testFiscalYearCellProcessor() {
        assertEquals("FY2017", new FiscalYearCellProcessor().execute(2017, null));
    }

    @Test
    public void testDateCellProcessor() {
        assertEquals("03/22/2017", new DateCellProcessor().execute(LocalDate.of(2017, 3, 22), null));
    }

    @Test
    public void testServiceFeePercentCellProcessor() {
        assertEquals("34.5", new ServiceFeePercentCellProcessor().execute(BigDecimal.valueOf(0.3445), null));
    }
}
