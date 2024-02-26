package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.AbstractViewUsageBatchWindow;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.function.SerializablePredicate;

import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;

/**
 * Modal window that provides functionality for viewing and deleting {@link UsageBatch}es.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/24/2019
 *
 * @author Uladzislau Shalamitski
 */
class ViewFundPoolWindow extends AbstractViewUsageBatchWindow {

    private static final long serialVersionUID = -7223760637238404615L;

    /**
     * Constructor.
     *
     * @param controller {@link ICommonUsageController}
     */
    ViewFundPoolWindow(ICommonUsageController controller) {
        super(controller);
    }

    @Override
    protected String getSearchMessage() {
        return ForeignUi.getMessage("field.prompt.view_batch.search", "Fund Pool Name");
    }

    @Override
    protected String getCaptionMessage() {
        return ForeignUi.getMessage("window.view_fund_pool");
    }

    @Override
    protected String getDeleteMessage(String batchName) {
        return ForeignUi.getMessage("message.confirm.delete_action", batchName, "fund pool");
    }

    @Override
    protected String getDeleteErrorMessage(String fieldName, String itemsList) {
        return ForeignUi.getMessage("message.error.delete_action", "Fund pool", fieldName, itemsList);
    }

    @Override
    protected SerializablePredicate<UsageBatch> getSearchFilter(String searchValue) {
        return batch -> StringUtils.containsIgnoreCase(batch.getName(), searchValue)
            || StringUtils.containsIgnoreCase(batch.getRro().getName(), searchValue)
            || StringUtils.containsIgnoreCase(batch.getRro().getAccountNumber().toString(), searchValue)
            || StringUtils.containsIgnoreCase(batch.getPaymentDate()
            .format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)), searchValue);
    }

    @Override
    protected void addGridColumns(Grid<UsageBatch> grid) {
        addColumn(UsageBatch::getName, GridColumnEnum.FUND_POOL_NAME,
            (batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()));
        addColumn(batch -> batch.getRro().getAccountNumber(), GridColumnEnum.RRO_ACCOUNT_NUMBER);
        addColumn(batch -> batch.getRro().getName(), GridColumnEnum.RRO_NAME,
            (batch1, batch2) -> batch1.getRro().getName().compareToIgnoreCase(batch2.getRro().getName()));
        addColumn(batch -> toShortFormat(batch.getPaymentDate()), GridColumnEnum.PAYMENT_DATE,
            (batch1, batch2) -> batch1.getPaymentDate().compareTo(batch2.getPaymentDate()));
        addColumn(batch -> UsageBatchUtils.getFiscalYear(batch.getFiscalYear()), GridColumnEnum.FISCAL_YEAR);
        addAmountColumn(batch -> batch.getNtsFields().getStmAmount(), GridColumnEnum.STM_AMOUNT,
            (batch1, batch2) -> batch1.getNtsFields().getStmAmount()
                .compareTo(batch2.getNtsFields().getStmAmount()));
        addAmountColumn(batch -> batch.getNtsFields().getNonStmAmount(), GridColumnEnum.NON_STM_AMOUNT,
            (batch1, batch2) -> batch1.getNtsFields().getNonStmAmount()
                .compareTo(batch2.getNtsFields().getNonStmAmount()));
        addAmountColumn(batch -> batch.getNtsFields().getStmMinimumAmount(), GridColumnEnum.STM_MIN_AMOUNT,
            (batch1, batch2) -> batch1.getNtsFields().getStmMinimumAmount()
                .compareTo(batch2.getNtsFields().getStmMinimumAmount()));
        addAmountColumn(batch -> batch.getNtsFields().getNonStmMinimumAmount(), GridColumnEnum.NON_STM_MIN_AMOUNT,
            (batch1, batch2) -> batch1.getNtsFields().getNonStmMinimumAmount()
                .compareTo(batch2.getNtsFields().getNonStmMinimumAmount()));
        addColumn(batch -> String.join(", ", batch.getNtsFields().getMarkets()), GridColumnEnum.MARKETS);
        addColumn(batch -> batch.getNtsFields().getFundPoolPeriodFrom(), GridColumnEnum.MARKET_PERIOD_FROM);
        addColumn(batch -> batch.getNtsFields().getFundPoolPeriodTo(), GridColumnEnum.MARKET_PERIOD_TO);
        addColumn(UsageBatch::getCreateUser, GridColumnEnum.CREATED_USER,
            (batch1, batch2) -> batch1.getCreateUser().compareToIgnoreCase(batch2.getCreateUser()));
        addColumn(batch -> toLongFormat(batch.getCreateDate()), GridColumnEnum.CREATED_DATE,
            (batch1, batch2) -> batch1.getCreateDate().compareTo(batch2.getCreateDate()));
    }
}
