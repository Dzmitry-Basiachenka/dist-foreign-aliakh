package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;

/**
 * Event appears when ExcludeRightsholdersWindow closes.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/29/2019
 *
 * @author Pavel Liakh
 */
public class ExcludeUsagesEvent extends Event {

    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the source component of the event
     */
    public ExcludeUsagesEvent(Component source) {
        super(source);
    }
}
