package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Verify {@link CommonDialog}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/22/2023
 *
 * @author Dzmitry Basiachenka
 */
public class CommonDialogTest {

    @Test
    public void testSetModalWindowProperties() {
        CommonDialog dialog = new CommonDialog();
        dialog.setModalWindowProperties("test-dialog", true);
        assertEquals("test-dialog", dialog.getClassName());
        assertTrue(dialog.isResizable());
        assertTrue(dialog.isDraggable());
        assertFalse(dialog.isCloseOnEsc());
        assertFalse(dialog.isCloseOnOutsideClick());
        assertFalse(dialog.isOpened());
        assertNull(dialog.getWidth());
        assertNull(dialog.getMinWidth());
        assertEquals("101.7%", dialog.getMaxWidth());
        assertNull(dialog.getHeight());
        assertEquals("200px", dialog.getMinHeight());
        assertEquals("103.7%", dialog.getMaxHeight());
    }
}
