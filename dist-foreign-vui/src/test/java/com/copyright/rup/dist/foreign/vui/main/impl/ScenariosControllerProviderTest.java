package com.copyright.rup.dist.foreign.vui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosController;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Map;

/**
 * Verifies {@link ScenariosControllerProvider}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/06/2024
 *
 * @author Dzmitry Basiachenka
 */
public class ScenariosControllerProviderTest {

    @Test
    public void testGetProductFamilyToControllerMap() {
        var provider = new ScenariosControllerProvider();
        var fasScenariosController = createMock(IFasScenariosController.class);
        var ntsScenariosController = createMock(INtsScenariosController.class);
        Whitebox.setInternalState(provider, fasScenariosController);
        Whitebox.setInternalState(provider, ntsScenariosController);
        assertEquals(
            Map.of(
                "FAS", fasScenariosController,
                "FAS2", fasScenariosController,
                "NTS", ntsScenariosController
            ),
            provider.getProductFamilyToControllerMap()
        );
    }
}
