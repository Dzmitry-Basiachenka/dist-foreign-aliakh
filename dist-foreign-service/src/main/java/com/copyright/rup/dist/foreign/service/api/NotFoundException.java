package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.common.exception.RupRuntimeException;

/**
 * Exception is thrown when expected object is not found.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/11/2019
 *
 * @author Aliaksanr Liakh
 */
public class NotFoundException extends RupRuntimeException {

    private static final long serialVersionUID = -8717717292340527872L;

    /**
     * Constructor.
     *
     * @param message error message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
