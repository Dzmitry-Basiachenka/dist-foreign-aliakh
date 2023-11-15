package com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api;

/**
 * Interface for widgets with mediator.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 11/5/13
 *
 * @author Siarhei Sabetski
 * @author Anton Azarenka
 */
public interface IMediatorProvider {

    /**
     * Initializes mediator.
     *
     * @return mediator instance.
     */
    IMediator initMediator();
}
