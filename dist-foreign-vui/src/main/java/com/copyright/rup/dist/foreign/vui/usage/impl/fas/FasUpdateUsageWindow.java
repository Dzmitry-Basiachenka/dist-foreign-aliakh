package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUpdateUsageWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.AbstractGridMultiSelectionModel;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridMultiSelectionModel.SelectAllCheckboxVisibility;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Window to display FAS usages to update.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/09/2023
 *
 * @author Aliaksandr Liakh
 */
class FasUpdateUsageWindow extends CommonDialog implements IFasUpdateUsageWindow {

    private static final long serialVersionUID = 3933778706035133427L;

    private final IFasUsageController controller;
    private SearchWidget searchWidget;
    private Grid<UsageDto> usagesGrid;
    private AbstractGridMultiSelectionModel<UsageDto> gridSelectionModel;
    private ListDataProvider<UsageDto> dataProvider;
    private Button updateButton;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IFasUsageController}
     */
    FasUpdateUsageWindow(IFasUsageController controller) {
        this.controller = controller;
        super.setWidth("1280px");
        super.setHeight("530px");
        super.add(initContent());
        super.setHeaderTitle(ForeignUi.getMessage("window.update_usages"));
        super.setModalWindowProperties("update-usages-window", true);
    }

    @Override
    public void refresh() {
        performSearch();
        dataProvider.refreshAll();
    }

    private Component initContent() {
        searchWidget = new SearchWidget(this::refresh, ForeignUi.getMessage("prompt.rightsholder_update"), "70%");
        getFooter().add(initButtonsLayout());
        return VaadinUtils.initSizeFullVerticalLayout(searchWidget, initGrid());
    }

    private Grid<UsageDto> initGrid() {
        List<UsageDto> usages = controller.getUsageDtosToUpdate();
        dataProvider = DataProvider.ofCollection(usages);
        usagesGrid = new Grid<>();
        usagesGrid.setItems(dataProvider);
        gridSelectionModel =
            (AbstractGridMultiSelectionModel<UsageDto>) usagesGrid.setSelectionMode(SelectionMode.MULTI);
        switchSelectAllCheckBoxVisibility(usages.size());
        addGridColumns();
        usagesGrid.addSelectionListener(event ->
            updateButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        usagesGrid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.setGridProperties(usagesGrid, "update-fas-usages-grid");
        return usagesGrid;
    }

    private void switchSelectAllCheckBoxVisibility(int beansCount) {
        boolean selectAllCheckBoxVisibility = 0 < beansCount && beansCount < controller.getRecordsThreshold();
        gridSelectionModel.setSelectAllCheckboxVisibility(selectAllCheckBoxVisibility
            ? SelectAllCheckboxVisibility.VISIBLE : SelectAllCheckboxVisibility.HIDDEN);
        if (selectAllCheckBoxVisibility) {
            gridSelectionModel.setSelectionColumnFrozen(true);
        }
    }

    private void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "300px");
        addColumn((ValueProvider<UsageDto, ?>) UsageDto::getStatus, "table.column.status", "180px")
            .setComparator(Comparator.comparing(status -> status.getStatus().name()));
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "200px");
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "140px");
        usagesGrid.addColumn((ValueProvider<UsageDto, ?>) UsageDto::getSystemTitle)
            .setHeader(ForeignUi.getMessage("table.column.system_title"))
            .setFlexGrow(0)
            .setSortable(true)
            .setResizable(true);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "170px");
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "300px");
    }

    private Column<UsageDto> addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String width) {
        return usagesGrid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setFlexGrow(0)
            .setWidth(width)
            .setSortable(true)
            .setResizable(true);
    }

    private HorizontalLayout initButtonsLayout() {
        var buttonsLayout = new HorizontalLayout();
        updateButton = Buttons.createButton(ForeignUi.getMessage("button.update"));
        updateButton.addClickListener(event -> Windows.showModalWindow(new FasEditMultipleUsagesWindow(controller,
            this, usagesGrid.getSelectedItems().stream().map(UsageDto::getId).collect(Collectors.toList()))));
        updateButton.setEnabled(false);
        var closeButton = Buttons.createCloseButton(this);
        VaadinUtils.setButtonsAutoDisabled(updateButton);
        buttonsLayout.add(updateButton, closeButton);
        return buttonsLayout;
    }

    private void performSearch() {
        dataProvider.clearFilters();
        var searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.addFilter(
                value -> StringUtils.containsIgnoreCase(Objects.toString(value.getWrWrkInst(), null), searchValue)
                    || StringUtils.containsIgnoreCase(value.getSystemTitle(), searchValue));
        }
    }
}
