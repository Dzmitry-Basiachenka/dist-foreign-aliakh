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

    private final Column<T> column;
    private final int columnIdx;
    private final Supplier<T> itemSupplier;
    private final BiConsumer<T, V> setter;

    public GridColumnVerifier(Grid<T> grid, int columnIdx, Supplier<T> itemSupplier, BiConsumer<T, V> setter) {
        this.column = grid.getColumns().get(columnIdx);
        this.columnIdx = columnIdx;
        this.itemSupplier = itemSupplier;
        this.setter = setter;
    }

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

    public GridColumnVerifier<T, V> verifyComparator(V value1, V value2) {
        T item1 = itemSupplier.get();
        setter.accept(item1, value1);
        T item2 = itemSupplier.get();
        setter.accept(item2, value2);
        var ascendingComparator = column.getComparator(SortDirection.ASCENDING);
        assertNotNull(ascendingComparator);
        assertTrue(ascendingComparator.compare(item1, item2) < 0);
        var descendingComparator = column.getComparator(SortDirection.DESCENDING);
        assertNotNull(descendingComparator);
        assertTrue(descendingComparator.compare(item1, item2) > 0);
        return this;
    }

    public void verifyClassNameGenerator(String expectedClassName) {
        var classNameGenerator = column.getClassNameGenerator();
        assertNotNull(classNameGenerator);
        assertEquals(expectedClassName, classNameGenerator.apply(itemSupplier.get()));
    }
}
