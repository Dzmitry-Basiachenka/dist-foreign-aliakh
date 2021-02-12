package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

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

    private ICommonBatchStatusController controller;
    private ListDataProvider<UsageBatchStatus> dataProvider;
    private Grid<UsageBatchStatus> batchStatusGrid;

    @SuppressWarnings("unchecked")
    @Override
    public ICommonBatchStatusWidget init() {
        setSizeFull();
        dataProvider = new ListDataProvider<>(controller.getBatchStatuses());
        batchStatusGrid = new Grid<>(dataProvider);
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
        dataProvider.refreshAll();
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
}
