package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclciUsageFilterController}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/18/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageFilterControllerTest {

    private AclciUsageFilterController controller;

    @Before
    public void setUp() {
        controller = new AclciUsageFilterController();
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclciUsageFilterWidget.class));
    }
}
