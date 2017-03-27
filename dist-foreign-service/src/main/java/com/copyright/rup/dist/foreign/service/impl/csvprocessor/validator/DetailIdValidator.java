package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.Set;

/**
 * The validator to check whether detail id doesn't exist in the database.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/22/17
 *
 * @author Aliaksei Pchelnikau
 */
public class DetailIdValidator implements IValidator<Usage> {

    private static final String ERROR_MESSAGE = "Detail ID: Detail with such ID already exists";
    private Set<Long> databaseDuplicates;

    /**
     * Constructor.
     *
     * @param databaseDuplicates set of duplicate details ids
     */
    public DetailIdValidator(Set<Long> databaseDuplicates) {
        this.databaseDuplicates = databaseDuplicates;
    }

    @Override
    public boolean isValid(Usage value) {
        return !databaseDuplicates.contains(value.getDetailId());
    }

    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
