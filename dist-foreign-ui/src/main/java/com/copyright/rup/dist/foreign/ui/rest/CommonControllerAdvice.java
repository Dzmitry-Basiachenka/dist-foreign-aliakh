package com.copyright.rup.dist.foreign.ui.rest;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.service.api.BadRequestException;
import com.copyright.rup.dist.foreign.service.api.NotFoundException;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.Error;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
     * Handles {@link BadRequestException} exception.
     *
     * @param exception instance of {@link BadRequestException}
     * @return instance of {@link ResponseEntity}
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Error> handleBadRequestException(BadRequestException exception) {
        return handleExpectedException(exception, "BAD_REQUEST", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link MissingServletRequestParameterException} exception.
     *
     * @param exception instance of {@link MissingServletRequestParameterException}
     * @return instance of {@link MissingServletRequestParameterException}
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException exception) {
        return handleExpectedException(exception, "BAD_REQUEST", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link IllegalArgumentException} exception.
     *
     * @param exception instance of {@link IllegalArgumentException}
     * @return instance of {@link ResponseEntity}
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException exception) {
        return handleExpectedException(exception, "BAD_REQUEST", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link NoSuchElementException} exception.
     *
     * @param exception instance of {@link NoSuchElementException}
     * @return instance of {@link ResponseEntity}
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> handleNoSuchElementException(NoSuchElementException exception) {
        return handleExpectedException(exception, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link NotFoundException} exception.
     *
     * @param exception instance of {@link NotFoundException}
     * @return instance of {@link ResponseEntity}
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handleNoSuchElementException(NotFoundException exception) {
        return handleExpectedException(exception, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all the uncaught exceptions.
     *
     * @param exception instance of {@link Exception}
     * @return instance of {@link ResponseEntity}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception exception) {
        return handleUnexpectedException(exception, "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Error> handleExpectedException(Exception exception, String errorText, HttpStatus status) {
        return new ResponseEntity<>(buildError(exception, errorText), status);
    }

    private ResponseEntity<Error> handleUnexpectedException(Exception exception, String errorText, HttpStatus status) {
        Error error = buildError(exception, errorText);
        error.setStackTrace(ExceptionUtils.getStackTrace(exception));
        LOGGER.warn("Exception on REST call handling", exception);
        return new ResponseEntity<>(error, status);
    }

    private Error buildError(Exception exception, String errorText) {
        Error error = new Error();
        error.setError(errorText);
        error.setMessage(exception.getMessage());
        return error;
    }
}
