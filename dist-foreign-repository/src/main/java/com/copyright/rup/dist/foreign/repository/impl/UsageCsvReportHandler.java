package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
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

    private static final String[] USAGE_PROPERTY_MAPPING = new String[]{"detailId", "batchName", "fiscalYear",
        "rroAccountNumber", "rroName", "paymentDate", "workTitle", "article", "standardNumber", "wrWrkInst",
        "rightsholderAccountNumber", "rightsholderName", "publisher", "publicationDate", "numberOfCopies",
        "originalAmount", "grossAmount", "market", "marketPeriodFrom", "marketPeriodTo", "author", "status"};
    private static final NotNull NOT_NULL_PROCESSOR = new NotNull();
    private static final Optional OPTIONAL_PROCESSOR = new Optional();
    private static final CellProcessor[] CELL_PROCESSORS = new CellProcessor[]{
        NOT_NULL_PROCESSOR, NOT_NULL_PROCESSOR, new FiscalYearCellProcessor(), NOT_NULL_PROCESSOR, OPTIONAL_PROCESSOR,
        new DateCellProcessor(), NOT_NULL_PROCESSOR, NOT_NULL_PROCESSOR, NOT_NULL_PROCESSOR, NOT_NULL_PROCESSOR,
        NOT_NULL_PROCESSOR, OPTIONAL_PROCESSOR, NOT_NULL_PROCESSOR, new DateCellProcessor(), NOT_NULL_PROCESSOR,
        NOT_NULL_PROCESSOR, NOT_NULL_PROCESSOR, NOT_NULL_PROCESSOR, NOT_NULL_PROCESSOR, NOT_NULL_PROCESSOR,
        NOT_NULL_PROCESSOR, NOT_NULL_PROCESSOR};
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
        beanWriter.writeHeader("Detail ID", "Usage Batch Name", "Fiscal Year", "RRO Account #", "RRO Name",
            "Payment Date", "Title", "Article", "Standard Number", "Wr Wrk Inst", "RH Account #", "RH Name",
            "Publisher", "Pub Date", "Number of Copies", "Amt in Orig Currency", "Amt in USD", "Market",
            "Market Period From", "Market Period To", "Author", "Detail Status");
    }

    @Override
    public void handleResult(ResultContext<? extends UsageDto> resultContext) {
        try {
            beanWriter.write(resultContext.getResultObject(), USAGE_PROPERTY_MAPPING, CELL_PROCESSORS);
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

        @Override
        public Object execute(Object value, CsvContext context) {
            return null != value
                ? ((LocalDate) value).format(
                DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT, Locale.US))
                : null;
        }
    }
}
