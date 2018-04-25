package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
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

    private final CsvWriter csvWriter;

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    BaseCsvReportHandler(OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        csvWriter = new CsvWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8),
            new CsvWriterSettings());
        csvWriter.writeHeaders(getBeanHeaders());
    }

    /**
     * Gets bean properties that will be written into report.
     *
     * @param bean bean instance
     * @return a {@link List} that contains bean properties
     */
    abstract List<String> getBeanProperties(T bean);

    /**
     * Gets bean headers that will be written into report.
     *
     * @return a {@link List} that contains bean header values
     */
    abstract List<String> getBeanHeaders();

    @Override
    public void handleResult(ResultContext<? extends T> context) {
        csvWriter.writeRow(getBeanProperties(context.getResultObject()));
    }

    @Override
    public void close() {
        csvWriter.close();
    }

    /**
     * Generate bean fiscal year value.
     *
     * @param value fiscal year value
     * @return fiscal year represented as string
     */
    String getBeanFiscalYear(Integer value) {
        return UsageBatchUtils.getFiscalYear(value);
    }

    /**
     * Generate date value in {@link RupDateUtils#US_DATE_FORMAT_PATTERN_SHORT} format
     * for {@link java.time.OffsetDateTime} type fields.
     *
     * @param value date value
     * @return date represented as string
     */
    String getBeanOffsetDateTime(OffsetDateTime value) {
        return CommonDateUtils.format(value, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }

    /**
     * Generate date value in {@link RupDateUtils#US_DATE_FORMAT_PATTERN_SHORT} format
     * for {@link java.time.LocalDate} type fields.
     *
     * @param value date value
     * @return date represented as string
     */
    String getBeanLocalDate(LocalDate value) {
        return CommonDateUtils.format(value, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }

    /**
     * Generate service fee percent value.
     *
     * @param value service fee percent value
     * @return service fee percent value represented as string
     */
    String getBeanServiceFeePercent(BigDecimal value) {
        String result = null;
        if (Objects.nonNull(value)) {
            result = String.format("%.1f", new BigDecimal("100").multiply(value));
        }
        return result;
    }

    /**
     * Transform zero big decimal to string.
     *
     * @param value zero big decimal value
     * @return zero big decimal represented as string
     */
    String getBeanBigDecimal(BigDecimal value) {
        return value.signum() == 0 ? "0.00" : value.toString();
    }

    /**
     * Gets bean property and converts it to string.
     *
     * @param value bean property
     * @return bean property represented as string or empty string - if value is {@code null}
     */
    String getBeanPropertyAsString(Object value) {
        return Objects.nonNull(value) ? value.toString() : StringUtils.EMPTY;
    }
}
