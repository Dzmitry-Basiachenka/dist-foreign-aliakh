package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
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
class ViewUsageBatchWindow extends AbstractViewUsageBatchWindow {

    private static final long serialVersionUID = -8417390380475219393L;

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
        addColumn(UsageBatch::getName, "table.column.batch_name", "200px",
            (batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()));
        addColumn(batch -> batch.getRro().getAccountNumber(), "table.column.rro_account_number", "160px");
        addColumn(batch -> batch.getRro().getName(), "table.column.rro_account_name", "300px",
            (batch1, batch2) -> batch1.getRro().getName().compareToIgnoreCase(batch2.getRro().getName()));
        addColumn(batch -> CommonDateUtils.format(batch.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "150px",
            (batch1, batch2) -> batch1.getPaymentDate().compareTo(batch2.getPaymentDate()));
        addColumn(batch -> UsageBatchUtils.getFiscalYear(batch.getFiscalYear()), "table.column.fiscal_year", "140px");
        addAmountColumn(UsageBatch::getGrossAmount, "table.column.batch_gross_amount", "180px",
            (batch1, batch2) -> batch1.getGrossAmount().compareTo(batch2.getGrossAmount()));
        addColumn(UsageBatch::getCreateUser, "table.column.created_by", "330px",
            (batch1, batch2) -> batch1.getCreateUser().compareToIgnoreCase(batch2.getCreateUser()));
        addColumn(batch -> toLongFormat(batch.getCreateDate()), "table.column.created_date", "150px",
            (batch1, batch2) -> batch1.getCreateDate().compareTo(batch2.getCreateDate()));
    }
}
