package com.copyright.rup.dist.foreign.integration.crm.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResult;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResultStatusEnum;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionResponse;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmService;

import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Verifies {@link CrmIntegrationService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/02/18
 *
 * @author Darya Baraukova
 */
public class CrmIntegrationServiceTest {

    private ICrmService crmService;
    private CrmIntegrationService crmIntegrationService;

    @Before
    public void setUp() {
        crmService = createMock(ICrmService.class);
        crmIntegrationService = new CrmIntegrationService();
        Whitebox.setInternalState(crmIntegrationService, ICrmService.class, crmService);
    }


    @Test
    public void testReadRightsDistribution() {
        String cccEventId1 = "12477";
        String cccEventId2 = "13315";
        ImmutableList<String> cccEventIds = ImmutableList.of(cccEventId1, cccEventId2);
        List<CrmRightsDistributionResponse> responses = new ArrayList<>();
        CrmRightsDistributionResponse response1 = new CrmRightsDistributionResponse();
        response1.setCccEventId(cccEventId1);
        responses.add(response1);
        CrmRightsDistributionResponse response2 = new CrmRightsDistributionResponse();
        response2.setCccEventId(cccEventId1);
        responses.add(response2);
        CrmRightsDistributionResponse response3 = new CrmRightsDistributionResponse();
        response3.setCccEventId(cccEventId2);
        responses.add(response3);
        expect(crmService.readRightsDistribution(cccEventIds)).andReturn(responses).once();
        replay(crmService);
        Map<String, List<CrmRightsDistributionResponse>> cccEventIdsToResponses =
            crmIntegrationService.readRightsDistribution(cccEventIds);
        List<CrmRightsDistributionResponse> responses1 = cccEventIdsToResponses.get(cccEventId1);
        assertEquals(2, responses1.size());
        assertEquals(cccEventId1, responses1.get(0).getCccEventId());
        assertEquals(cccEventId1, responses1.get(1).getCccEventId());
        List<CrmRightsDistributionResponse> responses2 = cccEventIdsToResponses.get(cccEventId2);
        assertEquals(1, responses2.size());
        assertEquals(cccEventId2, responses2.get(0).getCccEventId());
        verify(crmService);
    }

    @Test
    public void testSendRightsDistributionRequests() {
        CrmResult result = new CrmResult(CrmResultStatusEnum.SUCCESS);
        CrmRightsDistributionRequest request = new CrmRightsDistributionRequest(new PaidUsage());
        List<CrmRightsDistributionRequest> requests = Collections.singletonList(request);
        expect(crmService.sendRightsDistributionRequests(requests)).andReturn(result).once();
        replay(crmService);
        assertSame(result, crmIntegrationService.sendRightsDistributionRequests(requests));
        verify(crmService);
    }
}
