package com.copyright.rup.dist.foreign.repository.impl.csv.sal;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.SalFundPoolReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes {@link SalFundPoolReportDto} usages to {@link OutputStream}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/10/23
 *
 * @author Stepan Karakhanov
 */
public class SalFundPoolsExportHandler extends BaseCsvReportHandler<SalFundPoolReportDto> {

    private static final List<String> HEADERS = List.of("Fund Pool Name", "Date Received", "Assessment Name",
        "Licensee Account #", "Licensee Name", "Gross Amount", "Service Fee %", "Item Bank Split %",
        "Grade K-5 Number of Students", "Grade 6-8 Number of Students", "Grade 9-12 Number of Students",
        "Item Bank Gross Amount", "Grade K-5 Gross Amount", "Grade 6-8 Gross Amount", "Grade 9-12 Gross Amount");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public SalFundPoolsExportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(SalFundPoolReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getFundPoolName());
        beanProperties.add(getBeanLocalDate(bean.getDateReceived()));
        beanProperties.add(bean.getAssessmentName());
        beanProperties.add(getBeanPropertyAsString(bean.getLicenseeAccountNumber()));
        beanProperties.add(bean.getLicenseeName());
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanServiceFeePercent(bean.getServiceFee()));
        beanProperties.add(getBeanServiceFeePercent(bean.getItemBankSplitPercent()));
        beanProperties.add(getBeanPropertyAsString(bean.getGradeKto5NumberOfStudents()));
        beanProperties.add(getBeanPropertyAsString(bean.getGrade6to8NumberOfStudents()));
        beanProperties.add(getBeanPropertyAsString(bean.getGrade9to12NumberOfStudents()));
        beanProperties.add(getBeanBigDecimal(bean.getItemBankGrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getGradeKto5GrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getGrade6to8GrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getGrade9to12GrossAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
