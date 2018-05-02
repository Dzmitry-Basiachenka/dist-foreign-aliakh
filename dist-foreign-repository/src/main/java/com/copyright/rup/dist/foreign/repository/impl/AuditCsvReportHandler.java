package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes usages into a {@link PipedOutputStream} connected to the {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/18
 *
 * @author Aliaksandr Radkevich
 */
class AuditCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS = Arrays.asList("Detail ID", "Detail Status", "Product Family",
        "Usage Batch Name", "Payment Date", "RH Account #", "RH Name", "Payee Account #", "Payee Name",
        "Wr Wrk Inst", "Title", "Standard Number", "Amt in USD", "Service Fee %", "Scenario Name",
        "Check #", "Check Date", "Event ID", "Dist. Name", "Dist. Date", "Period Ending");

    /**
     * Constructor.
     *
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    AuditCsvReportHandler(PipedOutputStream pipedOutputStream) {
        super(pipedOutputStream);
    }

    @Override
    List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getPayeeAccountNumber()));
        beanProperties.add(bean.getPayeeName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(bean.getStandardNumber());
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFee()));
        beanProperties.add(bean.getScenarioName());
        beanProperties.add(bean.getCheckNumber());
        beanProperties.add(getBeanOffsetDateTime(bean.getCheckDate()));
        beanProperties.add(bean.getCccEventId());
        beanProperties.add(bean.getDistributionName());
        beanProperties.add(getBeanOffsetDateTime(bean.getDistributionDate()));
        beanProperties.add(getBeanLocalDate(bean.getPeriodEndDate()));
        return beanProperties;
    }

    @Override
    List<String> getBeanHeaders() {
        return HEADERS;
    }
}
