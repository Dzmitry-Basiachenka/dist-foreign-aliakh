package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.UdmUsage;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * The validator checks that Wr Wrk Inst, Reported Title and Reported Standard # are not null or empty at the same time.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/24/2021
 *
 * @author Ihar Suvorau
 */
public class UdmWorkInfoValidator implements DistCsvProcessor.IValidator<UdmUsage> {

    @Override
    public boolean isValid(UdmUsage usage) {
        return Objects.nonNull(Objects.requireNonNull(usage).getWrWrkInst())
            || StringUtils.isNotEmpty(usage.getReportedTitle())
            || StringUtils.isNotEmpty(usage.getReportedStandardNumber());
    }

    @Override
    public String getErrorMessage() {
        return "No Work information found";
    }
}
