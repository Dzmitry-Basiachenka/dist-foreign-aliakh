package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verify {@link ConfirmDialogWindow}.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 01/23/2014
 *
 * @author Mikalai Bezmen
 */
public class ConfirmDialogWindowTest {

    @Test
    public void testConfirmDialogWidgetWithBlankCaption() {
        IConfirmCancelListener listener = createMock(IConfirmCancelListener.class);
        ConfirmDialogWindow confirmDialogWindow = new ConfirmDialogWindow(listener,
            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        assertEquals("Confirm action", confirmDialogWindow.getHeaderTitle());
        verifyButtonsLayout(getFooterLayout(confirmDialogWindow), true, "Yes", "Cancel");
    }

    @Test
    public void testConfirmDialogWidgetWithNonBlankCaption() {
        IConfirmCancelListener listener = createMock(IConfirmCancelListener.class);
        ConfirmDialogWindow confirmDialogWindow =
            new ConfirmDialogWindow(listener, "Window Caption", "Window Message", "Confirm Button", "Decline Button");
        assertNotNull(confirmDialogWindow);
        assertEquals("confirm-dialog-window", confirmDialogWindow.getId().orElseThrow());
        assertEquals("confirm-dialog-window", confirmDialogWindow.getClassName());
        assertEquals(listener, Whitebox.getInternalState(confirmDialogWindow, "listener"));
        assertEquals("Window Caption", confirmDialogWindow.getHeaderTitle());
        assertFalse(confirmDialogWindow.isResizable());
        assertFalse(confirmDialogWindow.isCloseOnEsc());
        assertFalse(confirmDialogWindow.isCloseOnOutsideClick());
        assertEquals("400.0px", confirmDialogWindow.getWidth());
        assertEquals(Unit.PIXELS, confirmDialogWindow.getWidthUnit().orElseThrow());
        verifyConfirmDialogWidgetContent(confirmDialogWindow);
    }

    private void verifyConfirmDialogWidgetContent(ConfirmDialogWindow confirmDialogWindow) {
        Component component = getDialogContent(confirmDialogWindow);
        assertNotNull(component);
        assertEquals(VerticalLayout.class, component.getClass());
        VerticalLayout contentLayout = (VerticalLayout) component;
        assertEquals(1, contentLayout.getComponentCount());
        assertNotNull(contentLayout);
        assertEquals("100.0%", contentLayout.getWidth());
        assertEquals(Unit.PERCENTAGE, contentLayout.getWidthUnit().orElseThrow());
        assertTrue(contentLayout.isSpacing());
        verifyContentComponents(contentLayout);
    }

    private void verifyContentComponents(VerticalLayout contentLayout) {
        assertEquals(1, contentLayout.getComponentCount());
        Component component = contentLayout.getComponentAt(0);
        assertNotNull(component);
        assertEquals(Html.class, component.getClass());
        Html windowLabel = (Html) component;
        assertEquals("Window Message", windowLabel.getInnerHtml());
    }
}
