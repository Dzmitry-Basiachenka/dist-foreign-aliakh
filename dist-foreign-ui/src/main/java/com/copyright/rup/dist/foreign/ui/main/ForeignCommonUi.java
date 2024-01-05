package com.copyright.rup.dist.foreign.ui.main;

import com.copyright.rup.vaadin.ui.CommonUi;

/**
 * Common implementation of Common UI class.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/20/2023
 *
 * @author Dzmitry Basiachenka
 */
public abstract class ForeignCommonUi extends CommonUi {

    private static final long serialVersionUID = -1129431279765902313L;

    /**
     * Gets string message by key and parameters.
     *
     * @param key        message key
     * @param parameters message parameters
     * @return the string message
     */
    abstract String getStringMessage(String key, Object... parameters);
}
