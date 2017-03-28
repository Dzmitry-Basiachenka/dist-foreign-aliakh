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
    private Map<Integer, T> result = Maps.newHashMap();
    private Map<Integer, ErrorRow> errors = Maps.newTreeMap();
    private String fileName;

    /**
     * Constructor.
     *
     * @param headers  csv file headers
     * @param fileName file name of uploaded file
     */
    public CsvProcessingResult(List<String> headers, String fileName) {
        this.headers = headers;
        this.fileName = fileName;
    }

    /**
     * Adds valid item to the valid list.
     *
     * @param item item to add
     * @param line line
     */
    public void addRecord(int line, T item) {
        result.put(line, item);
    }

    /**
     * Gets list of valid items.
     *
     * @return the list of valid items
     */
    public List<T> getResult() {
        return Lists.newArrayList(result.values());
    }

    /**
     * Gets map of valid items.
     *
     * @return the map of valid items
     */
    public Map<Integer, T> getResultMap() {
        return result;
    }

    /**
     * @return the list of {@link ErrorRow}s.
     */
    public List<ErrorRow> getErrors() {
        return Lists.newArrayList(errors.values());
    }

    /**
     * @return file headers.
     */
    public List<String> getHeaders() {
        return headers;
    }

    /**
     * @return uploaded file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Adds failed row to result, if the row exists error message will be appended.
     *
     * @param line         line number from CSV
     * @param originalRow  original row from CSV
     * @param errorMessage error message
     */
    public void logError(Integer line, List<String> originalRow, String errorMessage) {
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
        private final Integer lineNumber;
        private final List<String> originalRow;
        private List<String> errorMessages = Lists.newArrayList();

        private ErrorRow(Integer lineNumber, List<String> originalLine) {
            this.lineNumber = lineNumber;
            this.originalRow = originalLine;
        }

        private void addErrorMessage(String errorMessage) {
            errorMessages.add(errorMessage);
        }

        /**
         * @return line number.
         */
        public Integer getLineNumber() {
            return lineNumber;
        }

        /**
         * @return original row.
         */
        public List<String> getOriginalLine() {
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
