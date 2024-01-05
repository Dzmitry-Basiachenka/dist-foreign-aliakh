package com.copyright.rup.dist.foreign.vui.main;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.ICommonUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.UnsupportedBrowserWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.AccessDeniedWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.LoadingIndicatorConfiguration;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import com.vaadin.flow.theme.Theme;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entry point for application.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankovov
 * @author Anton Azarenka
 */
@Route("")
@Theme("dist")
@CssImport(themeFor = "vaadin-grid", value = "./themes/dist/components/vaadin-grid-cell.css")
@VaadinSessionScope
public class ForeignUi extends AppLayout implements AppShellConfigurator, ICommonUi,
    IMessageSource {

    private static final long serialVersionUID = -2562773459192866252L;
    private static final ResourceBundle MESSAGES =
        ResourceBundle.getBundle("com.copyright.rup.dist.foreign.vui.messages");
    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final IMainWidgetController controller;

    /**
     * Constructor.
     *
     * @param controller controller
     */
    public ForeignUi(@Autowired IMainWidgetController controller) {
        this.controller = controller;
        String applicationTitle = getApplicationTitle();
        addToNavbar(initHeader(applicationTitle));
        UI current = UI.getCurrent();
        if (Objects.nonNull(current)) {
            LOGGER.info("Initialize UI. User={}, ClientDate={}, ServerTimeZone={}", getActiveUser(),
                LocalDateTime.now(ZoneId.systemDefault()), ZoneId.systemDefault());
            current.getSession().setErrorHandler(new ForeignErrorHandler(this));
            // Retrieving extended client details to have cached data for avoiding empty date in exports
            current.getPage().retrieveExtendedClientDetails(extendedClientDetails -> {
            });
            if (!hasAccessPermission()) {
                setContent(new AccessDeniedWidget());
            } else if (!isWebBrowserSupported()) {
                UnsupportedBrowserWindow unsupportedBrowserWindow =
                    new UnsupportedBrowserWindow(applicationTitle, getSupportedBrowsers());
                unsupportedBrowserWindow.open();
            } else {
                Component widget = Objects.requireNonNull(initMainWidget());
                setContent(initRootWidget(widget));
            }
            LoadingIndicatorConfiguration conf = current.getLoadingIndicatorConfiguration();
            conf.setApplyDefaultTheme(false);
        }
    }

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
    public void configurePage(AppShellSettings settings) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("rel", "shortcut icon");
        settings.addLink("./themes/dist/img/favicon.ico", attributes);
        settings.addFavIcon("icon", "./themes/dist/img/favicon.ico", "64x64");
    }

    @Override
    public final boolean hasAccessPermission() {
        return ForeignSecurityUtils.hasAccessPermission();
    }

    @Override
    public final String getApplicationTitle() {
        return getMessage("application.name");
    }

    @Override
    public final Component initMainWidget() {
        return (Component) controller.initWidget();
    }

    @Override
    public final String getStringMessage(String key, Object... parameters) {
        return getMessage(key, parameters);
    }
}
