package com.copyright.rup.dist.foreign.repository.impl;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * Writes scenario usages into a {@link PipedOutputStream} connected to the {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 10/11/2017
 *
 * @author Uladzislau Shalamitski
 */
class ScenarioUsagesCsvReportHandler extends BaseCsvReportHandler {

    private static final Optional OPTIONAL_PROCESSOR = new Optional();
    private static final DateCellProcessor DATE_CELL_PROCESSOR = new DateCellProcessor();

    private static final Table<String, String, CellProcessor> PROPERTY_TABLE =
        ImmutableTable.<String, String, CellProcessor>builder()
            .put("detailId", "Detail ID", OPTIONAL_PROCESSOR)
            .put("batchName", "Usage Batch Name", OPTIONAL_PROCESSOR)
            .put("fiscalYear", "Fiscal Year", new FiscalYearCellProcessor())
            .put("rroAccountNumber", "RRO Account #", OPTIONAL_PROCESSOR)
            .put("rroName", "RRO Name", OPTIONAL_PROCESSOR)
            .put("paymentDate", "Payment Date", DATE_CELL_PROCESSOR)
            .put("workTitle", "Title", OPTIONAL_PROCESSOR)
            .put("article", "Article", OPTIONAL_PROCESSOR)
            .put("standardNumber", "Standard Number", OPTIONAL_PROCESSOR)
            .put("wrWrkInst", "Wr Wrk Inst", OPTIONAL_PROCESSOR)
            .put("rhAccountNumber", "RH Account #", OPTIONAL_PROCESSOR)
            .put("rhName", "RH Name", OPTIONAL_PROCESSOR)
            .put("payeeAccountNumber", "Payee Account #", OPTIONAL_PROCESSOR)
            .put("payeeName", "Payee Name", OPTIONAL_PROCESSOR)
            .put("publisher", "Publisher", OPTIONAL_PROCESSOR)
            .put("publicationDate", "Pub Date", new DateCellProcessor())
            .put("numberOfCopies", "Number of Copies", OPTIONAL_PROCESSOR)
            .put("reportedValue", "Reported value", OPTIONAL_PROCESSOR)
            .put("grossAmount", "Gross Amt in USD", OPTIONAL_PROCESSOR)
            .put("serviceFeeAmount", "Service Fee Amount", OPTIONAL_PROCESSOR)
            .put("netAmount", "Net Amt in USD", OPTIONAL_PROCESSOR)
            .put("serviceFee", "Service Fee %", new ServiceFeePercentCellProcessor())
            .put("market", "Market", OPTIONAL_PROCESSOR)
            .put("marketPeriodFrom", "Market Period From", OPTIONAL_PROCESSOR)
            .put("marketPeriodTo", "Market Period To", OPTIONAL_PROCESSOR)
            .put("author", "Author", OPTIONAL_PROCESSOR)
            .build();

    /**
     * Constructor.
     *
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     * @throws IOException if header cannot be written
     */
    ScenarioUsagesCsvReportHandler(PipedOutputStream pipedOutputStream) throws IOException {
        super(pipedOutputStream);
    }

    @Override
    Table<String, String, CellProcessor> getPropertyTable() {
        return PROPERTY_TABLE;
    }
}
