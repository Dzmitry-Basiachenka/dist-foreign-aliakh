package com.copyright.rup.dist.foreign.vui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.vaadin.flow.component.grid.ColumnPathRenderer;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.data.provider.KeyMapper;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.dom.Element;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import elemental.json.impl.JreJsonObject;

/**
 * Class for verifying grid columns.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/27/2024
 *
 * @param <T> bean type
 * @param <V> field type
 * @author Aliaksandr Liakh
 */
public class GridColumnVerifier<T, V> {

    private static final String STRING_VALUE = "value";
    private static final String MIN_STRING_VALUE = "a";
    private static final String MAX_STRING_VALUE = "Z";

    private final Column<T> column;
    private final int columnIdx;
    private final Supplier<T> itemSupplier;
    private final BiConsumer<T, V> setter;

    /**
     * Constructor.
     *
     * @param grid         grid
     * @param columnIdx    column index
     * @param itemSupplier item supplier
     * @param setter       setter
     */
    public GridColumnVerifier(Grid<T> grid, int columnIdx, Supplier<T> itemSupplier, BiConsumer<T, V> setter) {
        this.column = grid.getColumns().get(columnIdx);
        this.columnIdx = columnIdx;
        this.itemSupplier = itemSupplier;
        this.setter = setter;
    }

    /**
     * Verifies Integer column.
     *
     * @param grid         grid
     * @param columnIdx    column index
     * @param itemSupplier item supplier
     * @param setter       setter
     * @param <T>          bean type
     * @return instance of {@link GridColumnVerifier}
     */
    public static <T> GridColumnVerifier<T, Integer> verifyIntegerColumn(Grid<T> grid, int columnIdx,
                                                                         Supplier<T> itemSupplier,
                                                                         BiConsumer<T, Integer> setter) {
        return new GridColumnVerifier<>(grid, columnIdx, itemSupplier, setter)
            .verifyDataProvider(0, "0")
            .verifyComparator(0, 1)
            .verifyClassNameGenerator(null);
    }

    /**
     * Verifies case-sensitive String column.
     *
     * @param grid         grid
     * @param columnIdx    column index
     * @param itemSupplier item supplier
     * @param setter       setter
     * @param <T>          bean type
     * @return instance of {@link GridColumnVerifier}
     */
    public static <T> GridColumnVerifier<T, String> verifyStringColumn(Grid<T> grid, int columnIdx,
                                                                       Supplier<T> itemSupplier,
                                                                       BiConsumer<T, String> setter) {
        return new GridColumnVerifier<>(grid, columnIdx, itemSupplier, setter)
            .verifyDataProvider(STRING_VALUE, STRING_VALUE)
            .verifyComparator(MIN_STRING_VALUE, "z")
            .verifyComparator(MAX_STRING_VALUE, MIN_STRING_VALUE)
            .verifyClassNameGenerator(null);
    }

    /**
     * Verifies case-insensitive String column.
     *
     * @param grid         grid
     * @param columnIdx    column index
     * @param itemSupplier item supplier
     * @param setter       setter
     * @param <T>          bean type
     * @return instance of {@link GridColumnVerifier}
     */
    public static <T> GridColumnVerifier<T, String> verifyStringColumnIgnoreCase(Grid<T> grid, int columnIdx,
                                                                                 Supplier<T> itemSupplier,
                                                                                 BiConsumer<T, String> setter) {
        return new GridColumnVerifier<>(grid, columnIdx, itemSupplier, setter)
            .verifyDataProvider(STRING_VALUE, STRING_VALUE)
            .verifyComparator(MIN_STRING_VALUE, "z")
            .verifyComparator(MIN_STRING_VALUE, MAX_STRING_VALUE)
            .verifyClassNameGenerator(null);
    }

    /**
     * Verifies Date column with long format.
     *
     * @param grid         grid
     * @param columnIdx    column index
     * @param itemSupplier item supplier
     * @param setter       setter
     * @param <T>          bean type
     * @return instance of {@link GridColumnVerifier}
     */
    public static <T> GridColumnVerifier<T, Date> verifyDateColumnLongFormat(Grid<T> grid, int columnIdx,
                                                                             Supplier<T> itemSupplier,
                                                                             BiConsumer<T, Date> setter) {
        return new GridColumnVerifier<>(grid, columnIdx, itemSupplier, setter)
            .verifyDataProvider(new Date(0L), "12/31/1969 7:00 PM")
            .verifyDataProvider(null, StringUtils.EMPTY)
            .verifyComparator(new Date(1), new Date(2))
            .verifyClassNameGenerator(null);
    }

    /**
     * Verifies LocalDate column with short format.
     *
     * @param grid         grid
     * @param columnIdx    column index
     * @param itemSupplier item supplier
     * @param setter       setter
     * @param <T>          bean type
     * @return instance of {@link GridColumnVerifier}
     */
    public static <T> GridColumnVerifier<T, LocalDate> verifyLocalDateColumnShortFormat(Grid<T> grid, int columnIdx,
                                                                                      Supplier<T> itemSupplier,
                                                                                      BiConsumer<T, LocalDate> setter) {
        return new GridColumnVerifier<>(grid, columnIdx, itemSupplier, setter)
            .verifyDataProvider(LocalDate.of(2000, 1, 2), "01/02/2000")
            .verifyDataProvider(null, StringUtils.EMPTY)
            .verifyComparator(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 2))
            .verifyClassNameGenerator(null);
    }

    /**
     * Verifies BigDecimal column.
     *
     * @param grid         grid
     * @param columnIdx    column index
     * @param itemSupplier item supplier
     * @param setter       setter
     * @param <T>          bean type
     * @return instance of {@link GridColumnVerifier}
     */
    public static <T> GridColumnVerifier<T, BigDecimal> verifyBigDecimalColumn(Grid<T> grid, int columnIdx,
                                                                               Supplier<T> itemSupplier,
                                                                               BiConsumer<T, BigDecimal> setter) {
        return new GridColumnVerifier<>(grid, columnIdx, itemSupplier, setter)
            .verifyDataProvider(new BigDecimal("1234567.89"), "1,234,567.89")
            .verifyDataProvider(null, StringUtils.EMPTY)
            .verifyComparator(BigDecimal.ZERO, BigDecimal.ONE)
            .verifyClassNameGenerator(null);
    }

    /**
     * Verifies column data provider.
     *
     * @param value          value
     * @param expectedResult expected result
     * @return instance of {@link GridColumnVerifier}
     */
    public GridColumnVerifier<T, V> verifyDataProvider(V value, Object expectedResult) {
        assertThat(column.getRenderer(), instanceOf(ColumnPathRenderer.class));
        var renderer = (ColumnPathRenderer<T>) column.getRenderer();
        var rendering = renderer.render(new Element("div"), new KeyMapper<>());
        assertTrue(rendering.getDataGenerator().isPresent());
        var dataGenerator = rendering.getDataGenerator().get();
        JsonObject jsonObject = new JreJsonObject(new JreJsonFactory());
        T item = itemSupplier.get();
        setter.accept(item, value);
        dataGenerator.generateData(item, jsonObject);
        assertEquals(expectedResult, jsonObject.getString("col" + columnIdx));
        return this;
    }

    /**
     * Verifies column comparator.
     *
     * @param minValue minimum value
     * @param maxValue maximum value
     * @return instance of {@link GridColumnVerifier}
     */
    public GridColumnVerifier<T, V> verifyComparator(V minValue, V maxValue) {
        T item1 = itemSupplier.get();
        setter.accept(item1, minValue);
        T item2 = itemSupplier.get();
        setter.accept(item2, maxValue);
        var ascendingComparator = column.getComparator(SortDirection.ASCENDING);
        assertNotNull(ascendingComparator);
        assertTrue(ascendingComparator.compare(item1, item2) < 0);
        var descendingComparator = column.getComparator(SortDirection.DESCENDING);
        assertNotNull(descendingComparator);
        assertTrue(descendingComparator.compare(item1, item2) > 0);
        return this;
    }

    /**
     * Verifies column class name generator.
     *
     * @param expectedClassName expected class name
     * @return instance of {@link GridColumnVerifier}
     */
    public GridColumnVerifier<T, V> verifyClassNameGenerator(String expectedClassName) {
        var classNameGenerator = column.getClassNameGenerator();
        assertNotNull(classNameGenerator);
        assertEquals(expectedClassName, classNameGenerator.apply(itemSupplier.get()));
        return this;
    }
}
