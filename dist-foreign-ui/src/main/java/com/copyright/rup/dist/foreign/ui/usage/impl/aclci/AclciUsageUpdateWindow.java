package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IRefreshable;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.MultiSelectionModel.SelectAllCheckBoxVisibility;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Window to display ACLCI usages to update.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/28/2022
 *
 * @author Dzmitry Basiachenka
 */
class AclciUsageUpdateWindow extends Window implements IRefreshable {

    private final IAclciUsageController controller;
    private SearchWidget searchWidget;
    private Grid<UsageDto> usagesGrid;
    private MultiSelectionModelImpl<UsageDto> gridSelectionModel;
    private ListDataProvider<UsageDto> dataProvider;
    private Button updateButton;

    /**
     * Constructor.
     *
     * @param controller {@link IAclciUsageController} instance
     */
    AclciUsageUpdateWindow(IAclciUsageController controller) {
        this.controller = controller;
        setWidth(1000, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
        setContent(initContent());
        setCaption(ForeignUi.getMessage("window.update_usages"));
        VaadinUtils.addComponentStyle(this, "update-usages-window");
    }

    @Override
    public void refresh() {
        performSearch();
        dataProvider.refreshAll();
    }

    /**
     * Refreshes data provider and updates usages grid.
     */
    void refreshDataProvider() {
        List<UsageDto> usages = controller.getUsageDtosToUpdate();
        dataProvider = DataProvider.ofCollection(usages);
        usagesGrid.setDataProvider(dataProvider);
        gridSelectionModel.setSelectAllCheckBoxVisibility(
            usages.isEmpty() ? SelectAllCheckBoxVisibility.HIDDEN : SelectAllCheckBoxVisibility.VISIBLE);
        gridSelectionModel.beforeClientResponse(false);
        dataProvider.refreshAll();
    }

    private Component initContent() {
        initSearchWidget();
        initGrid();
        HorizontalLayout buttonsLayout = buildButtonsLayout();
        VerticalLayout content = new VerticalLayout(searchWidget, usagesGrid, buttonsLayout);
        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        content.setExpandRatio(usagesGrid, 1.0f);
        content.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private void initGrid() {
        dataProvider = DataProvider.ofCollection(controller.getUsageDtosToUpdate());
        usagesGrid = new Grid<>(dataProvider);
        usagesGrid.setSizeFull();
        gridSelectionModel = (MultiSelectionModelImpl<UsageDto>) usagesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        addGridColumns();
        usagesGrid.addSelectionListener(event ->
            updateButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        usagesGrid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(usagesGrid, "update-aclci-usages-grid");
    }

    private void initSearchWidget() {
        searchWidget = new SearchWidget(this::refresh);
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.rightsholder_update"));
        searchWidget.setWidth(70, Unit.PERCENTAGE);
    }

    private void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", 250);
        addColumn(UsageDto::getStatus, "table.column.status", "status", 170);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", 200);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 130);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 250);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, double width) {
        usagesGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setWidth(width);
    }

    private HorizontalLayout buildButtonsLayout() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        updateButton = Buttons.createButton(ForeignUi.getMessage("button.update"));
        updateButton.addClickListener(event -> Windows.showModalWindow(new AclciMultipleEditUsagesWindow(controller,
            this, usagesGrid.getSelectedItems().stream().map(UsageDto::getId).collect(Collectors.toSet()))));
        updateButton.setEnabled(false);
        Button closeButton = Buttons.createCloseButton(this);
        VaadinUtils.setButtonsAutoDisabled(updateButton);
        buttonsLayout.addComponents(updateButton, closeButton);
        return buttonsLayout;
    }

    private void performSearch() {
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.addFilter(
                value -> StringUtils.containsIgnoreCase(Objects.toString(value.getWrWrkInst(), null), searchValue)
                    || StringUtils.containsIgnoreCase(value.getSystemTitle(), searchValue));
        }
    }
}
