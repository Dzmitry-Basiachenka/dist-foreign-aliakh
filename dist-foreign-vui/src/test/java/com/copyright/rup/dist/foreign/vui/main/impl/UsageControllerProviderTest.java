package com.copyright.rup.dist.foreign.vui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;

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
        var aaclUsagesController = createMock(IAaclUsageController.class);
        var fasUsagesController = createMock(IFasUsageController.class);
        var ntsUsagesController = createMock(INtsUsageController.class);
        var provider = new UsagesControllerProvider();
        Whitebox.setInternalState(provider, aaclUsagesController);
        Whitebox.setInternalState(provider, fasUsagesController);
        Whitebox.setInternalState(provider, ntsUsagesController);
        provider.initProductFamilyMap();
        assertEquals(
            Map.of(
                "AACL", aaclUsagesController,
                "FAS", fasUsagesController,
                "FAS2", fasUsagesController,
                "NTS", ntsUsagesController
            ),
            provider.getProductFamilyToControllerMap()
        );
    }
}
