package com.copyright.rup.dist.foreign.vui.main.api;


import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

/**
 * Interface for the main widget of application.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public interface IMainWidget extends IWidget<IMainWidgetController> {

    /**
     * Displays specific widgets based on globally selected product family.
     */
    void updateProductFamily();
}
