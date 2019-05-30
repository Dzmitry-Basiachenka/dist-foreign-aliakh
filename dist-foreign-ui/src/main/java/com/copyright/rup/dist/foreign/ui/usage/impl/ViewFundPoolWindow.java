package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;

import com.vaadin.ui.Grid;

/**
 * Modal window that provides functionality for viewing and deleting {@link UsageBatch}es.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/24/19
 *
 * @author Uladzislau Shalamitski
 */
class ViewFundPoolWindow extends AbstractViewUsageBatchWindow {

    /**
     * Constructor.
     *
     * @param controller {@link IUsagesController}
     */
    ViewFundPoolWindow(IUsagesController controller) {
        super(controller);
    }

    @Override
    String getSearchMessage() {
        return ForeignUi.getMessage("field.prompt.view_batch.search", "Fund Pool Name");
    }

    @Override
    String getCaptionMessage() {
        return ForeignUi.getMessage("window.view_fund_pool");
    }

    @Override
    String getDeleteMessage(String batchName) {
        return ForeignUi.getMessage("message.confirm.delete_action", batchName, "fund pool");
    }

    @Override
    String getDeleteErrorMessage(String fieldName, String itemsList) {
        return ForeignUi.getMessage("message.error.delete_action", "Fund pool", fieldName, itemsList);
    }

    @Override
    void addGridColumns(Grid<UsageBatch> grid) {
        grid.addColumn(UsageBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.fund_pool_name"))
            .setComparator((batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()))
            .setWidth(150);
        addColumn(batch -> batch.getRro().getAccountNumber(), "table.column.rro_account_number", 120);
        addColumn(batch -> batch.getRro().getName(), "table.column.rro_account_name", 150);
        addColumn(UsageBatch::getPaymentDate, "table.column.payment_date", 100);
        addColumn(batch -> UsageBatchUtils.getFiscalYear(batch.getFiscalYear()), "table.column.fiscal_year", 90);
        addColumn(batch -> batch.getFundPool().getStmAmount(), "table.column.stm_amount", 100,
            (batch1, batch2) -> batch1.getFundPool().getStmAmount()
                .compareTo(batch2.getFundPool().getStmAmount()));
        addColumn(batch -> batch.getFundPool().getNonStmAmount(), "table.column.non_stm_amount", 115,
            (batch1, batch2) -> batch1.getFundPool().getNonStmAmount()
                .compareTo(batch2.getFundPool().getNonStmAmount()));
        addColumn(batch -> batch.getFundPool().getStmMinimumAmount(), "table.column.stm_minimum_amount", 140,
            (batch1, batch2) -> batch1.getFundPool().getStmMinimumAmount()
                .compareTo(batch2.getFundPool().getStmMinimumAmount()));
        addColumn(batch -> batch.getFundPool().getNonStmMinimumAmount(), "table.column.non_stm_minimum_amount", 160,
            (batch1, batch2) -> batch1.getFundPool().getNonStmMinimumAmount()
                .compareTo(batch2.getFundPool().getNonStmMinimumAmount()));
        addColumn(batch -> String.join(", ", batch.getFundPool().getMarkets()), "table.column.markets", 140);
        addColumn(batch -> batch.getFundPool().getFundPoolPeriodFrom(), "table.column.market_period_from", 140);
        addColumn(batch -> batch.getFundPool().getFundPoolPeriodTo(), "table.column.market_period_to", 125);
        addColumn(UsageBatch::getCreateUser, "table.column.create_user", 170);
        grid.addColumn(batch -> getStringFromDate(batch.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.create_date"));
    }
}
