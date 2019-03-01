package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.exception.RupRuntimeException;

/**
 * Represents exceptional case when usage is in inconsistent state.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/28/2019
 *
 * @author Uladzislau Shalamitski
 */
public class InconsistentUsageStateException extends RupRuntimeException {

    /**
     * Constructor.
     */
    public InconsistentUsageStateException() {
        super("Usage is in inconsistent state");
    }
}
