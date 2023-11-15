package com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api;

/**
 * Interface used to indicate ability to refresh component content.
 * Provides {@link #refresh()} method that will be called to refresh the content of the widget.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 10/04/13
 *
 * @author Nikita Levyankov
 * @author Anton Azarenka
 */
public interface IRefreshable {

    /**
     * Refreshes component content.
     */
    void refresh();
}
