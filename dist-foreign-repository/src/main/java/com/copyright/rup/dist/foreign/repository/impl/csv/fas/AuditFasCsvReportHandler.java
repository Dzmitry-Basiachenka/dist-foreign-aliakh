package com.copyright.rup.dist.foreign.repository.impl.csv.fas;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Writes usages into a {@link java.io.PipedOutputStream} connected to the {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/17/19
 *
 * @author Anton Azarenka
 */
public class AuditFasCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS = List.of("Detail ID", "Detail Status", "Product Family",
        "Usage Batch Name", "Payment Date", "RH Account #", "RH Name", "Payee Account #", "Payee Name",
        "Wr Wrk Inst", "System Title", "Reported Title", "Reported Standard Number", "Standard Number",
        "Standard Number Type", "Reported Value", "Gross Amt in USD", "Batch Amt in USD", "Service Fee %",
        "Scenario Name", "Check #", "Check Date", "Event ID", "Dist. Name", "Dist. Date", "Period Ending", "Comment");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public AuditFasCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
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
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(formatStringStartedWithZero(getReportedStandardNumber(bean)));
        beanProperties.add(formatStringStartedWithZero(bean.getStandardNumber()));
        beanProperties.add(bean.getStandardNumberType());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getReportedValue()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getBatchGrossAmount()));
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFee()));
        beanProperties.add(bean.getScenarioName());
        beanProperties.add(bean.getCheckNumber());
        beanProperties.add(getBeanOffsetDateTime(bean.getCheckDate()));
        beanProperties.add(bean.getCccEventId());
        beanProperties.add(bean.getDistributionName());
        beanProperties.add(getBeanOffsetDateTime(bean.getDistributionDate()));
        beanProperties.add(getBeanLocalDate(bean.getPeriodEndDate()));
        beanProperties.add(bean.getComment());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String getReportedStandardNumber(UsageDto bean) {
        return Objects.isNull(bean.getFasUsage())
            ? StringUtils.EMPTY
            : bean.getFasUsage().getReportedStandardNumber();
    }
}
