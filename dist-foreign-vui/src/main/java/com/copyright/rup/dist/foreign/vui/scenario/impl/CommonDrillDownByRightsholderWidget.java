package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;

/**
 * Contains common functionality for widget that displays drill down report.
 * Shows information about {@link UsageDto} for selected {@link
 * com.copyright.rup.dist.common.domain.Rightsholder}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanslau Rudak
 */
public abstract class CommonDrillDownByRightsholderWidget extends CommonDialog
    implements ICommonDrillDownByRightsholderWidget {

    private static final long serialVersionUID = 5513951425218447888L;

    private ICommonDrillDownByRightsholderController controller;
    private SearchWidget searchWidget;
    private Grid<UsageDto> grid;
    private DataProvider<UsageDto, Void> dataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public CommonDrillDownByRightsholderWidget init() {
        super.setWidth("1280px");
        super.setHeight("600px");
        super.setModalWindowProperties("drill-down-by-rightsholder-widget", true);
        super.add(initContent());
        return this;
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public void setController(ICommonDrillDownByRightsholderController controller) {
        this.controller = controller;
    }

    /**
     * @return the search prompt.
     */
    protected abstract String getSearchPrompt();

    /**
     * Adds columns to the grid.
     */
    protected abstract void addColumns();

    private VerticalLayout initContent() {
        initGrid();
        var content = new VerticalLayout(initSearchWidget(), grid);
        getFooter().add(new HorizontalLayout(Buttons.createCloseButton(this)));
        content.setSizeFull();
        return content;
    }

    private void initGrid() {
        dataProvider = DataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getSize());
        VaadinUtils.setGridProperties(grid, "drill-down-by-rightsholder-table");
        grid = new Grid<>();
        grid.setItems(dataProvider);
        addColumns();
        grid.getColumns().forEach(usageDtoColumn -> usageDtoColumn.setSortable(true));
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(() -> dataProvider.refreshAll());
        searchWidget.setPrompt(getSearchPrompt());
        searchWidget.setWidth("60%");
        var layout = new HorizontalLayout(searchWidget);
        layout.setWidth("100%");
        return layout;
    }
}
