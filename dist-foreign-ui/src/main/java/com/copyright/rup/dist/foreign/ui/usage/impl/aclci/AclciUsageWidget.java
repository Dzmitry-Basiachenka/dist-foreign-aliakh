package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.HorizontalLayout;

/**
 * Implementation of {@link IAclciUsageWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageWidget extends CommonUsageWidget implements IAclciUsageWidget {

    @Override
    public IMediator initMediator() {
        return new AclciUsageMediator();
    }

    @Override
    protected void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", true, 115);
        addColumn(usage -> usage.getAclciUsage().getLicenseType(), "table.column.license_type",
            "licenseType", true, 115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(usage -> CommonDateUtils.format(usage.getPeriodEndDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.period_end_date", "periodEndDate", true, 115);
        addColumn(usage -> usage.getAclciUsage().getCoveragePeriod(), "table.column.coverage_period",
            "coveragePeriod", true, 300);
        addColumn(usage -> usage.getAclciUsage().getLicenseeAccountNumber(), "table.column.licensee_account_number",
            "licenseeAccountNumber", true, 150);
        addColumn(usage -> usage.getAclciUsage().getLicenseeName(), "table.column.licensee_name",
            "licenseeName", true, 300);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", true, 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type",
            "standardNumberType", true, 155);
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", true, 300);
        addColumn(usage -> usage.getAclciUsage().getReportedMediaType(), "table.column.reported_media_type",
            "reportedMediaType", true, 150);
        addColumn(usageDto -> usageDto.getAclciUsage().getMediaTypeWeight(), "table.column.media_type_weight",
            "mediaTypeWeight", true, 130);
        addColumn(usage -> usage.getAclciUsage().getReportedArticle(), "table.column.reported_article",
            "reportedArticle", true, 240);
        addColumn(usage -> usage.getAclciUsage().getReportedStandardNumber(),
            "table.column.reported_standard_number_or_image_id_number", "reportedStandardNumber", true, 315);
        addColumn(usage -> usage.getAclciUsage().getReportedAuthor(), "table.column.reported_author",
            "reportedAuthor", true, 150);
        addColumn(usage -> usage.getAclciUsage().getReportedPublisher(), "table.column.reported_publisher",
            "reportedPublisher", true, 150);
        addColumn(usage -> usage.getAclciUsage().getReportedPublicationDate(), "table.column.reported_publication_date",
            "reportedPublicationDate", true, 200);
        addColumn(usage -> usage.getAclciUsage().getReportedGrade(), "table.column.reported_grade",
            "reportedGrade", true, 150);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 115);
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        return new HorizontalLayout(); //TODO: implement
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        return null; //TODO: implement
    }
}
