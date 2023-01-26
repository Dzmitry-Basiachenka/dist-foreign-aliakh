package com.copyright.rup.dist.foreign.ui.usage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
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
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
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
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Array;
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
        verifyLabel(component, caption, contentMode, width, Unit.PIXELS);
    }

    /**
     * Verifies label.
     *
     * @param component   UI component
     * @param caption     caption
     * @param contentMode content mode
     * @param width       width
     * @param unit        unit
     */
    public static void verifyLabel(Component component, String caption, ContentMode contentMode, float width,
                                   Unit unit) {
        assertThat(component, instanceOf(Label.class));
        assertEquals(width, component.getWidth(), 0);
        assertEquals(unit, component.getWidthUnits());
        Label label = (Label) component;
        assertEquals(caption, label.getValue());
        assertEquals(contentMode, label.getContentMode());
    }

    /**
     * Verifies label.
     *
     * @param component UI component
     * @param caption   caption
     */
    public static void verifyLabel(Component component, String caption) {
        assertThat(component, instanceOf(Label.class));
        Label label = (Label) component;
        assertEquals(caption, label.getValue());
    }

    /**
     * Verifies filters label.
     *
     * @param component UI component
     */
    public static void verifyFiltersLabel(Component component) {
        assertThat(component, instanceOf(Label.class));
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
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
        assertThat(component, instanceOf(UploadField.class));
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
    public static <T> void validateFieldAndVerifyErrorMessage(AbstractField field, T value, Binder binder,
                                                              String message, boolean isValid) {
        field.setValue(value);
        binder.validate();
        List<HasValue<?>> fields = (List<HasValue<?>>) binder.getFields()
            .filter(actualField -> actualField.equals(field))
            .collect(Collectors.toList());
        assertEquals(1, fields.size());
        AbstractField actualField = (AbstractField) fields.get(0);
        assertNotNull(actualField);
        String actualErrorMessage = Objects.nonNull(actualField.getErrorMessage())
            ? actualField.getErrorMessage().toString()
            : null;
        assertEquals(value, actualField.getValue());
        assertEquals(message, actualErrorMessage);
        assertEquals(isValid, Objects.isNull(actualErrorMessage));
    }

    /**
     * Verifies Grid field error message.
     *
     * @param field   field
     * @param value   value of field
     * @param binder  binder
     * @param message error message
     * @param isValid <code>true</code> if valid otherwise <code>false</code>
     */
    @SuppressWarnings(UNCHECKED)
    public static <T> void verifyGridEditableFieldErrorMessage(AbstractField field, T value, Binder binder,
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
        assertThat(component, instanceOf(MenuBar.class));
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
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(captions.length, layout.getComponentCount());
        IntStream.range(0, captions.length).forEach(i -> {
            Component button = layout.getComponent(i);
            if (Objects.nonNull(captions[i])) {
                assertThat(button, instanceOf(Button.class));
                // TODO verify disable on click
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
     * Verifies button.
     *
     * @param component button component
     * @param caption   header of button
     * @param isVisible {@code true} if button is visible, {@code false} otherwise
     * @return instance of {@link Button}
     */
    public static Button verifyButton(Component component, String caption, boolean isVisible) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(isVisible, button.isVisible());
        return button;
    }

    /**
     * Verifies more filters button.
     *
     * @param component      button component
     * @param listenersCount count of listeners applied to the button
     */
    public static void verifyMoreFiltersButton(Component component, int listenersCount) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals("More Filters", component.getCaption());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(listenersCount, listeners.size());
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
    public static TextField verifyTextField(Component component, String caption, String styleName) {
        return verifyTextField(component, caption, 100, styleName);
    }

    /**
     * Verifies text field.
     *
     * @param component UI component
     * @param caption   caption of field
     * @param width     width
     * @return of {@link TextField}
     */
    public static TextField verifyTextField(Component component, String caption, float width, String styleName) {
        assertThat(component, instanceOf(TextField.class));
        assertEquals(caption, component.getCaption());
        assertEquals(width, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(styleName, component.getStyleName());
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
    public static <T> void setComboBoxValue(Window window, String field, T value) {
        ((ComboBox<T>) Whitebox.getInternalState(window, field)).setValue(value);
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
        assertThat(component, instanceOf(ComboBox.class));
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
        assertThat(component, instanceOf(CheckBox.class));
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
     * Verifies grid items.
     *
     * @param grid          grid
     * @param expectedItems expected items
     * @param expectedCells expected cells
     */
    public static <T> void verifyGridItems(Grid grid, List<T> expectedItems, Object[]... expectedCells) {
        DataProvider<T, ?> dataProvider = grid.getDataProvider();
        assertEquals(expectedItems.size(), dataProvider.size(new Query<>()));
        assertEquals(expectedItems, dataProvider.fetch(new Query<>()).collect(Collectors.toList()));
        List<Grid.Column<T, ?>> columns = grid.getColumns();
        Object[][] actualCells = (Object[][]) Array.newInstance(Object.class, expectedItems.size(), columns.size());
        for (int y = 0; y < expectedItems.size(); y++) {
            for (int x = 0; x < columns.size(); x++) {
                Object actualResult = columns.get(x).getValueProvider().apply(expectedItems.get(y));
                actualCells[y][x] = actualResult instanceof Button
                    ? ((Button) actualResult).getCaption()
                    : actualResult;
            }
        }
        assertArrayEquals(expectedCells, actualCells);
    }

    /**
     * Verifies grid footer items.
     *
     * @param grid                  grid
     * @param expectedFooterColumns expected footer columns
     */
    public static <T> void verifyFooterItems(Grid grid, Object[]... expectedFooterColumns) {
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertNotNull(footerRow);
        for (Object[] expectedFooterColumn : expectedFooterColumns) {
            FooterCell footerCell = footerRow.getCell(expectedFooterColumn[0].toString());
            assertEquals(expectedFooterColumn[1], footerCell.getText());
            assertEquals(expectedFooterColumn[2], footerCell.getStyleName());
        }
    }

    /**
     * Verifies filter widget.
     *
     * @param component UI component
     * @param caption   caption of filter widget
     */
    @SuppressWarnings(UNCHECKED)
    public static void verifyItemsFilterWidget(Component component, String caption) {
        assertThat(component, instanceOf(HorizontalLayout.class));
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
}
