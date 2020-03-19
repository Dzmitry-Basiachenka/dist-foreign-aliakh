package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.ParametersSaveEvent;

import com.vaadin.ui.Window;

/**
 * Common AACL scenario parameter window.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/11/2020
 *
 * @param <T> type of scenario parameter
 * @author Ihar Suvorau
 */
public abstract class AaclCommonScenarioParameterWindow<T> extends Window {

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
