package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The validator to check that Reported Type Of Use should be blank in case Reported Title equals to 'None',
 * otherwise it should be not blank and that Reported Type Of Use exists in the system.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/13/21
 *
 * @author Anton Azarenka
 */
public class ReportedTypeOfUseValidator implements DistCsvProcessor.IValidator<UdmUsage> {

    private static final String NONE_TITLE_ERROR_MESSAGE = "Reported TOU should be blank if Reported Title equals to " +
        "'None', otherwise it should be filled in";
    private static final String REPORTED_TITLE_NONE = "None";

    private final Set<String> udmTous = new HashSet<>();
    private String errorMessage;

    /**
     * Constructor.
     *
     * @param udmTypeOfUseService instance of {@link IUdmTypeOfUseService}
     */
    public ReportedTypeOfUseValidator(IUdmTypeOfUseService udmTypeOfUseService) {
        this.udmTous.addAll(udmTypeOfUseService.getAllUdmTous());
    }

    @Override
    public boolean isValid(UdmUsage udmUsage) {
        String reportedTypeOfUse = Objects.requireNonNull(udmUsage).getReportedTypeOfUse();
        boolean reportedTouWithNoneReportedTitleValid = isReportedTouWithNoneReportedTitleValid(udmUsage);
        if (reportedTouWithNoneReportedTitleValid && StringUtils.isNotBlank(reportedTypeOfUse)) {
            errorMessage = "Reported TOU doesn't exist in the system";
            return udmTous.contains(reportedTypeOfUse);
        }
        errorMessage = NONE_TITLE_ERROR_MESSAGE;
        return reportedTouWithNoneReportedTitleValid;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    private boolean isReportedTouWithNoneReportedTitleValid(UdmUsage udmUsage) {
        String reportedTitle = udmUsage.getReportedTitle();
        String reportedTypeOfUse = udmUsage.getReportedTypeOfUse();
        return StringUtils.isNotBlank(reportedTitle) && StringUtils.equalsIgnoreCase(REPORTED_TITLE_NONE, reportedTitle)
            ? StringUtils.isBlank(reportedTypeOfUse)
            : StringUtils.isNotBlank(reportedTypeOfUse);
    }
}
