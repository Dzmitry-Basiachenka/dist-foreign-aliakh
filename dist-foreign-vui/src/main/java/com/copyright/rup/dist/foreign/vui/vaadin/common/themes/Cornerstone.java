package com.copyright.rup.dist.foreign.vui.vaadin.common.themes;

/**
 * Custom theme class inherited from {@link } class.
 * Contains custom constants with styles.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 7/1/14
 *
 * @author Nikita Levyankov
 */
public class Cornerstone {

    /**
     * Style for root tabsheet.
     */
    public static final String MAIN_TABSHEET = "main-tabsheet";

    /**
     * Style for the RootWidget.
     * The layout with Header and Tabsheet if header enabled or with tabsheet only if header disabled.
     */
    public static final String ROOT_WIDGET = "root-widget";

    /**
     * Style for additional components that will be placed to the right top corner before logout-layout.
     */
    public static final String ADDITIONAL_COMPONENTS = "additional-components";

    /**
     * Root style for the widget that contains application logo, application title and logout-layout.
     */
    public static final String HEADER_WIDGET = "header-layout";

    /**
     * Style for user layout with Logout button and User name.
     */
    public static final String USER_WIDGET = "logout-layout";

    /**
     * Style for user name label for user widget.
     */
    public static final String USER_WIDGET_USERNAME_LABEL = USER_WIDGET + "-username";

    /**
     * Style for Logout link for user widget.
     */
    public static final String USER_WIDGET_LOGOUT_BUTTON = USER_WIDGET + "-logout-button";

    /**
     * Style for layout with Logo and Application title.
     */
    public static final String LOGO_MAIN_LAYOUT = "logo-layout";

    /**
     * Style for Application title label.
     */
    public static final String LOGO_APPLICATION_TITLE_LABEL = LOGO_MAIN_LAYOUT + "-title";

    /**
     * Constructor.
     */
    protected Cornerstone() {
        throw new IllegalStateException("Constructor shouldn't be called directly");
    }
}
