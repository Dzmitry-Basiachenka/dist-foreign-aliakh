package com.copyright.rup.dist.foreign.vui.main;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.main.impl.MainWidget;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.CommonErrorHandler;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.LoadingIndicatorConfiguration;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Verifies {@link ForeignUi}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 3/8/17
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    ForeignSecurityUtils.class, SecurityUtils.class, VaadinSession.class, ForeignUi.class, VaadinUtils.class, UI.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ForeignUiTest {

    private static final String USER_NAME = "User@copyright.com";
    private IMainWidgetController mainWidgetController;
    private ForeignUi foreignUi;

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        mockStatic(ForeignSecurityUtils.class);
        mockStatic(UI.class);
        mainWidgetController = createMock(IMainWidgetController.class);
    }

    @After
    public void tearDown() {
        Whitebox.setInternalState(ForeignUi.class, "MESSAGES",
            ResourceBundle.getBundle("com.copyright.rup.dist.foreign.vui.messages"));
    }

    @Test
    public void testGetApplicationTitle() {
        initWindow(1);
        assertEquals("Federated Distribution", foreignUi.getApplicationTitle());
        verifyAll();
    }

    @Test
    public void testGetMessage() {
        ResourceBundle bundle = PowerMock.createMock(ResourceBundle.class);
        Whitebox.setInternalState(ForeignUi.class, bundle);
        expect(bundle.getString("Case without parameters")).andReturn("No parameters");
        expect(bundle.getString("Case with 1 digit")).andReturn("Parameter is %d");
        expect(bundle.getString("Case with 2 digit, one ignore")).andReturn("Parameter is %d");
        expect(bundle.getString("Case with digit and string")).andReturn("Parameters are %d, %s");
        expect(bundle.getString("Case with 2 digits")).andReturn("Parameters are %d, %d");
        replay(ForeignUi.class, bundle);
        assertEquals("No parameters", foreignUi.getMessage("Case without parameters"));
        assertEquals("Parameter is 1", foreignUi.getMessage("Case with 1 digit", 1));
        assertEquals("Parameter is 1", foreignUi.getMessage("Case with 2 digit, one ignore", 1, 2));
        assertEquals("Parameters are 1, string", foreignUi.getMessage("Case with digit and string", 1, "string"));
        assertEquals("Parameters are 1, 2", foreignUi.getMessage("Case with 2 digits", 1, 2));
        verify(ForeignUi.class, bundle);
    }

    @Test
    public void testInitMainWidget() {
        expect(SecurityUtils.getUserName()).andReturn(USER_NAME).times(2);
        WebBrowser browser = createMock(WebBrowser.class);
        VaadinSession session = createMock(VaadinSession.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(1);
        expect(ui.getSession()).andReturn(session).times(2);
        expect(ui.getPage()).andReturn(createMock(Page.class)).times(2);
        LoadingIndicatorConfiguration loadingIndicatorConfiguration =
            createMock(LoadingIndicatorConfiguration.class);
        expect(ui.getLoadingIndicatorConfiguration()).andReturn(loadingIndicatorConfiguration).once();
        loadingIndicatorConfiguration.setApplyDefaultTheme(false);
        expectLastCall().once();
        session.setErrorHandler(anyObject(CommonErrorHandler.class));
        expectLastCall().once();
        expect(ForeignSecurityUtils.hasAccessPermission()).andReturn(false).times(1);
        MainWidget mainWidget = new MainWidget();
        expect(mainWidgetController.initWidget()).andReturn(mainWidget).once();
        expect(browser.isChrome()).andReturn(true).once();
        expect(browser.getBrowserMajorVersion()).andReturn(50).once();
        expect(session.getBrowser()).andReturn(browser);
        replay(ForeignSecurityUtils.class, SecurityUtils.class, UI.class, ui, session, browser, mainWidgetController,
            loadingIndicatorConfiguration);
        foreignUi = new ForeignUi(mainWidgetController);
        assertSame(mainWidget, foreignUi.initMainWidget());
        verifyAll();
    }

    @Test
    public void testHasAccessPermissionTrue() {
        initWindow(2);
        assertTrue(foreignUi.hasAccessPermission());
        verifyAll();
    }

    @Test
    public void testHasAccessPermissionFalse() {
        expect(SecurityUtils.getUserName()).andReturn(USER_NAME).times(2);
        WebBrowser browser = createMock(WebBrowser.class);
        VaadinSession session = createMock(VaadinSession.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(1);
        expect(ui.getSession()).andReturn(session).times(2);
        expect(ui.getPage()).andReturn(createMock(Page.class)).times(2);
        LoadingIndicatorConfiguration loadingIndicatorConfiguration =
            createMock(LoadingIndicatorConfiguration.class);
        expect(ui.getLoadingIndicatorConfiguration()).andReturn(loadingIndicatorConfiguration).once();
        loadingIndicatorConfiguration.setApplyDefaultTheme(false);
        expectLastCall().once();
        session.setErrorHandler(anyObject(CommonErrorHandler.class));
        expectLastCall().once();
        expect(ForeignSecurityUtils.hasAccessPermission()).andReturn(false).times(2);
        MainWidget mainWidget = new MainWidget();
        expect(mainWidgetController.initWidget()).andReturn(mainWidget).once();
        expect(browser.isChrome()).andReturn(true).once();
        expect(browser.getBrowserMajorVersion()).andReturn(50).once();
        expect(session.getBrowser()).andReturn(browser);
        replay(ForeignSecurityUtils.class, SecurityUtils.class, UI.class, ui, session, browser, mainWidgetController,
            loadingIndicatorConfiguration);
        foreignUi = new ForeignUi(mainWidgetController);
        assertFalse(foreignUi.hasAccessPermission());
        verifyAll();
    }

    private void initWindow(int times) {
        expect(SecurityUtils.getUserName()).andReturn(USER_NAME).times(2);
        WebBrowser browser = createMock(WebBrowser.class);
        VaadinSession session = createMock(VaadinSession.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(2);
        LoadingIndicatorConfiguration loadingIndicatorConfiguration =
            createMock(LoadingIndicatorConfiguration.class);
        expect(ui.getLoadingIndicatorConfiguration()).andReturn(loadingIndicatorConfiguration).once();
        loadingIndicatorConfiguration.setApplyDefaultTheme(false);
        expectLastCall().once();
        expect(ui.getSession()).andReturn(session).times(2);
        expect(ui.getPage()).andReturn(createMock(Page.class)).times(2);
        session.setErrorHandler(anyObject(CommonErrorHandler.class));
        expectLastCall().once();
        expect(ForeignSecurityUtils.hasAccessPermission()).andReturn(true).times(times);
        MainWidget mainWidget = new MainWidget();
        expect(mainWidgetController.initWidget()).andReturn(mainWidget).once();
        expect(browser.isChrome()).andReturn(true).once();
        expect(browser.getBrowserMajorVersion()).andReturn(50).once();
        expect(session.getBrowser()).andReturn(browser);
        replay(ForeignSecurityUtils.class, SecurityUtils.class, UI.class, ui, session, browser, mainWidgetController,
            loadingIndicatorConfiguration);
        foreignUi = new ForeignUi(mainWidgetController);
    }

    private static class MockSecurityContext implements SecurityContext {

        private static final String USER_NAME = "User@copyright.com";

        @Override
        public Authentication getAuthentication() {
            return new UsernamePasswordAuthenticationToken(USER_NAME, null, Collections.emptyList());
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            // stub method
        }
    }
}
