package com.copyright.rup.dist.foreign.ui.scenario.api.fas;

import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

/**
 * Listener for exclude usages from scenario.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/29/2019
 *
 * @author Pavel Liakh
 */
public interface IExcludeUsagesListener {

    /**
     * {@link #onExcludeDetails(ExcludeUsagesEvent)}.
     */
    Method EXCLUDE_DETAILS_HANDLER = ReflectTools.findMethod(IExcludeUsagesListener.class, "onExcludeDetails",
        ExcludeUsagesEvent.class);

    /**
     * Handles excluding details from scenario.
     *
     * @param excludeUsagesEvent an instance of {@link ExcludeUsagesEvent}
     */
    void onExcludeDetails(ExcludeUsagesEvent excludeUsagesEvent);
}
