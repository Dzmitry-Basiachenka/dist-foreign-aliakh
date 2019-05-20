package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import com.copyright.rup.common.exception.RupRuntimeException;

/**
 * Represents validation exception.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/12/17
 *
 * @author Aliaksei Pchelnikau
 */
// TODO move to dist-common
public class ValidationException extends RupRuntimeException {

    /**
     * Constructs a new ValidationException with specified message.
     *
     * @param message message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of ValidationException with specified message and cause.
     *
     * @param message the exception message
     * @param cause   the exception cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
