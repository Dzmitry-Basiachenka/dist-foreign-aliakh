package com.copyright.rup.dist.foreign.vui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.dom.Element;

import org.apache.commons.lang3.tuple.Pair;
import org.hamcrest.core.IsInstanceOf;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
