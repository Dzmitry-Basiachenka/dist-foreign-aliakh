package com.copyright.rup.dist.foreign.repository.impl.csv.sal;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.SalHistoricalItemBankDetailsReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BaseCsvReportHandler} to write SAL Historical Item Bank Details Report.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 11/26/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalHistoricalItemBankDetailsReportHandler
    extends BaseCsvReportHandler<SalHistoricalItemBankDetailsReportDto> {

    private static final List<String> HEADERS = List.of("Detail ID", "Item Bank Name", "Period End Date",
        "Licensee Account #", "Licensee Name", "RH Account #", "RH Name", "Wr Wrk Inst", "System Title",
        "Standard Number", "Standard Number Type", "Assessment Name", "Reported Work Portion ID", "Reported Title",
        "Reported Article or Chapter", "Reported Standard Number or Image ID Number", "Reported Media Type",
        "Coverage Year", "Grade");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public SalHistoricalItemBankDetailsReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(SalHistoricalItemBankDetailsReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getItemBankName());
        beanProperties.add(getBeanLocalDate(bean.getPeriodEndDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getLicenseeAccountNumber()));
        beanProperties.add(bean.getLicenseeName());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(bean.getWrWrkInst());
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(bean.getStandardNumber());
        beanProperties.add(bean.getStandardNumberType());
        beanProperties.add(bean.getAssessmentName());
        beanProperties.add(bean.getReportedWorkPortionId());
        beanProperties.add(bean.getReportedTitle());
        beanProperties.add(bean.getReportedArticle());
        beanProperties.add(bean.getReportedStandardNumber());
        beanProperties.add(bean.getReportedMediaType());
        beanProperties.add(bean.getCoverageYear());
        beanProperties.add(bean.getGrade());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
