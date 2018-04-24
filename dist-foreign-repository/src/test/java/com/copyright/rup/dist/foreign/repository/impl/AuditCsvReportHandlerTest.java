package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.BigDecimalCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.LocalDateCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.OffsetDateTimeCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.ServiceFeePercentCellProcessor;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.stream.IntStream;

/**
 * Verifies {@link AuditCsvReportHandler}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/23/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class AuditCsvReportHandlerTest {

    private static final Optional OPTIONAL_PROCESSOR = new Optional();
    private static final OffsetDateTimeCellProcessor OFFSET_DATE_TIME_CELL_PROCESSOR =
        new OffsetDateTimeCellProcessor();
    private static final LocalDateCellProcessor LOCAL_DATE_CELL_PROCESSOR = new LocalDateCellProcessor();

    private AuditCsvReportHandler auditCsvReportHandler;

    @Before
    public void setUp() throws IOException {
        auditCsvReportHandler = new AuditCsvReportHandler(new PipedOutputStream());
    }

    @Test
    public void testProcessors() {
        CellProcessor[] processors = auditCsvReportHandler.getPropertyTable().values()
            .toArray(new CellProcessor[auditCsvReportHandler.getPropertyTable().size()]);
        assertTrue(ArrayUtils.isNotEmpty(processors));
        assertEquals(21, processors.length);
        CellProcessor[] cellProcessors = {OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            OPTIONAL_PROCESSOR, new LocalDateCellProcessor(), OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            new BigDecimalCellProcessor(), new ServiceFeePercentCellProcessor(), OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            OFFSET_DATE_TIME_CELL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OFFSET_DATE_TIME_CELL_PROCESSOR,
            LOCAL_DATE_CELL_PROCESSOR};
        IntStream.range(0, processors.length)
            .forEach(index -> assertEquals(cellProcessors[index].getClass(), processors[index].getClass()));
    }

    @Test
    public void testHeaders() {
        String[] headers = auditCsvReportHandler.getPropertyTable().columnKeySet()
            .toArray(new String[auditCsvReportHandler.getPropertyTable().size()]);
        assertTrue(ArrayUtils.isNotEmpty(headers));
        assertEquals(21, headers.length);
        assertArrayEquals(new String[]{"Detail ID", "Detail Status", "Product Family", "Usage Batch Name",
            "Payment Date", "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Wr Wrk Inst", "Title",
            "Standard Number", "Amt in USD", "Service Fee %", "Scenario Name", "Check #", "Check Date", "Event ID",
            "Dist. Name", "Dist. Date", "Period Ending"}, headers);
    }

    @Test
    public void testNameMapping() {
        String[] nameMapping = auditCsvReportHandler.getPropertyTable().rowKeySet()
            .toArray(new String[auditCsvReportHandler.getPropertyTable().size()]);
        assertTrue(ArrayUtils.isNotEmpty(nameMapping));
        assertEquals(21, nameMapping.length);
        assertArrayEquals(new String[]{"id", "status", "productFamily", "batchName", "paymentDate",
            "rhAccountNumber", "rhName", "payeeAccountNumber", "payeeName", "wrWrkInst", "workTitle", "standardNumber",
            "grossAmount", "serviceFee", "scenarioName", "checkNumber", "checkDate", "cccEventId", "distributionName",
            "distributionDate", "periodEndDate"}, nameMapping);
    }
}
