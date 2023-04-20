package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.domain.report.AclWorkSharesByAggLcReportDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write Work Shares by Aggregate Licensee Class Report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 12/08/2022
 *
 * @author Ihar Suvorau
 */
public class AclWorkSharesByAggLcCsvReportHandler extends BaseCsvReportHandler<AclWorkSharesByAggLcReportDto> {

    private static final List<String> HEADERS =
        List.of("Agg LC ID", "Agg LC Name", "WrWrkInst", "Work Title", "Print RH Account #", "Print RH Name",
            "Digital RH Account #", "Digital RH Name", "Print Total Share", "Print Value Share", "Print Volume Share",
            "Print Net Amount", "Digital Total Share", "Digital Value Share", "Digital Volume Share",
            "Digital Net Amount", "Net Amount Total");
    private static final List<String> METADATA_HEADERS =
        List.of("Report Name", "Scenario Name", "License Type", "Report Period", "User", "Report Date");
    private static final String REPORT_NAME = "Work Shares by Aggregate Licensee Class Report";

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public AclWorkSharesByAggLcCsvReportHandler(OutputStream outputStream) {
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
    protected List<String> getBeanProperties(AclWorkSharesByAggLcReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getTitle());
        beanProperties.add(getBeanPropertyAsString(bean.getPrintRhAccountNumber()));
        beanProperties.add(bean.getPrintRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getDigitalRhAccountNumber()));
        beanProperties.add(bean.getDigitalRhName());
        beanProperties.add(getBeanBigDecimal(bean.getPrintTotalShare()));
        beanProperties.add(getBeanBigDecimal(bean.getPrintValueShare()));
        beanProperties.add(getBeanBigDecimal(bean.getPrintVolumeShare()));
        beanProperties.add(getBeanBigDecimal(bean.getPrintNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getDigitalTotalShare()));
        beanProperties.add(getBeanBigDecimal(bean.getDigitalValueShare()));
        beanProperties.add(getBeanBigDecimal(bean.getDigitalVolumeShare()));
        beanProperties.add(getBeanBigDecimal(bean.getDigitalNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getTotalNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
