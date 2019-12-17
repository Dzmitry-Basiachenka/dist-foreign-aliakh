package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;

import com.copyright.rup.dist.foreign.ui.usage.impl.AbstractViewUsageBatchWindow;
import com.vaadin.server.SerializableComparator;
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
class ViewUsageBatchWindow extends AbstractViewUsageBatchWindow {

    /**
     * Constructor.
     *
     * @param controller {@link ICommonUsageController}
     */
    ViewUsageBatchWindow(ICommonUsageController controller) {
        super(controller);
    }

    @Override
    protected String getSearchMessage() {
        return ForeignUi.getMessage("field.prompt.view_batch.search", "Batch Name");
    }

    @Override
    protected String getCaptionMessage() {
        return ForeignUi.getMessage("window.view_usage_batch");
    }

    @Override
    protected String getDeleteMessage(String butchName) {
        return ForeignUi.getMessage("message.confirm.delete_action", butchName, "usage batch");
    }

    @Override
    protected String getDeleteErrorMessage(String fieldName, String namesList) {
        return ForeignUi.getMessage("message.error.delete_action", "Usage batch", fieldName, namesList);
    }

    @Override
    protected void addGridColumns(Grid<UsageBatch> grid) {
        grid.addColumn(UsageBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.batch_name"))
            .setComparator((batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()))
            .setWidth(150);
        addColumn(batch -> batch.getRro().getAccountNumber(), "table.column.rro_account_number", 120);
        addColumn(batch -> batch.getRro().getName(), "table.column.rro_account_name", 150);
        addColumn(UsageBatch::getPaymentDate, "table.column.payment_date", 100);
        addColumn(batch -> UsageBatchUtils.getFiscalYear(batch.getFiscalYear()), "table.column.fiscal_year", 90);
        addColumn(UsageBatch::getGrossAmount, "table.column.batch_gross_amount", 130,
            (batch1, batch2) -> batch1.getGrossAmount().compareTo(batch2.getGrossAmount()));
        addColumn(UsageBatch::getCreateUser, "table.column.create_user", 170);
        grid.addColumn(batch -> getStringFromDate(batch.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.create_date"))
            .setComparator((SerializableComparator<UsageBatch>) (batch1, batch2) ->
                batch1.getCreateDate().compareTo(batch2.getCreateDate()));
    }
}
