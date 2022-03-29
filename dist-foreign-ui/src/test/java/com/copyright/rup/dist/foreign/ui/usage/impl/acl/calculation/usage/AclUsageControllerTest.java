package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link AclUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageControllerTest {

    private final AclUsageController controller = new AclUsageController();

    @Test
    public void testLoadBeans() {
        //TODO {dbasiachenka} implement
        assertEquals(Collections.emptyList(), controller.loadBeans(0, 10, null));
    }

    @Test
    public void testGetBeansCount() {
        //TODO {dbasiachenka} implement
        assertEquals(0, controller.getBeansCount());
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }
}
