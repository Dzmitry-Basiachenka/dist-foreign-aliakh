package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWindow.IRightsholderFilterSaveListener;
import com.copyright.rup.dist.foreign.ui.common.ProductFamilyFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link AuditFilterWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/23/18
 *
 * @author Aliaksandr Radkevich
 */
public class AuditFilterWidgetTest {

    private AuditFilterWidget widget;

    @Before
    public void setUp() {
        widget = new AuditFilterWidget();
        widget.init();
    }

    @Test
    public void testLayout() {
        assertTrue(widget.isSpacing());
        assertEquals(new MarginInfo(true), widget.getMargin());
        assertEquals("audit-filter-widget", widget.getStyleName());
        assertEquals(8, widget.getComponentCount());
        Component component = widget.getComponent(0);
        assertTrue(component instanceof Label);
        verifyLabel((Label) component);
        component = widget.getComponent(1);
        assertTrue(component instanceof ProductFamilyFilterWidget);
        verifyFilterWidget((ProductFamilyFilterWidget) component, "Product Families");
        component = widget.getComponent(2);
        assertTrue(component instanceof LazyRightsholderFilterWidget);
        assertEquals("Rightsholders", Whitebox.getInternalState(component, Button.class).getCaption());
        assertNotNull(Whitebox.getInternalState(component, IRightsholderFilterSaveListener.class));
        component = widget.getComponent(3);
        assertTrue(component instanceof UsageBatchFilterWidget);
        verifyFilterWidget((UsageBatchFilterWidget) component, "Batches");
        component = widget.getComponent(4);
        assertTrue(component instanceof StatusFilterWidget);
        verifyFilterWidget((StatusFilterWidget) component, "Status");
        verifyTextField(widget.getComponent(5), "Event ID");
        verifyTextField(widget.getComponent(6), "Dist. Name");
        component = widget.getComponent(7);
        assertTrue(component instanceof HorizontalLayout);
        verifyButtonsLayout((HorizontalLayout) component);
        assertEquals(Alignment.MIDDLE_RIGHT, widget.getComponentAlignment(component));
    }

    private void verifyLabel(Label label) {
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    private void verifyFilterWidget(BaseItemsFilterWidget filterWidget, String caption) {
        assertEquals(caption, Whitebox.getInternalState(filterWidget, Button.class).getCaption());
        assertNotNull(Whitebox.getInternalState(filterWidget, IFilterSaveListener.class));
    }

    private void verifyTextField(Component component, String caption) {
        assertNotNull(component);
        assertEquals(TextField.class, component.getClass());
        TextField textField = (TextField) component;
        assertEquals(caption, textField.getCaption());
        assertEquals(100, textField.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, textField.getWidthUnits());
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        verifySize(layout);
        assertEquals("filter-buttons", layout.getStyleName());
        assertEquals("filter-buttons", layout.getId());
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertTrue(component instanceof Button);
        verifyButton((Button) component, "Apply", false);
        component = layout.getComponent(1);
        assertTrue(component instanceof Button);
        verifyButton((Button) component, "Clear", true);
    }

    private void verifyButton(Button button, String caption, boolean enabled) {
        assertEquals(caption, button.getCaption());
        assertEquals(1, button.getListeners(ClickEvent.class).size());
        assertEquals(enabled, button.isEnabled());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(-1, component.getHeight(), 0);
        assertEquals(Unit.PIXELS, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
