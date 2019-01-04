package com.copyright.rup.dist.foreign.integration.rms.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsAssignmentService;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsService;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult.RightsAssignmentResultStatusEnum;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link RmsIntegrationService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/2018
 *
 * @author Aliaksandr Liakh
 */
public class RmsIntegrationServiceTest {

    private static final Long WR_WRK_INST_1 = 122799407L;
    private static final Long WR_WRK_INST_2 = 123565461L;
    private static final LocalDate CURRENT_DATE = LocalDate.now();
    private RmsIntegrationService rmsIntegrationService;
    private IRmsService rmsService;
    private IRmsRightsAssignmentService rmsRightsAssignmentService;

    @Before
    public void setUp() {
        rmsService = createMock(IRmsService.class);
        rmsRightsAssignmentService = createMock(IRmsRightsAssignmentService.class);
        rmsIntegrationService = new RmsIntegrationService();
        Whitebox.setInternalState(rmsIntegrationService, "rmsService", rmsService);
        Whitebox.setInternalState(rmsIntegrationService, "rmsRightsAssignmentService", rmsRightsAssignmentService);
    }

    @Test
    public void testGetAllRmsGrants() {
        List<Long> wrWrkInsts = Lists.newArrayList(WR_WRK_INST_1, WR_WRK_INST_2);
        Capture<LocalDate> localDateCapture = new Capture<>();
        expect(rmsService.getAllRmsGrants(eq(wrWrkInsts), capture(localDateCapture)))
            .andReturn(Collections.emptySet()).once();
        replay(rmsService);
        assertTrue(rmsIntegrationService.getAllRmsGrants(wrWrkInsts).isEmpty());
        assertEquals(CURRENT_DATE, localDateCapture.getValue());
        verify(rmsService);
    }

    @Test
    public void testSendForRightsAssignment() {
        Set<Long> wrWrkInsts = Sets.newHashSet(WR_WRK_INST_1);
        RightsAssignmentResult result = new RightsAssignmentResult(RightsAssignmentResultStatusEnum.SUCCESS);
        result.setJobId("d4127dbb-9ab8-46a3-b952-b9517deff116");
        expect(rmsRightsAssignmentService.sendForRightsAssignment(wrWrkInsts))
            .andReturn(result).once();
        replay(rmsRightsAssignmentService);
        assertSame(result, rmsIntegrationService.sendForRightsAssignment(wrWrkInsts));
        verify(rmsRightsAssignmentService);
    }
}
