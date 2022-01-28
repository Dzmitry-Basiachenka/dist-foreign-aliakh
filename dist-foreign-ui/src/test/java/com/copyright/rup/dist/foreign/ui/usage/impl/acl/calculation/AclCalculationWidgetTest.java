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
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailWidget;

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

    @Before
    public void setUp() {
        controller = createMock(IAclCalculationController.class);
    }

    @Test
    public void testWidgetStructure() {
        IAclGrantDetailController aclGrantDetailController = createMock(IAclGrantDetailController.class);
        IAclGrantDetailWidget aclGrantDetailWidget = createNiceMock(IAclGrantDetailWidget.class);
        expect(aclGrantDetailController.initWidget()).andReturn(aclGrantDetailWidget).once();
        aclGrantDetailWidget.setController(aclGrantDetailController);
        expectLastCall().once();
        expect(controller.getAclGrantDetailController()).andReturn(aclGrantDetailController).once();
        replay(controller, aclGrantDetailController, aclGrantDetailWidget);
        AclCalculationWidget widget = new AclCalculationWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, aclGrantDetailController, aclGrantDetailWidget);
        assertEquals(1, widget.getComponentCount());
        TabSheet.Tab tab = widget.getTab(0);
        assertEquals("Grant Set", tab.getCaption());
        assertTrue(tab.getComponent() instanceof IAclGrantDetailWidget);
    }

    @Test
    public void testRefresh() {
        IAclGrantDetailController aclGrantDetailController = createMock(IAclGrantDetailController.class);
        IAclGrantDetailWidget aclGrantDetailWidget = createNiceMock(IAclGrantDetailWidget.class);
        expect(aclGrantDetailController.initWidget()).andReturn(aclGrantDetailWidget).once();
        aclGrantDetailWidget.setController(aclGrantDetailController);
        expectLastCall().once();
        aclGrantDetailWidget.refresh();
        expectLastCall().once();
        expect(controller.getAclGrantDetailController()).andReturn(aclGrantDetailController).once();
        replay(controller, aclGrantDetailController, aclGrantDetailWidget);
        AclCalculationWidget widget = new AclCalculationWidget();
        widget.setController(controller);
        widget.init();
        widget.refresh();
        verify(controller, aclGrantDetailController, aclGrantDetailWidget);
    }
}
