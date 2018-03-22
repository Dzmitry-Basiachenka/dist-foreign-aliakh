package com.copyright.rup.dist.foreign.repository.impl;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * Writes usages into a {@link PipedOutputStream} connected to the {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/18
 *
 * @author Aliaksandr Radkevich
 */
class AuditCsvReportHandler extends BaseCsvReportHandler {

    private static final Optional OPTIONAL_PROCESSOR = new Optional();
    private static final OffsetDateTimeCellProcessor OFFSET_DATE_TIME_CELL_PROCESSOR =
        new OffsetDateTimeCellProcessor();
    private static final LocalDateCellProcessor LOCAL_DATE_CELL_PROCESSOR = new LocalDateCellProcessor();

    private static final Table<String, String, CellProcessor> PROPERTY_TABLE =
        ImmutableTable.<String, String, CellProcessor>builder()
            .put("detailId", "Detail ID", OPTIONAL_PROCESSOR)
            .put("status", "Detail Status", OPTIONAL_PROCESSOR)
            .put("productFamily", "Product Family", OPTIONAL_PROCESSOR)
            .put("batchName", "Usage Batch Name", OPTIONAL_PROCESSOR)
            .put("paymentDate", "Payment Date", LOCAL_DATE_CELL_PROCESSOR)
            .put("rhAccountNumber", "RH Account #", OPTIONAL_PROCESSOR)
            .put("rhName", "RH Name", OPTIONAL_PROCESSOR)
            .put("payeeAccountNumber", "Payee Account #", OPTIONAL_PROCESSOR)
            .put("payeeName", "Payee Name", OPTIONAL_PROCESSOR)
            .put("wrWrkInst", "Wr Wrk Inst", OPTIONAL_PROCESSOR)
            .put("workTitle", "Title", OPTIONAL_PROCESSOR)
            .put("standardNumber", "Standard Number", OPTIONAL_PROCESSOR)
            .put("grossAmount", "Amt in USD", new BigDecimalCellProcessor())
            .put("serviceFee", "Service Fee %", new ServiceFeePercentCellProcessor())
            .put("scenarioName", "Scenario Name", OPTIONAL_PROCESSOR)
            .put("checkNumber", "Check #", OPTIONAL_PROCESSOR)
            .put("checkDate", "Check Date", OFFSET_DATE_TIME_CELL_PROCESSOR)
            .put("cccEventId", "Event ID", OPTIONAL_PROCESSOR)
            .put("distributionName", "Dist. Name", OPTIONAL_PROCESSOR)
            .put("distributionDate", "Dist. Date", OFFSET_DATE_TIME_CELL_PROCESSOR)
            .put("periodEndDate", "Period Ending", LOCAL_DATE_CELL_PROCESSOR)
            .build();

    /**
     * Constructor.
     *
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     * @throws IOException if header cannot be written
     */
    AuditCsvReportHandler(PipedOutputStream pipedOutputStream) throws IOException {
        super(pipedOutputStream);
    }

    @Override
    Table<String, String, CellProcessor> getPropertyTable() {
        return PROPERTY_TABLE;
    }
}
