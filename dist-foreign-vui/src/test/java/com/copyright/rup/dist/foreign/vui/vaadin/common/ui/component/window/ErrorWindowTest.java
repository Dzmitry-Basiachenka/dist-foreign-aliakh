package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.junit.Test;

/**
 * Verify {@link ErrorWindow}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/09/2023
 *
 * @author Anton Azarenka
 */
public class ErrorWindowTest {

    private static final String ERROR_MESSAGE = "Error Message";
    private static final String ERROR_STACKTRACE = "Error Stacktrace";
    private static final String COMPONENT_FULL_SIZE = "100%";
    private ErrorWindow errorWindow;

    @Test
    public void testConstructorWithArguments() {
        errorWindow = new ErrorWindow(ERROR_MESSAGE, ERROR_STACKTRACE);
        assertEquals(ERROR_MESSAGE, errorWindow.getMessage());
        assertEquals(ERROR_STACKTRACE, errorWindow.getStackTrace());
        assertTrue(errorWindow.isModal());
        assertTrue(errorWindow.isResizable());
        assertTrue(errorWindow.isCloseOnEsc());
        assertEquals("Error", errorWindow.getHeaderTitle());
        assertEquals("500.0px", errorWindow.getWidth());
        assertEquals(Unit.PIXELS, errorWindow.getWidthUnit().orElseThrow());
    }

    @Test
    public void testAttachWithNullStackTrace() {
        errorWindow = new ErrorWindow(null, null);
        Component component = errorWindow.initRootLayout();
        assertNotNull(component);
        assertEquals(1, ((VerticalLayout) component).getComponentCount());
    }

    @Test
    public void testAttachWithNotNullStackTrace() {
        errorWindow = new ErrorWindow(ERROR_MESSAGE, ERROR_STACKTRACE);
        Component component = errorWindow.initRootLayout();
        assertNotNull(component);
        assertEquals(VerticalLayout.class, component.getClass());
        VerticalLayout rootLayout = (VerticalLayout) component;
        assertEquals(2, rootLayout.getComponentCount());
        assertEquals(HorizontalLayout.class, rootLayout.getComponentAt(0).getClass());
        assertEquals(VerticalLayout.class, rootLayout.getComponentAt(1).getClass());
        assertTrue(rootLayout.isMargin());
    }

    @Test
    public void testBuildRootLayout() {
        errorWindow = new ErrorWindow(ERROR_MESSAGE, ERROR_STACKTRACE);
        VerticalLayout verticalLayout = new VerticalLayout();
        assertNotNull(verticalLayout);
        assertTrue(verticalLayout.isSpacing());
        assertFalse(verticalLayout.isMargin());
    }

    @Test
    public void testBuildErrorMessageLayoutWithNotNullMessage() {
        errorWindow = new ErrorWindow(ERROR_MESSAGE, ERROR_STACKTRACE);
        HorizontalLayout horizontalLayout = errorWindow.buildErrorMessageLayout();
        assertNotNull(horizontalLayout);
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(1, horizontalLayout.getComponentCount());
        Component component = horizontalLayout.getComponentAt(0);
        verifyLabel(component);
    }

    @Test
    public void testBuildErrorMessageLayoutWithNullMessage() {
        errorWindow = new ErrorWindow(null, null);
        HorizontalLayout horizontalLayout = errorWindow.buildErrorMessageLayout();
        assertEquals("Exception occurred", ((Label) horizontalLayout.getComponentAt(0)).getText());
    }

    @Test
    public void testBuildErrorStackTracePanel() {
        errorWindow = new ErrorWindow(ERROR_MESSAGE, ERROR_STACKTRACE);
        VerticalLayout verticalLayout = errorWindow.buildErrorStackTracePanel(ERROR_STACKTRACE);
        assertNotNull(verticalLayout);
        assertFalse(verticalLayout.isVisible());
        assertEquals(COMPONENT_FULL_SIZE, verticalLayout.getHeight());
        assertEquals(Unit.PERCENTAGE, verticalLayout.getHeightUnit().orElseThrow());
        assertEquals(COMPONENT_FULL_SIZE, verticalLayout.getWidth());
        assertEquals(Unit.PERCENTAGE, verticalLayout.getWidthUnit().orElseThrow());
        verifyPreComponent(verticalLayout.getComponentAt(0));
    }

    private void verifyPreComponent(Component component) {
        assertNotNull(component);
        assertEquals(Pre.class, component.getClass());
        Pre pre = (Pre) component;
        assertEquals(ERROR_STACKTRACE, pre.getText());
    }

    private void verifyLabel(Component component) {
        assertNotNull(component);
        assertEquals(Label.class, component.getClass());
        Label label = (Label) component;
        assertEquals(ERROR_MESSAGE, label.getText());
    }
}
