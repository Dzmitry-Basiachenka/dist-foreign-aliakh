package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

/**
 * Contains common functionality for widget that displays drill down report.
 * Shows information about {@link com.copyright.rup.dist.foreign.domain.UsageDto} for selected {@link
 * com.copyright.rup.dist.common.domain.Rightsholder}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanslau Rudak
 */
public abstract class CommonDrillDownByRightsholderWidget extends Window
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
        setWidth(1280, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "drill-down-by-rightsholder-widget");
        setContent(initContent());
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
     *
     * @see #addColumn(ValueProvider, String, String)
     * @see #addColumn(ValueProvider, String, String, boolean, double)
     * @see #addAmountColumn(Function, String, String, double)
     */
    protected abstract void addColumns();

    /**
     * Adds a column.
     *
     * @param provider        a {@link ValueProvider} for the column value
     * @param captionProperty caption property
     * @param sort            sort property
     */
    protected void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true);
    }

    /**
     * Adds a column.
     *
     * @param provider        a {@link ValueProvider} for the column value
     * @param captionProperty caption property
     * @param sort            sort property
     * @param isHidable       {@code true} if user should be able to hide the column, {@code false} - otherwise
     * @param width           column width
     */
    protected void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort,
                             boolean isHidable,
                             double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(isHidable)
            .setWidth(width);
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
                                   double width) {
        grid.addColumn(usageDto -> CurrencyUtils.format(function.apply(usageDto), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setWidth(width);
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
            ? Objects.toString(serviceFee.multiply(new BigDecimal("100")).setScale(1, BigDecimal.ROUND_HALF_UP))
            : StringUtils.EMPTY;
    }

    private VerticalLayout initContent() {
        initGrid();
        HorizontalLayout buttonsLayout = new HorizontalLayout(Buttons.createCloseButton(this));
        VerticalLayout content = new VerticalLayout(initSearchWidget(), grid, buttonsLayout);
        content.setSizeFull();
        content.setMargin(new MarginInfo(false, true, true, true));
        content.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        content.setExpandRatio(grid, 1);
        return content;
    }

    private void initGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getSize());
        grid = new Grid<>(dataProvider);
        addColumns();
        grid.getColumns().forEach(usageDtoColumn -> usageDtoColumn.setSortable(true));
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "drill-down-by-rightsholder-table");
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(() -> dataProvider.refreshAll());
        searchWidget.setPrompt(getSearchPrompt());
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        HorizontalLayout layout = new HorizontalLayout(searchWidget);
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        return layout;
    }
}
