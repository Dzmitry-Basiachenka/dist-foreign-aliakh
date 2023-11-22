package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents window for notifications.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 11/6/13
 *
 * @author Siarhei Sabetski
 * @author Nikita Levyankov
 */
public final class NotificationWindow extends CommonDialog {

    /**
     * Constructs new notification window with given message.
     * If message is blank or empty - {@link IllegalArgumentException} will be thrown.
     *
     * @param message message to show in window.
     */
    public NotificationWindow(String message) {
        this(message, "Notification Window");
    }

    /**
     * Constructs new notification window with given message with title.
     * If message is blank or empty - {@link IllegalArgumentException} will be thrown.
     *
     * @param message message to show in window.
     * @param title   title
     */
    public NotificationWindow(String message, String title) {
        checkArgument(StringUtils.isNotBlank(message));
        setHeaderTitle(title);
        add(initContent(message));
        getFooter().add(new HorizontalLayout(Buttons.createOkButton(this)));
        setWidth(600, Unit.PIXELS);
        setModalWindowProperties("notification-window", false);
    }

    private Component initContent(String message) {
        Html label = new Html(String.format("<span>%s</span>", message));
        VerticalLayout layout = new VerticalLayout(label);
        layout.setClassName("v-layout-white-space-normal");
        layout.setClassName("v-layout-notification-window");
        layout.setWidth(100, Unit.PERCENTAGE);
        return layout;
    }
}
