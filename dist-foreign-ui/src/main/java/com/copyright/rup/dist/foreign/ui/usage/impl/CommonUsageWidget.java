package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Common usages widget for {@link ICommonUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @param <C> controller instance
 * @param <W> widget instance
 * @author Uladzislau Shalamitski
 */
public abstract class CommonUsageWidget<W extends ICommonUsageWidget<W, C>,
    C extends ICommonUsageController<W, C>> extends HorizontalSplitPanel implements ICommonUsageWidget<W, C> {

    private static final String EMPTY_STYLE_NAME = "empty-usages-grid";

    private C controller;
    private DataProvider<UsageDto, Void> dataProvider;
    private Grid<UsageDto> usagesGrid;
    private IUsagesFilterWidget usagesFilterWidget;

    @Override
    public void fireWidgetEvent(Event event) {
        fireEvent(event);
    }

    @Override
    public void refresh() {
        dataProvider.refreshAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public W init() {
        usagesFilterWidget = controller.initUsagesFilterWidget();
        setFirstComponent(usagesFilterWidget);
        setSecondComponent(initUsagesLayout());
        setSplitPosition(200, Unit.PIXELS);
        setLocked(true);
        setSizeFull();
        return (W) this;
    }

    @Override
    public void setController(C controller) {
        this.controller = controller;
    }

    @Override
    public C getController() {
        return controller;
    }

    /**
     * @return instance of {@link IUsagesFilterWidget}.
     */
    IUsagesFilterWidget getFilterWidget() {
        return usagesFilterWidget;
    }

    /**
     * Adds columns to data grid.
     *
     * @see #addColumn(ValueProvider, String, String, boolean, double)
     * @see #addAmountColumn(Function, String, String, double)
     */
    abstract void addGridColumns();

    /**
     * @return instance of {@link HorizontalLayout} that contains available buttons.
     */
    abstract HorizontalLayout initButtonsLayout();

    /**
     * Adds column to the grid.
     *
     * @param provider        value provider
     * @param captionProperty property of the column's caption
     * @param sort            sort property
     * @param isHidable       sets whether this column can be hidden by the user
     * @param width           width of the column
     */
    void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, boolean isHidable,
                   double width) {
        usagesGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(isHidable)
            .setWidth(width);
    }

    /**
     * Adds amount column to the grid.
     *
     * @param function        function to get value
     * @param captionProperty property of the column's caption
     * @param sort            sort property
     * @param width           width of the column
     */
    void addAmountColumn(Function<UsageDto, BigDecimal> function, String captionProperty, String sort, double width) {
        usagesGrid.addColumn(usageDto -> CurrencyUtils.format(function.apply(usageDto), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setStyleGenerator(item -> "v-align-right")
            .setWidth(width);
    }

    /**
     * Shows create scenario window.
     *
     * @param window window to show
     */
    void showCreateScenarioWindow(Window window) {
        window.addListener(ScenarioCreateEvent.class, getController(), ICommonUsageController.ON_SCENARIO_CREATED);
        Windows.showModalWindow(window);
    }

    private VerticalLayout initUsagesLayout() {
        initUsagesGrid();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), usagesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(usagesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "usages-layout");
        return layout;
    }

    private void initUsagesGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    usagesGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    usagesGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                return size;
            });
        usagesGrid = new Grid<>(dataProvider);
        addGridColumns();
        usagesGrid.setSelectionMode(Grid.SelectionMode.NONE);
        usagesGrid.setSizeFull();
        usagesGrid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(usagesGrid, "usages-grid");
    }
}
