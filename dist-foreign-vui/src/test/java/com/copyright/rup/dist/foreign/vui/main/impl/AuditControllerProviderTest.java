package com.copyright.rup.dist.foreign.vui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditController;

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
        var ntsAuditController = createMock(INtsAuditController.class);
        var aaclAuditController = createMock(IAaclAuditController.class);
        Whitebox.setInternalState(provider, fasAuditController);
        Whitebox.setInternalState(provider, ntsAuditController);
        Whitebox.setInternalState(provider, aaclAuditController);
        assertEquals(
            Map.of(
                "FAS", fasAuditController,
                "FAS2", fasAuditController,
                "NTS", ntsAuditController,
                "AACL", aaclAuditController
            ),
            provider.getProductFamilyToControllerMap()
        );
    }
}
