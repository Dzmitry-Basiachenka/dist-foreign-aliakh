package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult.ErrorRow;

import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * Provide functionality for building errors result during uploading usage batch.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/21/17
 *
 * @author Ihar Suvorau
 */
public class CsvErrorResultWriter {

    /**
     * Write information about errors during uploading usage batch into output stream.
     * Contains information about original line, line number and list of error for current line.
     *
     * @param outputStream        output stream for writing
     * @param csvProcessingResult contains information about errors
     */
    public void writeErrorsResult(OutputStream outputStream, CsvProcessingResult<Usage> csvProcessingResult) {
        try (CsvListWriter csvListWriter = new CsvListWriter(
            new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), CsvPreference.STANDARD_PREFERENCE)) {
            List<String> header = csvProcessingResult.getHeaders();
            header.add("Line");
            header.add("Error Reason");
            csvListWriter.writeHeader(header.stream().toArray(String[]::new));
            List<ErrorRow> errors = csvProcessingResult.getErrors();
            for (ErrorRow errorRow : errors) {
                csvListWriter.write(getColumns(errorRow));
            }
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    private List<String> getColumns(ErrorRow errorRow) {
        List<String> columns = errorRow.getOriginalLine();
        columns.add(Objects.toString(errorRow.getLineNumber()));
        columns.add(String.join("; ", errorRow.getErrorMessages()));
        return columns;
    }
}
