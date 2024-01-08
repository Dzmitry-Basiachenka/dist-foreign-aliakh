package com.copyright.rup.dist.foreign.vui.usage.api;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

/**
 * Event of filter value changed.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/07/2017
 *
 * @author Aliaksei Pchelnikau
 */
public class FilterChangedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -6008545726970512259L;

    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the source component of the event
     */
    public FilterChangedEvent(Component source) {
        super(source, true);
    }
}
