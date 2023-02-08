package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusWidget;
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
import com.copyright.rup.vaadin.widget.api.IWidget;

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
    private IUdmUsageController udmUsageController;
    private IUdmUsageWidget udmUsageWidget;
    private IUdmValueController udmValueController;
    private IUdmValueWidget udmValueWidget;
    private IUdmBatchStatusController udmBatchStatusController;
    private IUdmBatchStatusWidget udmBatchStatusWidget;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IUdmController.class);
        udmUsageController = createMock(IUdmUsageController.class);
        udmUsageWidget = createNiceMock(IUdmUsageWidget.class);
        expect(udmUsageController.initWidget()).andReturn(udmUsageWidget).once();
        udmUsageWidget.setController(udmUsageController);
        expectLastCall().once();
        expect(controller.getUdmUsageController()).andReturn(udmUsageController).once();
        udmValueController = createMock(IUdmValueController.class);
        udmValueWidget = createNiceMock(IUdmValueWidget.class);
        expect(udmValueController.initWidget()).andReturn(udmValueWidget).once();
        udmValueWidget.setController(udmValueController);
        expectLastCall().once();
        expect(controller.getUdmValueController()).andReturn(udmValueController).once();
        udmBatchStatusController = createMock(IUdmBatchStatusController.class);
        udmBatchStatusWidget = createNiceMock(IUdmBatchStatusWidget.class);
        expect(udmBatchStatusController.initWidget()).andReturn(udmBatchStatusWidget).once();
        udmBatchStatusWidget.setController(udmBatchStatusController);
        expectLastCall().once();
        expect(controller.getUdmBatchStatusController()).andReturn(udmBatchStatusController).once();
    }

    @Test
    public void testWidgetStructure() {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
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
            udmBaselineValueController, udmBaselineValueWidget, udmBatchStatusController, udmBatchStatusWidget);
        UdmWidget widget = new UdmWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmProxyValueController, udmProxyValueWidget, udmBaselineController, udmBaselineWidget,
            udmBaselineValueController, udmBaselineValueWidget, udmBatchStatusController, udmBatchStatusWidget);
        assertEquals(6, widget.getComponentCount());
        verifyTab(widget.getTab(0), "Usages", IUdmUsageWidget.class);
        verifyTab(widget.getTab(1), "Values", IUdmValueWidget.class);
        verifyTab(widget.getTab(2), "Proxy Values", IUdmProxyValueWidget.class);
        verifyTab(widget.getTab(3), "Baseline", IUdmBaselineWidget.class);
        verifyTab(widget.getTab(4), "Baseline Values", IUdmBaselineValueWidget.class);
        verifyTab(widget.getTab(5), "Batch Status", IUdmBatchStatusWidget.class);
    }

    @Test
    public void testWidgetStructureForResearcher() {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        replay(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmBatchStatusController, udmBatchStatusWidget);
        UdmWidget widget = new UdmWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmBatchStatusController, udmBatchStatusWidget);
        assertEquals(3, widget.getComponentCount());
        verifyTab(widget.getTab(0), "Usages", IUdmUsageWidget.class);
        verifyTab(widget.getTab(1), "Values", IUdmValueWidget.class);
        verifyTab(widget.getTab(2), "Batch Status", IUdmBatchStatusWidget.class);
    }

    @Test
    public void testRefresh() {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        udmUsageWidget.refresh();
        expectLastCall().once();
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
            udmBaselineValueController, udmBaselineValueWidget, udmBatchStatusController, udmBatchStatusWidget);
        UdmWidget widget = new UdmWidget();
        widget.setController(controller);
        widget.init();
        widget.refresh();
        verify(controller, ForeignSecurityUtils.class, udmUsageController, udmUsageWidget, udmValueController,
            udmValueWidget, udmProxyValueController, udmProxyValueWidget, udmBaselineController, udmBaselineWidget,
            udmBaselineValueController, udmBaselineValueWidget, udmBatchStatusController, udmBatchStatusWidget);
    }

    private void verifyTab(TabSheet.Tab tab, String expectedCaption, Class<? extends IWidget> clazz) {
        assertEquals(expectedCaption, tab.getCaption());
        assertThat(tab.getComponent(), instanceOf(clazz));
    }
}
