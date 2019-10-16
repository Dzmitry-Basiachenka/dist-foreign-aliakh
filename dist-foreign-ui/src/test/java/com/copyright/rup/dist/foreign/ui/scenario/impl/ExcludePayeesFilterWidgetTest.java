package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesFilterController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.ImmutableMap;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link ExcludePayeesFilterWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/15/19
 *
 * @author Uladzislau Shalamitski
 */
public class ExcludePayeesFilterWidgetTest {

    private IExcludePayeesFilterController controller;
    private ExcludePayeesFilterWidget widget;

    @Before
    public void setUp() {
        controller = createMock(IExcludePayeesFilterController.class);
        widget = new ExcludePayeesFilterWidget();
        widget.setController(controller);
    }

    @Test
    public void testStructure() {
        expect(controller.getParticipatingStatuses())
            .andReturn(ImmutableMap.of("Participating", Boolean.TRUE, "Not Participating", Boolean.FALSE))
            .once();
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(4, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLabel(widget.getComponent(0));
        verifyParticipatingStatusCombobox(widget.getComponent(1));
        verifyMinimumThresholdComponent(widget.getComponent(2));
        verifyButtonsLayout(widget.getComponent(3));
        verify(controller);
    }

    private void verifyParticipatingStatusCombobox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox comboBox = (ComboBox) component;
        assertEquals("Participating Status", comboBox.getCaption());
        assertEquals(Sizeable.Unit.PERCENTAGE, comboBox.getWidthUnits());
        Collection<String> items = ((ListDataProvider<String>) comboBox.getDataProvider()).getItems();
        assertEquals(2, CollectionUtils.size(items));
        assertTrue(items.containsAll(Arrays.asList("Participating", "Not Participating")));
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    private void verifyMinimumThresholdComponent(Component component) {
        assertNotNull(component);
        assertEquals(TextField.class, component.getClass());
        TextField textField = (TextField) component;
        assertEquals("Minimum Threshold", textField.getCaption());
        assertEquals(100, textField.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, textField.getWidthUnits());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.isSpacing());
        verifyButton(layout.getComponent(0), "Apply");
        verifyButton(layout.getComponent(1), "Clear");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(100, button.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, button.getWidthUnits());
        verifyButtonClickListener(button);
    }

    private void verifyButtonClickListener(Button button) {
        Collection<?> listeners = button.getListeners(Button.ClickEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(1, listeners.size());
        assertNotNull(listeners.iterator().next());
    }
}
