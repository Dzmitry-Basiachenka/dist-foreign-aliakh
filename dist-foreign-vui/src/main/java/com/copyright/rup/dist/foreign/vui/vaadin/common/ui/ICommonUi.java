package com.copyright.rup.dist.foreign.vui.vaadin.common.ui;

import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Browser.BrowserType;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.HeaderWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.RootWidget;

import com.google.common.base.Predicates;
import com.google.common.collect.Range;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.WebBrowser;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Interface for init UI logic.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/07/2023
 *
 * @author Anton Azarenka
 */
public interface ICommonUi {

    /**
     * Initializes common header for application.
     *
     * @param applicationTitle application title
     * @return instance of {@link  HorizontalLayout}
     */
    default HorizontalLayout initHeader(String applicationTitle) {
        return new HeaderWidget(applicationTitle, getActiveUser());
    }

    /**
     * Initialize common root widget.
     *
     * @param mainWidget main widget
     * @return instance of {@link RootWidget}
     */
    default RootWidget initRootWidget(Component mainWidget) {
        return new RootWidget(mainWidget);
    }

    /**
     * Returns the user name that will be shown in the header.
     * RupContextHolder is used to define user name.
     *
     * @return active user name.
     */
    default String getActiveUser() {
        return SecurityUtils.getUserName();
    }

    /**
     * Checks that browser is support.
     *
     * @return <pre>{@code true}
     * if browser is support, otherwise <pre>{@code false}.
     * </pre>
     */
    default boolean isWebBrowserSupported() {
        Set<Browser> supportedBrowsers = getSupportedBrowsers();
        if (Objects.nonNull(supportedBrowsers) && !supportedBrowsers.isEmpty()) {
            UI current = UI.getCurrent();
            WebBrowser webBrowser = null;
            if (Objects.nonNull(current)) {
                webBrowser = current.getSession().getBrowser();
            }
            return Predicates.or(supportedBrowsers).apply(webBrowser);
        }
        return false;
    }

    /**
     * Gets set of supported browsers with major versions for application.
     * By default, returns all known browsers with all major versions.
     * In case when browser is not supported
     * {@link com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.UnsupportedBrowserWindow}
     * will be shown.
     * <p/>
     * See example of defining Firefox 18+, Chrome 23+ as supported browsers:
     * <pre>{@code
     * protected Set<Browser> getSupportedBrowsers() {
     *      return Sets.newHashSet(
     *          new Browser(Browser.BrowserType.FIREFOX, Range.atLeast(18)),
     *          new Browser(Browser.BrowserType.CHROME, Range.atLeast(23))
     *      );
     * }}</pre>
     *
     * @return set of supported browsers.
     */
    default Set<Browser> getSupportedBrowsers() {
        return new LinkedHashSet<>(Arrays.asList(
            new Browser(BrowserType.CHROME, Range.all()),
            new Browser(BrowserType.FIREFOX, Range.all()),
            new Browser(BrowserType.IE, Range.all()),
            new Browser(BrowserType.OPERA, Range.all()),
            new Browser(BrowserType.SAFARI, Range.all())
        ));
    }

    /**
     * @return true if user has access permissions to application.
     * If not - {@link com.copyright.rup.dist.foreign.vui.vaadin.common.widget.AccessDeniedWidget}
     * will be shown.
     */
    boolean hasAccessPermission();

    /**
     * @return application title that will be set to the page title.
     */
    String getApplicationTitle();

    /**
     * Initializes main ui component that will be shown under the header.
     *
     * @return main widget.
     */
    Component initMainWidget();
}
