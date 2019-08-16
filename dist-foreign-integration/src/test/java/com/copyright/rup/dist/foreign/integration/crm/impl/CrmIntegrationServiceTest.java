package com.copyright.rup.dist.foreign.integration.crm.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.integration.crm.api.GetRightsDistributionResponse;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmService;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionResponse;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionResponseStatusEnum;

import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public void testGetRightsDistribution() {
        String usageId1 = "2cb529a7-24c5-41ec-a095-46a645df9eea";
        String usageId2 = "dab30b02-565e-4fae-957e-5db09d9aca07";
        String cccEventId1 = "12477";
        String cccEventId2 = "13315";
        ImmutableSet<String> cccEventIds = ImmutableSet.of(cccEventId1, cccEventId2);
        List<GetRightsDistributionResponse> responses = new ArrayList<>();
        GetRightsDistributionResponse response1 = new GetRightsDistributionResponse();
        response1.setCccEventId(cccEventId1);
        response1.setOmOrderDetailNumber(usageId1);
        responses.add(response1);
        GetRightsDistributionResponse response2 = new GetRightsDistributionResponse();
        response2.setCccEventId(cccEventId2);
        response2.setOmOrderDetailNumber(usageId2);
        responses.add(response2);
        expect(crmService.getRightsDistribution(cccEventIds)).andReturn(responses).once();
        replay(crmService);
        Map<String, Set<String>> cccEventIdsToResponses = crmIntegrationService.getRightsDistribution(cccEventIds);
        Set<String> responses1 = cccEventIdsToResponses.get(cccEventId1);
        assertEquals(1, responses1.size());
        assertTrue(responses1.contains(usageId1));
        Set<String> responses2 = cccEventIdsToResponses.get(cccEventId2);
        assertEquals(1, responses2.size());
        assertTrue(responses2.contains(usageId2));
        verify(crmService);
    }

    @Test
    public void testInsertRightsDistribution() {
        InsertRightsDistributionResponse response =
            new InsertRightsDistributionResponse(InsertRightsDistributionResponseStatusEnum.SUCCESS);
        InsertRightsDistributionRequest request = new InsertRightsDistributionRequest(new PaidUsage());
        List<InsertRightsDistributionRequest> requests = Collections.singletonList(request);
        expect(crmService.insertRightsDistribution(requests)).andReturn(response).once();
        replay(crmService);
        assertSame(response, crmIntegrationService.insertRightsDistribution(requests));
        verify(crmService);
    }
}
