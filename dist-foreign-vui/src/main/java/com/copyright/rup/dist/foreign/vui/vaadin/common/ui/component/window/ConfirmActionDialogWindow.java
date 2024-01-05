package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * Widget is a kind of confirmation dialog, but has an extra feature - user is able to add textual comment (reason)
 * for action confirmation. Entering reason is optional.
 * <p/>
 * Copyright (C) 2015 copyright.com
 * <p/>
 * Date: 11/11/15
 *
 * @author Mikalai Bezmen
 */
//TODO {vaadin23} check the ability to inherit from ConfirmDialogWindow
public class ConfirmActionDialogWindow extends Dialog {

    private static final long serialVersionUID = -5792848926766681285L;

    private final Consumer<String> action;
    private final Binder<String> binder = new Binder<>();
    private TextField reasonField;

    /**
     * Constructs new confirm action dialog.
     *
     * @param action         action on confirmation button click
     * @param caption        window caption
     * @param message        window message
     * @param confirmCaption caption for confirm button
     * @param declineCaption caption for decline button
     * @param validators     list of validators
     */
    public ConfirmActionDialogWindow(Consumer<String> action, String caption, String message,
                                     String confirmCaption, String declineCaption, List<Validator<String>> validators) {
        this.action = action;
        initWindow(caption, message, confirmCaption, declineCaption, validators);
    }

    private void initWindow(String caption, String message, String confirmCaption, String declineCaption,
                            List<Validator<String>> validators) {
        setHeaderTitle(StringUtils.defaultIfBlank(caption, "Confirm action"));
        setResizable(false);
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        setWidth(550, Unit.PIXELS);
        add(initContentLayout(message));
        getFooter().add(initButtonsLayout(confirmCaption, declineCaption));
        addValidators(validators);
        VaadinUtils.addComponentStyle(this, "confirm-action-dialog-window");
    }

    private VerticalLayout initContentLayout(String message) {
        Html contentLabel = new Html(String.format("<div>%s</div>", message));
        reasonField = new TextField("Reason");
        VaadinUtils.setMaxComponentsWidth(reasonField);
        VerticalLayout contentLayout = new VerticalLayout(contentLabel, reasonField);
        contentLayout.setClassName("v-label-white-space-normal");
        contentLayout.setWidth(100, Unit.PERCENTAGE);
        return contentLayout;
    }

    private HorizontalLayout initButtonsLayout(String confirmCaption, String declineCaption) {
        Button confirmButton = StringUtils.isNotBlank(confirmCaption)
            ? Buttons.createButton(confirmCaption) : Buttons.createYesButton();
        confirmButton.addClickListener(event -> {
            if (!binder.isValid()) {
                Windows.showValidationErrorWindow();
            } else {
                action.accept(reasonField.getValue());
                close();
            }
        });
        Button declineButton = StringUtils.isNotBlank(declineCaption)
            ? Buttons.createButton(declineCaption) : Buttons.createCancelButton();
        declineButton.addClickListener(event -> close());
        return new HorizontalLayout(confirmButton, declineButton);
    }

    private void addValidators(List<Validator<String>> validators) {
        if (CollectionUtils.isNotEmpty(validators)) {
            Binder.BindingBuilder<String, String> builder = binder.forField(reasonField);
            for (Validator<String> validator : validators) {
                builder = builder.withValidator(validator);
            }
            builder.bind(ValueProvider.identity(), (s, v) -> s = v).validate();
            reasonField.setRequiredIndicatorVisible(true);
            reasonField.setValueChangeMode(ValueChangeMode.LAZY);
        }
    }
}
