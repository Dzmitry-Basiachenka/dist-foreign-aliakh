package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Objects;

/**
 * Implementation of {@link ISalAuditWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalAuditWidget extends CommonAuditWidget implements ISalAuditWidget {

    private final ISalAuditController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalAuditController}
     */
    SalAuditWidget(ISalAuditController controller) {
        this.controller = controller;
    }

    @Override
    protected void addColumns() {
        getAuditGrid().addComponentColumn(usageDto -> {
            String detailId = Objects.toString(usageDto.getId());
            Button button = Buttons.createButton(detailId);
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> controller.showUsageHistory(usageDto.getId(), detailId));
            return button;
        })
            .setCaption(ForeignUi.getMessage("table.column.detail_id"))
            .setSortProperty("detailId")
            .setWidth(130);
        addColumn(usageDto -> usageDto.getSalUsage().getDetailType(), "table.column.detail_type", "detailType", 90);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", 115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", 140);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getSalUsage().getBatchPeriodEndDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.period_end_date", "batchPeriodEndDate", 115);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", 300);
        addColumn(UsageDto::getPayeeAccountNumber, "table.column.payee_account_number", "payeeAccountNumber", 115);
        addColumn(UsageDto::getPayeeName, "table.column.payee_name", "payeeName", 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", 140);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", 155);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", 130);
        addAmountColumn(UsageDto::getServiceFeeAmount, "table.column.service_fee_amount", "serviceFeeAmount", 150);
        addAmountColumn(UsageDto::getNetAmount, "table.column.net_amount_in_usd", "netAmount", 120);
        addColumn(UsageDto::getScenarioName, "table.column.scenario_name", "scenarioName", 125);
        addColumn(UsageDto::getCheckNumber, "table.column.check_number", "checkNumber", 85);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getCheckDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.check_date", "checkDate", 105);
        addColumn(UsageDto::getCccEventId, "table.column.event_id", "cccEventId", 85);
        addColumn(UsageDto::getDistributionName, "table.column.distribution_name", "distributionName", 110);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getDistributionDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.distribution_date", "distributionDate", 105);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedWorkPortionId(),
            "table.column.reported_work_portion_id", "reportedWorkPortionId", 180);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedStandardNumber(),
            "table.column.reported_standard_number", "reportedStandardNumber", 200);
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", 300);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedMediaType(), "table.column.reported_media_type",
            "reportedMediaType", 150);
        addColumn(usageDto -> usageDto.getSalUsage().getMediaTypeWeight(), "table.column.media_type_weight",
            "mediaTypeWeight", 130);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedArticle(), "table.column.reported_article",
            "reportedArticle", 240);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedAuthor(), "table.column.reported_author",
            "reportedAuthor", 150);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedPublisher(), "table.column.reported_publisher",
            "reportedPublisher", 150);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedPublicationDate(),
            "table.column.reported_publication_date", "reportedPublicationDate", 180);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedPageRange(), "table.column.reported_page_range",
            "reportedPageRange", 150);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedVolNumberSeries(),
            "table.column.reported_vol_number_series", "reportedVolNumberSeries", 200);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getSalUsage().getScoredAssessmentDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.scored_assessment_date", "scoredAssessmentDate",
            190);
        addColumn(usageDto -> usageDto.getSalUsage().getAssessmentName(), "table.column.assessment_name",
            "assessmentName", 180);
        addColumn(usageDto -> usageDto.getSalUsage().getCoverageYear(), "table.column.coverage_year", "coverageYear",
            115);
        addColumn(usageDto -> usageDto.getSalUsage().getGrade(), "table.column.grade", "grade", 60);
        addColumn(usageDto -> usageDto.getSalUsage().getGradeGroup(), "table.column.grade_group", "gradeGroup", 110);
        addColumn(usageDto -> usageDto.getSalUsage().getAssessmentType(), "table.column.assessment_type",
            "assessmentType", 150);
        addColumn(usageDto -> usageDto.getSalUsage().getQuestionIdentifier(), "table.column.question_identifier",
            "questionIdentifier", 150);
        addColumn(usageDto -> usageDto.getSalUsage().getStates(), "table.column.states", "states", 115);
        addColumn(usageDto -> usageDto.getSalUsage().getNumberOfViews(), "table.column.number_of_views",
            "numberOfViews", 120);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", 200);
    }

    @Override
    public String initSearchMessage() {
        return "prompt.audit_search_aacl_sal";
    }
}
