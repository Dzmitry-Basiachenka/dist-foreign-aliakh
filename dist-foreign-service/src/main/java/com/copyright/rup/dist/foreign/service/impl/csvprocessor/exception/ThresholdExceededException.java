package com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception;

import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;

/**
 * Exception occurs if number of errors in {@link CsvProcessingResult} more than threshold.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/22/17
 *
 * @author Ihar Suvorau
 */
public class ThresholdExceededException extends ValidationException {

    private final CsvProcessingResult processingResult;

    /**
     * Constructor.
     *
     * @param threshold        errors threshold
     * @param processingResult instance of {@link CsvProcessingResult}
     */
    public ThresholdExceededException(int threshold, CsvProcessingResult processingResult) {
        super(String.format("The file could not be uploaded. There are more than %s errors", threshold));
        this.processingResult = processingResult;
    }

    public CsvProcessingResult getProcessingResult() {
        return processingResult;
    }
}
