package com.copyright.rup.dist.foreign.repository.impl.csv.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes baseline usages into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/19/2020
 *
 * @author Ihar Suvorau
 */
public class AaclBaselineUsagesCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS =
        List.of("Detail ID", "Wr Wrk Inst", "Usage Period", "Usage Source", "Number of Copies", "Number of Pages",
            "Det LC ID", "Det LC Enrollment", "Det LC Discipline", "Pub Type", "Pub Type Weight", "Historical Pub Type",
            "Institution", "Comment", "Update User", "Update Date");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AaclBaselineUsagesCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getAaclUsage().getBaselineId());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getUsageAge().getPeriod()));
        beanProperties.add(bean.getAaclUsage().getUsageSource());
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfCopies()));
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getNumberOfPages()));
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getDetailLicenseeClass().getId()));
        beanProperties.add(bean.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile());
        beanProperties.add(bean.getAaclUsage().getDetailLicenseeClass().getDiscipline());
        beanProperties.add(bean.getAaclUsage().getPublicationType().getName());
        beanProperties.add(getBeanPropertyAsString(bean.getAaclUsage().getPublicationType().getWeight()));
        beanProperties.add(bean.getAaclUsage().getOriginalPublicationType());
        beanProperties.add(bean.getAaclUsage().getInstitution());
        beanProperties.add(bean.getComment());
        beanProperties.add(bean.getUpdateUser());
        beanProperties.add(DateFormatUtils.format(bean.getUpdateDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
