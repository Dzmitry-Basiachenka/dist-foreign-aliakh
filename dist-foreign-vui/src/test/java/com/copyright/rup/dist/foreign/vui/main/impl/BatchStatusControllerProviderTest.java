package com.copyright.rup.dist.foreign.vui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.vui.status.api.IFasBatchStatusController;
import com.copyright.rup.dist.foreign.vui.status.api.INtsBatchStatusController;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Map;

/**
 * Verifies {@link BatchStatusControllerProvider}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/02/2021
 *
 * @author Ihar Suvorau
 */
public class BatchStatusControllerProviderTest {

    @Test
    public void testGetProductFamilyToControllerMap() {
        var provider = new BatchStatusControllerProvider();
        var fasBatchStatusController = createMock(IFasBatchStatusController.class);
        var ntsBatchStatusController = createMock(INtsBatchStatusController.class);
        Whitebox.setInternalState(provider, fasBatchStatusController);
        Whitebox.setInternalState(provider, ntsBatchStatusController);
        assertEquals(
            Map.of(
                "FAS", fasBatchStatusController,
                "FAS2", fasBatchStatusController,
                "NTS", ntsBatchStatusController
            ),
            provider.getProductFamilyToControllerMap()
        );
    }
}
