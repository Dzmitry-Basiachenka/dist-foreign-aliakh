package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonDrillDownByRightsholderWidget;

/**
 * Implementation of {@link INtsDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/2019
 *
 * @author Stanislau Rudak
 */
public class NtsDrillDownByRightsholderWidget extends CommonDrillDownByRightsholderWidget
    implements INtsDrillDownByRightsholderWidget {

    private static final long serialVersionUID = -6932982725647270658L;

    @Override
    protected void addColumns() {
        addColumn(UsageDto::getId, GridColumnEnum.ID);
        addColumn(UsageDto::getProductFamily, GridColumnEnum.PRODUCT_FAMILY);
        addColumn(UsageDto::getBatchName, GridColumnEnum.BATCH_NAME);
        addColumn(UsageDto::getRroAccountNumber, GridColumnEnum.RRO_ACCOUNT_NUMBER);
        addColumn(UsageDto::getRroName, GridColumnEnum.RRO_NAME);
        addColumn(UsageDto::getWrWrkInst, GridColumnEnum.WR_WRK_INST);
        addColumn(UsageDto::getSystemTitle, GridColumnEnum.SYSTEM_TITLE);
        addColumn(UsageDto::getStandardNumber, GridColumnEnum.STANDARD_NUMBER);
        addColumn(UsageDto::getStandardNumberType, GridColumnEnum.STANDARD_NUMBER_TYPE);
        addColumn(usageDto -> formatFiscalYear(usageDto.getFiscalYear()), GridColumnEnum.FISCAL_YEAR);
        addColumn(
            usageDto -> CommonDateUtils.format(usageDto.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.PAYMENT_DATE);
        addColumn(UsageDto::getWorkTitle, GridColumnEnum.WORK_TITLE);
        addColumn(UsageDto::getArticle, GridColumnEnum.ARTICLE);
        addColumn(UsageDto::getPublisher, GridColumnEnum.PUBLISHER);
        addColumn(usageDto ->
                CommonDateUtils.format(usageDto.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.PUB_DATE);
        addColumn(UsageDto::getNumberOfCopies, GridColumnEnum.NUMBER_OF_COPIES);
        addAmountColumn(UsageDto::getReportedValue, GridColumnEnum.REPORTED_VALUE);
        addAmountColumn(UsageDto::getGrossAmount, GridColumnEnum.GROSS_AMOUNT);
        addAmountColumn(UsageDto::getServiceFeeAmount, GridColumnEnum.SERVICE_FEE_AMOUNT);
        addAmountColumn(UsageDto::getNetAmount, GridColumnEnum.NET_AMOUNT);
        addColumn(usageDto -> formatServiceFee(usageDto.getServiceFee()), GridColumnEnum.SERVICE_FEE);
        addColumn(UsageDto::getMarket, GridColumnEnum.MARKET);
        addColumn(UsageDto::getMarketPeriodFrom, GridColumnEnum.MARKET_PERIOD_FROM);
        addColumn(UsageDto::getMarketPeriodTo, GridColumnEnum.MARKET_PERIOD_TO);
        addColumn(UsageDto::getAuthor, GridColumnEnum.AUTHOR);
        addColumn(UsageDto::getComment, GridColumnEnum.COMMENT);
    }

    @Override
    protected String[] getExcludedColumns() {
        return new String[]{"Detail ID"};
    }

    @Override
    protected String getSearchPrompt() {
        return ForeignUi.getMessage("field.prompt.drill_down_by_rightsholder.search_widget");
    }
}
