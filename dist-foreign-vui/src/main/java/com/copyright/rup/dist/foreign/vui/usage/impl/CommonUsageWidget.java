package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.HideGridColumnsProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * Common usages widget for {@link ICommonUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/05/2019
 *
 * @author Uladzislau Shalamitski
 */
public abstract class CommonUsageWidget extends SplitLayout implements ICommonUsageWidget {

    private static final long serialVersionUID = 6521223260066415297L;
    private static final String EMPTY_STYLE_NAME = "empty-usages-grid";

    private ICommonUsageController controller;
    private ICommonUsageFilterWidget usagesFilterWidget;
    private Grid<UsageDto> usagesGrid;
    private HideGridColumnsProvider<UsageDto> hideGridColumnsProvider;

    @Override
    public void fireWidgetEvent(ComponentEvent<Component> event) {
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
        usagesFilterWidget = initUsagesFilterWidget();
        addToPrimary((CommonUsageFilterWidget) usagesFilterWidget);
        addToSecondary(initContent());
        setSplitterPosition(15);
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
     */
    protected abstract void addGridColumns();

    /**
     * @return instance of {@link HorizontalLayout} that contains available buttons.
     */
    protected abstract HorizontalLayout initButtonsLayout();

    /**
     * Adds column to the grid.
     *
     * @param provider   value provider
     * @param gridColumn grid column
     * @return the column for method chaining
     */
    protected Column<UsageDto> addColumn(ValueProvider<UsageDto, ?> provider, GridColumnEnum gridColumn) {
        return usagesGrid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(gridColumn.getCaption()))
            .setSortProperty(gridColumn.getSort())
            .setFlexGrow(0)
            .setWidth(gridColumn.getWidth())
            .setSortable(true)
            .setResizable(true);
    }

    /**
     * Adds amount column to the grid.
     *
     * @param function   function to get value
     * @param gridColumn grid column
     * @return the column for method chaining
     */
    protected Column<UsageDto> addAmountColumn(Function<UsageDto, BigDecimal> function, GridColumnEnum gridColumn) {
        return addColumn(usageDto -> CurrencyUtils.format(function.apply(usageDto), null), gridColumn)
            .setClassNameGenerator(item -> "label-amount");
    }

    /**
     * Shows create scenario window.
     *
     * @param window implementation of window
     */
    protected void onAddToScenarioClicked(Dialog window) {
        var message = getScenarioValidationMessage();
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
        grid.setItems(
            DataProvider.fromCallbacks(
                query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
                query -> {
                    int size = controller.getBeansCount();
                    if (0 < size) {
                        grid.removeClassName(EMPTY_STYLE_NAME);
                    } else {
                        grid.addClassName(EMPTY_STYLE_NAME);
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
        grid.setSelectionMode(SelectionMode.NONE);
    }

    /**
     * Gets specific validation message.
     *
     * @return message
     */
    protected abstract String getProductFamilySpecificScenarioValidationMessage();

    protected HideGridColumnsProvider<UsageDto> getHideGridColumnsProvider() {
        return hideGridColumnsProvider;
    }

    private ICommonUsageFilterWidget initUsagesFilterWidget() {
        ICommonUsageFilterWidget widget = controller.getUsageFilterController().initWidget();
        widget.addFilterSaveAction(this::refresh);
        return widget;
    }

    private void showCreateScenarioWindow(Dialog window) {
        ComponentUtil.addListener(window, ScenarioCreateEvent.class, event -> controller.onScenarioCreated(event));
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

    private VerticalLayout initContent() {
        initUsagesGrid();
        var content = VaadinUtils.initCommonVerticalLayout(initButtonsLayout(), usagesGrid);
        VaadinUtils.addComponentStyle(content, "usages-layout");
        return content;
    }

    private void initUsagesGrid() {
        usagesGrid = new Grid<>();
        initDataProvider(usagesGrid);
        setGridSelectionMode(usagesGrid);
        addGridColumns();
        VaadinUtils.setGridProperties(usagesGrid, "usages-grid");
        hideGridColumnsProvider = new HideGridColumnsProvider<>(usagesGrid);
    }
}
