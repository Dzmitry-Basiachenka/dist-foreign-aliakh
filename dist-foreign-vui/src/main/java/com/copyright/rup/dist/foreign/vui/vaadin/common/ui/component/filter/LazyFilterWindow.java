package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.AbstractGridMultiSelectionModel;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;

/**
 * Class represents modal window containing {@link Grid} component with lazy load, search toolbar, buttons toolbar.
 * Window provides ability to search and select items of specified type.
 * Searching should be implemented as a part of {@link ILazyFilterWindowController#loadBeans} method.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 11/24/2021
 *
 * @param <T> bean type
 * @author Ihar Suvorau
 */
public class LazyFilterWindow<T> extends CommonFilterWindow<T> {

    private static final long serialVersionUID = -3551911397094518841L;

    private final ILazyFilterWindowController<T> controller;
    private final SearchWidget searchWidget;
    private Grid<T> grid;
    private DataProvider<T, Void> dataProvider;

    /**
     * Constructor.
     *
     * @param caption    window caption
     * @param controller {@link IFilterWindowController} instance
     */
    public LazyFilterWindow(String caption, ILazyFilterWindowController<T> controller) {
        this(caption, controller, null, null);
    }

    /**
     * Constructor.
     *
     * @param caption            window caption
     * @param controller         {@link IFilterWindowController} instance
     * @param saveButtonCaption  caption of the Save button
     * @param clearButtonCaption caption of the Clear button
     */
    public LazyFilterWindow(String caption, ILazyFilterWindowController<T> controller, String saveButtonCaption,
                            String clearButtonCaption) {
        super(caption);
        this.controller = Objects.requireNonNull(controller);
        super.addFilterSaveListener(controller);
        super.setWidth(550, Unit.PIXELS);
        super.setHeight(530, Unit.PIXELS);
        searchWidget = new SearchWidget(() -> dataProvider.refreshAll());
        var content = VaadinUtils.initSizeFullVerticalLayout(searchWidget, initGrid());
        VaadinUtils.setPadding(content, 10, 10, 10, 10);
        super.getFooter().add(createButtonsLayout(saveButtonCaption, clearButtonCaption));
        super.add(content);
    }

    /**
     * Sets selected items ids.
     *
     * @param selectedItemsIds collection of selected items ids
     */
    public void setSelectedItemsIds(Set<T> selectedItemsIds) {
        grid.deselectAll();
        selectedItemsIds.forEach(row -> grid.select(row));
    }

    /**
     * @return collection (Set) of selected items ids.
     */
    public Set<T> getSelectedItemsIds() {
        return grid.getSelectedItems();
    }

    /**
     * Sets search widget prompt string. Only takes effect when searching is allowed.
     *
     * @param promptString prompt string to be displayed when search field is empty
     */
    public void setSearchPromptString(String promptString) {
        searchWidget.setPrompt(promptString);
    }

    /**
     * Fires an {@link CommonFilterWindow.FilterSaveEvent} with selected values.
     */
    protected void fireFilterSaveEvent() {
        fireEvent(new CommonFilterWindow.FilterSaveEvent<>(this, grid.getSelectedItems()));
    }

    private HorizontalLayout createButtonsLayout(String saveButtonCaption, String clearButtonCaption) {
        Button saveButton = Buttons.createButton(Objects.nonNull(saveButtonCaption) ? saveButtonCaption : "Save");
        saveButton.addClickListener(event -> {
            UI.getCurrent().remove(this);
            fireFilterSaveEvent();
        });
        Button clearButton = Buttons.createButton(Objects.nonNull(clearButtonCaption) ? clearButtonCaption : "Clear");
        clearButton.addClickListener(event -> grid.deselectAll());
        return new HorizontalLayout(saveButton, clearButton, Buttons.createCloseButton(this));
    }

    private Grid<T> initGrid() {
        dataProvider = DataProvider.fromCallbacks(
            query -> controller.loadBeans(getSearchValue(),
                query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getBeansCount(getSearchValue()));
        grid = new Grid<>();
        grid.setItems(dataProvider);
        grid.setSelectionMode(SelectionMode.MULTI);
        ((AbstractGridMultiSelectionModel<T>) grid.getSelectionModel()).setSelectionColumnFrozen(true);
        grid.addColumn(controller.getGridColumnValueProvider()).setAutoWidth(true);
        VaadinUtils.setGridProperties(grid, "lazy-filter-widget-grid");
        return grid;
    }

    private String getSearchValue() {
        return StringUtils.defaultIfBlank(searchWidget.getSearchValue(), null);
    }
}
