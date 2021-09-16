package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.AbstractViewUsageBatchWindow;

import com.vaadin.server.SerializableComparator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.LocalDateRenderer;

import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;

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
        grid.addColumn(UsageBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.fund_pool_name"))
            .setComparator((batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()))
            .setWidth(150);
        addColumn(batch -> batch.getRro().getAccountNumber(), "table.column.rro_account_number", 120);
        addColumn(batch -> batch.getRro().getName(), "table.column.rro_account_name", 150,
            (batch1, batch2) -> batch1.getRro().getName().compareToIgnoreCase(batch2.getRro().getName()));
        grid.addColumn(UsageBatch::getPaymentDate)
            .setCaption(ForeignUi.getMessage("table.column.payment_date"))
            .setRenderer(new LocalDateRenderer(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT))
            .setWidth(100);
        addColumn(batch -> UsageBatchUtils.getFiscalYear(batch.getFiscalYear()), "table.column.fiscal_year", 90);
        addAmountColumn(batch -> batch.getNtsFields().getStmAmount(), "table.column.stm_amount", 100,
            (batch1, batch2) -> batch1.getNtsFields().getStmAmount()
                .compareTo(batch2.getNtsFields().getStmAmount()));
        addAmountColumn(batch -> batch.getNtsFields().getNonStmAmount(), "table.column.non_stm_amount", 115,
            (batch1, batch2) -> batch1.getNtsFields().getNonStmAmount()
                .compareTo(batch2.getNtsFields().getNonStmAmount()));
        addAmountColumn(batch -> batch.getNtsFields().getStmMinimumAmount(), "table.column.stm_minimum_amount", 140,
            (batch1, batch2) -> batch1.getNtsFields().getStmMinimumAmount()
                .compareTo(batch2.getNtsFields().getStmMinimumAmount()));
        addAmountColumn(batch -> batch.getNtsFields().getNonStmMinimumAmount(), "table.column.non_stm_minimum_amount",
            160, (batch1, batch2) -> batch1.getNtsFields().getNonStmMinimumAmount()
                .compareTo(batch2.getNtsFields().getNonStmMinimumAmount()));
        addColumn(batch -> String.join(", ", batch.getNtsFields().getMarkets()), "table.column.markets", 140);
        addColumn(batch -> batch.getNtsFields().getFundPoolPeriodFrom(), "table.column.market_period_from", 140);
        addColumn(batch -> batch.getNtsFields().getFundPoolPeriodTo(), "table.column.market_period_to", 125);
        addColumn(UsageBatch::getCreateUser, "table.column.created_by", 170,
            (batch1, batch2) -> batch1.getCreateUser().compareToIgnoreCase(batch2.getCreateUser()));
        grid.addColumn(batch -> getStringFromDate(batch.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.created_date"))
            .setComparator((SerializableComparator<UsageBatch>) (batch1, batch2) ->
                batch1.getCreateDate().compareTo(batch2.getCreateDate()));
    }
}
