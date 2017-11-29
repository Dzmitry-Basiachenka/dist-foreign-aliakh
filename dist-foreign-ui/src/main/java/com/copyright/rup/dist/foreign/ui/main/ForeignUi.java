package com.copyright.rup.dist.foreign.ui.main;

import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.CommonUi;
import com.copyright.rup.vaadin.widget.RootWidget;
import com.copyright.rup.vaadin.widget.api.IController;

import com.google.common.collect.Lists;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ErrorHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Entry point for application.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankovov
 */
@org.springframework.stereotype.Component("dist.foreignUi")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Theme("foreign")
public class ForeignUi extends CommonUi {

    private static final ResourceBundle MESSAGES =
        ResourceBundle.getBundle("com.copyright.rup.dist.foreign.ui.messages");

    private static final String REPORT_MENU_CSS_POSITION = "left: 265px;";
    @Autowired
    private IMainWidgetController controller;
    @Autowired
    private IReportController reportController;

    /**
     * Gets a message associated with specified {@code key}.
     *
     * @param key        the key to get {@code message}
     * @param parameters arguments referenced by the format specifiers in the format string
     * @return the string message
     */
    public static String getMessage(String key, Object... parameters) {
        return String.format(MESSAGES.getString(key), parameters);
    }

    @Override
    protected void initUi() {
        super.initUi();
        controller.refreshWidget();
        addReportMenu();
    }

    @Override
    protected Component initMainWidget() {
        return controller.initWidget();
    }

    @Override
    protected String getApplicationTitle() {
        return getMessage("application.name");
    }

    @Override
    protected boolean hasAccessPermission() {
        return ForeignSecurityUtils.hasAccessPermission();
    }

    @Override
    protected ErrorHandler initErrorHandler() {
        return new ForeignErrorHandler(this);
    }

    @Override
    protected List<Component> getAdditionalComponents() {
        Button refreshButton = Buttons.createRefreshIcon();
        refreshButton.addListener(Button.ClickEvent.class, controller, IController.REFRESH_WIDGET_HANDLER);
        return Lists.newArrayList(refreshButton);
    }

    /**
     * Sets main widget controller.
     *
     * @param mainWidgetController main widget controller
     */
    void setMainWidgetController(IMainWidgetController mainWidgetController) {
        this.controller = mainWidgetController;
    }

    private void addReportMenu() {
        ((RootWidget) getContent()).getAbsoluteLayout().addComponent(reportController.initWidget(),
            REPORT_MENU_CSS_POSITION);
    }
}
