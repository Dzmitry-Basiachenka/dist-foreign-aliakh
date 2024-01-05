package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;

import com.vaadin.ui.Window;

/**
 * Common scenario parameter window.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/11/2020
 *
 * @param <T> type of scenario parameter
 * @author Ihar Suvorau
 */
public abstract class CommonScenarioParameterWindow<T> extends Window {

    private static final long serialVersionUID = -7886076868059841715L;

    /**
     * Sets default parameters for the window.
     *
     * @param params to set
     */
    abstract void setDefault(T params);

    /**
     * Sets applied parameters for the window.
     *
     * @param params to set
     */
    abstract void setAppliedParameters(T params);

    /**
     * Fires specified {@link ParametersSaveEvent}.
     *
     * @param parametersSaveEvent instance of {@link ParametersSaveEvent}
     */
    abstract void fireParametersSaveEvent(ParametersSaveEvent<T> parametersSaveEvent);
}
