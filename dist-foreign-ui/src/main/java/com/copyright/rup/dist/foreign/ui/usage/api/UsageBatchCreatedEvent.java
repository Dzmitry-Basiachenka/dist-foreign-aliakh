package com.copyright.rup.dist.foreign.ui.usage.api;

import com.vaadin.ui.Component;

/**
 * Usage batch creation event.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/08/2022
 *
 * @author Aliaksandr Liakh
 */
public class UsageBatchCreatedEvent extends Component.Event {

    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the source component of the event
     */
    public UsageBatchCreatedEvent(Component source) {
        super(source);
    }
}
