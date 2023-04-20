package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UdmSurveyDashboardReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write UDM Survey Dashboard Report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Anton Azarenka
 */
public class UdmSurveyDashboardCsvReportHandler extends BaseCsvReportHandler<UdmSurveyDashboardReportDto> {

    private static final List<String> HEADERS =
        List.of("Usage Origin", "Channel ", "Period", "Company ID", "Company Name", "Det LC ID", "Det LC Name",
            "Survey Start Date", "Unadjusted Unique Users", "Adjusted Unique Users", "# Details Loaded",
            "# Usable Details Loaded", "# Surveys", "Period Month Order");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public UdmSurveyDashboardCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmSurveyDashboardReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(bean.getUsageOrigin());
        beanProperties.add(bean.getChannel());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(getBeanPropertyAsString(bean.getCompanyId()));
        beanProperties.add(bean.getCompanyName());
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClassId()));
        beanProperties.add(bean.getDetailLicenseeClassName());
        beanProperties.add(getBeanLocalDate(bean.getSurveyStartDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getUnadjustedUniqueUsers()));
        beanProperties.add(getBeanPropertyAsString(bean.getAdjustedUniqueUsers()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfLoadedDetails()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfUsableLoadedDetails()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfSurveys()));
        beanProperties.add(getBeanPropertyAsString(bean.getPeriodMonthOrder()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
