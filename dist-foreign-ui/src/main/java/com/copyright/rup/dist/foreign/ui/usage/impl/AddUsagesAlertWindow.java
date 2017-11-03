package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Confirmation window that informed about usages which won't be added to scenario since they have 'NEW' status.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/02/17
 *
 * @author Ihar Suvorau
 */
public class AddUsagesAlertWindow extends Window {

    private ConfirmDialogWindow.IListener listener;

    void setListener(IListener listener) {
        this.listener = listener;
    }

    /**
     * Constructor.
     *
     * @param scenarioName scenario name
     */
    public AddUsagesAlertWindow(String scenarioName) {
        setWidth(400, Unit.PIXELS);
        setHeight(120, Unit.PIXELS);
        setContent(initContent(scenarioName));
        setCaption("Confirm action");
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "add-usages-alert-window");
    }

    private VerticalLayout initContent(String scenarioName) {
        Label label =
            new Label(ForeignUi.getMessage("message.warning.create_scenario", scenarioName), ContentMode.HTML);
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout content = new VerticalLayout(label, buttonsLayout);
        content.setSizeFull();
        content.setSpacing(true);
        content.setMargin(true);
        content.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private HorizontalLayout initButtonsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        Button continueButton = new Button("Continue");
        continueButton.addClickListener((ClickListener) e -> {
            listener.onActionConfirmed();
            close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener((ClickListener) e -> {
            listener.onActionDeclined();
            close();
        });
        layout.addComponents(continueButton, cancelButton);
        layout.setSpacing(true);
        return layout;
    }
}
