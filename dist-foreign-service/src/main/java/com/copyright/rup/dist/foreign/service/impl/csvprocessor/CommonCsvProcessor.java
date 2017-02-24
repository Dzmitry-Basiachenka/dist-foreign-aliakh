package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.common.exception.RupRuntimeException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Common CSV Processor for uploading items from CSV files.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/21/17
 *
 * @param <T> the type of processing item
 * @author Aliaksei Pchelnikau
 */
public abstract class CommonCsvProcessor<T> {

    private static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);

    private Map<ICsvColumn, List<IValidator<String>>> plainValidators = Maps.newHashMap();
    private List<ICsvColumn> headers = Lists.newArrayList();
    private CsvProcessingResult<T> processingResult = new CsvProcessingResult<>();
    private int line = 1;

    /**
     * Processes CSV resource from the specified stream and {@link CsvProcessingResult}
     * containing valid objects and errors data (if it was found) based on uploaded information.
     *
     * @param stream byte array output stream
     * @return {@link CsvProcessingResult} instance with uploaded data
     */
    public CsvProcessingResult<T> process(ByteArrayOutputStream stream) {
        try (ICsvListReader listReader = new CsvListReader(
            new InputStreamReader(new ByteArrayInputStream(stream.toByteArray()), StandardCharsets.UTF_8),
            CsvPreference.STANDARD_PREFERENCE)) {
            headers = getHeaders();
            validateHeader(listReader.getHeader(true));
            initPlainValidators();
            processRows(listReader);
        } catch (IOException | SuperCsvException e) {
            throw new RupRuntimeException(e);
        }
        if (processingResult.isEmpty()) {
            throw new RupRuntimeException("File doesn't contain data");
        }
        return processingResult;
    }

    /**
     * @return csv file header.
     */
    protected abstract List<ICsvColumn> getHeaders();

    /**
     * Deserializes csv row to item.
     *
     * @param params data of csv row
     * @return item
     */
    protected abstract T deserialize(List<String> params);

    /**
     * Initializations plain validators.
     */
    protected void initPlainValidators() {
        //Empty
    }

    /**
     * Puts validators by given column.
     *
     * @param column     column to add validators
     * @param validators vararg of validators
     */
    @SuppressWarnings("unchecked")
    protected void addPlainValidators(ICsvColumn column, IValidator<String>... validators) {
        plainValidators.put(column, Lists.newArrayList(validators));
    }

    /**
     * Return String from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or String
     */
    protected String getString(ICsvColumn column, List<String> params) {
        return getValue(column, params);
    }

    /**
     * Return Long from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or Long
     */
    protected Long getLong(ICsvColumn column, List<String> params) {
        String value = getValue(column, params);
        return NumberUtils.createLong(StringUtils.defaultIfBlank(value, null));
    }

    /**
     * Return LocalDate from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or LocalDate
     */
    protected LocalDate getDate(ICsvColumn column, List<String> params) {
        String value = getValue(column, params);
        return null != value ? LocalDate.parse(value, DATE_FORMAT) : null;
    }

    /**
     * Return Integer from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or Integer
     */
    protected Integer getInteger(ICsvColumn column, List<String> params) {
        String value = getValue(column, params);
        return NumberUtils.createInteger(StringUtils.defaultIfBlank(value, null));
    }

    /**
     * Return BigDecimal from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or BigDecimal
     */
    protected BigDecimal getBigDecimal(ICsvColumn column, List<String> params) {
        String value = getValue(column, params);
        return NumberUtils.createBigDecimal(StringUtils.defaultIfBlank(value, null));
    }

    private List<IValidator<String>> getPlainValidators(int index) {
        List<IValidator<String>> result = plainValidators.get(headers.get(index));
        return null != result ? result : Collections.emptyList();
    }

    private void processRows(ICsvListReader listReader) throws IOException {
        while (true) {
            ++line;
            List<String> params = listReader.read();
            if (null != params) {
                processRow(params);
            } else {
                break;
            }
        }
    }

    private void processRow(List<String> params) {
        List<String> trimmedParams = trimNulls(params);
        if (plainValidate(trimmedParams)) {
            T item = deserialize(trimmedParams);
            if (businessValidate(item)) {
                processingResult.addRecord(item);
            }
        }
    }

    private boolean plainValidate(List<String> params) {
        for (int i = 0; i < params.size(); i++) {
            String value = params.get(i);
            List<IValidator<String>> validators = getPlainValidators(i);
            validators.stream()
                .filter(validator -> !validator.isValid(value))
                //TODO stub
                .forEach(validator -> processingResult.logError(line, validator.getErrorMessage()));
        }
        return processingResult.isSuccessful();
    }

    private boolean businessValidate(T item) {
        //TODO stub
        return item != null;
    }

    private void validateHeader(String... fileHeaders) {
        if (ArrayUtils.isEmpty(fileHeaders) || fileHeaders.length != headers.size()) {
            throw new RupRuntimeException("Columns count is incorrect.");
        }
        for (int i = 0; i < headers.size(); i++) {
            if (!headers.get(i).getColumnName().equals(fileHeaders[i])) {
                //TODO initial version
                throw new RupRuntimeException("Column headers are incorrect.");
            }
        }
    }

    private String getValue(ICsvColumn header, List<String> params) {
        return params.get(header.ordinal());
    }

    private static List<String> trimNulls(List<String> values) {
        List<String> result = Lists.newArrayListWithExpectedSize(values.size());
        result.addAll(
            values.stream()
                .map(CommonCsvProcessor::trimNulls)
                .collect(Collectors.toList()));
        return result;
    }

    private static String trimNulls(String value) {
        return null != value
            ? StringUtils
            .trim(value.replaceAll("^\\s*(([nN][uU][lL][lL])|([nN][aA])|([nN]/[aA]))\\s*$", StringUtils.EMPTY))
            : null;
    }

    /**
     * Interface to handle CSV column names.
     */
    protected interface ICsvColumn {

        /**
         * @return ordinal of the column.
         */
        int ordinal();

        /**
         * @return name of the enum.
         */
        String name();

        /**
         * @return name of the column.
         */
        String getColumnName();
    }
}
