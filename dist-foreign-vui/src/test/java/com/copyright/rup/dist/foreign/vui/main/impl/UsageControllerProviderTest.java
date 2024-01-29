package com.copyright.rup.dist.foreign.vui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Map;

/**
 * Verifies {@link UsagesControllerProvider}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/29/2024
 *
 * @author Ihar Suvorau
 */
public class UsageControllerProviderTest {

    @Test
    public void testGetProductFamilyToControllerMap() {
        UsagesControllerProvider provider = new UsagesControllerProvider();
        IFasUsageController controller = createMock(IFasUsageController.class);
        Whitebox.setInternalState(provider, controller);
        provider.initProductFamilyMap();
        assertEquals(Map.of("FAS", controller, "FAS2", controller), provider.getProductFamilyToControllerMap());
    }
}
