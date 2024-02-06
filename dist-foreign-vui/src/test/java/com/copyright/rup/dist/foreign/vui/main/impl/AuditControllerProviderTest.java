package com.copyright.rup.dist.foreign.vui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditController;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Map;

/**
 * Verifies {@link AuditControllerProvider}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/06/2024
 *
 * @author Dzmitry Basiachenka
 */
public class AuditControllerProviderTest {

    @Test
    public void testGetProductFamilyToControllerMap() {
        var provider = new AuditControllerProvider();
        var fasAuditController = createMock(IFasAuditController.class);
        Whitebox.setInternalState(provider, fasAuditController);
        assertEquals(
            Map.of(
                "FAS", fasAuditController,
                "FAS2", fasAuditController
            ),
            provider.getProductFamilyToControllerMap()
        );
    }
}
