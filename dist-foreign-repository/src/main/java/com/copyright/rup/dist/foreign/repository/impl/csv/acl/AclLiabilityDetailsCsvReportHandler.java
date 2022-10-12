package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.domain.report.AclLiabilityDetailsReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclReportTotalAmountsDto;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Writes ACL Liability details into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/11/2022
 *
 * @author Anton Azarenka
 */
public class AclLiabilityDetailsCsvReportHandler extends BaseCsvReportHandler<AclLiabilityDetailsReportDto> {

    private static final List<String> HEADERS =
        Arrays.asList("RH Account #", "RH Name", "Work Title", "Wr Wrk Inst", "Scenario Name",
            "License Type", "Dist TOU", "Agg LC ID", "Agg LC Name", "Gross Amount", "Net Amount");
    private static final List<String> METADATA_HEADERS =
        ImmutableList.of("Report Name", "Report Period", "User", "Report Date", "Scenarios");
    private final DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG);

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public AclLiabilityDetailsCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public List<String> getBeanProperties(AclLiabilityDetailsReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(bean.getTitle());
        beanProperties.add(bean.getWrWrkInst());
        beanProperties.add(bean.getScenarioName());
        beanProperties.add(bean.getLicenseType());
        beanProperties.add(bean.getTypeOfUse());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getNetAmount()));
        return beanProperties;
    }

    /**
     * Writes reportInfo into the report.
     *
     * @param reportInfo instance of {@link AclReportTotalAmountsDto}
     */
    public void writeMetadata(AclCalculationReportsInfoDto reportInfo) {
        List<AclScenario> scenarios = reportInfo.getScenarios();
        writeStringRow(Arrays.asList(StringUtils.EMPTY, StringUtils.EMPTY));
        writeStringRow(METADATA_HEADERS);
        writeStringRow(Arrays.asList("ACL Liability Details Report", getBeanPropertyAsString(reportInfo.getPeriod()),
            reportInfo.getUser(), reportInfo.getReportDateTime().format(formatter),
            scenarios.stream().map(AclScenario::getName).collect(Collectors.joining(", "))));
    }

    /**
     * Writes total amounts of report.
     *
     * @param totalAmountsDto instance of {@link AclReportTotalAmountsDto}
     */
    public void writeTotals(AclReportTotalAmountsDto totalAmountsDto) {
        writeStringRow(
            Arrays.asList("Grand Total", StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                getBeanBigDecimal(totalAmountsDto.getGrossAmount()),
                getBeanBigDecimal(totalAmountsDto.getNetAmount())));
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
