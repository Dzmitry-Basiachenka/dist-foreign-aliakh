package com.copyright.rup.dist.foreign.vui.vaadin.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.junit.Test;

/**
 * Verifies {@link VaadinUtils}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 07/12/2023
 *
 * @author Anton Azarenka
 */
public class VaadinUtilsTest {

    @Test
    public void testSetMaxComponentWidth() {
        var layout = new VerticalLayout();
        VaadinUtils.setFullComponentsWidth(layout);
        assertEquals("100%", layout.getStyle().get("width"));
    }

    @Test
    public void testSetButtonsAutoDisabled() {
        var button = new Button();
        VaadinUtils.setButtonsAutoDisabled(button);
        assertTrue(button.isDisableOnClick());
        button.click();
        assertTrue(button.isEnabled());
    }

    @Test(expected = NullPointerException.class)
    public void testSetButtonsAutoDisabledNullParameters() {
        VaadinUtils.setButtonsAutoDisabled((Button[]) null);
        VaadinUtils.setButtonsAutoDisabled((Button) null);
    }

    @Test
    public void testAddComponentStyle() {
        var sourceComponent = new Button();
        var testStyleName = "testStyle1";
        VaadinUtils.addComponentStyle(sourceComponent, testStyleName);
        assertEquals(testStyleName, sourceComponent.getId().orElseThrow());
        assertTrue(sourceComponent.getClassName().contains(testStyleName));
    }

    @Test
    public void testAddComponentStyleWithDuplicateId() {
        var sourceComponent = new Button();
        var testStyleName = "testStyle1";
        var anotherStyle = "testStyle2";
        VaadinUtils.addComponentStyle(sourceComponent, testStyleName);
        assertEquals(testStyleName, sourceComponent.getId().orElseThrow());
        VaadinUtils.addComponentStyle(sourceComponent, anotherStyle);
        assertNotEquals(anotherStyle, sourceComponent.getId().orElseThrow());
        assertTrue(sourceComponent.getClassName().contains(anotherStyle));
    }

    @Test
    public void testSetElementsEnabled() {
        var button = new Button();
        VaadinUtils.setComponentsEnabled(true, button);
        assertTrue(button.isEnabled());
        VaadinUtils.setComponentsEnabled(false, button);
        assertFalse(button.isEnabled());
    }
}
