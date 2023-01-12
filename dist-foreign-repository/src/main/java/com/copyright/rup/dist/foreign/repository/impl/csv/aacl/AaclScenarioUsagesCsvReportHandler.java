package com.copyright.rup.dist.foreign.repository.impl.csv.aacl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes AACL scenario usages into a {@link java.io.PipedOutputStream} connected to the
 * {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 04/23/2020
 *
 * @author Anton Azarenka
 */
public class AaclScenarioUsagesCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS =
        List.of("Detail ID", "Product Family", "Usage Batch Name", "Period End Date", "RH Account #", "RH Name",
            "Payee Account #", "Payee Name", "Wr Wrk Inst", "System Title", "Standard Number", "Standard Number Type",
            "Gross Amt in USD", "Service Fee Amount", "Net Amt in USD", "Service Fee %", "Det LC ID",
            "Det LC Enrollment", "Det LC Discipline", "Agg LC ID", "Agg LC Enrollment", "Agg LC Discipline", "Pub Type",
            "Pub Type Weight", "Historical Pub Type", "Institution", "Usage Period", "Usage Age Weight", "Usage Source",
            "Number of Copies", "Number of Pages", "Right Limitation", "Comment");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public AaclScenarioUsagesCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
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
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFee()));
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getDetailLicenseeClass().getId()));
        beanProperties.add(bean.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile());
        beanProperties.add(bean.getAaclUsage().getDetailLicenseeClass().getDiscipline());
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getAggregateLicenseeClass().getId()));
        beanProperties.add(bean.getAaclUsage().getAggregateLicenseeClass().getEnrollmentProfile());
        beanProperties.add(bean.getAaclUsage().getAggregateLicenseeClass().getDiscipline());
        beanProperties.add(bean.getAaclUsage().getPublicationType().getName());
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getPublicationType().getWeight()));
        beanProperties.add(bean.getAaclUsage().getOriginalPublicationType());
        beanProperties.add(bean.getAaclUsage().getInstitution());
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getUsageAge().getPeriod()));
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getUsageAge().getWeight()));
        beanProperties.add(bean.getAaclUsage().getUsageSource());
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfCopies()));
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getNumberOfPages()));
        beanProperties.add(bean.getAaclUsage().getRightLimitation());
        beanProperties.add(bean.getComment());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
