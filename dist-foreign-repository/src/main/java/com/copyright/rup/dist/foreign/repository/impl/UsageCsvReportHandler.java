package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.util.CsvContext;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Handles {@link ResultContext} result and writes it into output stream.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/06/2017
 *
 * @author Mikita Hladkikh
 */
public class UsageCsvReportHandler implements ResultHandler<UsageDto> {

    private static final Optional OPTIONAL_PROCESSOR = new Optional();
    private static final DateCellProcessor DATE_CELL_PROCESSOR = new DateCellProcessor();
    private Table<String, String, CellProcessor> propertyTable = ImmutableTable.<String, String, CellProcessor>builder()
        .put("detailId", "Detail ID", OPTIONAL_PROCESSOR)
        .put("status", "Detail Status", OPTIONAL_PROCESSOR)
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
        .put("publisher", "Publisher", OPTIONAL_PROCESSOR)
        .put("publicationDate", "Pub Date", new DateCellProcessor())
        .put("numberOfCopies", "Number of Copies", OPTIONAL_PROCESSOR)
        .put("reportedValue", "Reported value", OPTIONAL_PROCESSOR)
        .put("grossAmount", "Amt in USD", OPTIONAL_PROCESSOR)
        .put("market", "Market", OPTIONAL_PROCESSOR)
        .put("marketPeriodFrom", "Market Period From", OPTIONAL_PROCESSOR)
        .put("marketPeriodTo", "Market Period To", OPTIONAL_PROCESSOR)
        .put("author", "Author", OPTIONAL_PROCESSOR)
        .build();
    private ICsvBeanWriter beanWriter;

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     * @throws IOException if header cannot be written
     */
    public UsageCsvReportHandler(OutputStream outputStream) throws IOException {
        beanWriter = new CsvBeanWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8),
            CsvPreference.STANDARD_PREFERENCE);
        beanWriter.writeHeader(propertyTable.columnKeySet().toArray(new String[propertyTable.size()]));
    }

    @Override
    public void handleResult(ResultContext<? extends UsageDto> resultContext) {
        try {
            beanWriter.write(resultContext.getResultObject(),
                propertyTable.rowKeySet().toArray(new String[propertyTable.size()]),
                propertyTable.values().toArray(new CellProcessor[propertyTable.size()]));
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    /**
     * Closes the report's output stream.
     */
    protected void closeStream() {
        try {
            beanWriter.close();
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    /**
     * The cell processor to generate fiscal year value for report.
     */
    static class FiscalYearCellProcessor implements CellProcessor {

        @Override
        public Object execute(Object value, CsvContext context) {
            return null != value ? UsageBatchUtils.getFiscalYear((Integer) value) : null;
        }
    }

    /**
     * The cell processor to generate date value in {@link RupDateUtils#US_DATE_FORMAT_PATTERN_SHORT} for report.
     */
    static class DateCellProcessor implements CellProcessor {

        private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT, Locale.US);

        @Override
        public Object execute(Object value, CsvContext context) {
            return null != value ? ((LocalDate) value).format(FORMATTER) : null;
        }
    }
}
