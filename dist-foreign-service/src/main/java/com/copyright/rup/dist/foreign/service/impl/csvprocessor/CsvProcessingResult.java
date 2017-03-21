package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Processing Result for uploading data from CSV file.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/22/17
 *
 * @param <T> the type of uploading item
 * @author Aliaksei Pchelnikau
 */
public class CsvProcessingResult<T> {

    private final List<String> headers;
    private List<T> result = Lists.newArrayList();
    private Map<Integer, ErrorRow> errors = Maps.newTreeMap();

    /**
     * Constructor.
     *
     * @param headers csv file headers
     */
    public CsvProcessingResult(List<String> headers) {
        this.headers = headers;
    }

    /**
     * Adds valid item to the valid list.
     *
     * @param item item to add
     */
    public void addRecord(T item) {
        result.add(item);
    }

    /**
     * Gets list of valid items.
     *
     * @return the list of valid items
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * @return the map of errors. Key is the line number, value is {@link ErrorRow}.
     */
    public Map<Integer, ErrorRow> getErrors() {
        return errors;
    }

    /**
     * @return file headers.
     */
    public List<String> getHeaders() {
        return headers;
    }

    /**
     * Adds failed row to result, if the row exists error message will be appended.
     *
     * @param line         line number from CSV
     * @param originalRow  original row from CSV
     * @param errorMessage error message
     */
    public void logError(Integer line, String originalRow, String errorMessage) {
        ErrorRow errorRow = errors.get(line);
        if (null == errorRow) {
            errorRow = new ErrorRow(line, originalRow);
            errors.put(line, errorRow);
        }
        errorRow.addErrorMessage(errorMessage);
    }

    /**
     * @return {@code true} if result has no error(s), {@code false} - otherwise.
     */
    public boolean isSuccessful() {
        return errors.isEmpty();
    }

    /**
     * Checks whether processing result has information about valid items and invalid data.
     *
     * @return {@code true} - if processing result is empty, {@code false} - otherwise
     */
    public boolean isEmpty() {
        return result.isEmpty() && errors.isEmpty();
    }

    /**
     * Contains information about errors.
     */
    public static class ErrorRow {
        private final int line;
        private final String originalRow;
        private List<String> errorMessages = Lists.newArrayList();

        private ErrorRow(int line, String originalRow) {
            this.line = line;
            this.originalRow = originalRow;
        }

        private void addErrorMessage(String errorMessage) {
            errorMessages.add(errorMessage);
        }

        /**
         * @return line number.
         */
        public int getLine() {
            return line;
        }

        /**
         * @return original row.
         */
        public String getOriginalRow() {
            return originalRow;
        }

        /**
         * @return list of error messages.
         */
        public List<String> getErrorMessages() {
            return errorMessages;
        }
    }
}
