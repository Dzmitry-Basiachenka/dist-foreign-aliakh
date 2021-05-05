package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.UdmUsage;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * The validator to check that Quantity equals to zero in case Reported Title equals to 'None',
 * otherwise it is greater than zero.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/03/21
 *
 * @author Uladzislau Shalamitski
 */
public class QuantityValidator implements DistCsvProcessor.IValidator<UdmUsage> {

    private static final String REPORTED_TITLE_NONE = "None";

    @Override
    public boolean isValid(UdmUsage udmUsage) {
        String reportedTitle = Objects.requireNonNull(udmUsage).getReportedTitle();
        return StringUtils.isNotBlank(reportedTitle) && StringUtils.equalsIgnoreCase(REPORTED_TITLE_NONE, reportedTitle)
            ? 0 == udmUsage.getQuantity()
            : 0 < udmUsage.getQuantity();
    }

    @Override
    public String getErrorMessage() {
        return "Quantity should be zero if Reported Title equals to 'None', otherwise it should be greater than zero";
    }
}
