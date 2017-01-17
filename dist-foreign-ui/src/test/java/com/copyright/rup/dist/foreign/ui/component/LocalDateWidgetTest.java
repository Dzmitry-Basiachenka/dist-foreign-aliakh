package com.copyright.rup.dist.foreign.ui.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.vaadin.widget.PopupDateWidget;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Verifies {@link LocalDateWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/13/2017
 *
 * @author Mikita Hladkikh
 */
public class LocalDateWidgetTest {

    private LocalDateWidget widget;

    @Before
    public void setUp() {
        Property<LocalDate> property = new ObjectProperty<>(null, LocalDate.class);
        widget = new LocalDateWidget("Caption", property);
    }

    @Test
    public void testConstructor() {
        PopupDateWidget dateWidget = Whitebox.getInternalState(widget, PopupDateWidget.class);
        Property dateProperty = Whitebox.getInternalState(widget, Property.class);
        assertNotNull(dateWidget);
        assertEquals("Caption", dateWidget.getCaption());
        assertNotNull(dateProperty);
    }

    @Test
    public void testInitContent() {
        assertSame(Whitebox.getInternalState(widget, PopupDateWidget.class), widget.initContent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getValue() {
        Whitebox.getInternalState(widget, Property.class).setValue(new Date());
        assertEquals(LocalDate.now(), widget.getValue());
    }

    @Test
    public void setInternalValue() {
        Property dateProperty = Whitebox.getInternalState(widget, Property.class);
        widget.setInternalValue(LocalDate.now());
        assertEquals(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
            dateProperty.getValue());
    }

    @Test
    public void testGetType() {
        assertEquals(LocalDate.class, widget.getType());
    }
}
