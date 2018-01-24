package com.copyright.rup.dist.foreign.integration.rms.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult.RightsAssignmentResultStatusEnum;

import org.junit.Test;

/**
 * Verifies {@link RightsAssignmentResult}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/25/18
 *
 * @author Darya Baraukova
 */
public class RightsAssignmentResultTest {

    @Test
    public void testIsSuccessfull() {
        RightsAssignmentResult result = new RightsAssignmentResult(RightsAssignmentResultStatusEnum.SUCCESS);
        result.setJobId("2aa2c93d-6819-446a-a97f-49c32da792b8");
        assertTrue(result.isSuccessful());
    }

    @Test
    public void testIsSuccessfullNoJobId() {
        assertFalse(new RightsAssignmentResult(RightsAssignmentResultStatusEnum.SUCCESS).isSuccessful());
    }

    @Test
    public void testIsSuccessfullRightsAssignmentError() {
        assertFalse(new RightsAssignmentResult(RightsAssignmentResultStatusEnum.RA_ERROR).isSuccessful());
    }
}
