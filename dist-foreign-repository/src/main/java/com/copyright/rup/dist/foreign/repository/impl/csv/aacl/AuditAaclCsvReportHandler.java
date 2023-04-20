package com.copyright.rup.dist.foreign.repository.impl.csv.aacl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes usages into a {@link java.io.PipedOutputStream} connected to the {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/20
 *
 * @author Anton Azarenka
 */
public class AuditAaclCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS =
        List.of("Detail ID", "Baseline ID", "Detail Status", "Product Family", "Usage Batch Name",
            "Period End Date", "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Wr Wrk Inst",
            "System Title", "Standard Number", "Standard Number Type", "Gross Amt in USD", "Service Fee Amount",
            "Net Amt in USD", "Scenario Name", "Check #", "Check Date", "Event ID", "Dist. Name", "Dist. Date",
            "Det LC ID", "Det LC Enrollment", "Det LC Discipline", "Pub Type", "Usage Period", "Usage Source",
            "Comment");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AuditAaclCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(bean.getId());
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getBaselineId()));
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanLocalDate(bean.getAaclUsage().getBatchPeriodEndDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getPayeeAccountNumber()));
        beanProperties.add(bean.getPayeeName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(formatStringStartedWithZero(bean.getStandardNumber()));
        beanProperties.add(bean.getStandardNumberType());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmount()));
        beanProperties.add(bean.getScenarioName());
        beanProperties.add(bean.getCheckNumber());
        beanProperties.add(getBeanOffsetDateTime(bean.getCheckDate()));
        beanProperties.add(bean.getCccEventId());
        beanProperties.add(bean.getDistributionName());
        beanProperties.add(getBeanOffsetDateTime(bean.getDistributionDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getDetailLicenseeClass().getId()));
        beanProperties.add(bean.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile());
        beanProperties.add(bean.getAaclUsage().getDetailLicenseeClass().getDiscipline());
        beanProperties.add(bean.getAaclUsage().getPublicationType().getName());
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getUsageAge().getPeriod()));
        beanProperties.add(bean.getAaclUsage().getUsageSource());
        beanProperties.add(bean.getComment());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
