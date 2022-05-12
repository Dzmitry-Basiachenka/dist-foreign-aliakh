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

    @Test
    public void testGetFundPoolNames() {
        //TODO will be implemented later
    }

    @Test
    public void testGetPeriods() {
        //TODO will be implemented later
    }

    @Test
    public void testGetDetailLicenseeClasses() {
        //TODO will be implemented later
    }

    @Test
    public void testGetAggregateLicenseeClasses() {
        //TODO will be implemented later
    }

    @Test
    public void testGetLicenseTypes() {
        //TODO will be implemented later
    }
}
