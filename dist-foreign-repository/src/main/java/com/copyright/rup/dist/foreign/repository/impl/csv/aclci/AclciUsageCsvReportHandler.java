package com.copyright.rup.dist.foreign.repository.impl.csv.aclci;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes ACLCI usages into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 04/13/2023
 *
 * @author Aliaksandr Liakh
 */
public class AclciUsageCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS =
        List.of("Detail ID", "Detail Status", "License Type", "Product Family", "Usage Batch Name", "Period End Date",
            "Coverage Period", "Licensee Account #", "Licensee Name", "RH Account #", "RH Name", "Wr Wrk Inst",
            "System Title", "Standard Number", "Standard Number Type", "Reported Title", "Reported Media Type",
            "Media Type Weight", "Reported Article or Chapter Title", "Reported Standard Number or Image ID Number",
            "Reported Author", "Reported Publisher", "Reported Publication Date", "Reported Grade", "Grade Group",
            "Comment");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclciUsageCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(bean.getAclciUsage().getLicenseType().name());
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanLocalDate(bean.getPeriodEndDate()));
        beanProperties.add(bean.getAclciUsage().getCoveragePeriod());
        beanProperties.add(getBeanPropertyAsString(bean.getAclciUsage().getLicenseeAccountNumber()));
        beanProperties.add(bean.getAclciUsage().getLicenseeName());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(formatStringStartedWithZero(bean.getStandardNumber()));
        beanProperties.add(bean.getStandardNumberType());
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(bean.getAclciUsage().getReportedMediaType());
        beanProperties.add(getBeanPropertyAsString(bean.getAclciUsage().getMediaTypeWeight()));
        beanProperties.add(bean.getAclciUsage().getReportedArticle());
        beanProperties.add(bean.getAclciUsage().getReportedStandardNumber());
        beanProperties.add(bean.getAclciUsage().getReportedAuthor());
        beanProperties.add(bean.getAclciUsage().getReportedPublisher());
        beanProperties.add(bean.getAclciUsage().getReportedPublicationDate());
        beanProperties.add(bean.getAclciUsage().getReportedGrade());
        beanProperties.add(bean.getAclciUsage().getGradeGroup().name());
        beanProperties.add(bean.getComment());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
