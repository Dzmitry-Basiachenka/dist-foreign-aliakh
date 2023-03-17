package com.copyright.rup.dist.foreign.integration.rms.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsAssignmentService;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult.RightsAssignmentResultStatusEnum;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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
    private RmsIntegrationService rmsIntegrationService;
    private IRmsRightsAssignmentService rmsRightsAssignmentService;

    @Before
    public void setUp() {
        rmsRightsAssignmentService = createMock(IRmsRightsAssignmentService.class);
        rmsIntegrationService = new RmsIntegrationService();
        Whitebox.setInternalState(rmsIntegrationService, "rmsRightsAssignmentService", rmsRightsAssignmentService);
    }

    @Test
    public void testSendForRightsAssignment() {
        Set<Long> wrWrkInsts = Set.of(WR_WRK_INST_1);
        RightsAssignmentResult result = new RightsAssignmentResult(RightsAssignmentResultStatusEnum.SUCCESS);
        result.setJobId("d4127dbb-9ab8-46a3-b952-b9517deff116");
        expect(rmsRightsAssignmentService.sendForRightsAssignment("FAS Batch 02/04/2018", wrWrkInsts))
            .andReturn(result).once();
        replay(rmsRightsAssignmentService);
        assertSame(result, rmsIntegrationService.sendForRightsAssignment("FAS Batch 02/04/2018", wrWrkInsts));
        verify(rmsRightsAssignmentService);
    }
}
