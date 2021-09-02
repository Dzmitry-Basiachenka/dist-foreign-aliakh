package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
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
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        IUdmUsageController udmUsageController = createMock(IUdmUsageController.class);
        IUdmUsageWidget udmUsageWidget = createNiceMock(IUdmUsageWidget.class);
        expect(udmUsageWidget.init()).andReturn(udmUsageWidget).once();
        udmUsageWidget.setController(udmUsageController);
        expectLastCall().once();
        expect(udmUsageController.initWidget()).andReturn(udmUsageWidget).once();
        expect(controller.getUdmUsageController()).andReturn(udmUsageController).once();
        IUdmValueController udmValueController = createMock(IUdmValueController.class);
        IUdmValueWidget udmValueWidget = createNiceMock(IUdmValueWidget.class);
        expect(udmValueWidget.init()).andReturn(udmValueWidget).once();
        udmValueWidget.setController(udmValueController);
        expectLastCall().once();
        expect(udmValueController.initWidget()).andReturn(udmValueWidget).once();
        expect(controller.getUdmValueController()).andReturn(udmValueController).once();
        IUdmBaselineController udmBaselineController = createMock(IUdmBaselineController.class);
        IUdmBaselineWidget udmBaselineWidget = createNiceMock(IUdmBaselineWidget.class);
        expect(udmBaselineWidget.init()).andReturn(udmBaselineWidget).once();
        udmBaselineWidget.setController(udmBaselineController);
        expectLastCall().once();
        expect(udmBaselineController.initWidget()).andReturn(udmBaselineWidget).once();
        expect(controller.getUdmBaselineController()).andReturn(udmBaselineController).once();
        replay(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmBaselineController, udmBaselineWidget);
        UdmWidget widget = new UdmWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmBaselineController, udmBaselineWidget);
        assertEquals(3, widget.getComponentCount());
        TabSheet.Tab tab1 = widget.getTab(0);
        assertEquals("Usages", tab1.getCaption());
        assertTrue(tab1.getComponent() instanceof IUdmUsageWidget);
        TabSheet.Tab tab2 = widget.getTab(1);
        assertEquals("Values", tab2.getCaption());
        assertTrue(tab2.getComponent() instanceof IUdmValueWidget);
        TabSheet.Tab tab3 = widget.getTab(2);
        assertEquals("Baseline", tab3.getCaption());
        assertTrue(tab3.getComponent() instanceof IUdmBaselineWidget);
    }

    @Test
    public void testWidgetStructureForResearcher() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        IUdmUsageController udmUsageController = createMock(IUdmUsageController.class);
        IUdmUsageWidget udmUsageWidget = createNiceMock(IUdmUsageWidget.class);
        expect(udmUsageWidget.init()).andReturn(udmUsageWidget).once();
        udmUsageWidget.setController(udmUsageController);
        expectLastCall().once();
        expect(udmUsageController.initWidget()).andReturn(udmUsageWidget).once();
        expect(controller.getUdmUsageController()).andReturn(udmUsageController).once();
        IUdmValueController udmValueController = createMock(IUdmValueController.class);
        IUdmValueWidget udmValueWidget = createNiceMock(IUdmValueWidget.class);
        expect(udmValueWidget.init()).andReturn(udmValueWidget).once();
        udmValueWidget.setController(udmValueController);
        expectLastCall().once();
        expect(udmValueController.initWidget()).andReturn(udmValueWidget).once();
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
        assertTrue(tab1.getComponent() instanceof IUdmUsageWidget);
        TabSheet.Tab tab2 = widget.getTab(1);
        assertEquals("Values", tab2.getCaption());
        assertTrue(tab2.getComponent() instanceof IUdmValueWidget);
    }
}
