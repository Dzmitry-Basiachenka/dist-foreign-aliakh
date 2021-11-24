package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Verifies {@link UdmBaselineValueFilterController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueFilterControllerTest {

    private final UdmBaselineValueFilterController controller = new UdmBaselineValueFilterController();

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }
}
