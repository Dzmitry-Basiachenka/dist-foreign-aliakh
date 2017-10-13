package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.DateCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.FiscalYearCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.ServiceFeePercentCellProcessor;
import com.copyright.rup.dist.foreign.repository.impl.BaseCsvReportHandler.ZeroBigDecimalCellProcessor;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Verifies {@link ScenarioUsagesCsvReportHandler}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/20/17
 *
 * @author Uladzislau Shalamitski
 */
public class ScenarioUsagesCsvReportHandlerTest {

    private static final Optional OPTIONAL_PROCESSOR = new Optional();
    private static final DateCellProcessor DATE_CELL_PROCESSOR = new DateCellProcessor();
    private static final ZeroBigDecimalCellProcessor ZERO_BIG_DECIMAL_PROCESSOR = new ZeroBigDecimalCellProcessor();

    private ScenarioUsagesCsvReportHandler scenarioUsagesCsvReportHandler;

    @Before
    public void setUp() throws IOException {
        scenarioUsagesCsvReportHandler = new ScenarioUsagesCsvReportHandler(new PipedOutputStream());
    }

    @Test
    public void testProcessors() {
        CellProcessor[] processors = scenarioUsagesCsvReportHandler.getPropertyTable().values()
            .toArray(new CellProcessor[scenarioUsagesCsvReportHandler.getPropertyTable().size()]);
        assertTrue(ArrayUtils.isNotEmpty(processors));
        assertEquals(26, processors.length);
        CellProcessor[] cellProcessors = {OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, new FiscalYearCellProcessor(),
            OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, DATE_CELL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, DATE_CELL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            ZERO_BIG_DECIMAL_PROCESSOR, ZERO_BIG_DECIMAL_PROCESSOR, ZERO_BIG_DECIMAL_PROCESSOR,
            new ServiceFeePercentCellProcessor(), OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR, OPTIONAL_PROCESSOR,
            OPTIONAL_PROCESSOR};
        IntStream.range(0, processors.length)
            .forEach(index -> assertEquals(processors[index].getClass(), cellProcessors[index].getClass()));
    }

    @Test
    public void testHeaders() {
        String[] headers = scenarioUsagesCsvReportHandler.getPropertyTable().columnKeySet()
            .toArray(new String[scenarioUsagesCsvReportHandler.getPropertyTable().size()]);
        assertTrue(ArrayUtils.isNotEmpty(headers));
        assertEquals(26, headers.length);
        assertTrue(Objects.deepEquals(headers, new String[]{"Detail ID", "Usage Batch Name", "Fiscal Year",
            "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number", "Wr Wrk Inst",
            "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Publisher", "Pub Date", "Number of Copies",
            "Reported value", "Gross Amt in USD", "Service Fee Amount", "Net Amt in USD", "Service Fee %", "Market",
            "Market Period From", "Market Period To", "Author"}));
    }

    @Test
    public void testNameMapping() {
        String[] nameMapping = scenarioUsagesCsvReportHandler.getPropertyTable().rowKeySet()
            .toArray(new String[scenarioUsagesCsvReportHandler.getPropertyTable().size()]);
        assertTrue(ArrayUtils.isNotEmpty(nameMapping));
        assertEquals(26, nameMapping.length);
        assertTrue(Objects.deepEquals(nameMapping, new String[]{"detailId", "batchName", "fiscalYear",
            "rroAccountNumber", "rroName", "paymentDate", "workTitle", "article", "standardNumber", "wrWrkInst",
            "rhAccountNumber", "rhName", "payeeAccountNumber", "payeeName", "publisher", "publicationDate",
            "numberOfCopies", "reportedValue", "grossAmount", "serviceFeeAmount", "netAmount", "serviceFee", "market",
            "marketPeriodFrom", "marketPeriodTo", "author"}));
    }
}
