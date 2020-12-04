package com.copyright.rup.dist.foreign.repository.impl.csv.sal;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes SAL scenario usages into a {@link java.io.PipedOutputStream} connected to the
 * {@link java.io.PipedInputStream}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/13/20
 *
 * @author Anton Azarenka
 */
public class SalScenarioUsagesCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS =
        Arrays.asList("Detail ID", "Detail Status", "Detail Type", "Product Family", "Usage Batch Name",
            "Period End Date", "Licensee Account #", "Licensee Name", "RH Account #", "RH Name", "Payee Account #",
            "Payee Name", "Wr Wrk Inst", "System Title", "Standard Number", "Standard Number Type", "Gross Amt in USD",
            "Service Fee Amount", "Net Amt in USD", "Service Fee %", "Assessment Name", "Assessment Type",
            "Date of Scored Assessment", "Reported Work Portion ID", "Reported Title",
            "Reported Article or Chapter Title", "Reported Standard Number", "Reported Author", "Reported Publisher",
            "Reported Publication Date", "Reported Page Range", "Reported Vol/Number/Series", "Reported Media Type",
            "Coverage Year", "Question Identifier", "Grade", "Grade Group", "States", "Number of Views", "Comment");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public SalScenarioUsagesCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(getBeanPropertyAsString(bean.getSalUsage().getDetailType()));
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanLocalDate(bean.getSalUsage().getBatchPeriodEndDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getSalUsage().getLicenseeAccountNumber()));
        beanProperties.add(bean.getSalUsage().getLicenseeName());
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
        beanProperties.add(bean.getSalUsage().getAssessmentName());
        beanProperties.add(bean.getSalUsage().getAssessmentType());
        beanProperties.add(getBeanLocalDate(bean.getSalUsage().getScoredAssessmentDate()));
        beanProperties.add(bean.getSalUsage().getReportedWorkPortionId());
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(bean.getSalUsage().getReportedArticle());
        beanProperties.add(bean.getSalUsage().getReportedStandardNumber());
        beanProperties.add(bean.getSalUsage().getReportedAuthor());
        beanProperties.add(bean.getSalUsage().getReportedPublisher());
        beanProperties.add(bean.getSalUsage().getReportedPublicationDate());
        beanProperties.add(bean.getSalUsage().getReportedPageRange());
        beanProperties.add(bean.getSalUsage().getReportedVolNumberSeries());
        beanProperties.add(bean.getSalUsage().getReportedMediaType());
        beanProperties.add(bean.getSalUsage().getCoverageYear());
        beanProperties.add(bean.getSalUsage().getQuestionIdentifier());
        beanProperties.add(bean.getSalUsage().getGrade());
        beanProperties.add(getBeanPropertyAsString(bean.getSalUsage().getGradeGroup()));
        beanProperties.add(bean.getSalUsage().getStates());
        beanProperties.add(getBeanPropertyAsString(bean.getSalUsage().getNumberOfViews()));
        beanProperties.add(bean.getComment());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
