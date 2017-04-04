package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception.HeaderValidationException;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception.ValidationException;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.IValidator;

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
     * Processes CSV resource from the specified stream and {@link CsvProcessingResult}
     * containing valid objects and errors data (if it was found) based on uploaded information.
     *
     * @param stream   byte array output stream
     * @param fileName name of uploaded file
     * @return {@link CsvProcessingResult} instance with uploaded data
     * @throws ValidationException in case of invalid file
     */
    public CsvProcessingResult<T> process(ByteArrayOutputStream stream, String fileName) throws ValidationException {
        try (ICsvListReader listReader = new CsvListReader(
            new InputStreamReader(new ByteArrayInputStream(stream.toByteArray()), StandardCharsets.UTF_8),
            CsvPreference.STANDARD_PREFERENCE)) {
            headers = getCsvHeaders();
            processingResult = new CsvProcessingResult<>(getScvColumnsNames(), fileName);
            validateHeader(listReader.getHeader(true));
            initValidators();
            processRows(listReader);
            validateBusinessRules();
        } catch (IOException | SuperCsvException e) {
            throw new ValidationException(String.format("Failed to read file: %s", e.getMessage()), e);
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
    protected void addPlainValidators(ICsvColumn column, IValidator<String>... validators) {
        plainValidators.put(column, Lists.newArrayList(validators));
    }

    /**
     * Puts business validators.
     *
     * @param validators vararg of validators
     */
    @SuppressWarnings("unchecked")
    protected void addBusinessValidators(IValidator<T>... validators) {
        businessValidators = Lists.newArrayList(validators);
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
        return NumberUtils.createLong(getValue(column, params));
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
        return null != value ? parseDateValue(value) : null;
    }

    /**
     * Return Integer from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or Integer
     */
    protected Integer getInteger(ICsvColumn column, List<String> params) {
        return NumberUtils.createInteger(getValue(column, params));
    }

    /**
     * Return BigDecimal from parsed parameters by specified column.
     *
     * @param column column
     * @param params params
     * @return null or BigDecimal
     */
    protected BigDecimal getBigDecimal(ICsvColumn column, List<String> params) {
        return NumberUtils.createBigDecimal(getValue(column, params));
    }

    /**
     * Validates business rules.
     */
    protected void validateBusinessRules() {
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

    private void processRows(ICsvListReader listReader) throws IOException {
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

    private void processRow(int line, List<String> params) {
        List<String> trimmedParams = trimNulls(params);
        if (plainValidate(line, trimmedParams)) {
            T item = build(trimmedParams);
            processingResult.addRecord(line, item);
        }
    }

    private boolean plainValidate(int line, List<String> params) {
        boolean valid = true;
        if (!Objects.equals(headers.size(), params.size())) {
            processingResult.logError(line, originalValuesMap.get(line),
                String.format("Row is incorrect: Expected columns are %s actual %s", headers.size(), params.size()));
            return false;
        }
        for (int i = 0; i < params.size(); i++) {
            String value = params.get(i);
            String field = headers.get(i).getColumnName();
            List<IValidator<String>> validators = getPlainValidators(i);
            for (IValidator<String> validator : validators) {
                if (!validator.isValid(value)) {
                    valid = false;
                    processingResult.logError(line, originalValuesMap.get(line),
                        String.format("%s: %s", field, validator.getErrorMessage()));
                }
            }
        }
        return valid;
    }

    private void validateBusinessRules(int line, T item) {
        businessValidators.stream()
            .filter(validator -> !validator.isValid(item))
            .forEach(validator -> {
                processingResult.logError(line, originalValuesMap.get(line), validator.getErrorMessage());
            });
    }

    private void validateHeader(String... fileHeaders) throws HeaderValidationException {
        if (ArrayUtils.isEmpty(fileHeaders) || fileHeaders.length != headers.size()) {
            throw new HeaderValidationException(getScvColumnsNames(), fileHeaders);
        }
        for (int i = 0; i < headers.size(); i++) {
            if (!headers.get(i).getColumnName().equalsIgnoreCase(fileHeaders[i])) {
                throw new HeaderValidationException(getScvColumnsNames(), fileHeaders);
            }
        }
    }

    private List<String> getScvColumnsNames() {
        return headers.stream()
            .map(ICsvColumn::getColumnName)
            .collect(Collectors.toList());
    }

    private String getValue(ICsvColumn header, List<String> params) {
        return StringUtils.defaultIfBlank(params.get(header.ordinal()), null);
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
        return null != value ? StringUtils.trim(value.replaceAll(EMPTY_STRING_REGEX, StringUtils.EMPTY)) : null;
    }

    private LocalDate parseDateValue(String value) {
        try {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern("M/d/uuuu", Locale.US).withResolverStyle(
                ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return null;
        }
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
