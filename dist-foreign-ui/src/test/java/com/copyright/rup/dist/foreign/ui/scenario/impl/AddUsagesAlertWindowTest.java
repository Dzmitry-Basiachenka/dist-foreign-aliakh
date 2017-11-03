package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.impl.AddUsagesAlertWindow;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.junit.Test;

/**
 * Verifies {@link AddUsagesAlertWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/02/17
 *
 * @author Ihar Suvorau
 */
public class AddUsagesAlertWindowTest {

    @Test
    public void testCreateScenarioWarningWindow() {
        Window window = new AddUsagesAlertWindow("New scenario");
        assertEquals(400, window.getWidth(), 0);
        assertEquals(120, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals("Confirm action", window.getCaption());
        assertFalse(window.isResizable());
        verifyContent(window);
    }

    private void verifyContent(Window window) {
        Component component = window.getContent();
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0));
        verifyButtons(layout.getComponent(1));
    }

    private void verifyLabel(Component label) {
        assertTrue(label instanceof Label);
        assertEquals("Usages in NEW status won`t be added to <i><b>'New scenario'</b></i> scenario",
            ((Label) label).getValue());
    }

    private void verifyButtons(Component buttons) {
        assertTrue(buttons instanceof HorizontalLayout);
        HorizontalLayout buttonsLayout = (HorizontalLayout) buttons;
        assertEquals(2, buttonsLayout.getComponentCount());
        verifyButton(buttonsLayout.getComponent(0), "Continue");
        verifyButton(buttonsLayout.getComponent(1), "Cancel");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }
}
