package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterWindowController;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.function.ValueProvider;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Utility class to work with modal windows.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 6/19/14
 *
 * @author Nikita Levyankov
 */
public final class Windows {

    private Windows() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Show modal window.
     *
     * @param window Window
     */
    public static void showModalWindow(Dialog window) {
        Objects.requireNonNull(window).setModal(true);
        window.open();
    }

    /**
     * Shows window with notification.
     *
     * @param message message to show
     */
    public static void showNotificationWindow(String message) {
        showModalWindow(new NotificationWindow(message));
    }

    /**
     * Shows window with notification.
     *
     * @param caption caption of window
     * @param message message to show
     */
    public static void showNotificationWindow(String caption, String message) {
        NotificationWindow notificationWindow = new NotificationWindow(message, caption);
        showModalWindow(notificationWindow);
    }

    /**
     * Shows validation error window.
     */
    public static void showValidationErrorWindow() {
        showModalWindow(new NotificationWindow(ForeignUi.getMessage("message.validation.common_message"),
            "Validation Error Window"));
    }

    /**
     * Shows confirm dialog window.
     *
     * @param message  message of dialog
     * @param listener confirm dialog listener
     * @return confirm dialog
     */
    public static Dialog showConfirmDialog(String message, IConfirmCancelListener listener) {
        return showConfirmDialog(null, message, null, null, listener);
    }

    /**
     * Shows confirm dialog window.
     *
     * @param caption        caption of dialog
     * @param message        message of dialog
     * @param confirmCaption caption for confirm button
     * @param declineCaption caption for decline button
     * @param listener       confirm dialog listener
     * @return confirm dialog.
     */
    public static Dialog showConfirmDialog(String caption, String message, String confirmCaption,
                                           String declineCaption, IConfirmCancelListener listener) {
        ConfirmDialogWindow window =
            new ConfirmDialogWindow(listener, caption, message, confirmCaption, declineCaption);
        showModalWindow(window);
        return window;
    }

    /**
     * Constructs and shows {@link FilterWindow} based on provided parameters. {@link FilterWindow} parameters
     * correspond to the parameters of given {@link IFilterWindowController} instance.
     *
     * @param caption    window caption. Can be blank if no caption is required
     * @param controller {@link IFilterWindowController} instance. Must not be null
     * @param providers  {@link ValueProvider}s
     * @param <T>        bean type
     * @return constructed {@link FilterWindow}
     */
    public static <T> FilterWindow<T> showFilterWindow(String caption, IFilterWindowController<T> controller,
                                                       ValueProvider... providers) {
        FilterWindow<T> filterWindow = new FilterWindow(caption, Objects.requireNonNull(controller), providers);
        showModalWindow(filterWindow);
        return filterWindow;
    }

    /**
     * Shows confirm action dialog window.
     *
     * @param caption        caption of dialog
     * @param message        message of dialog
     * @param confirmCaption caption for confirm button
     * @param declineCaption caption for decline button
     * @param action         action on confirmation button click
     */
    public static void showConfirmDialogWithReason(String caption, String message, String confirmCaption,
                                                   String declineCaption,
                                                   Consumer<String> action) {
        showConfirmDialogWithReason(caption, message, confirmCaption, declineCaption, action,
            Collections.emptyList());
    }

    /**
     * Shows confirm action dialog window.
     *
     * @param caption        caption of dialog
     * @param message        message of dialog
     * @param confirmCaption caption for confirm button
     * @param declineCaption caption for decline button
     * @param action         action on confirmation button click
     * @param validators     list of validators for reason text field
     */
    public static void showConfirmDialogWithReason(String caption, String message, String confirmCaption,
                                                   String declineCaption,
                                                   Consumer<String> action,
                                                   List<Validator<String>> validators) {
        Objects.requireNonNull(validators);
        ConfirmActionDialogWindow window =
            new ConfirmActionDialogWindow(action, caption, message, confirmCaption, declineCaption, validators);
        showModalWindow(window);
    }
}
