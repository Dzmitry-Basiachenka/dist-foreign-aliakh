package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Root widget for application.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/07/2023
 *
 * @author Anton Azarenka
 */
public class RootWidget extends VerticalLayout {

    /**
     * Constructor.
     *
     * @param mainWidget main widget
     */
    public RootWidget(Component mainWidget) {
        initializeRootWidget(mainWidget);
    }

    private void initializeRootWidget(Component mainWidget) {
        add(mainWidget);
        setSizeFull();
        setPadding(false);
        setMargin(false);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.STRETCH);
        setHorizontalComponentAlignment(Alignment.CENTER);
    }
}
