package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * Common usages widget for {@link ICommonUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
public abstract class CommonUsageWidget extends HorizontalSplitPanel implements ICommonUsageWidget {

    private static final String EMPTY_STYLE_NAME = "empty-usages-grid";

    private ICommonUsageController controller;
    private ICommonUsageFilterWidget usagesFilterWidget;
    private Grid<UsageDto> usagesGrid;

    @Override
    public void fireWidgetEvent(Event event) {
        fireEvent(event);
    }

    @Override
    public void refresh() {
        usagesGrid.deselectAll();
        usagesGrid.getDataProvider().refreshAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ICommonUsageWidget init() {
        usagesFilterWidget = controller.initUsagesFilterWidget();
        setFirstComponent(usagesFilterWidget);
        setSecondComponent(initUsagesLayout());
        setSplitPosition(200, Unit.PIXELS);
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(ICommonUsageController controller) {
        this.controller = controller;
    }

    @Override
    public ICommonUsageController getController() {
        return controller;
    }

    /**
     * @return instance of {@link ICommonUsageFilterWidget}.
     */
    protected ICommonUsageFilterWidget getFilterWidget() {
        return usagesFilterWidget;
    }

    /**
     * Adds columns to data grid.
     *
     * @see #addColumn(ValueProvider, String, String, boolean, double)
     * @see #addAmountColumn(Function, String, String, double)
     */
    protected abstract void addGridColumns();

    /**
     * @return instance of {@link HorizontalLayout} that contains available buttons.
     */
    protected abstract HorizontalLayout initButtonsLayout();

    /**
     * Adds column to the grid.
     *
     * @param provider        value provider
     * @param captionProperty property of the column's caption
     * @param sort            sort property
     * @param isHidable       sets whether this column can be hidden by the user
     * @param width           width of the column
     */
    protected void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort,
                             boolean isHidable, double width) {
        usagesGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
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
    protected void addAmountColumn(Function<UsageDto, BigDecimal> function, String captionProperty, String sort,
                                   double width) {
        usagesGrid.addColumn(usageDto -> CurrencyUtils.format(function.apply(usageDto), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setSortProperty(sort)
            .setHidable(true)
            .setStyleGenerator(item -> "v-align-right")
            .setWidth(width);
    }

    /**
     * Shows create scenario window.
     *
     * @param window implementation of window
     */
    protected void onAddToScenarioClicked(Window window) {
        String message = getScenarioValidationMessage();
        if (null != message) {
            Windows.showNotificationWindow(message);
        } else {
            showCreateScenarioWindow(window);
        }
    }

    /**
     * Initializes and sets data provided for usages grid.
     *
     * @param grid grid to set initialized data provider
     */
    protected void initDataProvider(Grid<UsageDto> grid) {
        grid.setDataProvider(
            LoadingIndicatorDataProvider.fromCallbacks(
                query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
                query -> {
                    int size = controller.getBeansCount();
                    if (0 < size) {
                        grid.removeStyleName(EMPTY_STYLE_NAME);
                    } else {
                        grid.addStyleName(EMPTY_STYLE_NAME);
                    }
                    return size;
                }));
    }

    /**
     * Sets grid selection mode. By default {@link Grid.SelectionMode#NONE} is set.
     *
     * @param grid grid to set selection mode
     */
    protected void setGridSelectionMode(Grid<UsageDto> grid) {
        grid.setSelectionMode(Grid.SelectionMode.NONE);
    }

    /**
     * Gets specific validation message.
     *
     * @return message
     */
    protected abstract String getProductFamilySpecificScenarioValidationMessage();

    private void showCreateScenarioWindow(Window window) {
        window.addListener(ScenarioCreateEvent.class, getController(), ICommonUsageController.ON_SCENARIO_CREATED);
        Windows.showModalWindow(window);
    }

    private String getScenarioValidationMessage() {
        String message;
        if (0 == controller.getBeansCount()) {
            message = ForeignUi.getMessage("message.error.empty_usages");
        } else if (!controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE)) {
            message = ForeignUi.getMessage("message.error.invalid_usages_status", UsageStatusEnum.ELIGIBLE,
                "added to scenario");
        } else {
            List<Long> accountNumbers = controller.getInvalidRightsholders();
            if (CollectionUtils.isNotEmpty(accountNumbers)) {
                message = ForeignUi.getMessage("message.error.add_to_scenario.invalid_rightsholders", "created",
                    accountNumbers);
            } else {
                message = getProductFamilySpecificScenarioValidationMessage();
            }
        }
        return message;
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
        usagesGrid = new Grid<>();
        initDataProvider(usagesGrid);
        setGridSelectionMode(usagesGrid);
        addGridColumns();
        usagesGrid.setSizeFull();
        VaadinUtils.addComponentStyle(usagesGrid, "usages-grid");
    }
}
