package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

/**
 * Represents listener to confirm or decline actions in dialogs.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 08/16/2023
 *
 * @author Ihar Suvorau
 */
public interface IConfirmCancelListener {

    /**
     * Method is called when confirmation button was pressed.
     */
    void confirm();

    /**
     * Method is called when action was canceled or dialog was closed.
     * Default implementation does nothing.
     */
    default void cancel() {
        // Stub implementation
    }
}
