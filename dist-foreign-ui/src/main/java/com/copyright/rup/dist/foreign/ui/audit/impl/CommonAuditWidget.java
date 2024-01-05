package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Common implementation for audit widgets.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Aliaksandr Radkevich
 */
public abstract class CommonAuditWidget extends HorizontalSplitPanel implements ICommonAuditWidget {

    private static final String EMPTY_STYLE_NAME = "empty-audit-grid";

    private static final long serialVersionUID = 3719304140302915594L;

    private ICommonAuditController controller;
    private SearchWidget searchWidget;
    private Grid<UsageDto> auditGrid;
    private DataProvider<UsageDto, Void> dataProvider;

    @SuppressWarnings("unchecked")
    @Override
    public ICommonAuditWidget init() {
        initContent();
        setSplitPosition(200, Unit.PIXELS);
        setLocked(true);
        VaadinUtils.addComponentStyle(this, "audit-widget");
        return this;
    }

    @Override
    public void setController(ICommonAuditController controller) {
        this.controller = controller;
    }

    @Override
    public ICommonAuditController getController() {
        return controller;
    }

    @Override
    public void refresh() {
        dataProvider.refreshAll();
    }

    @Override
    public String getSearchValue() {
        return StringUtils.defaultIfBlank(searchWidget.getSearchValue(), null);
    }

    /**
     * Gets the audit grid.
     *
     * @return audit grid
     */
    public Grid<UsageDto> getAuditGrid() {
        return auditGrid;
    }

    /**
     * @return message for search field
     */
    protected abstract String initSearchMessage();

    /**
     * Adds a column to the audit grid.
     *
     * @param provider        the column value provider
     * @param captionProperty the column caption property
     * @param sort            the column sort
     * @param width           the column width
     */
    protected void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, double width) {
        auditGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setWidth(width);
    }

    /**
     * Adds an amount column to the audit grid.
     *
     * @param function        the column amount function
     * @param captionProperty the column caption property
     * @param sort            the column sort
     * @param width           the column width
     */
    protected void addAmountColumn(Function<UsageDto, BigDecimal> function, String captionProperty, String sort,
                                   double width) {
        auditGrid.addColumn(usageDto -> CurrencyUtils.format(function.apply(usageDto), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setStyleGenerator(item -> "v-align-right")
            .setHidable(true)
            .setWidth(width);
    }

    /**
     * Adds columns to the audit grid.
     */
    protected abstract void addColumns();

    private void initContent() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getSize();
                if (0 < size) {
                    auditGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    auditGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                return size;
            });
        auditGrid = new Grid<>(dataProvider);
        auditGrid.setSelectionMode(SelectionMode.NONE);
        VaadinUtils.addComponentStyle(auditGrid, "audit-grid");
        auditGrid.setSizeFull();
        addColumns();
        auditGrid.getColumns().forEach(column -> column.setSortable(true));
        ICommonAuditFilterWidget filterWidget = controller.getAuditFilterController().initWidget();
        filterWidget.addListener(FilterChangedEvent.class, controller, ICommonAuditController.ON_FILTER_CHANGED);
        searchWidget = new SearchWidget(this::refresh);
        searchWidget.setPrompt(ForeignUi.getMessage(initSearchMessage()));
        searchWidget.setWidth(75, Unit.PERCENTAGE);
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        fileDownloader.extend(exportButton);
        HorizontalLayout toolbar = new HorizontalLayout(exportButton, searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        toolbar.setComponentAlignment(exportButton, Alignment.BOTTOM_LEFT);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setExpandRatio(searchWidget, 1f);
        toolbar.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.addComponentStyle(toolbar, "audit-toolbar");
        VerticalLayout layout = new VerticalLayout(toolbar, auditGrid);
        layout.setSizeFull();
        layout.setExpandRatio(auditGrid, 1f);
        layout.setMargin(false);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "audit-layout");
        addComponents(filterWidget, layout);
    }
}
