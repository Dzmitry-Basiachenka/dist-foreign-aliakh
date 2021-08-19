package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link UdmWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/27/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmWidgetTest {

    private IUdmController controller;

    @Before
    public void setUp() {
        controller = createMock(IUdmController.class);
    }

    @Test
    public void testWidgetStructure() {
        IUdmUsageController udmUsageController = createMock(IUdmUsageController.class);
        IUdmUsageWidget udmUsageWidget = createNiceMock(IUdmUsageWidget.class);
        expect(udmUsageWidget.init()).andReturn(udmUsageWidget).once();
        udmUsageWidget.setController(udmUsageController);
        expectLastCall().once();
        expect(udmUsageController.initWidget()).andReturn(udmUsageWidget).once();
        expect(controller.getUdmUsageController()).andReturn(udmUsageController).once();
        replay(controller, udmUsageController, udmUsageWidget);
        UdmWidget widget = new UdmWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, udmUsageController, udmUsageWidget);
        TabSheet.Tab tab1 = widget.getTab(0);
        assertEquals("Usages", tab1.getCaption());
        assertTrue(tab1.getComponent() instanceof IUdmUsageWidget);
        TabSheet.Tab tab2 = widget.getTab(1);
        assertEquals("Values", tab2.getCaption());
        assertTrue(tab2.getComponent() instanceof Panel);
        TabSheet.Tab tab3 = widget.getTab(2);
        assertEquals("Baseline", tab3.getCaption());
        assertTrue(tab3.getComponent() instanceof Panel);
    }
}
