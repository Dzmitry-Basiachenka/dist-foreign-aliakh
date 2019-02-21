package com.copyright.rup.dist.foreign.ui.rest;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.Error;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Common errors handler for REST services.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/06/2019
 *
 * @author Aliaksanr Liakh
 */
@ControllerAdvice
@Order
@SuppressWarnings("unused")
public class CommonControllerAdvice {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    /**
     * Handles {@link NoSuchElementException} exception.
     *
     * @param exception instance of {@link NoSuchElementException}
     * @return instance of {@link ResponseEntity}
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> handleNoSuchElementException(NoSuchElementException exception) {
        return handleException(exception, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all the uncaught exceptions.
     *
     * @param exception instance of {@link Exception}
     * @return instance of {@link ResponseEntity}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception exception) {
        return handleException(exception, "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Error> handleException(Exception exception, String errorText, HttpStatus status) {
        Error error = new Error();
        error.setError(errorText);
        String message = exception.getMessage();
        error.setMessage(message);
        error.setStackTrace(ExceptionUtils.getStackTrace(exception));
        LOGGER.warn(message, exception);
        return new ResponseEntity<>(error, status);
    }
}
