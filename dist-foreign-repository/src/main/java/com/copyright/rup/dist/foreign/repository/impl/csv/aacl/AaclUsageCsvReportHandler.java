package com.copyright.rup.dist.foreign.repository.impl.csv.aacl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes AACL usages into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 1/17/20
 *
 * @author Stanislau Rudak
 */
public class AaclUsageCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS =
        List.of("Detail ID", "Detail Status", "Product Family", "Usage Batch Name", "Period End Date",
            "RH Account #", "RH Name", "Wr Wrk Inst", "System Title", "Standard Number", "Standard Number Type",
            "Det LC ID", "Det LC Enrollment", "Det LC Discipline", "Pub Type", "Institution", "Usage Period",
            "Usage Source", "Number of Copies", "Number of Pages", "Right Limitation", "Comment");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AaclUsageCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanLocalDate(bean.getAaclUsage().getBatchPeriodEndDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(formatStringStartedWithZero(bean.getStandardNumber()));
        beanProperties.add(bean.getStandardNumberType());
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getDetailLicenseeClass().getId()));
        beanProperties.add(bean.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile());
        beanProperties.add(bean.getAaclUsage().getDetailLicenseeClass().getDiscipline());
        beanProperties.add(bean.getAaclUsage().getPublicationType().getName());
        beanProperties.add(bean.getAaclUsage().getInstitution());
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getUsageAge().getPeriod()));
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
