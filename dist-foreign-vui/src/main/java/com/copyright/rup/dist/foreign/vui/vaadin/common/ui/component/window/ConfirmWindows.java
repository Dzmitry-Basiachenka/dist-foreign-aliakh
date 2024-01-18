package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import com.vaadin.flow.data.binder.Validator;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Utility class to work with confirm modal windows.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 11/22/2023
 *
 * @author Aliaksandr Liakh
 */
public final class ConfirmWindows {

    private ConfirmWindows() {
        throw new AssertionError("Constructor shouldn't be called directly");
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
                                                   String declineCaption, Consumer<String> action) {
        showConfirmDialogWithReason(caption, message, confirmCaption, declineCaption, action, List.of());
    }

    /**
     * Shows confirm action dialog window.
     *
     * @param caption        caption of dialog
     * @param message        message of dialog
     * @param confirmCaption caption for confirm button
     * @param declineCaption caption for decline button
     * @param action         action on confirmation button click
     * @param validator      validator for reason text field
     */
    public static void showConfirmDialogWithReason(String caption, String message, String confirmCaption,
                                                   String declineCaption, Consumer<String> action,
                                                   Validator<String> validator) {
        showConfirmDialogWithReason(caption, message, confirmCaption, declineCaption, action, List.of(validator));
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
                                                   String declineCaption, Consumer<String> action,
                                                   List<Validator<String>> validators) {
        Objects.requireNonNull(validators);
        ConfirmActionDialogWindow window =
            new ConfirmActionDialogWindow(action, caption, message, confirmCaption, declineCaption, validators);
        Windows.showModalWindow(window);
    }
}
