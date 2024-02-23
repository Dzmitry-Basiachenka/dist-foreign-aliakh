package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.AbstractViewUsageBatchWindow;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.function.SerializablePredicate;

import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Modal window that provides functionality for viewing and deleting AACL {@link UsageBatch}es.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 02/12/2020
 *
 * @author Uladzislau Shalamitski
 */
public class ViewAaclUsageBatchWindow extends AbstractViewUsageBatchWindow {

    private static final long serialVersionUID = -209068756778449046L;

    /**
     * Constructor.
     *
     * @param controller {@link IAaclUsageController}
     */
    public ViewAaclUsageBatchWindow(IAaclUsageController controller) {
        super(controller);
        super.setWidth("800px");
    }

    @Override
    protected String getSearchMessage() {
        return ForeignUi.getMessage("field.prompt.view_batch.search.aacl");
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
            || StringUtils.containsIgnoreCase(batch.getPaymentDate()
            .format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)), searchValue);
    }

    @Override
    protected void addGridColumns(Grid<UsageBatch> grid) {
        //TODO {aliakh} fix column width
        //TODO {aliakh} reuse methods addColumn from AbstractViewUsageBatchWindow
        grid.addColumn(UsageBatch::getName)
            .setHeader(ForeignUi.getMessage("table.column.batch_name"))
            .setComparator((batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()));
        grid.addColumn(
            batch -> CommonDateUtils.format(batch.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT))
            .setHeader(ForeignUi.getMessage("table.column.period_end_date"))
            .setWidth("120px");
        grid.addColumn(UsageBatch::getNumberOfBaselineYears)
            .setHeader(ForeignUi.getMessage("table.column.number_of_baseline_years"))
            .setComparator(Comparator.comparing(UsageBatch::getNumberOfBaselineYears))
            .setWidth("180px");
        grid.addColumn(UsageBatch::getCreateUser)
            .setHeader(ForeignUi.getMessage("table.column.created_by"))
            .setComparator((batch1, batch2) -> batch1.getCreateUser().compareToIgnoreCase(batch2.getCreateUser()))
            .setWidth("170px");
        grid.addColumn(batch -> toLongFormat(batch.getCreateDate()))
            .setHeader(ForeignUi.getMessage("table.column.created_date"))
            .setComparator(Comparator.comparing(StoredEntity::getCreateDate))
            .setWidth("170px");
    }
}
