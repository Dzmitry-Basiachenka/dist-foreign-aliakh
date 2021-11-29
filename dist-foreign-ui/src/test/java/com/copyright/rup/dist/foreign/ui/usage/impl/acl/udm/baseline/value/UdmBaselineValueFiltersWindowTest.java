package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link UdmBaselineValueFiltersWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/26/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueFiltersWindowTest {

    private static final String UNCHECKED = "unchecked";
    private static final List<String> Y_N_ITEMS = Arrays.asList("Y", "N");

    private UdmBaselineValueFiltersWindow window;

    @Before
    public void setUp() {
        window = new UdmBaselineValueFiltersWindow(new UdmBaselineValueFilter());
    }

    @Test
    public void testConstructor() {
        verifyWindow(window, "UDM baseline values additional filters", 550, 400, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(8, verticalLayout.getComponentCount());
        verifyTextFieldLayout(
            verticalLayout.getComponent(0), TextField.class, "Wr Wrk Inst", ComboBox.class, "Pub Type");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(1), "System Title");
        verifyTextFieldLayout(verticalLayout.getComponent(2), ComboBox.class, "Price Flag",
            ComboBox.class, "Content Flag");
        assertComboboxItems("priceFlagComboBox", Y_N_ITEMS);
        assertComboboxItems("contentFlagComboBox", Y_N_ITEMS);
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(3), "Price From", "Price To");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(4), "Content From", "Content To");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(5), "Content Unit Price From",
            "Content Unit Price To");
        verifyTextField(verticalLayout.getComponent(6), "Comment");
    }

    private void verifyLayoutWithOperatorComponent(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextField(layout.getComponent(0), caption);
        assertTrue(layout.getComponent(1) instanceof ComboBox);
        assertEquals("Operator", layout.getComponent(1).getCaption());
    }

    private void verifyLayoutWithOperatorComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyTextField(layout.getComponent(0), captionFrom);
        verifyTextField(layout.getComponent(1), captionTo);
        assertTrue(layout.getComponent(2) instanceof ComboBox);
        assertEquals("Operator", layout.getComponent(2).getCaption());
    }

    private void verifyTextFieldLayout(Component component, Class<?> firstClass, String firstCaption,
                                       Class<?> secondClass, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyComponent(layout.getComponent(0), firstClass, firstCaption, true);
        verifyComponent(layout.getComponent(1), secondClass, secondCaption, true);
    }

    private void verifyComponent(Component component, Class<?> clazz, String caption, boolean isEnabled) {
        assertTrue(clazz.isInstance(component));
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
        assertEquals(isEnabled, component.isEnabled());
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboboxItems(String fieldName, List<T> expectedItems) {
        assertComboboxItems((ComboBox<T>) Whitebox.getInternalState(window, fieldName), expectedItems);
    }

    private <T> void assertComboboxItems(ComboBox<T> comboBox, List<T> expectedItems) {
        ListDataProvider<T> listDataProvider = (ListDataProvider<T>) comboBox.getDataProvider();
        Collection<?> actualItems = listDataProvider.getItems();
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);
    }
}
