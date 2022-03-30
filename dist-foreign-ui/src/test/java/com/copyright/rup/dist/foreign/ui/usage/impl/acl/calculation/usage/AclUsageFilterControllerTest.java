package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link AclUsageFilterController}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFilterControllerTest {

    private final AclUsageFilterController controller = new AclUsageFilterController();

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetAllAclUsageBatches() {
        //TODO {dbasiachenka} implement
        assertEquals(Collections.emptyList(), controller.getAllAclUsageBatches());
    }
}
