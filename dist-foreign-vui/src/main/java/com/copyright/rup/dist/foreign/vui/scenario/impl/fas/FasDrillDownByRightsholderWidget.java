package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonDrillDownByRightsholderWidget;

import java.util.Objects;

/**
 * Implementation of {@link IFasDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
public class FasDrillDownByRightsholderWidget extends CommonDrillDownByRightsholderWidget
    implements IFasDrillDownByRightsholderWidget {

    private static final long serialVersionUID = -6030611472342480603L;
    private static final String WIDTH_130_PX = "130px";
    private static final String WIDTH_170_PX = "170px";
    private static final String WIDTH_150_PX = "150px";
    private static final String WIDTH_200_PX = "200px";

    @Override
    protected void addColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", WIDTH_130_PX);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", WIDTH_170_PX);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", "180px");
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber", WIDTH_150_PX);
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", "140px");
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", WIDTH_130_PX);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", "300px");
        addColumn(usageDto -> Objects.nonNull(usageDto.getFasUsage())
                ? usageDto.getFasUsage().getReportedStandardNumber() : null, "table.column.reported_standard_number",
            "reportedStandardNumber", "270px");
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", "190px");
        addColumn(
            UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", "220px");
        addColumn(usageDto -> formatFiscalYear(usageDto.getFiscalYear()), "table.column.fiscal_year", "fiscalYear",
            WIDTH_130_PX);
        addColumn(
            usageDto -> CommonDateUtils.format(usageDto.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", WIDTH_150_PX);
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", "300px");
        addColumn(UsageDto::getArticle, "table.column.article", "article");
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", "135px");
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getPublicationDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.publication_date", "publicationDate",
            WIDTH_130_PX);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", "180px");
        addAmountColumn(UsageDto::getReportedValue, "table.column.reported_value", "reportedValue", WIDTH_170_PX);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", WIDTH_170_PX);
        addAmountColumn(
            UsageDto::getBatchGrossAmount, "table.column.batch_gross_amount", "batchGrossAmount", WIDTH_170_PX);
        addAmountColumn(
            UsageDto::getServiceFeeAmount, "table.column.service_fee_amount", "serviceFeeAmount", WIDTH_200_PX);
        addAmountColumn(UsageDto::getNetAmount, "table.column.net_amount_in_usd", "netAmount", WIDTH_150_PX);
        addColumn(usageDto -> formatServiceFee(usageDto.getServiceFee()), "table.column.service_fee", "serviceFee",
            WIDTH_150_PX);
        addColumn(UsageDto::getMarket, "table.column.market", "market", "115px");
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", WIDTH_200_PX);
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", "190px");
        addColumn(UsageDto::getAuthor, "table.column.author", "author", "105px");
        addColumn(UsageDto::getComment, "table.column.comment", "comment", WIDTH_200_PX);
    }

    @Override
    protected String getSearchPrompt() {
        return ForeignUi.getMessage("field.prompt.drill_down_by_rightsholder.search_widget");
    }

    @Override
    protected String[] getExcludedColumns() {
        return new String[]{"Detail ID"};
    }
}
