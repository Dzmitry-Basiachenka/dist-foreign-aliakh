package com.copyright.rup.dist.foreign.vui.audit.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.HideGridColumnsProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
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
public abstract class CommonAuditWidget extends SplitLayout implements ICommonAuditWidget {

    private static final long serialVersionUID = 3616787468284929274L;
    private static final String EMPTY_STYLE_NAME = "empty-audit-grid";

    private ICommonAuditController controller;
    private SearchWidget searchWidget;
    private Grid<UsageDto> auditGrid;
    private DataProvider<UsageDto, Void> dataProvider;

    @Override
    public void refresh() {
        dataProvider.refreshAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ICommonAuditWidget init() {
        addToPrimary(initAuditFilter());
        addToSecondary(initContent());
        setSplitterPosition(15);
        setSizeFull();
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
     * @return the column for method chaining
     */
    protected Column<UsageDto> addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort,
                                         String width) {
        return auditGrid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setSortProperty(sort)
            .setWidth(width)
            .setFlexGrow(0)
            .setResizable(true);
    }

    /**
     * Adds an amount column to the audit grid.
     *
     * @param function        the column amount function
     * @param captionProperty the column caption property
     * @param sort            the column sort
     * @param width           the column width
     * @return the column for method chaining
     */
    protected Column<UsageDto> addAmountColumn(Function<UsageDto, BigDecimal> function, String captionProperty,
                                               String sort, String width) {
        return addColumn(usageDto -> CurrencyUtils.format(function.apply(usageDto), null), captionProperty, sort, width)
            .setClassNameGenerator(item -> "v-align-right");
    }

    /**
     * Adds a calculated service fee column to the audit grid.
     *
     * @return the column for method chaining
     */
    protected Column<UsageDto> addServiceFeeColumn() {
        return addColumn(this::calculateServiceFee, "table.column.service_fee", "serviceFee", "145px");
    }

    /**
     * Adds columns to the audit grid.
     */
    protected abstract void addColumns();

    private CommonAuditFilterWidget initAuditFilter() {
        var auditFilterWidget = (CommonAuditFilterWidget) controller.getAuditFilterController().initWidget();
        auditFilterWidget.setFilterSaveAction(this::refresh);
        return auditFilterWidget;
    }

    private VerticalLayout initContent() {
        dataProvider = DataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getSize();
                if (0 < size) {
                    auditGrid.removeClassName(EMPTY_STYLE_NAME);
                } else {
                    auditGrid.addClassName(EMPTY_STYLE_NAME);
                }
                return size;
            });
        auditGrid = new Grid<>();
        auditGrid.setItems(dataProvider);
        auditGrid.setSelectionMode(SelectionMode.NONE);
        addColumns();
        VaadinUtils.setGridProperties(auditGrid, "audit-grid");
        var layout = VaadinUtils.initSizeFullVerticalLayout(initToolbar(), auditGrid);
        VaadinUtils.addComponentStyle(layout, "audit-layout");
        return layout;
    }

    private HorizontalLayout initToolbar() {
        var exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        var fileDownloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        fileDownloader.extend(exportButton);
        searchWidget = new SearchWidget(this::refresh, ForeignUi.getMessage(initSearchMessage()), "70%");
        var hideGridColumnsProvider = new HideGridColumnsProvider<UsageDto>();
        hideGridColumnsProvider.hideColumns(auditGrid.getColumns(), "Detail ID");
        var menuButton = hideGridColumnsProvider.getMenuButton();
        var toolbar = new HorizontalLayout(fileDownloader, searchWidget, menuButton);
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        toolbar.setWidthFull();
        VaadinUtils.setPadding(toolbar, 0, 3, 0, 3);
        VaadinUtils.addComponentStyle(toolbar, "audit-toolbar");
        return toolbar;
    }

    private String calculateServiceFee(UsageDto usageDto) {
        var value = usageDto.getServiceFee();
        return Objects.nonNull(value)
            ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP))
            : StringUtils.EMPTY;
    }
}
