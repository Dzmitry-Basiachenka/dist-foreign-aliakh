package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Widget is displayed when user has no permissions to access the application.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 12/27/13
 *
 * @author Siarhei Sabetski
 * @author Mikalai Bezmen
 * @author Nikita Levyankov
 * @author Anton Azarenka
 */
public final class AccessDeniedWidget extends VerticalLayout {

    /**
     * Constructor.
     */
    public AccessDeniedWidget() {
        Label message = new Label("You have no permission to access application. Please log out.");
        message.setWidth(null);
        message.addClassName("access-denied-label");
        add(message);
        addClassName("access-denied-layout");
        setSizeFull();
        setAlignSelf(Alignment.CENTER, message);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
