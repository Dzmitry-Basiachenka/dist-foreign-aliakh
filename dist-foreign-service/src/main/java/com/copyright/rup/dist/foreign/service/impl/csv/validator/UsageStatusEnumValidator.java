package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor;

import java.util.Objects;

/**
 * The validator to check string value of {@link UsageStatusEnum}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class UsageStatusEnumValidator implements DistCsvProcessor.IValidator<String> {

    private final UsageStatusEnum requiredStatus;

    /**
     * Constructor.
     *
     * @param requiredStatus the required status.
     */
    public UsageStatusEnumValidator(UsageStatusEnum requiredStatus) {
        this.requiredStatus = Objects.requireNonNull(requiredStatus);
    }

    @Override
    public boolean isValid(String value) {
        try {
            return requiredStatus == UsageStatusEnum.valueOf(value);
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public String getErrorMessage() {
        return String.format("Usage status should be '%s'", requiredStatus);
    }
}
