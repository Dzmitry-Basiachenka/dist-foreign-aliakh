package com.copyright.rup.dist.foreign.ui.usage.api;

import com.vaadin.ui.Component;

/**
 * Event of filter value changed.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/07/17
 *
 * @author Aliaksei Pchelnikau
 */
public class FilterChangedEvent extends Component.Event {

    private static final long serialVersionUID = -6008545726970512259L;

    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the source component of the event
     */
    public FilterChangedEvent(Component source) {
        super(source);
    }
}
