package com.copyright.rup.dist.foreign.vui.common;

import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.function.ValueProvider;

/**
 * Class for adding grid columns.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/27/2024
 *
 * @param <T> grid type
 * @author Aliaksandr Liakh
 */
public interface IGridColumnAdder<T> {

    /**
     * Adds column to data grid.
     *
     * @param grid          grid
     * @param valueProvider column value provider
     * @param gridColumn    grid column
     * @return the column for method chaining
     */
    default Column<T> addColumn(Grid<T> grid, ValueProvider<T, ?> valueProvider, GridColumnEnum gridColumn) {
        return grid.addColumn(valueProvider)
            .setHeader(ForeignUi.getMessage(gridColumn.getCaption()))
            .setFlexGrow(0)
            .setWidth(gridColumn.getWidth())
            .setSortable(true)
            .setResizable(true);
    }

    /**
     * Adds column to data grid.
     *
     * @param grid          grid
     * @param valueProvider column value provider
     * @param gridColumn    grid column
     * @param comparator    column comparator
     * @return the column for method chaining
     */
    default Column<T> addColumn(Grid<T> grid, ValueProvider<T, ?> valueProvider,
                                GridColumnEnum gridColumn, SerializableComparator<T> comparator) {
        return addColumn(grid, valueProvider, gridColumn).setComparator(comparator);
    }

    /**
     * Adds amount column to data grid.
     *
     * @param grid          grid
     * @param valueProvider column value provider
     * @param gridColumn    grid column
     * @param comparator    column comparator
     * @return the column for method chaining
     */
    default Column<T> addAmountColumn(Grid<T> grid, ValueProvider<T, ?> valueProvider,
                                      GridColumnEnum gridColumn, SerializableComparator<T> comparator) {
        return addColumn(grid, valueProvider, gridColumn, comparator).setClassNameGenerator(item -> "label-amount");
    }
}
