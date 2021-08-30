package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
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

    @Before
    public void setUp() {
        udmUsageController = createMock(IUdmUsageController.class);
        Whitebox.setInternalState(controller, udmUsageController);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetUdmUsageController() {
        assertSame(udmUsageController, controller.getUdmUsageController());
    }
}
