package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;

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

    @SuppressWarnings("unchecked")
    @Override
    public ICommonBatchStatusWidget init() {
        initContent();
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

    private void initContent() {
        dataProvider = new ListDataProvider<>(controller.getBatchStatuses());
        Grid<UsageBatchStatus> grid = new Grid<>(dataProvider);
        grid.setSizeFull();
        addColumns();
        addComponent(grid);
    }
}
