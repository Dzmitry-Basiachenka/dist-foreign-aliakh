package com.copyright.rup.dist.foreign.ui.usage.api.sal;

import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;

/**
 * Event appears when {@link com.copyright.rup.dist.foreign.ui.usage.impl.sal.SalUpdateRighstholderWindow} closes.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/30/20
 *
 * @author Darya Baraukova
 */
public class UpdateRightsholderEvent extends Event {

    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the source component of the event
     */
    public UpdateRightsholderEvent(Component source) {
        super(source);
    }
}
