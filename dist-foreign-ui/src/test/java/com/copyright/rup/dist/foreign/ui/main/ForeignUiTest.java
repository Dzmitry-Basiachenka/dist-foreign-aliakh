package com.copyright.rup.dist.foreign.ui.main;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.main.impl.MainWidget;
import com.copyright.rup.dist.foreign.ui.main.impl.MainWidgetController;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.report.impl.ReportWidget;
import com.copyright.rup.dist.foreign.ui.report.impl.udm.UdmReportWidget;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
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
@PrepareForTest({ForeignSecurityUtils.class, VaadinSession.class, ForeignUi.class})
public class ForeignUiTest {

    private ForeignUi foreignUi;

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Before
    public void setUp() {
        foreignUi = new ForeignUi();
        Whitebox.setInternalState(ForeignUi.class, "MESSAGES",
            ResourceBundle.getBundle("com.copyright.rup.dist.foreign.ui.messages"));
    }

    @Test
    public void testGetMessage() {
        ResourceBundle bundle = createMock(ResourceBundle.class);
        Whitebox.setInternalState(ForeignUi.class, bundle);
        expect(bundle.getString("Case without parameters")).andReturn("No parameters");
        expect(bundle.getString("Case with 1 digit")).andReturn("Parameter is %d");
        expect(bundle.getString("Case with 2 digit, one ignore")).andReturn("Parameter is %d");
        expect(bundle.getString("Case with digit and string")).andReturn("Parameters are %d, %s");
        expect(bundle.getString("Case with 2 digits")).andReturn("Parameters are %d, %d");
        replay(ForeignUi.class, bundle);
        assertEquals("No parameters", ForeignUi.getMessage("Case without parameters"));
        assertEquals("Parameter is 1", ForeignUi.getMessage("Case with 1 digit", 1));
        assertEquals("Parameter is 1", ForeignUi.getMessage("Case with 2 digit, one ignore", 1, 2));
        assertEquals("Parameters are 1, string", ForeignUi.getMessage("Case with digit and string", 1, "string"));
        assertEquals("Parameters are 1, 2", ForeignUi.getMessage("Case with 2 digits", 1, 2));
        verify(ForeignUi.class, bundle);
    }

    @Test
    public void testGetApplicationName() {
        assertEquals("Foreign Distribution", new ForeignUi().getApplicationTitle());
    }

    @Test
    public void testHasAccessPermissionTrue() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasAccessPermission()).andReturn(true).once();
        replay(ForeignSecurityUtils.class);
        boolean result = foreignUi.hasAccessPermission();
        assertTrue(result);
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testHasAccessPermissionFalse() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasAccessPermission()).andReturn(false).once();
        replay(ForeignSecurityUtils.class);
        boolean result = foreignUi.hasAccessPermission();
        assertFalse(result);
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testInitUi() {
        mockStatic(ForeignSecurityUtils.class);
        MockSecurityContext context = new MockSecurityContext();
        SecurityContextHolder.setContext(context);
        IMainWidgetController controller = createMock(IMainWidgetController.class);
        IReportController reportController = createMock(IReportController.class);
        IUdmReportController udmReportController = createMock(IUdmReportController.class);
        MainWidget mainWidget = new MainWidget();
        Whitebox.setInternalState(foreignUi, controller);
        Whitebox.setInternalState(foreignUi, reportController);
        Whitebox.setInternalState(foreignUi, udmReportController);
        expect(controller.initWidget()).andReturn(mainWidget).once();
        expect(reportController.initWidget()).andReturn(new ReportWidget()).once();
        expect(udmReportController.initWidget()).andReturn(new UdmReportWidget()).once();
        expect(controller.getWidget()).andReturn(mainWidget).times(2);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        controller.refreshWidget();
        expectLastCall().once();
        replay(controller, reportController, udmReportController, ForeignSecurityUtils.class);
        foreignUi.initUi();
        verify(controller, reportController, udmReportController, ForeignSecurityUtils.class);
    }

    @Test
    public void testInitUiForResearcherRole() {
        mockStatic(ForeignSecurityUtils.class);
        MockSecurityContext context = new MockSecurityContext();
        SecurityContextHolder.setContext(context);
        IMainWidgetController controller = createMock(IMainWidgetController.class);
        IReportController reportController = createMock(IReportController.class);
        IUdmReportController udmReportController = createMock(IUdmReportController.class);
        MainWidget mainWidget = new MainWidget();
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(foreignUi, controller);
        Whitebox.setInternalState(foreignUi, reportController);
        Whitebox.setInternalState(foreignUi, udmReportController);
        Whitebox.setInternalState(foreignUi, productFamilyProvider);
        expect(controller.initWidget()).andReturn(mainWidget).once();
        expect(reportController.initWidget()).andReturn(new ReportWidget()).once();
        expect(udmReportController.initWidget()).andReturn(new UdmReportWidget()).once();
        expect(controller.getWidget()).andReturn(mainWidget).times(2);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        controller.onProductFamilyChanged();
        expectLastCall().once();
        reportController.onProductFamilyChanged();
        expectLastCall().once();
        productFamilyProvider.setProductFamily(FdaConstants.ACL_PRODUCT_FAMILY);
        expectLastCall().once();
        controller.refreshWidget();
        expectLastCall().once();
        replay(controller, reportController, udmReportController, productFamilyProvider, ForeignSecurityUtils.class);
        foreignUi.initUi();
        verify(controller, reportController, udmReportController, productFamilyProvider, ForeignSecurityUtils.class);
    }

    @Test
    public void testGetAdditionalComponents() {
        Whitebox.setInternalState(foreignUi, new MainWidgetController());
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        replay(ForeignSecurityUtils.class);
        List<Component> additionalComponents = foreignUi.getAdditionalComponents();
        assertNotNull(additionalComponents);
        assertEquals(2, additionalComponents.size());
        assertEquals(ComboBox.class, additionalComponents.get(0).getClass());
        assertEquals(Button.class, additionalComponents.get(1).getClass());
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testInitMainWidget() {
        IMainWidgetController controller = createMock(IMainWidgetController.class);
        Whitebox.setInternalState(foreignUi, controller);
        MainWidget mainWidget = new MainWidget();
        expect(controller.initWidget()).andReturn(mainWidget).once();
        replay(controller);
        assertEquals(mainWidget, foreignUi.initMainWidget());
        verify(controller);
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
