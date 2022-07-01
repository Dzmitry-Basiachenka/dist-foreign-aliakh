package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

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
            .setCaption(ForeignUi.getMessage("table.column.batch_name"))
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
        addAmountColumn(UsageBatch::getGrossAmount, "table.column.batch_gross_amount", 130,
            (batch1, batch2) -> batch1.getGrossAmount().compareTo(batch2.getGrossAmount()));
        addColumn(UsageBatch::getCreateUser, "table.column.created_by", 170,
            (batch1, batch2) -> batch1.getCreateUser().compareToIgnoreCase(batch2.getCreateUser()));
        grid.addColumn(batch -> toLongFormat(batch.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.created_date"))
            .setComparator((SerializableComparator<UsageBatch>) (batch1, batch2) ->
                batch1.getCreateDate().compareTo(batch2.getCreateDate()));
    }
}
