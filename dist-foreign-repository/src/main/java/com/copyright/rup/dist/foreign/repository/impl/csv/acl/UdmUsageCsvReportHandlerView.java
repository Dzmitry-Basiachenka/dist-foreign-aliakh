package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Writes UDM usages into an {@link OutputStream} for View role.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/30/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmUsageCsvReportHandlerView extends BaseCsvReportHandler<UdmUsageDto> {

    private static final List<String> HEADERS = List.of("Detail ID", "Period", "Usage Origin", "Usage Detail ID",
        "Detail Status", "Assignee", "RH Account #", "RH Name", "Wr Wrk Inst", "Reported Title", "System Title",
        "Reported Standard Number", "Standard Number", "Reported Pub Type", "Publication Format", "Article",
        "Language", "Action Reason", "Comment", "Research URL", "Det LC ID", "Det LC Name", "Company ID",
        "Company Name", "Survey Respondent", "Survey Country", "Channel", "Usage Date", "Survey Start Date",
        "Survey End Date", "Annual Multiplier", "Statistical Multiplier", "Reported TOU", "Quantity",
        "Annualized Copies", "Ineligible Reason", "Load Date", "Updated By", "Updated Date");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public UdmUsageCsvReportHandlerView(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmUsageDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(bean.getId());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(bean.getUsageOrigin().name());
        beanProperties.add(bean.getOriginalDetailId());
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(bean.getAssignee());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getReportedTitle());
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(bean.getReportedStandardNumber());
        beanProperties.add(bean.getStandardNumber());
        beanProperties.add(bean.getReportedPubType());
        beanProperties.add(bean.getPubFormat());
        beanProperties.add(bean.getArticle());
        beanProperties.add(bean.getLanguage());
        beanProperties.add(Objects.nonNull(bean.getActionReason())
            ? bean.getActionReason().getReason()
            : StringUtils.EMPTY);
        beanProperties.add(bean.getComment());
        beanProperties.add(bean.getResearchUrl());
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClass().getId()));
        beanProperties.add(bean.getDetailLicenseeClass().getDescription());
        beanProperties.add(getBeanPropertyAsString(bean.getCompanyId()));
        beanProperties.add(bean.getCompanyName());
        beanProperties.add(bean.getSurveyRespondent());
        beanProperties.add(bean.getSurveyCountry());
        beanProperties.add(bean.getChannel().name());
        beanProperties.add(getBeanLocalDate(bean.getUsageDate()));
        beanProperties.add(getBeanLocalDate(bean.getSurveyStartDate()));
        beanProperties.add(getBeanLocalDate(bean.getSurveyEndDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getAnnualMultiplier()));
        beanProperties.add(getBeanBigDecimal(bean.getStatisticalMultiplier()));
        beanProperties.add(bean.getReportedTypeOfUse());
        beanProperties.add(getBeanPropertyAsString(bean.getQuantity()));
        beanProperties.add(getBeanBigDecimal(bean.getAnnualizedCopies()));
        beanProperties.add(Objects.nonNull(bean.getIneligibleReason())
            ? bean.getIneligibleReason().getReason()
            : StringUtils.EMPTY);
        beanProperties.add(convertDateToString(bean.getCreateDate()));
        beanProperties.add(bean.getUpdateUser());
        beanProperties.add(convertDateToString(bean.getUpdateDate()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
