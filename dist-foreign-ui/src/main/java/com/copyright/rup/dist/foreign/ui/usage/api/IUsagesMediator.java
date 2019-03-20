package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.vaadin.widget.api.IMediator;

/**
 * Mediator interface for Usages widget.
 * Provides ability of applying user permissions after initializing of the widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/12/19
 *
 * @author Darya Baraukova
 */
public interface IUsagesMediator extends IMediator {

    /**
     * Displays workflow buttons only related to selected Product Family. All other buttons become hidden.
     *
     * @param productFamily Product Family
     */
    void onProductFamilyChanged(String productFamily);
}
