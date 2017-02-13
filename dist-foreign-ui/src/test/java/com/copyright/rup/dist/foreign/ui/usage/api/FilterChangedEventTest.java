package com.copyright.rup.dist.foreign.ui.usage.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.vaadin.ui.HorizontalLayout;

import org.junit.Test;

/**
 * Verify {@link FilterChangedEvent}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/07/17
 *
 * @author Aliaksei Pchelnikau
 */
public class FilterChangedEventTest {

    @Test
    public void testConstructorWithArguments() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        FilterChangedEvent filterChangedEvent = new FilterChangedEvent(horizontalLayout);
        assertNotNull(filterChangedEvent);
        assertSame(horizontalLayout, filterChangedEvent.getSource());
    }
}
