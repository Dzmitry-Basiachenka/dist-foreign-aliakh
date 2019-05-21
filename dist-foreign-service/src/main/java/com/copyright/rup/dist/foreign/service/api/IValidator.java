package com.copyright.rup.dist.foreign.service.api;

/**
 * Interface for liability validator.
 * Performs validation of liabilities and throws
 * {@link com.copyright.rup.dist.foreign.service.impl.usage.paid.ValidationException} if validation fails.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 12/02/14
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 * @param <T> class of the value to be validated
 */
// TODO move to dist-common
public interface IValidator<T> {

    /**
     * Checks specified value for validity. Throws
     * {@link com.copyright.rup.dist.foreign.service.impl.usage.paid.ValidationException} if validation fails.
     *
     * @param value the value collection.
     */
    void validate(T value);
}
