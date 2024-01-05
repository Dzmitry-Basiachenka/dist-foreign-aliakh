package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.domain.Scenario;

import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;

/**
 * Event that is thrown when new {@link Scenario} is created.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/29/2019
 *
 * @author Pavel Liakh
 */
public class ScenarioCreateEvent extends Event {

    private static final long serialVersionUID = -2098343739030729321L;

    private final Scenario scenario;

    /**
     * Constructor.
     *
     * @param source   event source
     * @param scenario created {@link Scenario}
     */
    public ScenarioCreateEvent(Component source, Scenario scenario) {
        super(source);
        this.scenario = scenario;
    }

    /**
     * @return {@link Scenario}.
     */
    public Scenario getScenarioId() {
        return scenario;
    }
}

