package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.common.exception.RupRuntimeException;

/**
 * Exception is thrown when parameter is bad.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/15/2019
 *
 * @author Aliaksanr Liakh
 */
public class BadRequestException extends RupRuntimeException {

    /**
     * Constructor.
     *
     * @param cause cause
     */
    public BadRequestException(Exception cause) {
        super(cause);
    }
}
