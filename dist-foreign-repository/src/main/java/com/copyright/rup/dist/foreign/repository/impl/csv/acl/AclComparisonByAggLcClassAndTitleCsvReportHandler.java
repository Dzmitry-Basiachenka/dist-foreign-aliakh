package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.AclComparisonByAggLcClassAndTitleReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        "Digital RH Name Period 1", "Print Net Amt Period 1", "Digital Net Amt Period 1", "Net Total Amount Period 1",
        "Print RH Account # Period 2", "Print RH Name Period 2", "Digital RH Account # Period 2",
        "Digital RH Name Period 2", "Print Net Amt Period 2", "Digital Net Amt Period 2", "Net Total Amount Period 2",
        "Delta", "% Change");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclComparisonByAggLcClassAndTitleCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
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
        beanProperties.add(getBeanBigDecimal(bean.getPrintNetAmountPreviousPeriod()));
        beanProperties.add(getBeanBigDecimal(bean.getDigitalNetAmountPreviousPeriod()));
        beanProperties.add(getBeanBigDecimal(bean.getTotalNetAmountPreviousPeriod()));
        beanProperties.add(getBeanPropertyAsString(bean.getPrintRhAccountNumberCurrentPeriod()));
        beanProperties.add(bean.getPrintRhNameCurrentPeriod());
        beanProperties.add(getBeanPropertyAsString(bean.getDigitalRhAccountNumberCurrentPeriod()));
        beanProperties.add(bean.getDigitalRhNameCurrentPeriod());
        beanProperties.add(getBeanBigDecimal(bean.getPrintNetAmountCurrentPeriod()));
        beanProperties.add(getBeanBigDecimal(bean.getDigitalNetAmountCurrentPeriod()));
        beanProperties.add(getBeanBigDecimal(bean.getTotalNetAmountCurrentPeriod()));
        beanProperties.add(getBeanBigDecimal(bean.getDelta()));
        beanProperties.add(getBeanPercent(bean.getChangePercent()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String getBeanPercent(Integer value) {
        return Objects.nonNull(value) ? String.format("%d%%", value) : "N/A";
    }
}
