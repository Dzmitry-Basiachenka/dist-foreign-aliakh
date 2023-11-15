package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents widget for confirm dialog window.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 11/4/13
 *
 * @author Siarhei Sabetski
 * @author Mikalai Bezmen
 * @author Nikita Levyankov
 */
public final class ConfirmDialogWindow extends Dialog {

    private final IConfirmCancelListener listener;
    private final Button confirmButton;
    private final Button declineButton;
    private final Html contentLabel;

    /**
     * Constructs new confirm dialog.
     *
     * @param listener       confirm dialog listener
     * @param caption        window caption
     * @param message        window message
     * @param confirmCaption caption for confirm button
     * @param declineCaption caption for decline button
     */
    public ConfirmDialogWindow(IConfirmCancelListener listener, String caption, String message, String confirmCaption,
                               String declineCaption) {
        this.listener = listener;
        setHeaderTitle(StringUtils.defaultIfBlank(caption, "Confirm action"));
        contentLabel = new Html(String.format("<span>%s</span>", message));
        confirmButton = StringUtils.isNotBlank(confirmCaption)
            ? Buttons.createButton(confirmCaption) : Buttons.createYesButton();
        declineButton = StringUtils.isNotBlank(declineCaption)
            ? Buttons.createButton(declineCaption) : Buttons.createCancelButton();
        initWindow();
    }

    private void initWindow() {
        VaadinUtils.addComponentStyle(this, "confirm-dialog-window");
        setResizable(false);
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        setWidth(400, Unit.PIXELS);
        HorizontalLayout buttons = new HorizontalLayout(confirmButton, declineButton);
        VerticalLayout contentLayout = new VerticalLayout(contentLabel, buttons);
        contentLayout.setClassName("v-label-white-space-normal");
        contentLayout.setWidth(100, Unit.PERCENTAGE);
        add(contentLayout);
        getFooter().add(buttons);
        confirmButton.addClickListener(event -> {
            listener.confirm();
            close();
        });
        declineButton.addClickListener(event -> {
            listener.cancel();
            close();
        });
    }
}
