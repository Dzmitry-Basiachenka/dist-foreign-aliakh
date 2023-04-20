package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UdmSurveyLicenseeReportDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link BaseCsvReportHandler} to write UDM Survey Licensee Report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/05/2022
 *
 * @author Anton Azarenka
 */
public class UdmSurveyLicenseeReportHandler extends BaseCsvReportHandler<UdmSurveyLicenseeReportDto> {

    private static final List<String> HEADERS =
        List.of("Period", "Channel ", "Usage Origin", "Survey Start Date", "Company ID", "Company Name",
            "Det LC ID", "Det LC Name", "Agg LC", "Agg LC Name", "Total Details", "Usable Details", "% Usable Data",
            "# of registered users", "# of rows reported by registered users",
            "# of usable rows reported by registered users", "% Usable from registered users",
            "# of rows reported by unregistered users", "# of usable rows reported by unregistered users",
            "% Usable from unregistered users");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public UdmSurveyLicenseeReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmSurveyLicenseeReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(bean.getChannel());
        beanProperties.add(bean.getUsageOrigin());
        beanProperties.add(getBeanLocalDate(bean.getSurveyStartDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getCompanyId()));
        beanProperties.add(bean.getCompanyName());
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClassId()));
        beanProperties.add(bean.getDetailLicenseeClassName());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateClassId()));
        beanProperties.add(bean.getAggregateClassName());
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfTotalRows()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfUsableRows()));
        beanProperties.add(getBeanPercent(bean.getPercentUsable()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfRegisteredUsers()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfRowsByRegisteredUsers()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfUsableRowsByRegisteredUsers()));
        beanProperties.add(getBeanPercent(bean.getPercentUsableFromRegisteredUsers()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfRowsByUnregisteredUsers()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfUsableRowsByUnregisteredUsers()));
        beanProperties.add(getBeanPercent(bean.getPercentUsableFromUnregisteredUsers()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String getBeanPercent(Integer value) {
        return Objects.nonNull(value) ? String.format("%d%%", value) : StringUtils.EMPTY;
    }
}
