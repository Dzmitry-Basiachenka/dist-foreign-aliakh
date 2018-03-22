package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.domain.StoredEntity;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Writes usages into an {@link OutputStream}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/06/2017
 *
 * @param <T> the type of object that will be written into report
 * @author Mikita Hladkikh
 */
class UsageCsvReportHandler<T extends StoredEntity<String>> extends BaseCsvReportHandler<T> {

    private static final Optional OPTIONAL_PROCESSOR = new Optional();
    private static final LocalDateCellProcessor LOCAL_DATE_CELL_PROCESSOR = new LocalDateCellProcessor();

    private static final Table<String, String, CellProcessor> PROPERTY_TABLE =
        ImmutableTable.<String, String, CellProcessor>builder()
            .put("detailId", "Detail ID", OPTIONAL_PROCESSOR)
            .put("status", "Detail Status", OPTIONAL_PROCESSOR)
            .put("productFamily", "Product Family", OPTIONAL_PROCESSOR)
            .put("batchName", "Usage Batch Name", OPTIONAL_PROCESSOR)
            .put("fiscalYear", "Fiscal Year", new FiscalYearCellProcessor())
            .put("rroAccountNumber", "RRO Account #", OPTIONAL_PROCESSOR)
            .put("rroName", "RRO Name", OPTIONAL_PROCESSOR)
            .put("paymentDate", "Payment Date", LOCAL_DATE_CELL_PROCESSOR)
            .put("workTitle", "Title", OPTIONAL_PROCESSOR)
            .put("article", "Article", OPTIONAL_PROCESSOR)
            .put("standardNumber", "Standard Number", OPTIONAL_PROCESSOR)
            .put("wrWrkInst", "Wr Wrk Inst", OPTIONAL_PROCESSOR)
            .put("rhAccountNumber", "RH Account #", OPTIONAL_PROCESSOR)
            .put("rhName", "RH Name", OPTIONAL_PROCESSOR)
            .put("publisher", "Publisher", OPTIONAL_PROCESSOR)
            .put("publicationDate", "Pub Date", LOCAL_DATE_CELL_PROCESSOR)
            .put("numberOfCopies", "Number of Copies", OPTIONAL_PROCESSOR)
            .put("reportedValue", "Reported value", OPTIONAL_PROCESSOR)
            .put("grossAmount", "Amt in USD", OPTIONAL_PROCESSOR)
            .put("batchGrossAmount", "Gross Amt in USD", OPTIONAL_PROCESSOR)
            .put("market", "Market", OPTIONAL_PROCESSOR)
            .put("marketPeriodFrom", "Market Period From", OPTIONAL_PROCESSOR)
            .put("marketPeriodTo", "Market Period To", OPTIONAL_PROCESSOR)
            .put("author", "Author", OPTIONAL_PROCESSOR)
            .build();

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     * @throws IOException if header cannot be written
     */
    UsageCsvReportHandler(OutputStream outputStream) throws IOException {
        super(outputStream);
    }

    @Override
    Table<String, String, CellProcessor> getPropertyTable() {
        return PROPERTY_TABLE;
    }
}
