package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.domain.report.AclComparisonByAggLcClassAndTitleReportDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Writes ACL Comparison by Aggregate Licensee Class and Title Report into {@link OutputStream}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/27/2023
 *
 * @author Mikita Maistrenka
 */
public class AclComparisonByAggLcClassAndTitleCsvReportHandler extends
    BaseCsvReportHandler<AclComparisonByAggLcClassAndTitleReportDto> {

    private static final List<String> HEADERS = List.of("Agg LC ID", "Agg LC Name", "Wr Wrk Inst", "System Title",
        "Pub Type", "Print RH Account # Period 1", "Print RH Name Period 1", "Digital RH Account # Period 1",
        "Digital RH Name Period 1", "Print Net Amt Period 1", "Digital Net Amt Period 1", "Total Net Amt Period 1",
        "Print RH Account # Period 2", "Print RH Name Period 2", "Digital RH Account # Period 2",
        "Digital RH Name Period 2", "Print Net Amt Period 2", "Digital Net Amt Period 2", "Total Net Amt Period 2",
        "Delta", "% Change");
    private static final List<String> METADATA_HEADERS = List.of("Scenarios Period 1", "Scenarios Period 2");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclComparisonByAggLcClassAndTitleCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * Writes metadata into the report.
     *
     * @param metadata ACL calculation report metadata
     */
    public void writeMetadata(AclCalculationReportsInfoDto metadata) {
        String scenarioNamesPreviousPeriod = metadata.getPreviousScenarios().stream().map(AclScenario::getName)
            .sorted(String::compareToIgnoreCase).collect(Collectors.joining(", "));
        String scenarioNamesCurrentPeriod = metadata.getScenarios().stream().map(AclScenario::getName)
            .sorted(String::compareToIgnoreCase).collect(Collectors.joining(", "));
        writeStringRow(List.of(StringUtils.EMPTY, StringUtils.EMPTY));
        writeStringRow(METADATA_HEADERS);
        writeStringRow(List.of(scenarioNamesPreviousPeriod, scenarioNamesCurrentPeriod));
    }

    @Override
    protected List<String> getBeanProperties(AclComparisonByAggLcClassAndTitleReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(bean.getPubTypeName());
        beanProperties.add(getBeanPropertyAsString(bean.getPrintRhAccountNumberPreviousPeriod()));
        beanProperties.add(bean.getPrintRhNamePreviousPeriod());
        beanProperties.add(getBeanPropertyAsString(bean.getDigitalRhAccountNumberPreviousPeriod()));
        beanProperties.add(bean.getDigitalRhNamePreviousPeriod());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getPrintNetAmountPreviousPeriod()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getDigitalNetAmountPreviousPeriod()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getTotalNetAmountPreviousPeriod()));
        beanProperties.add(getBeanPropertyAsString(bean.getPrintRhAccountNumberCurrentPeriod()));
        beanProperties.add(bean.getPrintRhNameCurrentPeriod());
        beanProperties.add(getBeanPropertyAsString(bean.getDigitalRhAccountNumberCurrentPeriod()));
        beanProperties.add(bean.getDigitalRhNameCurrentPeriod());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getPrintNetAmountCurrentPeriod()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getDigitalNetAmountCurrentPeriod()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getTotalNetAmountCurrentPeriod()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getDelta()));
        beanProperties.add(getBeanPercent(bean.getChangePercent()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String getBeanPercent(BigDecimal value) {
        return Objects.nonNull(value) ? String.format("%.0f%%", value.setScale(0, RoundingMode.HALF_UP)) : "N/A";
    }
}
