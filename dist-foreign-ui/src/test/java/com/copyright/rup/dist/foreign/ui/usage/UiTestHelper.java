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

import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextField;

import com.vaadin.ui.Window;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.Iterator;
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
    private static final String UNCHECKED = "unchecked";

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
     * Verifies label.
     *
     * @param component   UI component
     * @param caption     caption
     * @param contentMode content mode
     * @param width       width
     */
    public static void verifyLabel(Component component, String caption, ContentMode contentMode, float width) {
        assertTrue(component instanceof Label);
        assertEquals(width, component.getWidth(), 0);
        assertEquals(Unit.PIXELS, component.getWidthUnits());
        Label label = (Label) component;
        assertEquals(caption, label.getValue());
        assertEquals(contentMode, label.getContentMode());
    }

    /**
     * Verifies listeners by fields.
     *
     * @param loadButton load button
     * @param fields     fields
     */
    public static void verifyLoadClickListener(Button loadButton, Collection<? extends AbstractComponent> fields) {
        mockStatic(Windows.class);
        Windows.showValidationErrorWindow(fields);
        expectLastCall().once();
        replay(Windows.class);
        loadButton.click();
        verify(Windows.class);
        reset(Windows.class);
    }

    /**
     * Verifies upload component.
     *
     * @param component instance of {@link Component}
     */
    public static void verifyUploadComponent(Component component) {
        assertTrue(component instanceof UploadField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
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
    @SuppressWarnings(UNCHECKED)
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
     * Verifies menu bar.
     *
     * @param component        instance of {@link Component}
     * @param menuBarName      menu bar name
     * @param isMenuBarVisible menu bar visibility
     * @param menuItemNames    menu item names
     */
    public static void verifyMenuBar(Component component, String menuBarName, boolean isMenuBarVisible,
                                     List<String> menuItemNames) {
        assertTrue(component instanceof MenuBar);
        MenuBar menuBar = (MenuBar) component;
        assertEquals(isMenuBarVisible, menuBar.isVisible());
        List<MenuBar.MenuItem> parentItems = menuBar.getItems();
        assertEquals(1, parentItems.size());
        MenuBar.MenuItem item = parentItems.get(0);
        assertEquals(menuBarName, item.getText());
        List<MenuBar.MenuItem> childItems = item.getChildren();
        assertEquals(menuItemNames.size(), childItems.size());
        IntStream.range(0, menuItemNames.size())
            .forEach(index -> assertEquals(menuItemNames.get(index), childItems.get(index).getText()));
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
     * Sets text field value.
     *
     * @param window instance of {@link Window}
     * @param field  name of the text field
     * @param value  value
     */
    public static void setTextFieldValue(Window window, String field, String value) {
        ((TextField) Whitebox.getInternalState(window, field)).setValue(value);
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
     * Sets combobox value.
     *
     * @param window instance of {@link Window}
     * @param field  name of the combobox
     * @param value  value
     */
    @SuppressWarnings("unchecked")
    public static void setComboBoxValue(Window window, String field, String value) {
        ((ComboBox<String>) Whitebox.getInternalState(window, field)).setValue(value);
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
    public static <T> ComboBox<T> verifyComboBox(Component component, String caption, boolean emptySelectionAllowed,
                                                 Collection<T> expectedItems) {
        return verifyComboBox(component, caption, Unit.PERCENTAGE, 100, emptySelectionAllowed, expectedItems);
    }

    /**
     * Verifies combobox with expected size.
     *
     * @param component             UI component
     * @param caption               caption of combobox
     * @param emptySelectionAllowed {@code true} if empty selection allowed, {@code false} otherwise
     * @param expectedItems         expected items of combobox
     * @param widthUnits            expected combobox width units
     * @param width                 expected combobox width
     * @return instance of {@link ComboBox}
     */
    @SuppressWarnings("unchecked")
    public static <T> ComboBox<T> verifyComboBox(Component component, String caption, Unit widthUnits, float width,
                                                 boolean emptySelectionAllowed, Collection<T> expectedItems) {
        assertTrue(component instanceof ComboBox);
        ComboBox<T> comboBox = (ComboBox<T>) component;
        assertFalse(comboBox.isReadOnly());
        assertTrue(comboBox.isTextInputAllowed());
        assertEquals(emptySelectionAllowed, comboBox.isEmptySelectionAllowed());
        assertEquals(caption, comboBox.getCaption());
        assertEquals(width, comboBox.getWidth(), 0);
        assertEquals(widthUnits, comboBox.getWidthUnits());
        ListDataProvider<T> listDataProvider = (ListDataProvider<T>) comboBox.getDataProvider();
        Collection<T> actualItems = listDataProvider.getItems();
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);
        return comboBox;
    }

    /**
     * Verifies checkbox.
     *
     * @param component UI component
     * @param caption   caption of checkbox
     * @param styleName name of style
     */
    public static void verifyCheckBox(Component component, String caption, String styleName) {
        assertTrue(component instanceof CheckBox);
        CheckBox checkBox = (CheckBox) component;
        assertEquals(caption, checkBox.getCaption());
        assertEquals(styleName, checkBox.getStyleName());
        assertTrue(checkBox.getValue());
    }

    /**
     * Verifies grid. Receives list instances of {@link Triple}'s where left is column caption, middle is column width
     * and right is column expand ratio.
     *
     * @param grid                        grid
     * @param columnsToWidthToExpandRatio list of {@link Triple}'s
     */
    @SuppressWarnings(UNCHECKED)
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

    /**
     * Verifies filter widget.
     *
     * @param component UI component
     * @param caption   caption of filter widget
     */
    @SuppressWarnings(UNCHECKED)
    public static void verifyItemsFilterWidget(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isEnabled());
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals(caption, button.getCaption());
        assertEquals(2, button.getListeners(Button.ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
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
