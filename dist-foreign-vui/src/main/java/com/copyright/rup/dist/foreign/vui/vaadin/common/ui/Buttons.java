package com.copyright.rup.dist.foreign.vui.vaadin.common.ui;

import com.copyright.rup.dist.foreign.vui.vaadin.common.themes.Cornerstone;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.MaximizeModalWindowManager;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.VaadinSession;

import java.util.Objects;

/**
 * Utility class to work with buttons.
 * Provides common methods for buttons creation.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/07/2023
 *
 * @author Nikita Levyankov
 * @author Anton Azarenka
 */
public final class Buttons {

    private static final String LOGOUT_BUTTON_TEXT = "log out";
    private static final String APPLICATION_LOGOUT_URL_FRAGMENT = "j_spring_security_logout";

    private Buttons() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Creates button which closes specified dialog.
     * and Escape key is used as click shortcut.
     * If caption or Window parameters are null - {@link NullPointerException} will be thrown.
     *
     * @param caption button's caption
     * @param dialog  dialog to close on button click
     * @return button instance with specified caption and click listener for closing specified dialog
     */
    public static Button createButton(String caption, Dialog dialog) {
        Button button = createButton(Objects.requireNonNull(caption));
        Objects.requireNonNull(dialog);
        button.addClickListener(event -> dialog.close());
        button.addClickShortcut(Key.ESCAPE);
        return button;
    }

    /**
     * Creates button with caption 'Log out' and adds listener that closes current session.
     *
     * @return button with caption 'Log out'
     */
    public static Button createLogoutButton() {
        Button button = new Button(LOGOUT_BUTTON_TEXT,
            VaadinIcon.SIGN_OUT.create(), event -> {
            VaadinSession.getCurrent().close();
            UI.getCurrent().getPage().setLocation(APPLICATION_LOGOUT_URL_FRAGMENT);
        });
        VaadinUtils.addComponentStyle(button, Cornerstone.USER_WIDGET_LOGOUT_BUTTON);
        return button;
    }

    /**
     * @return the icon which represents "Refresh" functionality.
     */
    public static Button createRefreshIcon() {
        Button result = new Button(new Icon(VaadinIcon.REFRESH));
        result.setTooltipText("Refresh");
        VaadinUtils.addComponentStyle(result, "button-refresh");
        return result;
    }

    /**
     * Creates icon which closes specified dialog.
     *
     * @param dialog dialog to close on button click
     * @return button instance with close listener
     */
    public static Button createCloseIcon(Dialog dialog) {
        Button closeIconButton = new Button(new Icon(VaadinIcon.CLOSE), event -> dialog.close());
        closeIconButton.setTooltipText("Close window");
        VaadinUtils.addComponentStyle(closeIconButton, "button-close");
        return closeIconButton;
    }

    /**
     * Creates icon which closes specified dialog.
     *
     * @param dialog dialog to close on button click
     * @return button instance with close listener
     */
    public static Button createMaximizeWindowIcon(Dialog dialog) {
        MaximizeModalWindowManager manager = new MaximizeModalWindowManager(dialog);
        return manager.getResizeButton();
    }

    /**
     * Creates button with caption.
     * Button will get the style name and the id equal to the caption.
     *
     * @param caption the caption for the button.
     * @return the button instance.
     */
    public static Button createButton(String caption) {
        Button result = new Button(caption);
        VaadinUtils.setButtonsAutoDisabled(result);
        VaadinUtils.addComponentStyle(result, caption);
        return result;
    }

    /**
     * Creates button with caption 'Ok' and click listener which closes specified dialog.
     *
     * @param dialog dialog to close on button click
     * @return button with 'Ok' caption and listener for closing specified dialog
     */
    public static Button createOkButton(Dialog dialog) {
        return createButton("Ok", Objects.requireNonNull(dialog));
    }

    /**
     * @return button with 'Ok' caption
     */
    public static Button createOkButton() {
        return createButton("Ok");
    }

    /**
     * Creates button with caption 'Cancel' and click listener which closes specified dialog.
     *
     * @param dialog dialog to close on button click
     * @return button with 'Cancel' caption and listener for closing specified dialog
     */
    public static Button createCancelButton(Dialog dialog) {
        return createButton("Cancel", Objects.requireNonNull(dialog));
    }

    /**
     * @return button with caption 'Cancel'.
     */
    public static Button createCancelButton() {
        return createButton("Cancel");
    }

    /**
     * @return button with caption 'Yes'.
     */
    public static Button createYesButton() {
        return createButton("Yes");
    }

    /**
     * Creates button which closes specified dialog.
     *
     * @param dialog dialog to close on button click
     * @return button instance with close listener
     */
    public static Button createCloseButton(Dialog dialog) {
        return createButton("Close", Objects.requireNonNull(dialog));
    }

    /**
     * Creates link Button with specified action handler.
     *
     * @param listener listener
     * @param caption  type of object
     * @return instance of {@link Button}
     */
    public static Button createLinkButton(ComponentEventListener<ClickEvent<Button>> listener, String caption) {
        Button button = createButton(caption);
        button.addClickListener(listener);
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_SMALL);
        VaadinUtils.setButtonsAutoDisabled(button);
        return button;
    }

    /**
     * Creates link Button.
     *
     * @param caption type of object
     * @return instance of {@link Button}
     */
    public static Button createLinkButton(String caption) {
        Button button = createButton(caption);
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_SMALL);
        VaadinUtils.setButtonsAutoDisabled(button);
        return button;
    }
}
