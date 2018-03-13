package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.Set;

/**
 * The validator to check whether Usage with such detail id exists or not.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/22/17
 *
 * @author Aliaksei Pchelnikau
 */
public class DuplicateDetailIdValidator implements IValidator<Usage> {

    private static final String ERROR_MESSAGE = "Detail ID: Detail with such ID already exists";
    private final Set<Long> duplicateDetailsIds;

    /**
     * Constructor.
     *
     * @param duplicateDetailsIds set of duplicate details ids
     */
    public DuplicateDetailIdValidator(Set<Long> duplicateDetailsIds) {
        this.duplicateDetailsIds = duplicateDetailsIds;
    }

    @Override
    public boolean isValid(Usage value) {
        return !duplicateDetailsIds.contains(value.getDetailId());
    }

    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
