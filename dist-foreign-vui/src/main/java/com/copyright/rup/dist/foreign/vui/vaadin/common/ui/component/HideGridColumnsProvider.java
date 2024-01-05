package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

/**
 * Represents initialization of context menu to be able to hide or unhide columns on the grid.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 05/17/2023
 *
 * @param <T> type of parametrized grid
 * @author Anton Azarenka
 */
public class HideGridColumnsProvider<T> {

    private final Button menuButton;
    private final HideColumnsContextMenu<T> columnToggleContextMenu;
    private Grid<T> grid;

    /**
     * Default constructor.
     */
    public HideGridColumnsProvider() {
        this.menuButton = new Button(new Icon(VaadinIcon.MENU));
        menuButton.setTooltipText("Hide/Unhide");
        this.columnToggleContextMenu = new HideColumnsContextMenu<>(menuButton);
    }

    /**
     * Constructor.
     *
     * @param grid instance of {@link Grid}
     */
    public HideGridColumnsProvider(Grid<T> grid) {
        this();
        this.grid = grid;
        initContextMenu();
    }

    /**
     * Sets ability to hide all columns on the table.
     */
    public final void initContextMenu() {
        List<Column<T>> columns = grid.getColumns();
        columns.forEach(this::hideColumn);
    }

    /**
     * Add column to hide to context menu.
     *
     * @param column instance of {@link Column}
     */
    public void hideColumn(Column<T> column) {
        columnToggleContextMenu.addColumnToggleItem(column.getHeaderText(), column);
    }

    /**
     * Add columns to hide to context menu and excludes provided columns.
     *
     * @param columns         columns of grid
     * @param excludedColumns excluded columns
     */
    public void hideColumns(List<Column<T>> columns, String... excludedColumns) {
        if (ArrayUtils.isNotEmpty(excludedColumns)) {
            List<String> listExcludedColumns = List.of(excludedColumns);
            columns.forEach(column -> {
                if (!listExcludedColumns.contains(column.getHeaderText())) {
                    hideColumn(column);
                }
            });
        } else {
            columns.forEach(this::hideColumn);
        }
    }

    /**
     * @return menu button.
     */
    public Button getMenuButton() {
        return menuButton;
    }

    /**
     * @return context menu
     */
    public HideColumnsContextMenu<T> getContextMenu() {
        return columnToggleContextMenu;
    }

    /**
     * Class represent context menu.
     *
     * @param <T> type of parametrized grid
     */
    static class HideColumnsContextMenu<T> extends ContextMenu {

        private static final long serialVersionUID = -3824296304264715526L;

        /**
         * Constructor.
         *
         * @param component component
         */
        public HideColumnsContextMenu(Component component) {
            super(component);
            setOpenOnClick(true);
        }

        /**
         * Adds item to the menu.
         *
         * @param label  label
         * @param column column
         */
        void addColumnToggleItem(String label, Column<T> column) {
            MenuItem menuItem = this.addItem(label, element -> {
                column.setVisible(element.getSource().isChecked());
            });
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }
}
