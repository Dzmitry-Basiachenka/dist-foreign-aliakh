package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getButtonFromFooter;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.junit.Test;

/**
 * Verify {@link NotificationWindow}.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 01/24/14
 *
 * @author Mikalai Bezmen
 * @author Nikita Levyankov
 */
public class NotificationWindowTest {

    private static final String LABEL_CONTENT = "Label";

    @Test(expected = IllegalArgumentException.class)
    public void testNullMessage() {
        new NotificationWindow(null);
    }

    @Test
    public void testConstructorWithArgument() {
        NotificationWindow notificationWindow = new NotificationWindow(LABEL_CONTENT);
        assertNotNull(notificationWindow);
        assertEquals("600.0px", notificationWindow.getWidth());
        assertEquals(Unit.PIXELS, notificationWindow.getWidthUnit().orElseThrow());
        assertFalse(notificationWindow.isCloseOnEsc());
        assertFalse(notificationWindow.isResizable());
        assertEquals("notification-window", notificationWindow.getClassName());
        assertEquals("notification-window", notificationWindow.getId().orElseThrow());
        verifyWidgetComponents(notificationWindow);
    }

    private void verifyWidgetComponents(NotificationWindow widget) {
        Component component = getDialogContent(widget);
        assertNotNull(component);
        assertEquals(VerticalLayout.class, component.getClass());
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(1, verticalLayout.getComponentCount());
        verifyLabel(verticalLayout.getComponentAt(0));
        verifyButton(getButtonFromFooter(widget, 0));
        assertTrue(verticalLayout.isSpacing());
        assertEquals("100%", verticalLayout.getWidth());
        assertEquals(Unit.PERCENTAGE, verticalLayout.getWidthUnit().orElseThrow());
    }

    private void verifyLabel(Component component) {
        assertNotNull(component);
        assertEquals(Html.class, component.getClass());
        Html label = (Html) component;
        assertEquals(LABEL_CONTENT, label.getInnerHtml());
    }

    private void verifyButton(Component component) {
        assertNotNull(component);
        assertEquals(Button.class, component.getClass());
        assertEquals("Ok", ((Button) component).getText());
    }
}
