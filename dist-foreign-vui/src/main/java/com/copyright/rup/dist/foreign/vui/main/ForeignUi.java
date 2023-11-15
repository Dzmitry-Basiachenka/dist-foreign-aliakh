package com.copyright.rup.dist.foreign.vui.main;

import com.vaadin.flow.component.applayout.AppLayout;

import java.util.ResourceBundle;

/**
 * Entry point for application.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankovov
 * @author Anton Azarenka
 */
public class ForeignUi extends AppLayout {

    private static final ResourceBundle MESSAGES =
     ResourceBundle.getBundle("com.copyright.rup.dist.foreign.ui.messages");

    /**
     * Gets a message associated with specified {@code key}.
     *
     * @param key        the key to get {@code message}
     * @param parameters arguments referenced by the format specifiers in the format string
     * @return the string message
     */
    public static String getMessage(String key, Object... parameters) {
        return String.format(MESSAGES.getString(key), parameters);
    }
}
