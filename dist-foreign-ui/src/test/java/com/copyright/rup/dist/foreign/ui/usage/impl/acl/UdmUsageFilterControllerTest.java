package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Verifies {@link UdmUsageFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/04/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmUsageFilterControllerTest {

    private final UdmUsageFilterController controller = new UdmUsageFilterController();

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Ignore
    @Test
    public void testGetUdmBatchesForFilter() {
        // TODO {dbasiachenka} complete after implementation of service logic
    }

    @Ignore
    @Test
    public void testGetPeriods() {
        // TODO {dbasiachenka} complete after implementation of service logic
    }
}
