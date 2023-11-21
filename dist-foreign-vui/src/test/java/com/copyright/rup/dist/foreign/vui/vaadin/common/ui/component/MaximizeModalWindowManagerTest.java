package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component;

import static org.junit.Assert.assertEquals;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;

import org.junit.Before;
import org.junit.Test;

/**
 * Verify {@link MaximizeModalWindowManager}.
 *
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 05/17/2023
 *
 * @author Anton Azarenka
 */
public class MaximizeModalWindowManagerTest {

    private static final String WIDTH = "300.0px";
    private static final String HEIGHT = "500.0px";
    private Dialog dialog;

    @Before
    public void setUp() {
        dialog = new Dialog();
        dialog.setWidth(WIDTH);
        dialog.setHeight(HEIGHT);
    }

    @Test
    public void testResizeWindow() {
        MaximizeModalWindowManager manager = new MaximizeModalWindowManager(dialog);
        Button resizeButton = manager.getResizeButton();
        assertEquals(WIDTH, dialog.getWidth());
        assertEquals(HEIGHT, dialog.getHeight());
        resizeButton.click();
        assertEquals("101.7%", dialog.getWidth());
        assertEquals("103.7%", dialog.getHeight());
        resizeButton.click();
        assertEquals(WIDTH, dialog.getWidth());
        assertEquals(HEIGHT, dialog.getHeight());
    }
}
