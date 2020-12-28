package com.copyright.rup.dist.foreign.repository.impl.csv.sal;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes usages into a {@link java.io.PipedOutputStream} connected to the {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 28/12/2020
 *
 * @author Aliaksandr Liakh
 */
public class AuditSalCsvReportHandler extends BaseCsvReportHandler<UsageDto> {

    private static final List<String> HEADERS =
        Arrays.asList("Detail ID", "Detail Type", "Detail Status", "Product Family", "Usage Batch Name",
            "Period End Date", "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Wr Wrk Inst",
            "System Title", "Standard Number", "Standard Number Type", "Gross Amt in USD", "Service Fee Amount",
            "Net Amt in USD", "Scenario Name", "Check #", "Check Date", "Event ID", "Dist. Name", "Dist. Date",
            "Reported Work Portion ID", "Reported Standard Number", "Reported Title", "Reported Media Type",
            "Media Type Weight", "Reported Article or Chapter Title", "Reported Author", "Reported Publisher",
            "Reported Publication Date", "Reported Page Range", "Reported Vol/Number/Series",
            "Date of Scored Assessment", "Assessment Name", "Coverage Year", "Grade", "Grade Group", "Assessment Type",
            "Question Identifier", "States", "Number of Views", "Comment");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AuditSalCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getSalUsage().getDetailType().name());
        beanProperties.add(bean.getStatus().name());
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanLocalDate(bean.getSalUsage().getBatchPeriodEndDate()));
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
        beanProperties.add(bean.getScenarioName());
        beanProperties.add(bean.getCheckNumber());
        beanProperties.add(getBeanOffsetDateTime(bean.getCheckDate()));
        beanProperties.add(bean.getCccEventId());
        beanProperties.add(bean.getDistributionName());
        beanProperties.add(getBeanOffsetDateTime(bean.getDistributionDate()));
        beanProperties.add(bean.getSalUsage().getReportedWorkPortionId());
        beanProperties.add(bean.getSalUsage().getReportedStandardNumber());
        beanProperties.add(bean.getWorkTitle());
        beanProperties.add(bean.getSalUsage().getReportedMediaType());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getSalUsage().getMediaTypeWeight()));
        beanProperties.add(bean.getSalUsage().getReportedArticle());
        beanProperties.add(bean.getSalUsage().getReportedAuthor());
        beanProperties.add(bean.getSalUsage().getReportedPublisher());
        beanProperties.add(bean.getSalUsage().getReportedPublicationDate());
        beanProperties.add(bean.getSalUsage().getReportedPageRange());
        beanProperties.add(bean.getSalUsage().getReportedVolNumberSeries());
        beanProperties.add(getBeanLocalDate(bean.getSalUsage().getScoredAssessmentDate()));
        beanProperties.add(bean.getSalUsage().getAssessmentName());
        beanProperties.add(bean.getSalUsage().getCoverageYear());
        beanProperties.add(bean.getSalUsage().getGrade());
        beanProperties.add(bean.getSalUsage().getGradeGroup().name());
        beanProperties.add(bean.getSalUsage().getAssessmentType());
        beanProperties.add(bean.getSalUsage().getQuestionIdentifier());
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
