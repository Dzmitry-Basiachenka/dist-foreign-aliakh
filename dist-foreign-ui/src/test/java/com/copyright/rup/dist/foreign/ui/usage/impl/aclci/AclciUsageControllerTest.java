package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclciUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageControllerTest {

    private AclciUsageController controller;

    @Before
    public void setUp() {
        controller = new AclciUsageController();
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclciUsageWidget.class));
    }

    @Test
    public void testOnFilterChanged() {
        //TODO: implement
    }

    @Test
    public void testLoadUsageData() {
        //TODO: implement
    }

    @Test
    public void testGetBeansCount() {
        //TODO: implement
    }

    @Test
    public void testLoadBeans() {
        //TODO: implement
    }

    @Test
    public void testGetExportUsagesStreamSource() {
        //TODO: implement
    }

    @Test
    public void testIsValidFilteredUsageStatus() {
        //TODO: implement
    }

    @Test
    public void testIsBatchProcessingCompleted() {
        //TODO: implement
    }

    @Test
    public void testDeleteUsageBatch() {
        //TODO: implement
    }
}
