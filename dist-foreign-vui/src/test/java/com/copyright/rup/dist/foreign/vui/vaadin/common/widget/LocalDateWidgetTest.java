package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;

/**
 * Verifies {@link LocalDateWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 05/03/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 */
public class LocalDateWidgetTest {

    private LocalDateWidget widget;

    @Before
    public void setUp() {
        widget = new LocalDateWidget("Caption");
    }

    @Test
    public void testConstructor() {
        DateWidget dateWidget = Whitebox.getInternalState(widget, DateWidget.class);
        assertNotNull(dateWidget);
        assertEquals("Caption", widget.getLabel());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetSetValue() {
        widget.getValue();
    }

    @Test
    public void testPlaceholder() {
        assertEquals("mm/dd/yyyy", widget.getPlaceholder());
    }

    @Test
    public void isEmpty() {
        assertTrue(widget.isEmpty());
        widget.setValue(LocalDate.now());
        assertFalse(widget.isEmpty());
    }
}
