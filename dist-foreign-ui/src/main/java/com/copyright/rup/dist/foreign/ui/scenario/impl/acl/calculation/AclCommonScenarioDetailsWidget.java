package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclCommonScenarioDetailsController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclCommonScenarioDetailsWidget;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Implementation of {@link IAclCommonScenarioDetailsWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
public abstract class AclCommonScenarioDetailsWidget extends Window implements IAclCommonScenarioDetailsWidget {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private IAclCommonScenarioDetailsController controller;
    private SearchWidget searchWidget;
    private Grid<AclScenarioDetailDto> grid;
    private DataProvider<AclScenarioDetailDto, Void> dataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public AclCommonScenarioDetailsWidget init() {
        setWidth(1280, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "acl-scenario-details-widget");
        setContent(initContent());
        return this;
    }

    @Override
    public String getSearchValue() {
        return StringUtils.trimToNull(searchWidget.getSearchValue());
    }

    @Override
    public void setController(IAclCommonScenarioDetailsController controller) {
        this.controller = controller;
    }

    private VerticalLayout initContent() {
        initGrid();
        HorizontalLayout buttonsLayout = initButtons();
        VerticalLayout content = new VerticalLayout(initSearchWidget(), grid, buttonsLayout);
        content.setSizeFull();
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
        grid.getColumns().forEach(column -> column.setSortable(true));
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "acl-scenario-details-grid");
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

    /**
     * @return the search prompt.
     */
    protected abstract String getSearchPrompt();

    /**
     * @return buttons layout.
     */
    protected abstract HorizontalLayout initButtons();

    /**
     * Adds columns to the grid.
     */
    protected abstract void addColumns();

    /**
     * Adds a Big Decimal column.
     *
     * @param function        a {@link Function} to get {@link BigDecimal} value from {@link AclScenarioDetailDto}
     * @param captionProperty caption property
     * @param sort            sort property
     * @param width           column width
     */
    protected void addBigDecimalColumn(Function<AclScenarioDetailDto, BigDecimal> function, String captionProperty,
                                       String sort, double width) {
        grid.addColumn(detail -> BigDecimalUtils.formatCurrencyForGrid(function.apply(detail)))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setWidth(width);
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
    protected void addColumn(ValueProvider<AclScenarioDetailDto, ?> provider, String captionProperty, String sort,
                             boolean isHidable, double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(isHidable)
            .setWidth(width);
    }

    /**
     * Adds a column, formatted as amount.
     *
     * @param function        a {@link Function} to get {@link BigDecimal} value from {@link AclScenarioDetailDto}
     * @param captionProperty caption property
     * @param sort            sort property
     * @param width           column width
     */
    protected void addAmountColumn(Function<AclScenarioDetailDto, BigDecimal> function, String captionProperty,
                                   String sort, double width) {
        grid.addColumn(detail -> CurrencyUtils.format(function.apply(detail), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setWidth(width);
    }

    /**
     * Adds a boolean column.
     *
     * @param provider        a {@link ValueProvider} for the column value
     * @param captionProperty caption property
     * @param columnId        id property
     * @param width           column width
     */
    protected void addBooleanColumn(ValueProvider<AclScenarioDetailDto, Boolean> provider, String captionProperty,
                                    String columnId, double width) {
        grid.addColumn(value -> BooleanUtils.toYNString(provider.apply(value)))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }
}
