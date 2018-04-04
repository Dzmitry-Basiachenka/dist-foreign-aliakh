package com.copyright.rup.dist.foreign.service.impl.csv;

import static java.util.Objects.requireNonNull;

import com.copyright.rup.common.exception.RupRuntimeException;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Parser for CSV files.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/20/2018
 *
 * @param <T> the type of processing item
 * @author Nikita Levyankov
 */
//TODO {nlevyankov} move to dist-common
public class DistCsvProcessor<T> {
    /**
     * Regular expression to find 'null', 'na', 'n/a' case insensitively.
     */
    private static final String EMPTY_STRING_REGEX = "^\\s*(([nN][uU][lL][lL])|([nN][aA])|([nN]/[aA]))\\s*$";
    private final Map<ICsvColumn, List<IValidator<String>>> plainValidators = new TreeMap<>();
    private final CsvParserSettings settings = new CsvParserSettings();
    private final List<IValidator<T>> businessValidators = new ArrayList<>();
    private final IConverter<T> converter;
    private final CsvParser parser = new CsvParser(settings);
    private final List<String> expectedHeaders;
    private boolean validateHeaders;

    /**
     * Constructor.
     *
     * @param converter       converter for bean
     * @param validateHeaders flag to enable or disable headers validation
     * @param headers         headers to process
     */
    public DistCsvProcessor(IConverter<T> converter, boolean validateHeaders, List<String> headers) {
        this.converter = requireNonNull(converter);
        this.expectedHeaders = requireNonNull(headers);
        settings.getFormat().setLineSeparator("\n");
        settings.setHeaders(headers.toArray(new String[0]));
        setValidateHeaders(validateHeaders);
        initPlainValidators();
    }

    private static String[] trimNulls(String... values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = trimNulls(values[i]);
        }
        return values;
    }

    private static String trimNulls(String value) {
        return null != value ? StringUtils.trim(value.replaceAll(EMPTY_STRING_REGEX, StringUtils.EMPTY)) : null;
    }

    /**
     * Sets whether header validation is needed.
     *
     * @param validateHeadersFlag true if validation is needed, false - otherwise
     */
    public void setValidateHeaders(boolean validateHeadersFlag) {
        this.validateHeaders = validateHeadersFlag;
        settings.setHeaderExtractionEnabled(!validateHeadersFlag);
    }

    /**
     * Processes CSV resource from the specified stream and {@link ProcessingResult}
     * containing valid objects and errors data (if it was found) based on uploaded information.
     *
     * @param stream byte array output stream
     * @return {@link ProcessingResult} instance with uploaded data
     */
    public ProcessingResult<T> process(ByteArrayOutputStream stream) {
        ProcessingResult<T> result = new ProcessingResult<>(expectedHeaders);
        try (ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray())) {
            parser.beginParsing(is, StandardCharsets.UTF_8);
            if (validateHeaders) {
                //first row contains header. take it first
                validateHeader(parser.parseNext());
            }
            String[] row;
            while (null != (row = parser.parseNext())) {
                process(trimNulls(row), parser.getContext(), result);
            }
        } catch (IOException e) {
            throw new ValidationException(String.format("Failed to read file: %s", e.getMessage()), e);
        }
        if (result.isEmpty()) {
            throw new ValidationException("File does not contain data");
        }
        return result;
    }

    /**
     * Method is used to initialize plain validators.
     */
    protected void initPlainValidators() {

    }

    /**
     * Puts business validators for bean instance and entire file.
     *
     * @param validators validators.
     */
    @SafeVarargs
    public final void addBusinessValidators(IValidator<T>... validators) {
        businessValidators.addAll(Arrays.asList(validators));
    }

    /**
     * Puts validators by given column.
     *
     * @param column     column to add validators
     * @param validators vararg of validators
     */
    @SafeVarargs
    public final void addPlainValidators(ICsvColumn column, IValidator<String>... validators) {
        plainValidators.put(column, Arrays.asList(validators));
    }

    private void process(String[] row, ParsingContext context, ProcessingResult<T> result) {
        long line = context.currentLine();
        if (plainValidate(row, line, result)) {
            T item = converter.convert(row);
            if (validateBusinessRules(row, line, item, result)) {
                result.addRecord(item);
            }
        }
    }

    private void validateHeader(String... fileHeaders) throws HeaderValidationException {
        if (ArrayUtils.isEmpty(fileHeaders) || fileHeaders.length != expectedHeaders.size()) {
            throw new HeaderValidationException(expectedHeaders, fileHeaders);
        }
        for (int i = 0; i < expectedHeaders.size(); i++) {
            if (!expectedHeaders.get(i).equalsIgnoreCase(fileHeaders[i])) {
                throw new HeaderValidationException(expectedHeaders, fileHeaders);
            }
        }
    }

    private boolean validateBusinessRules(String[] row, long line, T item, ProcessingResult result) {
        AtomicBoolean valid = new AtomicBoolean(true);
        businessValidators.forEach(validator -> {
            if (!validator.isValid(item)) {
                valid.set(false);
                result.logError(line, row, validator.getErrorMessage());
            }
        });
        return valid.get();
    }

    private boolean plainValidate(String[] row, long line, ProcessingResult result) {
        if (!Objects.equals(expectedHeaders.size(), row.length)) {
            result.logError(line, row,
                String.format("Row is incorrect: Expected columns are %s actual %s", settings.getHeaders().length,
                    row.length));
            return false;
        }
        AtomicBoolean valid = new AtomicBoolean(true);
        plainValidators.forEach((column, validators) -> {
            if (!validators.isEmpty()) {
                final String value = row[column.ordinal()];
                validators.forEach(validator -> {
                    if (!validator.isValid(value)) {
                        valid.set(false);
                        result.logError(line, row,
                            String.format("%s: %s", column.getColumnName(), validator.getErrorMessage()));
                    }
                });
            }
        });
        return valid.get();
    }

    /**
     * Converts row to bean.
     *
     * @param <T> type of bean
     */
    @FunctionalInterface
    public interface IConverter<T> {
        /**
         * Converts row to bean.
         *
         * @param row row
         * @return bean instance
         */
        T convert(String... row);
    }

    /**
     * Interface to handle CSV column names.
     */
    public interface ICsvColumn {

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

    /**
     * The contract for validators. Uses optional value approach (see {@link #isValid(Object)}).
     * <p>
     * Copyright (C) 2017 copyright.com
     * <p>
     * Date: 02/24/17
     *
     * @param <T> the type of object for validation
     * @author Aliaksei Pchelnikau
     */
    public interface IValidator<T> {

        /**
         * Performs validation of passed instance.
         *
         * @param value the instance to validate
         * @return the result of validation: {@code true} value passed validation otherwise {@code false}
         */
        boolean isValid(T value);

        /**
         * @return the error message.
         */
        String getErrorMessage();
    }

    /**
     * Processing results of CSV file.
     * <p>
     * Copyright (C) 2018 copyright.com
     * <p>
     * Date: 03/20/2018
     *
     * @param <T> the type of resulting
     * @author Nikita Levyankov
     */
    public static class ProcessingResult<T> {
        private static final int ERRORS_THRESHOLD = 2000;
        private final Map<Long, ErrorRow> errors = new TreeMap<>();
        private final List<T> result = new ArrayList<>();
        private final List<String> fileHeaders;
        private int errorsCount;

        /**
         * Constructor.
         *
         * @param fileHeaders array of headers
         */
        public ProcessingResult(List<String> fileHeaders) {
            this.fileHeaders = requireNonNull(fileHeaders);
        }

        /**
         * @return result of processing.
         */
        public List<T> get() {
            return result;
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
         * @return {@code true} if result has no error(s), {@code false} - otherwise.
         */
        public boolean isSuccessful() {
            return errors.isEmpty();
        }

        /**
         * Write information about errors to output stream.
         * Contains information about original line, line number and list of error for current line.
         *
         * @param outputStream output stream for writing
         */
        public void writeToFile(OutputStream outputStream) {
            CsvWriter writer = new CsvWriter(outputStream, new CsvWriterSettings());
            List<String> headers = new ArrayList<>(fileHeaders);
            headers.add("Line");
            headers.add("Error Reason");
            writer.writeHeaders(headers);
            writer.writeStringRowsAndClose(
                errors.values().stream().map(ErrorRow::getErrorRow).collect(Collectors.toList()));
        }

        /**
         * Adds valid item to the valid list.
         *
         * @param item item to add
         */
        void addRecord(T item) {
            if (0 == errorsCount) {
                result.add(item);
            }
        }

        /**
         * Adds failed row to result, if the row exists error message will be appended.
         *
         * @param line         line number from CSV
         * @param row          original row from CSV
         * @param errorMessage error message
         * @throws ThresholdExceededException if number of errors in file more than threshold
         */
        void logError(long line, String[] row, String errorMessage) throws ThresholdExceededException {
            errors.computeIfAbsent(line, errorRow -> new ErrorRow(line, row)).addErrorMessage(errorMessage);
            clear();
            if (ERRORS_THRESHOLD <= ++errorsCount) {
                throw new ThresholdExceededException(ERRORS_THRESHOLD, this);
            }
        }

        private void clear() {
            if (0 == errorsCount) {
                result.clear();
            }
        }

        /**
         * Contains information about errors.
         */
        private static class ErrorRow {
            private final long line;
            private final String[] originalRow;
            private final List<String> errorMessages = new ArrayList<>();

            /**
             * Constructor.
             *
             * @param line         line number from CSV
             * @param originalLine original line
             */
            ErrorRow(long line, String... originalLine) {
                this.line = line;
                this.originalRow = originalLine;
            }

            private void addErrorMessage(String errorMessage) {
                errorMessages.add(errorMessage);
            }

            private String[] getErrorRow() {
                return ArrayUtils.addAll(originalRow, String.valueOf(line), String.join("; ", errorMessages));
            }
        }
    }

    /**
     * Represents header validation exception.
     * <p>
     * Copyright (C) 2017 copyright.com
     * <p>
     * Date: 03/15/17
     *
     * @author Aliaksei Pchelnikau
     */
    public static class HeaderValidationException extends ValidationException {

        private static final String ERROR_MESSAGE = "Columns headers are incorrect. Expected columns headers are:%n%s";
        private static final String ERROR_MESSAGE_WITH_FILE_HEADERS = ERROR_MESSAGE + "%nFile headers:%n%s";
        private final List<String> expectedHeaders;

        /**
         * Constructor.
         *
         * @param expectedHeaders expected headers
         * @param fileHeaders     actual headers
         */
        HeaderValidationException(List<String> expectedHeaders, String... fileHeaders) {
            super(String.format(ERROR_MESSAGE_WITH_FILE_HEADERS, String.join(", ", expectedHeaders),
                getJoinedHeaders(fileHeaders)));
            this.expectedHeaders = expectedHeaders;
        }

        private static String getJoinedHeaders(String... fileHeaders) {
            return ArrayUtils.isNotEmpty(fileHeaders) ? String.join(", ", fileHeaders) : "''";
        }

        @Override
        public String getHtmlMessage() {
            String joinedHeaders =
                expectedHeaders.stream().collect(Collectors.joining("</li><li>", "<ul><li>", "</li></ul>"));
            return String.format(ERROR_MESSAGE, joinedHeaders);
        }
    }

    /**
     * Exception occurs if number of errors in processing result more than threshold.
     * <p>
     * Copyright (C) 2018 copyright.com
     * <p>
     * Date: 03/20/2018
     *
     * @author Nikita Levyankov
     */
    public static class ThresholdExceededException extends ValidationException {

        private final ProcessingResult processingResult;

        /**
         * Constructor.
         *
         * @param threshold        errors threshold
         * @param processingResult instance of {@link ProcessingResult}
         */
        ThresholdExceededException(int threshold, ProcessingResult processingResult) {
            super(String.format("The file could not be uploaded. There are more than %s errors", threshold));
            this.processingResult = processingResult;
        }

        public ProcessingResult getProcessingResult() {
            return processingResult;
        }
    }

    /**
     * Represents validation exception.
     * <p>
     * Copyright (C) 2017 copyright.com
     * <p>
     * Date: 03/15/17
     *
     * @author Aliaksei Pchelnikau
     */
    public static class ValidationException extends RupRuntimeException {

        /**
         * Constructor.
         *
         * @param message validation message
         */
        ValidationException(String message) {
            super(message);
        }

        /**
         * Constructor.
         *
         * @param message validation message
         * @param cause   validation cause
         */
        ValidationException(String message, Throwable cause) {
            super(message, cause);
        }

        /**
         * @return the message in html format, the same as the pure message by default.
         */
        public String getHtmlMessage() {
            return getMessage();
        }
    }
}
