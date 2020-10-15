package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonDrillDownByRightsholderWidget;

/**
 * Implementation of {@link IAaclDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/01/20
 *
 * @author Uladzislau Shalamitski
 */
public class AaclDrillDownByRightsholderWidget extends CommonDrillDownByRightsholderWidget
    implements IAaclDrillDownByRightsholderWidget {

    @Override
    protected void addColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getAaclUsage().getBatchPeriodEndDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.period_end_date", "periodEndDate", true, 120);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(
            UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true, 155);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", 130);
        addAmountColumn(UsageDto::getServiceFeeAmount, "table.column.service_fee_amount", "serviceFeeAmount", 150);
        addAmountColumn(UsageDto::getNetAmount, "table.column.net_amount", "netAmount", 120);
        addColumn(usageDto -> formatServiceFee(usageDto.getServiceFee()), "table.column.service_fee", "serviceFee",
            true, 115);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getId(), "table.column.det_lc_id",
            "detailLicenseeClassId", true, 80);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile(),
            "table.column.det_lc_enrollment", "detailLicenseeEnrollment", true, 140);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getDiscipline(),
            "table.column.det_lc_discipline", "detailLicenseeDiscipline", true, 140);
        addColumn(usage -> usage.getAaclUsage().getAggregateLicenseeClass().getId(),
            "table.column.aggregate_licensee_class_id", "aggregateLicenseeClassId", true, 80);
        addColumn(usage -> usage.getAaclUsage().getAggregateLicenseeClass().getEnrollmentProfile(),
            "table.column.aggregate_lc_enrollment", "aggregateLicenseeEnrollment", true, 140);
        addColumn(usage -> usage.getAaclUsage().getAggregateLicenseeClass().getDiscipline(),
            "table.column.aggregate_lc_discipline", "aggregateLicenseeDiscipline", true, 140);
        addColumn(usageDto -> usageDto.getAaclUsage().getPublicationType().getName(), "table.column.publication_type",
            "publicationType", true, 140);
        addColumn(usageDto -> usageDto.getAaclUsage().getPublicationType().getWeight(),
            "table.column.publication_type_weight", "publicationTypeWeight", true, 120);
        addColumn(usageDto -> usageDto.getAaclUsage().getOriginalPublicationType(),
            "table.column.historical_publication_type", "originalPublicationType", true, 140);
        addColumn(usageDto -> usageDto.getAaclUsage().getInstitution(), "table.column.institution", "institution",
            true, 115);
        addColumn(usageDto -> usageDto.getAaclUsage().getUsageAge().getPeriod(), "table.column.usage_period",
            "usagePeriod", true, 100);
        addAmountColumn(usageDto -> usageDto.getAaclUsage().getUsageAge().getWeight(), "table.column.usage_age_weight",
            "usageAgeWeight", 130);
        addColumn(usageDto -> usageDto.getAaclUsage().getUsageSource(), "table.column.usage_source", "usageSource",
            true, 140);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", true, 140);
        addColumn(usageDto -> usageDto.getAaclUsage().getNumberOfPages(), "table.column.number_of_pages",
            "numberOfPages", true, 140);
        addColumn(usageDto -> usageDto.getAaclUsage().getRightLimitation(), "table.column.right_limitation",
            "rightLimitation", true, 140);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 200);
    }

    @Override
    protected String getSearchPrompt() {
        return ForeignUi.getMessage("field.prompt.drill_down_by_rightsholder.search_widget.aacl_sal");
    }
}
