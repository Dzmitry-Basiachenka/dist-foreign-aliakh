package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.Usage;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * The validator to check that Work Title or Standard Number should not be null or empty when Wr Wrk Inst is not null.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/04/2018
 *
 * @author Aliaksandr Liakh
 */
public class WorkTitleStandardNumberValidator implements DistCsvProcessor.IValidator<Usage> {

    @Override
    public boolean isValid(Usage usage) {
        Objects.requireNonNull(usage);
        return Objects.isNull(usage.getWrWrkInst())
            || StringUtils.isNotEmpty(usage.getWorkTitle())
            || StringUtils.isNotEmpty(usage.getStandardNumber());
    }

    @Override
    public String getErrorMessage() {
        return "Work Title or Standard Number: Both fields values cannot be null or empty when Wr Wrk Inst is not null";
    }
}
