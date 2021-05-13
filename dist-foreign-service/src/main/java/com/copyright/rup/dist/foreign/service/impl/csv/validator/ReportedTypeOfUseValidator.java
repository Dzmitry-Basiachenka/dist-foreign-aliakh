package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.UdmUsage;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * The validator to check that Reported Type Of Use should be blank in case Reported Title equals to 'None',
 * otherwise it should be not blank.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/13/21
 *
 * @author Anton Azarenka
 */
public class ReportedTypeOfUseValidator implements DistCsvProcessor.IValidator<UdmUsage> {

    private static final String REPORTED_TITLE_NONE = "None";

    @Override
    public boolean isValid(UdmUsage udmUsage) {
        String reportedTitle = Objects.requireNonNull(udmUsage).getReportedTitle();
        String reportedTypeOfUse = Objects.requireNonNull(udmUsage).getReportedTypeOfUse();
        return StringUtils.isNotBlank(reportedTitle) && StringUtils.equalsIgnoreCase(REPORTED_TITLE_NONE, reportedTitle)
            ? StringUtils.isBlank(reportedTypeOfUse)
            : StringUtils.isNotBlank(reportedTypeOfUse);
    }

    @Override
    public String getErrorMessage() {
        return "Reported TOU should be blank if Reported Title equals to 'None', otherwise it should be filled in";
    }
}
