package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.domain.UsageFilter;

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

    private UsageFilter filter;

    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the source component of the event
     * @param filter {@link UsageFilter} which was changed
     */
    public FilterChangedEvent(Component source, UsageFilter filter) {
        super(source);
        this.filter = filter;
    }

    /**
     * @return {@link UsageFilter}.
     */
    public UsageFilter getFilter() {
        return filter;
    }
}
