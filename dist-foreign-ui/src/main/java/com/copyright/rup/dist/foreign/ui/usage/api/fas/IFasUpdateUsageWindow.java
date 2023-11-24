package com.copyright.rup.dist.foreign.ui.usage.api.fas;

import com.copyright.rup.vaadin.widget.api.IRefreshable;

/**
 * Interface for FAS update usage window.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/24/2023
 *
 * @author Dzmitry Basiachenka
 */
public interface IFasUpdateUsageWindow extends IRefreshable {

    /**
     * Method that handles window closing (from UI).
     */
    void close();
}
