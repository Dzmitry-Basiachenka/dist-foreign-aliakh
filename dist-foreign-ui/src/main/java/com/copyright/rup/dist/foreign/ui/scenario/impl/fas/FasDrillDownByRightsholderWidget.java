package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonDrillDownByRightsholderWidget;

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

    @Override
    protected void addColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber", true, 125);
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", true, 135);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(
            UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true, 155);
        addColumn(usageDto -> formatFiscalYear(usageDto.getFiscalYear()), "table.column.fiscal_year", "fiscalYear",
            true, 105);
        addColumn(
            usageDto -> CommonDateUtils.format(usageDto.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", true, 115);
        addColumn(UsageDto::getWorkTitle, "table.column.work_title", "workTitle");
        addColumn(UsageDto::getArticle, "table.column.article", "article");
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", true, 135);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getPublicationDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.publication_date", "publicationDate", true, 90);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", true, 140);
        addAmountColumn(UsageDto::getReportedValue, "table.column.reported_value", "reportedValue", 130);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", 130);
        addAmountColumn(UsageDto::getBatchGrossAmount, "table.column.batch_gross_amount", "batchGrossAmount", 130);
        addAmountColumn(UsageDto::getServiceFeeAmount, "table.column.service_fee_amount", "serviceFeeAmount", 150);
        addAmountColumn(UsageDto::getNetAmount, "table.column.net_amount_in_usd", "netAmount", 120);
        addColumn(usageDto -> formatServiceFee(usageDto.getServiceFee()), "table.column.service_fee", "serviceFee",
            true, 115);
        addColumn(UsageDto::getMarket, "table.column.market", "market", true, 115);
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", true, 150);
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", true, 145);
        addColumn(UsageDto::getAuthor, "table.column.author", "author", true, 90);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 200);
    }

    @Override
    protected String getSearchPrompt() {
        return ForeignUi.getMessage("field.prompt.drill_down_by_rightsholder.search_widget");
    }
}
