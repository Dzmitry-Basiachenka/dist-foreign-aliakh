package com.copyright.rup.dist.foreign.vui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.LongField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.FooterRow.FooterCell;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hamcrest.core.IsInstanceOf;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utils class for testing UI.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/02/2023
 *
 * @author Anton Azarenka
 */
public final class UiTestHelper {

    private static final String UNCHECKED = "unchecked";

    private UiTestHelper() {
        throw new IllegalStateException("Constructor shouldn't be called directly");
    }

    /**
     * Gets button from footer on any modal window by index.
     *
     * @param dialog modal window.
     * @param index  index of button
     * @return instance of {@link Button}
     */
    public static Button getButtonFromFooter(Dialog dialog, int index) {
        List<Element> dialogElements = dialog.getElement().getChildren().collect(Collectors.toList());
        Element footerElement = getFooterElement(dialogElements);
        HorizontalLayout layout = getElementContent(footerElement);
        return (Button) layout.getComponentAt(index);
    }

    /**
     * Returns middle content from modal window. Does not include Header and Footer content.
     *
     * @param component instance of {@link Dialog}
     * @return instance of {@link Component}
     */
    public static Component getDialogContent(Dialog component) {
        return component.getChildren().findFirst().orElseThrow();
    }

    /**
     * Returns horizontal layout from dialog footer.
     *
     * @param dialog instance of {@link Dialog}
     * @return instance of {@link HorizontalLayout}
     */
    public static HorizontalLayout getFooterLayout(Dialog dialog) {
        List<Element> dialogElements = dialog.getElement().getChildren().collect(Collectors.toList());
        return getElementContent(getFooterElement(dialogElements));
    }

    /**
     * Returns single component from dialog footer by footer index.
     *
     * @param dialog instance of {@link Dialog}
     * @param index  component index
     * @return instance of {@link Component}
     */
    public static Component getFooterComponent(Dialog dialog, int index) {
        return dialog.getElement().getChild(index).getChildren().findFirst().orElseThrow().getComponent().orElseThrow();
    }

    /**
     * Verifies window caption, size, and resizable.
     *
     * @param component instance of {@link Dialog}
     * @param title     header of window
     * @param width     width
     * @param height    height
     * @param unit      instance of {@link Unit}
     * @param resizable resizable
     */
    public static void verifyWindow(Dialog component, String title, String width, String height, Unit unit,
                                    boolean resizable) {
        assertEquals(title, component.getHeaderTitle());
        assertEquals(resizable, component.isResizable());
        assertEquals(width, component.getWidth());
        assertEquals(Objects.isNull(width) ? null : unit, component.getWidthUnit().orElse(null));
        assertEquals(height, component.getHeight());
        assertEquals(Objects.isNull(height) ? null : unit, component.getHeightUnit().orElse(null));
    }

    /**
     * Verifies label.
     *
     * @param component UI component
     * @param caption   caption
     * @param width     width
     */
    public static void verifyLabel(Component component, String caption, String width) {
        assertThat(component, instanceOf(Label.class));
        var label = (Label) component;
        assertEquals(width, label.getWidth());
        assertEquals(caption, label.getText());
    }

    /**
     * Verifies label.
     *
     * @param component UI component
     * @param caption   caption
     */
    public static void verifyLabel(Component component, String caption) {
        assertThat(component, instanceOf(Label.class));
        var label = (Label) component;
        assertEquals(caption, label.getText());
    }

    /**
     * Verifies filters label.
     *
     * @param component UI component
     */
    public static void verifyFiltersLabel(Component component) {
        assertThat(component, instanceOf(Label.class));
        var label = (Label) component;
        assertEquals("Filters", label.getText());
        assertEquals("filter-label", label.getClassName());
    }

    /**
     * Verifies listeners by fields.
     *
     * @param loadButton load button
     */
    public static void verifyLoadClickListener(Button loadButton) {
        mockStatic(Windows.class);
        Windows.showValidationErrorWindow();
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
    public static void verifyUploadComponent(Component component, String width) {
        assertThat(component, instanceOf(UploadField.class));
        assertEquals(width, ((UploadField) component).getWidth());
    }

    /**
     * Verifies vertical layout size.
     *
     * @param component  instance of {@link HasSize}
     * @param width      width
     * @param widthUnit  widthUnit
     * @param height     height
     * @param heightUnit heightUnit
     */
    public static void verifySize(HasSize component, String width, Unit widthUnit, String height,
                                  Unit heightUnit) {
        assertEquals(width, component.getWidth());
        assertEquals(widthUnit, component.getWidthUnit().orElseThrow());
        assertEquals(height, component.getHeight());
        assertEquals(heightUnit, component.getHeightUnit().orElseThrow());
    }

    /**
     * Verifies buttons in horizontal layout. Captions should be conveying by ordering.
     *
     * @param component horizontal layout
     * @param captions  headers of buttons.
     */
    public static void verifyButtonsLayout(Component component, boolean isDisableOnClick, String... captions) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(captions.length, layout.getComponentCount());
        IntStream.range(0, captions.length).forEach(i -> {
            Component button = layout.getComponentAt(i);
            if (Objects.nonNull(captions[i])) {
                assertThat(button, instanceOf(Button.class));
                assertEquals(isDisableOnClick, ((Button) button).isDisableOnClick());
            }
            assertEquals(captions[i], ((Button) button).getText());
        });
    }

    /**
     * Verifies button.
     *
     * @param component button component
     * @param text      button text
     * @param isVisible {@code true} if button is visible, {@code false} otherwise
     * @return instance of {@link Button}
     */
    public static Button verifyButton(Component component, String text, boolean isVisible) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(text, button.getText());
        assertEquals(isVisible, button.isVisible());
        return button;
    }

    /**
     * Sets text field value.
     *
     * @param dialog instance of {@link Dialog}
     * @param field  name of the text field
     * @param value  value
     */
    public static void setTextFieldValue(Dialog dialog, String field, String value) {
        ((TextField) Whitebox.getInternalState(dialog, field)).setValue(value);
    }

    /**
     * Sets BigDecimal field value.
     *
     * @param dialog instance of {@link Dialog}
     * @param field  name of the text field
     * @param value  value
     */
    public static void setBigDecimalFieldValue(Dialog dialog, String field, String value) {
        ((BigDecimalField) Whitebox.getInternalState(dialog, field)).setValue(new BigDecimal(value));
    }

    /**
     * Sets Long field value.
     *
     * @param dialog instance of {@link Dialog}
     * @param field  name of the text field
     * @param value  value
     */
    public static void setLongFieldValue(Dialog dialog, String field, String value) {
        ((LongField) Whitebox.getInternalState(dialog, field)).setValue(Long.valueOf(value));
    }

    /**
     * Verifies text field.
     *
     * @param component UI component
     * @param label     label of field
     * @return instance of {@link TextField}
     */
    public static TextField verifyTextField(Component component, String label, String styleName) {
        return verifyTextField(component, label, "100%", styleName);
    }

    /**
     * Verifies text field.
     *
     * @param component UI component
     * @param label     label of field
     * @param width     width
     * @return of {@link TextField}
     */
    public static TextField verifyTextField(Component component, String label, String width, String styleName) {
        assertThat(component, instanceOf(TextField.class));
        TextField textField = (TextField) component;
        assertEquals(label, textField.getLabel());
        assertEquals(width, textField.getWidth());
        assertEquals(styleName, textField.getClassName());
        return textField;
    }

    /**
     * Verifies BigDecimal field.
     *
     * @param component UI component
     * @param label     label of field
     * @param width     width
     * @return of {@link BigDecimalField}
     */
    public static BigDecimalField verifyBigDecimalField(Component component, String label, String width,
                                                        String styleName) {
        assertThat(component, instanceOf(BigDecimalField.class));
        BigDecimalField field = (BigDecimalField) component;
        assertEquals(label, field.getLabel());
        assertEquals(width, field.getWidth());
        assertEquals(styleName, field.getClassName());
        return field;
    }

    /**
     * Verifies Long field.
     *
     * @param component UI component
     * @param label     label of field
     * @param width     width
     * @return of {@link LongField}
     */
    public static LongField verifyLongField(Component component, String label, String width, String styleName) {
        assertThat(component, instanceOf(LongField.class));
        LongField field = (LongField) component;
        assertEquals(label, field.getLabel());
        assertEquals(width, field.getWidth());
        assertEquals(styleName, field.getClassName());
        return field;
    }

    /**
     * Returns width for all columns of provides grid.
     *
     * @param grid instance of {@link Grid}
     * @return array of width
     */
    public static String[] getColumnsWidth(Grid<?> grid) {
        return grid.getColumns().stream().map(Column::getWidth).toArray(String[]::new);
    }

    /**
     * Checks layout and returns checkbox group component.
     *
     * @param scroller instance of {@link Scroller}
     * @return instance of {@link CheckboxGroup}
     */
    public static CheckboxGroup getCheckboxGroup(Scroller scroller) {
        Component divComponent = scroller.getChildren().findFirst().orElseThrow();
        assertThat(divComponent, IsInstanceOf.instanceOf(Div.class));
        Component sectionComponent = divComponent.getChildren().findFirst().orElseThrow();
        assertThat(sectionComponent, IsInstanceOf.instanceOf(Section.class));
        Component checkboxGroupComponent = sectionComponent.getChildren().findFirst().orElseThrow();
        assertThat(checkboxGroupComponent, IsInstanceOf.instanceOf(CheckboxGroup.class));
        return (CheckboxGroup) checkboxGroupComponent;
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
        return verifyComboBox(component, caption, "100%", emptySelectionAllowed, expectedItems);
    }

    /**
     * Verifies combobox with expected size.
     *
     * @param component             UI component
     * @param caption               caption of combobox
     * @param emptySelectionAllowed {@code true} if empty selection allowed, {@code false} otherwise
     * @param expectedItems         expected items of combobox
     * @param width                 expected combobox width
     * @return instance of {@link ComboBox}
     */
    @SuppressWarnings(UNCHECKED)
    public static <T> ComboBox<T> verifyComboBox(Component component, String caption, String width,
                                                 boolean emptySelectionAllowed, Collection<T> expectedItems) {
        assertThat(component, instanceOf(ComboBox.class));
        ComboBox<T> comboBox = (ComboBox<T>) component;
        assertFalse(comboBox.isReadOnly());
        assertEquals(emptySelectionAllowed, !comboBox.isRequired());
        assertEquals(caption, comboBox.getLabel());
        assertEquals(width, comboBox.getWidth());
        ListDataProvider<T> listDataProvider = (ListDataProvider<T>) comboBox.getDataProvider();
        Collection<T> actualItems = listDataProvider.getItems();
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);
        return comboBox;
    }

    /**
     * Verifies grid. Receives list instances of {@link Pair}'s where left is column caption, middle is column width.
     *
     * @param grid           grid
     * @param columnsToWidth list of {@link Pair}'s
     */
    public static <T> void verifyGrid(Grid<T> grid, List<Pair<String, String>> columnsToWidth) {
        List<Column<T>> columns = grid.getColumns();
        assertEquals(columnsToWidth.stream().map(Pair::getLeft).collect(Collectors.toList()),
            columns.stream().map(Column::getHeaderText).collect(Collectors.toList()));
        assertEquals(columnsToWidth.stream().map(Pair::getRight).collect(Collectors.toList()),
            columns.stream().map(Column::getWidth).collect(Collectors.toList()));
    }

    /**
     * Verifies grid items.
     *
     * @param grid          grid
     * @param expectedItems expected items
     * @param expectedCells expected cells
     */
    @SuppressWarnings("unchecked")
    public static <T> void verifyGridItems(Grid grid, List<T> expectedItems, Object[]... expectedCells) {
        DataProvider<T, ?> dataProvider = grid.getDataProvider();
        assertEquals(expectedItems.size(), dataProvider.size(new Query<>()));
        assertEquals(expectedItems, dataProvider.fetch(new Query<>()).collect(Collectors.toList()));
        List<Grid.Column<T>> columns = grid.getColumns();
        Object[][] actualCells = (Object[][]) Array.newInstance(Object.class, expectedItems.size(), columns.size());
        for (int y = 0; y < expectedItems.size(); y++) {
            for (int x = 0; x < columns.size(); x++) {
                Renderer<T> renderer = columns.get(x).getRenderer();
                Map<String, ValueProvider<T, ?>> valueProvidersMap = renderer.getValueProviders();
                if (valueProvidersMap.isEmpty()) {
                    SerializableFunction<T, ?> componentFunction =
                        Whitebox.getInternalState(renderer, "componentFunction");
                    actualCells[y][x] = ((Button) componentFunction.apply(expectedItems.get(y))).getText();
                } else {
                    ValueProvider<T, ?> valueProvider = valueProvidersMap.get(String.format("col%s", x));
                    actualCells[y][x] = valueProvider.apply(expectedItems.get(y));
                }
            }
        }
        assertArrayEquals(expectedCells, actualCells);
    }

    /**
     * Verifies grid footer items.
     *
     * @param grid                  grid
     * @param rowNumber             row number
     * @param footerTitle           footer title
     * @param expectedFooterColumns expected footer columns
     */
    public static void verifyFooterItems(Grid<?> grid, int rowNumber, String footerTitle,
                                         Object[]... expectedFooterColumns) {
        FooterRow footerRow = grid.getFooterRows().get(rowNumber);
        assertNotNull(footerRow);
        assertEquals(footerTitle, footerRow.getCells().get(0).getText());
        for (Object[] expectedFooterColumn : expectedFooterColumns) {
            FooterCell footerCell = footerRow.getCell(grid.getColumnByKey((String) expectedFooterColumn[0]));
            var label = (Label) footerCell.getComponent();
            Html html = (Html) label.getChildren().findFirst().orElseThrow();
            assertTrue(StringUtils.contains(html.getInnerHtml(), (String) expectedFooterColumn[1]));
            assertEquals(expectedFooterColumn[2], label.getClassName());
        }
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
        List<HasValidation> fields = (List<HasValidation>) binder.getFields()
            .filter(actualField -> actualField.equals(field))
            .collect(Collectors.toList());
        assertEquals(1, fields.size());
        HasValidation actualField = fields.get(0);
        assertNotNull(actualField);
        String actualErrorMessage = Objects.nonNull(actualField.getErrorMessage())
            ? actualField.getErrorMessage()
            : null;
        assertEquals(value, ((AbstractField) actualField).getValue());
        assertEquals(message, actualErrorMessage);
        assertEquals(isValid, Objects.isNull(actualErrorMessage));
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
    public static <T> void assertFieldValidationMessage(AbstractField<?, T> field, T value, Binder<?> binder,
                                                        String message, boolean isValid) {
        field.setValue(value);
        BindingValidationStatus<?> fieldStatus = binder
            .validate()
            .getFieldValidationStatuses()
            .stream()
            .filter(status -> status.getField() == field)
            .findFirst()
            .orElseThrow();
        if (isValid) {
            assertFalse(fieldStatus.isError());
            assertTrue(fieldStatus.getMessage().isEmpty());
        } else {
            assertTrue(fieldStatus.isError());
            assertEquals(message, fieldStatus.getMessage().get());
        }
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
        List<MenuItem> parentItems = menuBar.getItems();
        assertEquals(1, parentItems.size());
        MenuItem item = parentItems.get(0);
        assertEquals(menuBarName, item.getText());
        List<MenuItem> childItems = item.getSubMenu().getItems();
        assertEquals(menuItemNames.size(), childItems.size());
        IntStream.range(0, menuItemNames.size())
            .forEach(index -> assertEquals(menuItemNames.get(index), childItems.get(index).getText()));
    }

    /**
     * Verifies filter widget.
     *
     * @param component UI component
     * @param caption   caption of filter widget
     */
    @SuppressWarnings(UNCHECKED)
    public static void verifyItemsFilterWidget(Component component, String caption) {
        verifyItemsFilterWidget(component, true, caption);
    }

    /**
     * Verifies filter widget.
     *
     * @param component UI component
     * @param isEnabled true - if widget is enabled, false - otherwise
     * @param caption   caption of filter widget
     */
    public static void verifyItemsFilterWidget(Component component, boolean isEnabled, String caption) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(isEnabled, layout.isEnabled());
        Iterator<Component> iterator = layout.getChildren().iterator();
        assertEquals("(0)", ((Html) iterator.next()).getInnerHtml());
        Button button = (Button) iterator.next();
        assertEquals(caption, button.getText());
        assertTrue(button.isDisableOnClick());
        assertFalse(iterator.hasNext());
    }

    private static HorizontalLayout getElementContent(Element element) {
        return (HorizontalLayout) element.getComponent().orElseThrow();
    }

    private static Element getFooterElement(List<Element> dialogElements) {
        return getFooter(dialogElements.stream()
            //TODO{vaadin23} optimize search of footer Element
            .filter(element -> element.getStyle().has("display"))
            .findFirst()
            .orElseThrow());
    }

    private static Element getFooter(Element element) {
        return element.getChildren().findFirst().orElseThrow();
    }
}
