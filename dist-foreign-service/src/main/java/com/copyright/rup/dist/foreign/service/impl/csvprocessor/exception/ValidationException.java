package com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception;

/**
 * Represents validation exception.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksei Pchelnikau
 */
public class ValidationException extends Exception {

    /**
     * Constructor.
     *
     * @param message validation message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message validation message
     * @param cause   validation cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @return the message in html format, the same as the pure message by default.
     */
    public String getHtmlMessage() {
        return getMessage();
    }
}
