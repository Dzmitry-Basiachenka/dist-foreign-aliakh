package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Common implementation for usage batch status widgets.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
public abstract class CommonBatchStatusWidget extends VerticalLayout implements ICommonBatchStatusWidget {

    private static final String EMPTY_STYLE_NAME = "empty-batch-status-grid";

    private ICommonBatchStatusController controller;
    private Grid<UsageBatchStatus> batchStatusGrid;

    @SuppressWarnings("unchecked")
    @Override
    public ICommonBatchStatusWidget init() {
        setSizeFull();
        // when a user selects the tab, the necessary list of batch statuses is loaded in method {@link this#refresh()}
        batchStatusGrid = new Grid<>();
        addColumns();
        batchStatusGrid.setSizeFull();
        addComponent(batchStatusGrid);
        VaadinUtils.addComponentStyle(batchStatusGrid, "batch-status-grid");
        VaadinUtils.addComponentStyle(this, "batch-status-widget");
        return this;
    }

    @Override
    public void setController(ICommonBatchStatusController controller) {
        this.controller = controller;
    }

    @Override
    public ICommonBatchStatusController getController() {
        return controller;
    }

    @Override
    public void refresh() {
        List<UsageBatchStatus> batchStatuses = controller.getBatchStatuses();
        batchStatusGrid.setDataProvider(DataProvider.ofCollection(batchStatuses));
        updateGridStyle(batchStatuses);
    }

    /**
     * Adds columns to the batch status grid.
     */
    protected abstract void addColumns();

    /**
     * Adds column to the grid.
     *
     * @param provider        value provider
     * @param captionProperty property of the column's caption
     * @param sort            sort property
     * @param isHidable       sets whether this column can be hidden by the user
     * @param width           width of the column
     */
    protected void addColumn(ValueProvider<UsageBatchStatus, ?> provider, String captionProperty, String sort,
                             boolean isHidable, double width) {
        batchStatusGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setSortProperty(sort)
            .setHidable(isHidable)
            .setWidth(width);
    }

    /**
     * Adds column to the grid.
     *
     * @param provider        value provider
     * @param captionProperty property of the column's caption
     * @param sort            sort property
     * @param isHidable       sets whether this column can be hidden by the user
     */
    protected void addColumn(ValueProvider<UsageBatchStatus, ?> provider, String captionProperty, String sort,
                             boolean isHidable) {
        batchStatusGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setSortProperty(sort)
            .setHidable(isHidable);
    }

    private void updateGridStyle(List<UsageBatchStatus> batchStatusList) {
        if (CollectionUtils.isNotEmpty(batchStatusList)) {
            batchStatusGrid.removeStyleName(EMPTY_STYLE_NAME);
        } else {
            batchStatusGrid.addStyleName(EMPTY_STYLE_NAME);
        }
    }
}
