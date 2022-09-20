package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;

import com.vaadin.ui.TabSheet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link UdmWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/27/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class})
public class UdmWidgetTest {

    private IUdmController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IUdmController.class);
    }

    @Test
    public void testWidgetStructure() {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        IUdmUsageController udmUsageController = createMock(IUdmUsageController.class);
        IUdmUsageWidget udmUsageWidget = createNiceMock(IUdmUsageWidget.class);
        expect(udmUsageController.initWidget()).andReturn(udmUsageWidget).once();
        udmUsageWidget.setController(udmUsageController);
        expectLastCall().once();
        expect(controller.getUdmUsageController()).andReturn(udmUsageController).once();
        IUdmValueController udmValueController = createMock(IUdmValueController.class);
        IUdmValueWidget udmValueWidget = createNiceMock(IUdmValueWidget.class);
        expect(udmValueController.initWidget()).andReturn(udmValueWidget).once();
        udmValueWidget.setController(udmValueController);
        expectLastCall().once();
        expect(controller.getUdmValueController()).andReturn(udmValueController).once();
        IUdmProxyValueController udmProxyValueController = createMock(IUdmProxyValueController.class);
        IUdmProxyValueWidget udmProxyValueWidget = createNiceMock(IUdmProxyValueWidget.class);
        expect(udmProxyValueController.initWidget()).andReturn(udmProxyValueWidget).once();
        udmProxyValueWidget.setController(udmProxyValueController);
        expectLastCall().once();
        expect(controller.getUdmProxyValueController()).andReturn(udmProxyValueController).once();
        IUdmBaselineController udmBaselineController = createMock(IUdmBaselineController.class);
        IUdmBaselineWidget udmBaselineWidget = createNiceMock(IUdmBaselineWidget.class);
        expect(udmBaselineController.initWidget()).andReturn(udmBaselineWidget).once();
        udmBaselineWidget.setController(udmBaselineController);
        expectLastCall().once();
        expect(controller.getUdmBaselineController()).andReturn(udmBaselineController).once();
        IUdmBaselineValueController udmBaselineValueController = createMock(IUdmBaselineValueController.class);
        IUdmBaselineValueWidget udmBaselineValueWidget = createNiceMock(IUdmBaselineValueWidget.class);
        expect(udmBaselineValueController.initWidget()).andReturn(udmBaselineValueWidget).once();
        udmBaselineValueWidget.setController(udmBaselineValueController);
        expectLastCall().once();
        expect(controller.getUdmBaselineValueController()).andReturn(udmBaselineValueController).once();
        replay(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmProxyValueController, udmProxyValueWidget, udmBaselineController, udmBaselineWidget,
            udmBaselineValueController, udmBaselineValueWidget);
        UdmWidget widget = new UdmWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmProxyValueController, udmProxyValueWidget, udmBaselineController, udmBaselineWidget,
            udmBaselineValueController, udmBaselineValueWidget);
        assertEquals(5, widget.getComponentCount());
        TabSheet.Tab tab1 = widget.getTab(0);
        assertEquals("Usages", tab1.getCaption());
        assertThat(tab1.getComponent(), instanceOf(IUdmUsageWidget.class));
        TabSheet.Tab tab2 = widget.getTab(1);
        assertEquals("Values", tab2.getCaption());
        assertThat(tab2.getComponent(), instanceOf(IUdmValueWidget.class));
        TabSheet.Tab tab3 = widget.getTab(2);
        assertEquals("Proxy Values", tab3.getCaption());
        assertThat(tab3.getComponent(), instanceOf(IUdmProxyValueWidget.class));
        TabSheet.Tab tab4 = widget.getTab(3);
        assertEquals("Baseline", tab4.getCaption());
        assertThat(tab4.getComponent(), instanceOf(IUdmBaselineWidget.class));
        TabSheet.Tab tab5 = widget.getTab(4);
        assertEquals("Baseline Values", tab5.getCaption());
        assertThat(tab5.getComponent(), instanceOf(IUdmBaselineValueWidget.class));
    }

    @Test
    public void testWidgetStructureForResearcher() {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        IUdmUsageController udmUsageController = createMock(IUdmUsageController.class);
        IUdmUsageWidget udmUsageWidget = createNiceMock(IUdmUsageWidget.class);
        expect(udmUsageController.initWidget()).andReturn(udmUsageWidget).once();
        udmUsageWidget.setController(udmUsageController);
        expectLastCall().once();
        expect(controller.getUdmUsageController()).andReturn(udmUsageController).once();
        IUdmValueController udmValueController = createMock(IUdmValueController.class);
        IUdmValueWidget udmValueWidget = createNiceMock(IUdmValueWidget.class);
        expect(udmValueController.initWidget()).andReturn(udmValueWidget).once();
        udmValueWidget.setController(udmValueController);
        expectLastCall().once();
        expect(controller.getUdmValueController()).andReturn(udmValueController).once();
        replay(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget);
        UdmWidget widget = new UdmWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget);
        assertEquals(2, widget.getComponentCount());
        TabSheet.Tab tab1 = widget.getTab(0);
        assertEquals("Usages", tab1.getCaption());
        assertThat(tab1.getComponent(), instanceOf(IUdmUsageWidget.class));
        TabSheet.Tab tab2 = widget.getTab(1);
        assertEquals("Values", tab2.getCaption());
        assertThat(tab2.getComponent(), instanceOf(IUdmValueWidget.class));
    }

    @Test
    public void testRefresh() {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        IUdmUsageController udmUsageController = createMock(IUdmUsageController.class);
        IUdmUsageWidget udmUsageWidget = createNiceMock(IUdmUsageWidget.class);
        expect(udmUsageController.initWidget()).andReturn(udmUsageWidget).once();
        udmUsageWidget.setController(udmUsageController);
        expectLastCall().once();
        udmUsageWidget.refresh();
        expectLastCall().once();
        expect(controller.getUdmUsageController()).andReturn(udmUsageController).once();
        IUdmValueController udmValueController = createMock(IUdmValueController.class);
        IUdmValueWidget udmValueWidget = createNiceMock(IUdmValueWidget.class);
        expect(udmValueController.initWidget()).andReturn(udmValueWidget).once();
        udmValueWidget.setController(udmValueController);
        expectLastCall().once();
        expect(controller.getUdmValueController()).andReturn(udmValueController).once();
        IUdmProxyValueController udmProxyValueController = createMock(IUdmProxyValueController.class);
        IUdmProxyValueWidget udmProxyValueWidget = createNiceMock(IUdmProxyValueWidget.class);
        expect(udmProxyValueController.initWidget()).andReturn(udmProxyValueWidget).once();
        udmProxyValueWidget.setController(udmProxyValueController);
        expectLastCall().once();
        expect(controller.getUdmProxyValueController()).andReturn(udmProxyValueController).once();
        IUdmBaselineController udmBaselineController = createMock(IUdmBaselineController.class);
        IUdmBaselineWidget udmBaselineWidget = createNiceMock(IUdmBaselineWidget.class);
        expect(udmBaselineController.initWidget()).andReturn(udmBaselineWidget).once();
        udmBaselineWidget.setController(udmBaselineController);
        expectLastCall().once();
        expect(controller.getUdmBaselineController()).andReturn(udmBaselineController).once();
        IUdmBaselineValueController udmBaselineValueController = createMock(IUdmBaselineValueController.class);
        IUdmBaselineValueWidget udmBaselineValueWidget = createNiceMock(IUdmBaselineValueWidget.class);
        expect(udmBaselineValueController.initWidget()).andReturn(udmBaselineValueWidget).once();
        udmBaselineValueWidget.setController(udmBaselineValueController);
        expectLastCall().once();
        expect(controller.getUdmBaselineValueController()).andReturn(udmBaselineValueController).once();
        replay(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmProxyValueController, udmProxyValueWidget, udmBaselineController,
            udmBaselineWidget, udmBaselineValueController, udmBaselineValueWidget);
        UdmWidget widget = new UdmWidget();
        widget.setController(controller);
        widget.init();
        widget.refresh();
        verify(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmProxyValueController, udmProxyValueWidget, udmBaselineController, udmBaselineWidget,
            udmBaselineValueController, udmBaselineValueWidget);
    }
}
