package com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api;

/**
 * Mediator interface for widgets.
 * Provides ability of applying user permissions after initializing of the widget.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 11/4/13
 *
 * @author Siarhei Sabetski
 * @author Nikita Levyankov
 * @author Anton Azarenka
 * @see CommonController
 */
public interface IMediator {

    /**
     * Applies permissions of the concrete user.
     */
    void applyPermissions();
}
