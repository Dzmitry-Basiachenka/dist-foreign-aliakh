package com.copyright.rup.dist.foreign.ui.status.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusWidget;

import org.junit.Test;

/**
 * Verifies {@link UdmBatchStatusController}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/2023
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBatchStatusControllerTest {

    private final UdmBatchStatusController controller = new UdmBatchStatusController();

    @Test
    public void testInstantiateWidget() {
        IUdmBatchStatusWidget udmBatchStatusWidget = controller.instantiateWidget();
        assertNotNull(udmBatchStatusWidget);
        assertEquals(UdmBatchStatusWidget.class, udmBatchStatusWidget.getClass());
    }
}
