package com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api;

import com.vaadin.flow.component.HasComponents;

/**
 * Interface that will allow to unify approach of UI handling.
 * Contains method that will be called to initialize current widget (Component, Dialog, etc).
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 1/25/13
 *
 * @param <X> controller class for given widget.
 * @author Nikita Levyankov
 * @author Anton Azarenka
 */
public interface IWidget<X extends IController<? extends IWidget>> extends HasComponents {

    /**
     * Performs initialization of given Widget for regular user.
     *
     * @param <T> {@link IWidget} implementation class.
     * @return the self instance.
     */
    <T extends IWidget> T init();

    /**
     * Sets the controller for given widget.
     *
     * @param controller {@link IController}
     */
    void setController(X controller);
}
