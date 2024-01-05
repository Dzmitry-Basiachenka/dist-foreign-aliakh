package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Implementation of {@link IUdmBatchStatusWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/2023
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBatchStatusWidget extends VerticalLayout implements IUdmBatchStatusWidget {

    private static final long serialVersionUID = -5803338747966920198L;
    private static final String EMPTY_STYLE_NAME = "empty-batch-status-grid";

    private Grid<UsageBatchStatus> batchStatusGrid;
    private ListDataProvider<UsageBatchStatus> dataProvider;
    private IUdmBatchStatusController controller;

    @SuppressWarnings("unchecked")
    @Override
    public IUdmBatchStatusWidget init() {
        setSizeFull();
        initGrid();
        addComponent(batchStatusGrid);
        VaadinUtils.addComponentStyle(this, "batch-status-widget");
        return this;
    }

    @Override
    public void setController(IUdmBatchStatusController controller) {
        this.controller = controller;
    }

    @Override
    public void refresh() {
        List<UsageBatchStatus> batchStatuses = controller.getBatchStatuses();
        dataProvider = DataProvider.ofCollection(batchStatuses);
        batchStatusGrid.setDataProvider(dataProvider);
        updateGridStyle(batchStatuses);
    }

    private void initGrid() {
        List<UsageBatchStatus> batchStatuses = controller.getBatchStatuses();
        dataProvider = DataProvider.ofCollection(batchStatuses);
        batchStatusGrid = new Grid<>(dataProvider);
        updateGridStyle(batchStatuses);
        addColumns();
        batchStatusGrid.setSizeFull();
        VaadinUtils.addComponentStyle(batchStatusGrid, "batch-status-grid");
    }

    private void addColumns() {
        addColumn(UsageBatchStatus::getBatchName, "table.column.batch_name", "batchName");
        addColumn(UsageBatchStatus::getTotalCount, "table.column.total_count", "totalCount", 130);
        addColumn(UsageBatchStatus::getNewCount, "table.column.new", "newCount", 130);
        addColumn(UsageBatchStatus::getWorkNotFoundCount, "table.column.work_not_found", "workNotFoundCount", 140);
        addColumn(UsageBatchStatus::getWorkFoundCount, "table.column.work_found", "workFoundCount", 130);
        addColumn(UsageBatchStatus::getRhNotFoundCount, "table.column.rh_not_found", "rhNotFoundCount", 130);
        addColumn(UsageBatchStatus::getRhFoundCount, "table.column.rh_found", "rhFoundCount", 130);
        addColumn(UsageBatchStatus::getIneligibleCount, "table.column.ineligible", "eligibleCount", 130);
        addColumn(UsageBatchStatus::getStatus, "table.column.status", "status");
    }

    private void addColumn(ValueProvider<UsageBatchStatus, ?> provider, String captionProperty, String sort) {
        batchStatusGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setSortProperty(sort)
            .setHidable(true);
    }

    private void addColumn(ValueProvider<UsageBatchStatus, ?> provider, String captionProperty, String sort,
                           double width) {
        batchStatusGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setSortProperty(sort)
            .setHidable(true)
            .setWidth(width);
    }

    private void updateGridStyle(List<UsageBatchStatus> batchStatusList) {
        if (CollectionUtils.isNotEmpty(batchStatusList)) {
            batchStatusGrid.removeStyleName(EMPTY_STYLE_NAME);
        } else {
            batchStatusGrid.addStyleName(EMPTY_STYLE_NAME);
        }
    }
}
