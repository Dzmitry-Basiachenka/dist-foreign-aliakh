package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

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
}
