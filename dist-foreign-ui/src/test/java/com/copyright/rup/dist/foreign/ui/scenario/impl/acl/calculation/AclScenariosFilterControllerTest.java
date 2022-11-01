package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Verifies {@link AclScenariosFilterController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Mikita Maistrenka
 */
public class AclScenariosFilterControllerTest {

    private final AclScenariosFilterController controller = new AclScenariosFilterController();

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetPeriods() {
        //TODO will be implemented
    }
}
