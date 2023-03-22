package com.copyright.rup.dist.foreign.repository.impl.csv.sal;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPool.SalFields;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Writes {@link FundPool} usages to {@link OutputStream}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/10/23
 *
 * @author Stepan Karakhanov
 */
public class SalFundPoolsExportHandler extends BaseCsvReportHandler<FundPool> {

    private static final List<String> HEADERS = List.of("Fund Pool Name", "Date Received", "Assessment Name",
        "Licensee Account #", "Licensee Name", "Gross Amount", "Service Fee %", "Item Bank Split %",
        "Grade K-5 Number of Students", "Grade 6-8 Number of Students", "Grade 9-12 Number of Students",
        "Item Bank Gross Amount", "Grade K-5 Gross Amount", "Grade 6-8 Gross Amount", "Grade 9-12 Gross Amount",
        "Created By", "Created Date");
    private final SimpleDateFormat formatter =
        new SimpleDateFormat(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG, Locale.getDefault());

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public SalFundPoolsExportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(FundPool bean) {
        List<String> beanProperties = new ArrayList<>();
        SalFields salFields = bean.getSalFields();
        beanProperties.add(bean.getName());
        beanProperties.add(getBeanLocalDate(salFields.getDateReceived()));
        beanProperties.add(salFields.getAssessmentName());
        beanProperties.add(getBeanPropertyAsString(salFields.getLicenseeAccountNumber()));
        beanProperties.add(salFields.getLicenseeName());
        beanProperties.add(getBeanBigDecimal(salFields.getGrossAmount()));
        beanProperties.add(getBeanServiceFeePercent(salFields.getServiceFee()));
        beanProperties.add(getBeanServiceFeePercent(salFields.getItemBankSplitPercent()));
        beanProperties.add(getBeanPropertyAsString(salFields.getGradeKto5NumberOfStudents()));
        beanProperties.add(getBeanPropertyAsString(salFields.getGrade6to8NumberOfStudents()));
        beanProperties.add(getBeanPropertyAsString(salFields.getGrade9to12NumberOfStudents()));
        beanProperties.add(getBeanBigDecimal(salFields.getItemBankGrossAmount()));
        beanProperties.add(getBeanBigDecimal(salFields.getGradeKto5GrossAmount()));
        beanProperties.add(getBeanBigDecimal(salFields.getGrade6to8GrossAmount()));
        beanProperties.add(getBeanBigDecimal(salFields.getGrade9to12GrossAmount()));
        beanProperties.add(bean.getCreateUser());
        beanProperties.add(formatter.format(bean.getCreateDate()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
