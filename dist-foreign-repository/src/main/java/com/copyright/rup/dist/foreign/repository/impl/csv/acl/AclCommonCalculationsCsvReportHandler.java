package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.domain.report.AclReportTotalAmountsDto;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents common logic to write ACL Calculations reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/18/2022
 *
 * @param <T> the type of object that will be written into report
 * @author Dzmitry Basiachenka
 */
public abstract class AclCommonCalculationsCsvReportHandler<T extends StoredEntity<?>> extends BaseCsvReportHandler<T> {

    private static final List<String> METADATA_HEADERS =
        ImmutableList.of("Report Name", "Report Period", "User", "Report Date", "Scenarios");
    private final String reportName;
    private final DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG);

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     * @param reportName   report name
     */
    public AclCommonCalculationsCsvReportHandler(OutputStream outputStream, String reportName) {
        super(outputStream);
        this.reportName = reportName;
    }

    /**
     * Writes metadata into the report.
     *
     * @param metadata ACL calculation report metadata
     */
    public void writeMetadata(AclCalculationReportsInfoDto metadata) {
        writeStringRow(Arrays.asList(StringUtils.EMPTY, StringUtils.EMPTY));
        writeStringRow(METADATA_HEADERS);
        writeStringRow(Arrays.asList(reportName, String.valueOf(metadata.getPeriod()), metadata.getUser(),
            metadata.getReportDateTime().format(formatter),
            metadata.getScenarios().stream().map(AclScenario::getName).collect(Collectors.joining(", "))));
    }

    /**
     * Writes total amounts row.
     *
     * @param totalAmountsDto instance of {@link AclReportTotalAmountsDto}
     */
    public abstract void writeTotals(AclReportTotalAmountsDto totalAmountsDto);
}
