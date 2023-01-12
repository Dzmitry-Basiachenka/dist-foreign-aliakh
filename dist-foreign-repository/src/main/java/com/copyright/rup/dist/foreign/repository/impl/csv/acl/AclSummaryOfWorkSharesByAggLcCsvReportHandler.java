package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.domain.report.AclSummaryOfWorkSharesByAggLcReportDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write Summary of Work Shares by Aggregate Licensee Class Report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 10/05/2022
 *
 * @author Mikita Maistrenka
 */
public class AclSummaryOfWorkSharesByAggLcCsvReportHandler extends
    BaseCsvReportHandler<AclSummaryOfWorkSharesByAggLcReportDto> {

    private static final List<String> HEADERS =
        List.of("Agg LC ID", "Agg LC Name", "Total Shares Print", "Total Shares Digital");
    private static final List<String> METADATA_HEADERS =
        List.of("Report Name", "Scenario Name", "License Type", "Report Period", "User", "Report Date");
    private static final String REPORT_NAME = "Summary of Work Shares by Aggregate Licensee Class Report";

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public AclSummaryOfWorkSharesByAggLcCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * Writes metadata into the report.
     *
     * @param metadata ACL calculation report metadata
     */
    public void writeMetadata(AclCalculationReportsInfoDto metadata) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG);
        AclScenario scenario = metadata.getScenarios().get(0);
        writeStringRow(List.of(StringUtils.EMPTY, StringUtils.EMPTY));
        writeStringRow(METADATA_HEADERS);
        writeStringRow(List.of(REPORT_NAME, scenario.getName(), scenario.getLicenseType(),
            String.valueOf(scenario.getPeriodEndDate()), metadata.getUser(),
            metadata.getReportDateTime().format(formatter)));
    }

    @Override
    protected List<String> getBeanProperties(AclSummaryOfWorkSharesByAggLcReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(getBeanBigDecimal(bean.getTotalDetailSharePrint()));
        beanProperties.add(getBeanBigDecimal(bean.getTotalDetailShareDigital()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
