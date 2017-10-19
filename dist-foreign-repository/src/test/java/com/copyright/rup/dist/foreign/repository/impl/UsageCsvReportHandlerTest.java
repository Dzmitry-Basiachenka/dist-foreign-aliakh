package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.DateCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.FiscalYearCellProcessor;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.stream.IntStream;

/**
 * Verifies {@link UsageCsvReportHandler}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/19/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class UsageCsvReportHandlerTest {

    private static final Optional OPTIONAL_PROCESSOR = new Optional();
    private static final DateCellProcessor DATE_CELL_PROCESSOR = new DateCellProcessor();

    private UsageCsvReportHandler usagesCsvReportHandler;

    @Before
    public void setUp() throws IOException {
        usagesCsvReportHandler = new UsageCsvReportHandler(new PipedOutputStream());
    }

    @Test
    public void testProcessors() {
        CellProcessor[] processors = usagesCsvReportHandler.getPropertyTable().values()
            .toArray(new CellProcessor[usagesCsvReportHandler.getPropertyTable().size()]);
        assertTrue(ArrayUtils.isNotEmpty(processors));
        assertEquals(23, processors.length);
        CellProcessor[] cellProcessors = {OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            new FiscalYearCellProcessor(), OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, DATE_CELL_PROCESSOR,
            OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, DATE_CELL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            OPTIONAL_PROCESSOR};
        IntStream.range(0, processors.length)
            .forEach(index -> assertEquals(processors[index].getClass(), cellProcessors[index].getClass()));
    }

    @Test
    public void testHeaders() {
        String[] headers = usagesCsvReportHandler.getPropertyTable().columnKeySet()
            .toArray(new String[usagesCsvReportHandler.getPropertyTable().size()]);
        assertTrue(ArrayUtils.isNotEmpty(headers));
        assertEquals(23, headers.length);
        assertArrayEquals(headers, new String[]{"Detail ID", "Detail Status", "Usage Batch Name",
            "Fiscal Year", "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number",
            "Wr Wrk Inst", "RH Account #", "RH Name", "Publisher", "Pub Date", "Number of Copies", "Reported value",
            "Amt in USD", "Gross Amt in USD", "Market", "Market Period From", "Market Period To", "Author"});
    }

    @Test
    public void testNameMapping() {
        String[] nameMapping = usagesCsvReportHandler.getPropertyTable().rowKeySet()
            .toArray(new String[usagesCsvReportHandler.getPropertyTable().size()]);
        assertTrue(ArrayUtils.isNotEmpty(nameMapping));
        assertEquals(23, nameMapping.length);
        assertArrayEquals(nameMapping, new String[]{"detailId", "status", "batchName", "fiscalYear",
            "rroAccountNumber", "rroName", "paymentDate", "workTitle", "article", "standardNumber", "wrWrkInst",
            "rhAccountNumber", "rhName", "publisher", "publicationDate", "numberOfCopies", "reportedValue",
            "grossAmount", "batchGrossAmount", "market", "marketPeriodFrom", "marketPeriodTo", "author",});
    }
}
