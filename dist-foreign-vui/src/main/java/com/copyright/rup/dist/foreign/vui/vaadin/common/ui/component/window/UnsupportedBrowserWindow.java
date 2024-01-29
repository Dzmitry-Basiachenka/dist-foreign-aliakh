package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Browser;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * Error window with unsupported browser.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/10/2023
 *
 * @author Anton Azarenka
 */
public class UnsupportedBrowserWindow extends Dialog {

    private static final long serialVersionUID = -4633021324239537256L;

    /**
     * Constructs widget.
     *
     * @param applicationTitle  the title of the application to be shown in the window.
     * @param supportedBrowsers set of supported browsers.
     */
    public UnsupportedBrowserWindow(String applicationTitle, Set<Browser> supportedBrowsers) {
        super.getHeader().add(new Span("Error"));
        super.setSizeFull();
        super.setDraggable(false);
        super.setCloseOnEsc(false);
        super.setResizable(false);
        super.setCloseOnOutsideClick(false);
        super.add(initLayout(buildUnsupportedBrowserMessage(applicationTitle, supportedBrowsers)));
    }

    private VerticalLayout initLayout(String unsupportedBrowserMessage) {
        var label = new Label(unsupportedBrowserMessage);
        label.setSizeFull();
        VaadinUtils.addComponentStyle(label, "unsupported-browser-label");
        var layout = VaadinUtils.initCommonVerticalLayout(label);
        layout.setHeightFull();
        layout.setAlignSelf(Alignment.CENTER, label);
        VaadinUtils.addComponentStyle(layout, "unsupported-browser-layout");
        return layout;
    }

    private String buildUnsupportedBrowserMessage(String applicationTitle, Set<Browser> supportedBrowser) {
        return String.format("%s is optimized for use with %s. For the best experience, " +
            "please use one of these browsers", applicationTitle, StringUtils.join(supportedBrowser, " or "));
    }
}
