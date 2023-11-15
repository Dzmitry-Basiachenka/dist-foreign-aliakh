package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Browser;

import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Verifies {@link UnsupportedBrowserWindow}.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 10/10/14
 *
 * @author Siarhei Sabetski
 */
public class UnsupportedBrowserWindowTest {

    private static final String WIDTH_SIZE = "100%";
    private static final Set<Browser> SUPPORTED_BROWSERS = Sets.newHashSet(
        new Browser(Browser.BrowserType.CHROME, Range.all()),
        new Browser(Browser.BrowserType.FIREFOX, Range.all()));

    @Test
    public void testConstruct() {
        UnsupportedBrowserWindow unsupportedBrowserWindow =
            new UnsupportedBrowserWindow("The Application", SUPPORTED_BROWSERS);
        verifyDialogSizeFull(unsupportedBrowserWindow);
        assertFalse(unsupportedBrowserWindow.isDraggable());
        assertFalse(unsupportedBrowserWindow.isResizable());
        assertFalse(unsupportedBrowserWindow.isCloseOnEsc());
        assertFalse(unsupportedBrowserWindow.isCloseOnOutsideClick());
        verifyContent(unsupportedBrowserWindow.getChildren().collect(Collectors.toList()).get(0));
    }

    private void verifyDialogSizeFull(Dialog window) {
        assertEquals(WIDTH_SIZE, window.getWidth());
        assertEquals(WIDTH_SIZE, window.getHeight());
        assertEquals(Unit.PERCENTAGE, window.getWidthUnit().orElseThrow());
        assertEquals(Unit.PERCENTAGE, window.getHeightUnit().orElseThrow());
    }

    private void verifyContent(Component content) {
        assertEquals(VerticalLayout.class, content.getClass());
        VerticalLayout layout = (VerticalLayout) content;
        verifySizeFull(layout);
        assertTrue(StringUtils.contains(layout.getClassName(), "unsupported-browser-layout"));
        assertEquals(1, layout.getComponentCount());
        verifyLabel(layout.getComponentAt(0));
    }

    private void verifyLabel(Component component) {
        assertEquals(Label.class, component.getClass());
        Label label = (Label) component;
        assertTrue(StringUtils.contains(label.getText(), "The Application"));
        assertTrue(StringUtils.contains(label.getText(), Browser.BrowserType.CHROME.toString()));
        assertTrue(StringUtils.contains(label.getText(), Browser.BrowserType.FIREFOX.toString()));
        assertEquals(WIDTH_SIZE, label.getWidth());
        assertTrue(StringUtils.contains(label.getClassName(), "unsupported-browser-label"));
    }

    private void verifySizeFull(VerticalLayout layout) {
        assertEquals(WIDTH_SIZE, layout.getWidth());
        assertEquals(WIDTH_SIZE, layout.getHeight());
        assertEquals(Unit.PERCENTAGE, layout.getWidthUnit().orElseThrow());
        assertEquals(Unit.PERCENTAGE, layout.getHeightUnit().orElseThrow());
    }
}
