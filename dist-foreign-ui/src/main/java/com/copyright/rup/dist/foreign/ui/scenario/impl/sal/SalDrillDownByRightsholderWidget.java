package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonDrillDownByRightsholderWidget;

/**
 * Implementation of {@link ISalDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/20
 *
 * @author Anton Azarenka
 */
public class SalDrillDownByRightsholderWidget extends CommonDrillDownByRightsholderWidget implements
    ISalDrillDownByRightsholderWidget {

    @Override
    protected void addColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(usageDto -> usageDto.getSalUsage().getDetailType(), "table.column.detail_type", "detailType", true,
            115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getPeriodEndDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.period_end_date", "periodEndDate", true, 115);
        addColumn(usage -> usage.getSalUsage().getLicenseeAccountNumber(), "table.column.licensee_account_number",
            "licenseeAccountNumber", true, 150);
        addColumn(usage -> usage.getSalUsage().getLicenseeName(), "table.column.licensee_name", "licenseeName", true,
            300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true,
            155);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", 130);
        addAmountColumn(UsageDto::getServiceFeeAmount, "table.column.service_fee_amount", "serviceFeeAmount", 150);
        addAmountColumn(UsageDto::getNetAmount, "table.column.net_amount_in_usd", "netAmount", 120);
        addColumn(usageDto -> formatServiceFee(usageDto.getServiceFee()), "table.column.service_fee", "serviceFee",
            true, 115);
        addColumn(usageDto -> usageDto.getSalUsage().getAssessmentName(), "table.column.assessment_name",
            "assessmentName", true, 180);
        addColumn(usageDto -> usageDto.getSalUsage().getAssessmentType(), "table.column.assessment_type",
            "assessmentType", true, 150);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getSalUsage().getScoredAssessmentDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.scored_assessment_date", "scoredAssessmentDate",
            true, 200);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedWorkPortionId(),
            "table.column.reported_work_portion_id", "reportedWorkPortionId", true, 180);
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", true, 170);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedArticle(), "table.column.reported_article",
            "reportedArticle", true, 240);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedStandardNumber(),
            "table.column.reported_standard_number_or_image_id_number", "reportedStandardNumber", true, 315);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedAuthor(), "table.column.reported_author",
            "reportedAuthor", true, 150);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedPublisher(), "table.column.reported_publisher",
            "reportedPublisher", true, 150);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedPublicationDate(),
            "table.column.reported_publication_date", "reportedPublicationDate", true, 200);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedPageRange(), "table.column.reported_page_range",
            "reportedPageRange", true, 150);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedVolNumberSeries(),
            "table.column.reported_vol_number_series", "reportedVolNumberSeries", true, 200);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedMediaType(), "table.column.reported_media_type",
            "reportedMediaType", true, 150);
        addColumn(usageDto -> usageDto.getSalUsage().getCoverageYear(), "table.column.coverage_year", "coverageYear",
            true, 115);
        addColumn(usageDto -> usageDto.getSalUsage().getQuestionIdentifier(), "table.column.question_identifier",
            "questionIdentifier", true, 150);
        addColumn(usageDto -> usageDto.getSalUsage().getGrade(), "table.column.grade", "grade", true, 115);
        addColumn(usageDto -> usageDto.getSalUsage().getGradeGroup(), "table.column.grade_group", "gradeGroup", true,
            115);
        addColumn(usageDto -> usageDto.getSalUsage().getStates(), "table.column.states", "states", true, 115);
        addColumn(usageDto -> usageDto.getSalUsage().getNumberOfViews(), "table.column.number_of_views",
            "numberOfViews", true, 150);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 115);
    }

    @Override
    protected String getSearchPrompt() {
        return ForeignUi.getMessage("field.prompt.drill_down_by_rightsholder.search_widget.aacl_sal");
    }
}
