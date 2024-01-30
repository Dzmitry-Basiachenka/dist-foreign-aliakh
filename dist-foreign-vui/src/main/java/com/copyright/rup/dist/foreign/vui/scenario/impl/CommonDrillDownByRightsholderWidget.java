package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.HideGridColumnsProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.Function;

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
    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private ICommonDrillDownByRightsholderController controller;
    private SearchWidget searchWidget;
    private Grid<UsageDto> grid;
    private DataProvider<UsageDto, Void> dataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public CommonDrillDownByRightsholderWidget init() {
        super.setWidth("1280px");
        super.setHeight("600px");
        super.add(initContent());
        super.setModalWindowProperties("drill-down-by-rightsholder-widget", true);
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

    /**
     * @return columns to be excluded from the menu button.
     */
    protected abstract String[] getExcludedColumns();

    /**
     * Adds a column.
     *
     * @param provider        a {@link ValueProvider} for the column value
     * @param captionProperty caption property
     * @param sort            sort property
     */
    protected void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort) {
        grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setResizable(true);
    }

    /**
     * Adds a column.
     *
     * @param provider        a {@link ValueProvider} for the column value
     * @param captionProperty caption property
     * @param sort            sort property
     * @param width           column width
     */
    protected void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort,
                             String width) {
        grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setFlexGrow(0)
            .setWidth(width)
            .setResizable(true);
    }

    /**
     * Adds a column, formatted as amount.
     *
     * @param function        a {@link Function} to get {@link BigDecimal} value from {@link UsageDto}
     * @param captionProperty caption property
     * @param sort            sort property
     * @param width           column width
     */
    protected void addAmountColumn(Function<UsageDto, BigDecimal> function, String captionProperty, String sort,
                                   String width) {
        grid.addColumn(usageDto -> CurrencyUtils.format(function.apply(usageDto), null))
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setClassNameGenerator(item -> STYLE_ALIGN_RIGHT)
            .setFlexGrow(0)
            .setWidth(width)
            .setResizable(true);
    }

    /**
     * Formats fiscal year.
     *
     * @param fiscalYear fiscal year
     * @return formatted fiscal year
     */
    protected String formatFiscalYear(Integer fiscalYear) {
        return Objects.nonNull(fiscalYear) ? "FY" + fiscalYear : null;
    }

    /**
     * Formats service fee.
     *
     * @param serviceFee service fee
     * @return formatted service fee
     */
    protected String formatServiceFee(BigDecimal serviceFee) {
        return Objects.nonNull(serviceFee)
            ? Objects.toString(serviceFee.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP))
            : StringUtils.EMPTY;
    }

    private VerticalLayout initContent() {
        initGrid();
        getFooter().add(Buttons.createCloseButton(this));
        return VaadinUtils.initSizeFullVerticalLayout(initToolbar(), grid);
    }

    private void initGrid() {
        dataProvider = DataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getSize());
        grid = new Grid<>();
        grid.setItems(dataProvider);
        addColumns();
        grid.getColumns().forEach(usageDtoColumn -> usageDtoColumn.setSortable(true));
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        VaadinUtils.setGridProperties(grid, "drill-down-by-rightsholder-table");
    }

    private HorizontalLayout initToolbar() {
        initSearchWidget();
        HideGridColumnsProvider<UsageDto> hideGridColumnsProvider =
            new HideGridColumnsProvider<>();
        hideGridColumnsProvider.hideColumns(grid.getColumns(), getExcludedColumns());
        Button menuButton = hideGridColumnsProvider.getMenuButton();
        var toolbar = new HorizontalLayout(new Div(), searchWidget, menuButton);
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        toolbar.setWidthFull();
        VaadinUtils.setPadding(toolbar, 0, 3, 0, 0);
        VaadinUtils.addComponentStyle(toolbar, "drill-down-by-rightsholder-toolbar");
        return toolbar;
    }

    private void initSearchWidget() {
        searchWidget = new SearchWidget(() -> dataProvider.refreshAll());
        searchWidget.setPrompt(getSearchPrompt());
        searchWidget.setWidth("60%");
    }
}
