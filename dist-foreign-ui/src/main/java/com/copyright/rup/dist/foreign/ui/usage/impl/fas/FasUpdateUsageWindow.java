package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUpdateUsageWindow;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

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
 * Window to display FAS usages to update.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/09/2023
 *
 * @author Aliaksandr Liakh
 */
class FasUpdateUsageWindow extends Window implements IFasUpdateUsageWindow {

    private static final long serialVersionUID = 3933778706035133427L;

    private final IFasUsageController controller;
    private SearchWidget searchWidget;
    private Grid<UsageDto> usagesGrid;
    private MultiSelectionModelImpl<UsageDto> gridSelectionModel;
    private ListDataProvider<UsageDto> dataProvider;
    private Button updateButton;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IFasUsageController}
     */
    FasUpdateUsageWindow(IFasUsageController controller) {
        this.controller = controller;
        super.setWidth(1280, Unit.PIXELS);
        super.setHeight(530, Unit.PIXELS);
        super.setContent(initContent());
        super.setCaption(ForeignUi.getMessage("window.update_usages"));
        VaadinUtils.addComponentStyle(this, "update-usages-window");
    }

    @Override
    public void refresh() {
        performSearch();
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

    private void initSearchWidget() {
        searchWidget = new SearchWidget(this::refresh);
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.rightsholder_update"));
        searchWidget.setWidth(70, Unit.PERCENTAGE);
    }

    private void initGrid() {
        List<UsageDto> usages = controller.getUsageDtosToUpdate();
        dataProvider = DataProvider.ofCollection(usages);
        usagesGrid = new Grid<>(dataProvider);
        usagesGrid.setSizeFull();
        gridSelectionModel = (MultiSelectionModelImpl<UsageDto>) usagesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        switchSelectAllCheckBoxVisibility(usages.size());
        addGridColumns();
        usagesGrid.addSelectionListener(event ->
            updateButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        usagesGrid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(usagesGrid, "update-fas-usages-grid");
    }

    private void switchSelectAllCheckBoxVisibility(int beansCount) {
        gridSelectionModel.setSelectAllCheckBoxVisibility(
            0 == beansCount || beansCount > controller.getRecordsThreshold()
                ? SelectAllCheckBoxVisibility.HIDDEN
                : SelectAllCheckBoxVisibility.VISIBLE);
    }

    private void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", 250);
        usagesGrid.addColumn((ValueProvider<UsageDto, ?>) UsageDto::getStatus)
            .setCaption(ForeignUi.getMessage("table.column.status"))
            .setComparator((status1, status2) -> status1.getStatus().name().compareTo(status2.getStatus().name()))
            .setWidth(150);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", 170);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", 100);
        usagesGrid.addColumn((ValueProvider<UsageDto, ?>) UsageDto::getSystemTitle)
            .setCaption(ForeignUi.getMessage("table.column.system_title"))
            .setExpandRatio(1);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", 100);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", 260);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, double width) {
        usagesGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setWidth(width);
    }

    private HorizontalLayout buildButtonsLayout() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        updateButton = Buttons.createButton(ForeignUi.getMessage("button.update"));
        updateButton.addClickListener(event -> Windows.showModalWindow(new FasEditMultipleUsagesWindow(controller,
            this, usagesGrid.getSelectedItems().stream().map(UsageDto::getId).collect(Collectors.toList()))));
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
