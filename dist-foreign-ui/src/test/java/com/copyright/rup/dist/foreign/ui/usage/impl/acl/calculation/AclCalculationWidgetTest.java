package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;
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
    private IAclScenariosController aclScenariosController;
    private IAclScenariosWidget aclScenariosWidget;

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
        aclScenariosController = createNiceMock(IAclScenariosController.class);
        aclScenariosWidget = createNiceMock(IAclScenariosWidget.class);
        expect(controller.getAclFundPoolController()).andReturn(aclFundPoolController).once();
        expect(aclScenariosController.initWidget()).andReturn(aclScenariosWidget).once();
        aclScenariosWidget.setController(aclScenariosController);
        expectLastCall().once();
        expect(controller.getAclScenariosController()).andReturn(aclScenariosController).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller, aclUsageController, aclUsageWidget, aclGrantDetailController, aclGrantDetailWidget,
            aclFundPoolWidget, aclFundPoolController, aclScenariosController, aclScenariosWidget);
        AclCalculationWidget widget = new AclCalculationWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, aclUsageController, aclUsageWidget, aclGrantDetailController, aclGrantDetailWidget,
            aclFundPoolWidget, aclFundPoolController, aclScenariosController, aclScenariosWidget);
        verifyTabs(widget);
    }

    @Test
    public void testRefresh() {
        aclUsageWidget.refresh();
        expectLastCall().once();
        replay(controller, aclUsageController, aclUsageWidget, aclGrantDetailController, aclGrantDetailWidget,
            aclFundPoolWidget, aclFundPoolController, aclScenariosController, aclScenariosWidget);
        AclCalculationWidget widget = new AclCalculationWidget();
        widget.setController(controller);
        widget.init();
        widget.refresh();
        verify(controller, aclUsageController, aclUsageWidget, aclGrantDetailController, aclGrantDetailWidget,
            aclFundPoolWidget, aclFundPoolController, aclScenariosController, aclScenariosWidget);
    }

    private void verifyTabs(AclCalculationWidget widget) {
        assertEquals(4, widget.getComponentCount());
        verifyTab(widget.getTab(0), "Usages", IAclUsageWidget.class);
        verifyTab(widget.getTab(1), "Fund Pool", IAclFundPoolWidget.class);
        verifyTab(widget.getTab(2), "Grant Set", IAclGrantDetailWidget.class);
        verifyTab(widget.getTab(3), "Scenarios", IAclScenariosWidget.class);
    }

    private void verifyTab(TabSheet.Tab tab, String caption, Class<?> clazz) {
        assertEquals(caption, tab.getCaption());
        assertTrue(clazz.isInstance(tab.getComponent()));
    }
}
