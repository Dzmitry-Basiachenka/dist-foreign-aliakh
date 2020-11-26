package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Window to display SAL IB details for RH update.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/20
 *
 * @author Darya Baraukova
 */
class SalDetailForRightsholderUpdateWindow extends Window implements IRefreshable {

    private SearchWidget searchWidget;
    private Grid<UsageDto> usagesGrid;
    private DataProvider<UsageDto, Void>  dataProvider;
    private final ISalUsageController controller;

    /**
     * Constructor.
     *
     * @param controller {@link ISalUsageController} instance
     */
    SalDetailForRightsholderUpdateWindow(ISalUsageController controller) {
        this.controller = controller;
        setWidth(1000, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
        setContent(initContent());
        setCaption(ForeignUi.getMessage("window.ib_details_for_rh_update"));
    }

    @Override
    public void refresh() {
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
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
                query ->  controller.getBeansCount());
        usagesGrid = new Grid<>(dataProvider);
        usagesGrid.setSizeFull();
        usagesGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        addGridColumns();
        usagesGrid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(usagesGrid, "ib-details-for-rh-update-grid");
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
        Button updateRightsholderButton = Buttons.createButton(ForeignUi.getMessage("button.update_rightsholder"));
        //TODO {dbaraukova} add listener for update RH button
        Button closeButton = Buttons.createCloseButton(this);
        buttonsLayout.addComponents(updateRightsholderButton, closeButton);
        return buttonsLayout;
    }
}
