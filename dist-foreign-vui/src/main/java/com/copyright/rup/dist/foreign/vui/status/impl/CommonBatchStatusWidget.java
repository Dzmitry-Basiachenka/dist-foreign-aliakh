package com.copyright.rup.dist.foreign.vui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.vui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.ValueProvider;

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

    private static final long serialVersionUID = -3771458158529160391L;
    private static final String EMPTY_STYLE_NAME = "empty-batch-status-grid";

    private ICommonBatchStatusController controller;
    private Grid<UsageBatchStatus> grid;

    @SuppressWarnings("unchecked")
    @Override
    public ICommonBatchStatusWidget init() {
        super.setMargin(false);
        super.setPadding(false);
        setSizeFull();
        grid = new Grid<>();
        addColumns();
        add(grid);
        VaadinUtils.setGridProperties(grid, "batch-status-grid");
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
        var batchStatuses = controller.getBatchStatuses();
        grid.setItems(DataProvider.ofCollection(batchStatuses));
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
     * @param width           width of the column
     */
    protected void addColumn(ValueProvider<UsageBatchStatus, ?> provider, String captionProperty, String sort,
                             String width) {
        grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setFlexGrow(0)
            .setWidth(width)
            .setSortable(true)
            .setResizable(true);
    }

    /**
     * Adds column to the grid.
     *
     * @param provider        value provider
     * @param captionProperty property of the column's caption
     * @param sort            sort property
     */
    protected void addColumn(ValueProvider<UsageBatchStatus, ?> provider, String captionProperty, String sort) {
        grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setSortable(true)
            .setResizable(true);
    }

    private void updateGridStyle(List<UsageBatchStatus> batchStatuses) {
        if (CollectionUtils.isNotEmpty(batchStatuses)) {
            grid.removeClassName(EMPTY_STYLE_NAME);
        } else {
            grid.addClassName(EMPTY_STYLE_NAME);
        }
    }
}
