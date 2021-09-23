package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link UdmController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/27/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmControllerTest {

    private final UdmController controller = new UdmController();

    private IUdmUsageController udmUsageController;
    private IUdmValueController udmValueController;
    private IUdmBaselineController udmBaselineController;

    @Before
    public void setUp() {
        udmUsageController = createMock(IUdmUsageController.class);
        udmValueController = createMock(IUdmValueController.class);
        udmBaselineController = createMock(IUdmBaselineController.class);
        Whitebox.setInternalState(controller, udmUsageController);
        Whitebox.setInternalState(controller, udmValueController);
        Whitebox.setInternalState(controller, udmBaselineController);
    }

    @Test
    public void testGetUdmUsageController() {
        assertSame(udmUsageController, controller.getUdmUsageController());
    }

    @Test
    public void testGetUdmValueController() {
        assertSame(udmValueController, controller.getUdmValueController());
    }

    @Test
    public void testGetUdmBaselineController() {
        assertSame(udmBaselineController, controller.getUdmBaselineController());
    }

    @Test
    public void testInstantiateWidget() {
        IUdmWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(UdmWidget.class, widget.getClass());
    }
}
