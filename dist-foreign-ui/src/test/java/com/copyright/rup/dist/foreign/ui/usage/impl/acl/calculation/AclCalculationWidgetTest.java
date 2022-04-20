package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageWidget;

import com.vaadin.ui.TabSheet;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclCalculationWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclCalculationWidgetTest {

    private IAclCalculationController controller;
    private IAclUsageController aclUsageController;
    private IAclUsageWidget aclUsageWidget;
    private IAclGrantDetailController aclGrantDetailController;
    private IAclGrantDetailWidget aclGrantDetailWidget;
    private IAclFundPoolWidget aclFundPoolWidget;
    private IAclFundPoolController aclFundPoolController;

    @Before
    public void setUp() {
        controller = createMock(IAclCalculationController.class);
        aclUsageController = createMock(IAclUsageController.class);
        aclFundPoolController = createMock(IAclFundPoolController.class);
        aclUsageWidget = createNiceMock(IAclUsageWidget.class);
        aclFundPoolWidget = createNiceMock(IAclFundPoolWidget.class);
        expect(aclUsageController.initWidget()).andReturn(aclUsageWidget).once();
        aclUsageWidget.setController(aclUsageController);
        expectLastCall();
        expect(controller.getAclUsageController()).andReturn(aclUsageController).once();
        aclGrantDetailController = createMock(IAclGrantDetailController.class);
        aclGrantDetailWidget = createNiceMock(IAclGrantDetailWidget.class);
        expect(aclGrantDetailController.initWidget()).andReturn(aclGrantDetailWidget).once();
        aclGrantDetailWidget.setController(aclGrantDetailController);
        expectLastCall().once();
        expect(controller.getAclGrantDetailController()).andReturn(aclGrantDetailController).once();
        aclFundPoolController = createMock(IAclFundPoolController.class);
        aclFundPoolWidget = createNiceMock(IAclFundPoolWidget.class);
        expect(aclFundPoolController.initWidget()).andReturn(aclFundPoolWidget).once();
        aclFundPoolWidget.setController(aclFundPoolController);
        expectLastCall().once();
        expect(controller.getAclFundPoolController()).andReturn(aclFundPoolController).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller, aclUsageController, aclUsageWidget, aclGrantDetailController, aclGrantDetailWidget,
            aclFundPoolWidget, aclFundPoolController);
        AclCalculationWidget widget = new AclCalculationWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, aclUsageController, aclUsageWidget, aclGrantDetailController, aclGrantDetailWidget,
            aclFundPoolWidget, aclFundPoolController);
        verifyTabs(widget);
    }

    @Test
    public void testRefresh() {
        aclUsageWidget.refresh();
        expectLastCall().once();
        replay(controller, aclUsageController, aclUsageWidget, aclGrantDetailController, aclGrantDetailWidget,
            aclFundPoolWidget, aclFundPoolController);
        AclCalculationWidget widget = new AclCalculationWidget();
        widget.setController(controller);
        widget.init();
        widget.refresh();
        verify(controller, aclUsageController, aclUsageWidget, aclGrantDetailController, aclGrantDetailWidget,
            aclFundPoolWidget, aclFundPoolController);
    }

    private void verifyTabs(AclCalculationWidget widget) {
        assertEquals(3, widget.getComponentCount());
        TabSheet.Tab usagesTab = widget.getTab(0);
        assertEquals("Usages", usagesTab.getCaption());
        assertTrue(usagesTab.getComponent() instanceof IAclUsageWidget);
        TabSheet.Tab fundPoolTab = widget.getTab(1);
        assertEquals("Fund Pool", fundPoolTab.getCaption());
        assertTrue(fundPoolTab.getComponent() instanceof IAclFundPoolWidget);
        TabSheet.Tab grantSetTab = widget.getTab(2);
        assertEquals("Grant Set", grantSetTab.getCaption());
        assertTrue(grantSetTab.getComponent() instanceof IAclGrantDetailWidget);
    }
}
