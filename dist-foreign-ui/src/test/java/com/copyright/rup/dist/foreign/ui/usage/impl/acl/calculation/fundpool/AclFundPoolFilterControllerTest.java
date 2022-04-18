package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Verifies {@link AclFundPoolFilterController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolFilterControllerTest {

    private final AclFundPoolFilterController controller = new AclFundPoolFilterController();

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }
}
