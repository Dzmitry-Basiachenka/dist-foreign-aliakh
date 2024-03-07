package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.AbstractViewUsageBatchWindow;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.function.SerializablePredicate;

import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;

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
        super.setWidth("1016px");
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
        addColumn(UsageBatch::getName, GridColumnEnum.BATCH_NAME,
            (batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()));
        addColumn(batch -> CommonDateUtils.format(batch.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.BATCH_PERIOD_END_DATE);
        addColumn(UsageBatch::getNumberOfBaselineYears, GridColumnEnum.NUMBER_OF_BASELINE_YEARS,
            (batch1, batch2) -> batch1.getNumberOfBaselineYears().compareTo(batch2.getNumberOfBaselineYears()));
        addColumn(UsageBatch::getCreateUser, GridColumnEnum.CREATED_USER,
            (batch1, batch2) -> batch1.getCreateUser().compareToIgnoreCase(batch2.getCreateUser()));
        addColumn(batch -> toLongFormat(batch.getCreateDate()), GridColumnEnum.CREATED_DATE,
            (batch1, batch2) -> batch1.getCreateDate().compareTo(batch2.getCreateDate()));
    }
}
