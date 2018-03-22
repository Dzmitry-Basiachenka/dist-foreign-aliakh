package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;

import com.google.common.collect.Table;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.util.CsvContext;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Base CSV report handler.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/2017
 *
 * @param <T> the type of object that will be written into report
 * @author Uladzislau_Shalamitski
 */
public abstract class BaseCsvReportHandler<T extends StoredEntity<String>> implements ResultHandler<T>, AutoCloseable {

    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT, Locale.US);
    private final ICsvBeanWriter beanWriter;

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     * @throws RupRuntimeException when usage details can't be written to the CSV report
     */
    BaseCsvReportHandler(OutputStream outputStream) throws RupRuntimeException {
        Objects.requireNonNull(outputStream);
        beanWriter = new CsvBeanWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8),
            CsvPreference.STANDARD_PREFERENCE);
        try {
            beanWriter.writeHeader(getPropertyTable().columnKeySet().toArray(new String[getPropertyTable().size()]));
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    /**
     * @return a {@link Table} that contains bean properties, header values and cell processors
     */
    abstract Table<String, String, CellProcessor> getPropertyTable();

    @Override
    public void handleResult(ResultContext<? extends T> context) throws RupRuntimeException {
        try {
            beanWriter.write(context.getResultObject(),
                getPropertyTable().rowKeySet().toArray(new String[getPropertyTable().size()]),
                getPropertyTable().values().toArray(new CellProcessor[getPropertyTable().size()]));
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    @Override
    public void close() {
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
     * The cell processor to generate date value in {@link RupDateUtils#US_DATE_FORMAT_PATTERN_SHORT} for report
     * for {@link java.time.OffsetDateTime} type fields.
     */
    static class OffsetDateTimeCellProcessor implements CellProcessor {

        @Override
        public Object execute(Object value, CsvContext context) {
            return null != value ? ((OffsetDateTime) value).format(DATE_FORMATTER) : null;
        }
    }

    /**
     * The cell processor to generate date value in {@link RupDateUtils#US_DATE_FORMAT_PATTERN_SHORT} for report
     * for {@link java.time.LocalDate} type fields.
     */
    static class LocalDateCellProcessor implements CellProcessor {

        @Override
        public Object execute(Object value, CsvContext context) {
            return null != value ? ((LocalDate) value).format(DATE_FORMATTER) : null;
        }
    }

    /**
     * The cell processor to generate service fee percent value for report.
     */
    static class ServiceFeePercentCellProcessor implements CellProcessor {

        private static final BigDecimal HUNDRED_PERCENT = new BigDecimal("100");

        @Override
        public Object execute(Object value, CsvContext context) {
            return null != value ? String.format("%.1f", HUNDRED_PERCENT.multiply((BigDecimal) value)) : null;
        }
    }

    /**
     * The cell processor to transform zero big decimal to string for report.
     */
    static class BigDecimalCellProcessor implements CellProcessor {
        @Override
        public Object execute(Object value, CsvContext context) {
            return !(value instanceof BigDecimal && ((BigDecimal) value).signum() == 0) ? value : "0.00";
        }
    }
}
