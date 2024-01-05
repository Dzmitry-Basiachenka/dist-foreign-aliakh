package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.dialog.Dialog;

/**
 * Widget for dialog.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/22/2023
 *
 * @author Dzmitry Basiachenka
 */
public class CommonDialog extends Dialog {

    private static final long serialVersionUID = 2584339011947788897L;

    /**
     * Sets modal window properties.
     *
     * @param style       CSS class name
     * @param isResizable {@code true} if the dialog is resizable, {@code false} otherwise
     */
    public void setModalWindowProperties(String style, boolean isResizable) {
        this.setResizable(isResizable);
        this.setDraggable(true);
        this.setCloseOnEsc(false);
        this.setCloseOnOutsideClick(false);
        this.setMinHeight("200px");
        if (isResizable) {
            this.getHeader().add(Buttons.createMaximizeWindowIcon(this));
        }
        this.getHeader().add(Buttons.createCloseIcon(this));
        VaadinUtils.addComponentStyle(this, style);
    }
}
