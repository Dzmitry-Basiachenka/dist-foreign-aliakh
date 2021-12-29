package com.copyright.rup.dist.foreign.ui.usage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Represents verifier of UI components.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/15/2021
 *
 * @author Anton Azarenka
 */
public final class UiTestHelper {

    private static final String EXPORT_BUTTON = "Export";

    private UiTestHelper() {
        throw new IllegalStateException("Constructor shouldn't be called directly");
    }

    /**
     * Verifies window caption and size.
     *
     * @param window  window component {@link AbstractComponent}
     * @param caption caption of window
     * @param width   width
     * @param height  height
     * @param unit    unit value of width and height
     */
    public static void verifyWindow(AbstractComponent window, String caption, int width, int height, Unit unit) {
        assertEquals(caption, window.getCaption());
        assertEquals(width, window.getWidth(), 0);
        assertEquals(unit, window.getWidthUnits());
        assertEquals(height, window.getHeight(), 0);
        assertEquals(unit, window.getHeightUnits());
    }

    /**
     * Verifies listeners by fields.
     *
     * @param loadButton load button
     * @param fields     fields
     */
    public static void verifyLoadClickListener(Button loadButton, Collection<? extends AbstractField<?>> fields) {
        mockStatic(Windows.class);
        Windows.showValidationErrorWindow(fields);
        expectLastCall().once();
        replay(Windows.class);
        loadButton.click();
        verify(Windows.class);
        reset(Windows.class);
    }

    /**
     * Verifies fields.
     *
     * @param field   field
     * @param value   value of field
     * @param binder  binder
     * @param message error message
     * @param isValid <code>true</code> if valid otherwise <code>false</code>
     */
    @SuppressWarnings("unchecked")
    public static void validateFieldAndVerifyErrorMessage(AbstractField field, String value, Binder binder,
                                                          String message, boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages = errors
            .stream()
            .map(ValidationResult::getErrorMessage)
            .collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }

    /**
     * Verifies buttons in horizontal layout. Captions should be conveying by ordering.
     *
     * @param component horizontal layout
     * @param captions  headers of buttons.
     */
    public static void verifyButtonsLayout(Component component, String... captions) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(captions.length, layout.getComponentCount());
        IntStream.range(0, captions.length).forEach(i -> {
            Component button = layout.getComponent(i);
            if (Objects.nonNull(captions[i])) {
                assertTrue(button instanceof Button);
                verifyButtonClickListener((Button) button);
            }
            assertEquals(captions[i], button.getCaption());
        });
    }

    /**
     * Verifies button visibility.
     *
     * @param mapButtonToVisible map of button and visible value
     */
    public static void verifyButtonsVisibility(Map<Component, Boolean> mapButtonToVisible) {
        mapButtonToVisible.forEach((component, isVisible) -> assertEquals(isVisible, component.isVisible()));
    }

    /**
     * Verifies text field.
     *
     * @param component UI component
     * @param caption   caption of field
     * @return instance of {@link TextField}
     */
    public static TextField verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(caption, component.getCaption());
        return (TextField) component;
    }

    /**
     * Verifies combobox.
     *
     * @param component             UI component
     * @param caption               caption of combobox
     * @param emptySelectionAllowed {@code true} if empty selection allowed, {@code false} otherwise
     * @param expectedItems         expected items of combobox
     * @return instance of {@link ComboBox}
     */
    @SafeVarargs
    public static <T> ComboBox<T> verifyComboBox(Component component, String caption, boolean emptySelectionAllowed,
                                                 T... expectedItems) {
        return verifyComboBox(component, caption, emptySelectionAllowed,
            Stream.of(expectedItems).collect(Collectors.toList()));
    }

    /**
     * Verifies combobox.
     *
     * @param component             UI component
     * @param caption               caption of combobox
     * @param emptySelectionAllowed {@code true} if empty selection allowed, {@code false} otherwise
     * @param expectedItems         expected items of combobox
     * @return instance of {@link ComboBox}
     */
    @SuppressWarnings("unchecked")
    public static <T> ComboBox<T> verifyComboBox(Component component, String caption, boolean emptySelectionAllowed,
                                                 Collection<T> expectedItems) {
        assertTrue(component instanceof ComboBox);
        ComboBox<T> comboBox = (ComboBox<T>) component;
        assertFalse(comboBox.isReadOnly());
        assertTrue(comboBox.isTextInputAllowed());
        assertEquals(emptySelectionAllowed, comboBox.isEmptySelectionAllowed());
        assertEquals(caption, comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<T> listDataProvider = (ListDataProvider<T>) comboBox.getDataProvider();
        Collection<T> actualItems = listDataProvider.getItems();
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);
        return comboBox;
    }

    /**
     * Verifies grid. Receives list instances of {@link Triple}'s where left is column caption, middle is column width
     * and right is column expand ratio.
     *
     * @param grid                        grid
     * @param columnsToWidthToExpandRatio list of {@link Triple}'s
     */
    @SuppressWarnings("unchecked")
    public static void verifyGrid(Grid grid,
                                List<Triple<String, ? extends Number, ? extends Number>> columnsToWidthToExpandRatio) {
        assertNull(grid.getCaption());
        List<Grid.Column> columns = grid.getColumns();
        assertEquals(columnsToWidthToExpandRatio.stream().map(Triple::getLeft).collect(Collectors.toList()),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(columnsToWidthToExpandRatio.stream().map(Triple::getMiddle).collect(Collectors.toList()),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
        assertEquals(columnsToWidthToExpandRatio.stream().map(Triple::getRight).collect(Collectors.toList()),
            columns.stream().map(Grid.Column::getExpandRatio).collect(Collectors.toList()));
    }

    private static void verifyButtonClickListener(Button button) {
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        if (!EXPORT_BUTTON.equals(button.getCaption())) {
            assertTrue(CollectionUtils.isNotEmpty(listeners));
            assertEquals(1, listeners.size());
            assertNotNull(listeners.iterator().next());
        } else {
            assertTrue(CollectionUtils.isEmpty(listeners));
        }
    }
}
