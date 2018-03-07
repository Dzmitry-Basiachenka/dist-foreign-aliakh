package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception.HeaderValidationException;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception.ValidationException;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.IValidator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.perf4j.StopWatch;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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

    /**
     * Regular expression to find 'null', 'na', 'n/a' case insensitively.
     */
    private static final String EMPTY_STRING_REGEX = "^\\s*(([nN][uU][lL][lL])|([nN][aA])|([nN]/[aA]))\\s*$";

    private Map<ICsvColumn, List<IValidator<String>>> plainValidators = Maps.newHashMap();
    private List<IValidator<T>> businessValidators = Lists.newArrayList();
    private List<ICsvColumn> headers = Lists.newArrayList();
    private CsvProcessingResult<T> processingResult;
    private Map<Integer, List<String>> originalValuesMap = Maps.newHashMap();

    /**
     * Verifies is string value positive number or not.
     *
     * @param value the instance to verify
     * @return {@code true} if value is positive number, {@code false} - otherwise
     */
    public static boolean isPositiveNumber(String value) {
        return null != value && value.matches("[1-9]\\d*(\\.\\d*[eE][+]\\d+)?");
    }

    /**
     * Returns parsed string from scientific format.
     *
     * @param value the instance to verify
     * @return parsed string
     */
    public static String parseScientific(String value) {
        return null != value ? new BigDecimal(value).toPlainString() : null;
    }

    /**
     * Processes CSV resource from the specified stream and {@link CsvProcessingResult}
     * containing valid objects and errors data (if it was found) based on uploaded information.
     *
     * @param stream   byte array output stream
     * @param fileName name of uploaded file
     * @return {@link CsvProcessingResult} instance with uploaded data
     * @throws ValidationException in case of invalid file
     */
    public CsvProcessingResult<T> process(ByteArrayOutputStream stream, String fileName) throws ValidationException {
        String stopWatchTag = "file.process_" + fileName;
        StopWatch stopWatch = new StopWatch(stopWatchTag);
        try (ICsvListReader listReader = new CsvListReader(
            new InputStreamReader(new ByteArrayInputStream(stream.toByteArray()), StandardCharsets.UTF_8),
            CsvPreference.STANDARD_PREFERENCE)) {
            headers = getCsvHeaders();
            stopWatch.lap(stopWatchTag + "_getCsvHeaders");
            processingResult = new CsvProcessingResult<>(getCsvColumnsNames(), fileName);
            validateHeader(listReader.getHeader(true));
            stopWatch.lap(stopWatchTag + "_validateHeader");
            initValidators();
            processRows(listReader);
            stopWatch.lap(stopWatchTag + "_processRows");
            validateBusinessRules();
            stopWatch.lap(stopWatchTag + "_validate");
        } catch (IOException | SuperCsvException e) {
            throw new ValidationException(String.format("Failed to read file: %s", e.getMessage()), e);
        } finally {
            stopWatch.stop();
        }
        if (processingResult.isEmpty()) {
            throw new ValidationException("File does not contain data");
        }
        return processingResult;
    }

    /**
     * @return csv file header.
     */
    protected abstract List<ICsvColumn> getCsvHeaders();

    /**
     * Builds object from csv row data.
     *
     * @param params data of csv row
     * @return item
     */
    protected abstract T build(List<String> params);

    /**
     * Initialization of plain validators.
     */
    protected void initValidators() {
        //Empty
    }

    /**
     * Puts validators by given column.
     *
     * @param column     column to add validators
     * @param validators vararg of validators
     */
    @SuppressWarnings("unchecked")
    void addPlainValidators(ICsvColumn column, IValidator<String>... validators) {
        plainValidators.put(column, Lists.newArrayList(validators));
    }

    /**
     * Puts business validators.
     *
     * @param validators vararg of validators
     */
    @SuppressWarnings("unchecked")
    void addBusinessValidators(IValidator<T>... validators) {
        businessValidators = Lists.newArrayList(validators);
    }

    /**
     * Return String from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or String
     */
    String getString(ICsvColumn column, List<String> params) {
        String value = getValue(column, params);
        return null != value ? (isPositiveNumber(value) ? parseScientific(value) : value) : null;
    }

    /**
     * Return Long from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or Long
     */
    Long getLong(ICsvColumn column, List<String> params) {
        String value = getValue(column, params);
        return null != value ? Long.valueOf(parseScientific(value)) : null;
    }

    /**
     * Return LocalDate from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or LocalDate
     */
    LocalDate getDate(ICsvColumn column, List<String> params) {
        String value = getValue(column, params);
        return null != value ? parseDateValue(value) : null;
    }

    /**
     * Return Integer from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or Integer
     */
    Integer getInteger(ICsvColumn column, List<String> params) {
        return NumberUtils.createInteger(getValue(column, params));
    }

    /**
     * Return BigDecimal from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or BigDecimal
     */
    BigDecimal getBigDecimal(ICsvColumn column, List<String> params) {
        String value = getValue(column, params);
        return null != value ? new BigDecimal(value).setScale(2, RoundingMode.HALF_UP) : null;
    }

    /**
     * Validates business rules.
     *
     * @throws ValidationException in case of invalid file
     */
    protected void validateBusinessRules() throws ValidationException {
        for (Map.Entry<Integer, T> entry : processingResult.getResultMap().entrySet()) {
            validateBusinessRules(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @return result for uploading.
     */
    protected CsvProcessingResult<T> getResult() {
        return processingResult;
    }

    private List<IValidator<String>> getPlainValidators(int index) {
        List<IValidator<String>> result = plainValidators.get(headers.get(index));
        return null != result ? result : Collections.emptyList();
    }

    private void processRows(ICsvListReader listReader) throws IOException, ValidationException {
        int line = 1;
        while (true) {
            ++line;
            List<String> originalValues = listReader.read();
            originalValuesMap.put(line, originalValues);
            if (null != originalValues) {
                processRow(line, originalValues);
            } else {
                break;
            }
        }
    }

    private void processRow(int line, List<String> params) throws ValidationException {
        List<String> trimmedParams = trimNulls(params);
        if (plainValidate(line, trimmedParams)) {
            T item = build(trimmedParams);
            processingResult.addRecord(line, item);
        }
    }

    private boolean plainValidate(int line, List<String> params) throws ValidationException {
        boolean valid = true;
        if (!Objects.equals(headers.size(), params.size())) {
            processingResult.logError(line, originalValuesMap.get(line),
                String.format("Row is incorrect: Expected columns are %s actual %s", headers.size(), params.size()));
            return false;
        }
        for (int i = 0; i < params.size(); i++) {
            String value = params.get(i);
            String field = headers.get(i).getColumnName();
            for (IValidator<String> validator : getPlainValidators(i)) {
                if (!validator.isValid(value)) {
                    valid = false;
                    processingResult.logError(line, originalValuesMap.get(line),
                        String.format("%s: %s", field, validator.getErrorMessage()));
                }
            }
        }
        return valid;
    }

    private void validateBusinessRules(int line, T item) throws ValidationException {
        List<IValidator<T>> validators =
            businessValidators.stream().filter(validator -> !validator.isValid(item)).collect(Collectors.toList());
        for (IValidator<T> validator : validators) {
            processingResult.logError(line, originalValuesMap.get(line), validator.getErrorMessage());
        }
    }

    private void validateHeader(String... fileHeaders) throws HeaderValidationException {
        if (ArrayUtils.isEmpty(fileHeaders) || fileHeaders.length != headers.size()) {
            throw new HeaderValidationException(getCsvColumnsNames(), fileHeaders);
        }
        for (int i = 0; i < headers.size(); i++) {
            if (!headers.get(i).getColumnName().equalsIgnoreCase(fileHeaders[i])) {
                throw new HeaderValidationException(getCsvColumnsNames(), fileHeaders);
            }
        }
    }

    private List<String> getCsvColumnsNames() {
        return headers.stream().map(ICsvColumn::getColumnName).collect(Collectors.toList());
    }

    private String getValue(ICsvColumn header, List<String> params) {
        return StringUtils.defaultIfBlank(params.get(header.ordinal()), null);
    }

    private List<String> trimNulls(List<String> values) {
        return values.stream().map(this::trimNulls).collect(Collectors.toList());
    }

    private String trimNulls(String value) {
        return null != value ? StringUtils.trim(value.replaceAll(EMPTY_STRING_REGEX, StringUtils.EMPTY)) : null;
    }

    private LocalDate parseDateValue(String value) {
        try {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern("M/d/uuuu", Locale.US)
                .withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Interface to handle CSV column names.
     */
    interface ICsvColumn {

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
