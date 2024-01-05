package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;

/**
 * Event appears when FasExcludeRightsholdersWindow closes.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/29/2019
 *
 * @author Pavel Liakh
 */
public class ExcludeUsagesEvent extends Event {

    private static final long serialVersionUID = -3160141471638653949L;

    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the source component of the event
     */
    public ExcludeUsagesEvent(Component source) {
        super(source);
    }
}
