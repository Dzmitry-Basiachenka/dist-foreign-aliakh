package com.copyright.rup.dist.foreign.integration.crm.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResult;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResultStatusEnum;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

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
    public void testInsertRightsDistribution() {
        CrmResult result = new CrmResult(CrmResultStatusEnum.SUCCESS);
        CrmRightsDistributionRequest request = new CrmRightsDistributionRequest(new PaidUsage());
        List<CrmRightsDistributionRequest> requests = List.of(request);
        expect(crmService.insertRightsDistribution(requests)).andReturn(result).once();
        replay(crmService);
        assertSame(result, crmIntegrationService.insertRightsDistribution(requests));
        verify(crmService);
    }
}
