package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

/**
 * The contract for validators. Used optional value approach (see {@link #isValid(Object)}).
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/24/17
 *
 * @param <T> the type of object for validation
 * @author Aliaksei Pchelnikau
 */
public interface IValidator<T> {

    /**
     * Performs validation of passed instance.
     *
     * @param value the instance to validate
     * @return the result of validation: {@code true} value passed validation otherwise {@code false}
     */
    boolean isValid(T value);

    /**
     * @return the error message.
     */
    String getErrorMessage();
}
