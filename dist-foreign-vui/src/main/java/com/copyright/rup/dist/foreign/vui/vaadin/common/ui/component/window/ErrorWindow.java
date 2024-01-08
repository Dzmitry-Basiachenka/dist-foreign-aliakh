package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * Modal window to display error of entity.
 * The error window is common and used to display the simple warning MESSAGES and extended MESSAGES with stacktrace.
 * If stacktrace was passed to the constructor and it was not blank, the More/Less button will be shown to display
 * the trace in the text area.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/2023
 *
 * @author Anton Azarenka
 */
public class ErrorWindow extends Dialog {

    private static final long serialVersionUID = 4914906305223639303L;

    private final String message;
    private final String stackTrace;

    /**
     * Constructor.
     *
     * @param message    the message.
     * @param stackTrace the stackTrace.
     */
    public ErrorWindow(String message, String stackTrace) {
        this.message = message;
        this.stackTrace = stackTrace;
        super.setHeaderTitle("Error");
        super.getHeader().add(Buttons.createCloseIcon(this));
        super.add(initRootLayout());
        super.setVisible(true);
        super.setResizable(true);
        super.setWidth(500, Unit.PIXELS);
        super.setHeight(200, Unit.PIXELS);
        super.getFooter().add(buildControlsLayout());
    }

    /**
     * @return instance of {@link VerticalLayout} root panel layout
     */
    //TODO {sonar} apply private access modifier and adjust tests
    final VerticalLayout initRootLayout() {
        var rootLayout = new VerticalLayout();
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        HorizontalLayout errorMessage = buildErrorMessageLayout();
        rootLayout.add(errorMessage);
        if (StringUtils.isNotBlank(stackTrace)) {
            VerticalLayout errorStackTracePanel = buildErrorStackTracePanel(stackTrace);
            var details = new Button("Show more");
            details.addClickListener(new DetailsButtonClickListener(details, errorStackTracePanel));
            getFooter().add(details);
            rootLayout.setMargin(true);
            rootLayout.add(errorStackTracePanel);
        }
        return rootLayout;
    }

    /**
     * @return {@link HorizontalLayout}, which contains a label with error message.
     */
    HorizontalLayout buildErrorMessageLayout() {
        Label errorMessage = new Label(StringUtils.defaultIfBlank(message, "Exception occurred"));
        HorizontalLayout horizontalLayout = new HorizontalLayout(errorMessage);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    /**
     * @return {@link HorizontalLayout} instance with OK button, which closes the window.
     */
    //TODO {sonar} apply private access modifier and adjust tests
    final HorizontalLayout buildControlsLayout() {
        Button okButton = Buttons.createOkButton();
        okButton.addClickListener(event -> close());
        HorizontalLayout controlPanel = new HorizontalLayout();
        controlPanel.add(okButton);
        return controlPanel;
    }

    /**
     * Initialize panel for displaying stacktrace of occurred error.
     *
     * @param stacktraceValue stacktrace of occurred error
     * @return {@link VerticalLayout} instance, which contains a label with stacktrace
     */
    VerticalLayout buildErrorStackTracePanel(String stacktraceValue) {
        Pre errorStackTrace = new Pre();
        errorStackTrace.add(stacktraceValue);
        VerticalLayout layout = new VerticalLayout(errorStackTrace);
        layout.setVisible(false);
        layout.setSizeFull();
        return layout;
    }

    public String getMessage() {
        return message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * ClickListener for button, which allows hiding or showing stacktrace.
     */
    class DetailsButtonClickListener implements ComponentEventListener<ClickEvent<Button>> {

        private static final long serialVersionUID = -6645356531789145850L;

        private final Button details;
        private final VerticalLayout stackTracePanel;

        /**
         * Constructor.
         *
         * @param detailsButton        the button, which allows hiding or showing stacktrace
         * @param errorStackTracePanel the panel, which contains stacktrace
         */
        DetailsButtonClickListener(Button detailsButton, VerticalLayout errorStackTracePanel) {
            details = detailsButton;
            stackTracePanel = errorStackTracePanel;
        }

        /**
         * @return details button
         */
        Button getDetails() {
            return details;
        }

        /**
         * @return panel with stacktrace
         */
        VerticalLayout getStackTracePanel() {
            return stackTracePanel;
        }

        @Override
        public void onComponentEvent(ClickEvent<Button> event) {
            boolean visible = stackTracePanel.isVisible();
            if (visible) {
                details.setText("Show more");
                setHeight(200, Unit.PIXELS);
                setWidth(500, Unit.PIXELS);
            } else {
                details.setText("Show less");
                setHeight(70, Unit.PERCENTAGE);
                setWidth(80, Unit.PERCENTAGE);
            }
            stackTracePanel.setVisible(!visible);
        }
    }
}
