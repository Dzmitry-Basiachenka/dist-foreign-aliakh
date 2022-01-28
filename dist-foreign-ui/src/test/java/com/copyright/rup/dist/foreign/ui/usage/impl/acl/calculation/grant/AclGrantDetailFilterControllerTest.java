package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Verifies {@link AclGrantDetailFilterController}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 01/28/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailFilterControllerTest {

    private final AclGrantDetailFilterController controller = new AclGrantDetailFilterController();

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }
}
