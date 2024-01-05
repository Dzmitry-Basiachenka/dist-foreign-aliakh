package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import com.copyright.rup.dist.foreign.vui.vaadin.common.themes.Cornerstone;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.ThemeResource;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * Widget for header.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/07/2023
 *
 * @author Anton Azarenka
 */
public class HeaderWidget extends HorizontalLayout {

    private static final long serialVersionUID = -5799526500224853304L;
    private static final String COPYRIGHT_LOGO = "img/cccLogo.svg";

    /**
     * Constructor.
     *
     * @param applicationTitle title of application
     * @param activeUser       active user name
     */
    public HeaderWidget(String applicationTitle, String activeUser) {
        initializeHeader(applicationTitle, activeUser);
    }

    private void initializeHeader(String applicationTitle, String activeUser) {
        setSizeFull();
        add(ThemeResource.getImage(COPYRIGHT_LOGO));
        Span appTitleSpan = new Span(StringUtils.defaultIfBlank(applicationTitle, StringUtils.EMPTY));
        addAndExpand(appTitleSpan);
        Label userNameLabel = new Label(activeUser);
        add(userNameLabel);
        add(Buttons.createLogoutButton());
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        setClassName(Cornerstone.HEADER_WIDGET);
        VaadinUtils.addComponentStyle(appTitleSpan, Cornerstone.LOGO_APPLICATION_TITLE_LABEL);
        VaadinUtils.addComponentStyle(userNameLabel, Cornerstone.USER_WIDGET_USERNAME_LABEL);
    }
}
