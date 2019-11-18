package com.copyright.rup.dist.foreign.integration.crm.impl;

import com.copyright.rup.dist.foreign.integration.crm.api.CrmResult;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmIntegrationService;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link ICrmIntegrationService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/02/18
 *
 * @author Darya Baraukova
 */
@Service
public class CrmIntegrationService implements ICrmIntegrationService {

    @Autowired
    private ICrmService crmService;

    @Override
    public CrmResult insertRightsDistribution(List<CrmRightsDistributionRequest> crmRightsDistributionRequests) {
        return crmService.insertRightsDistribution(crmRightsDistributionRequests);
    }
}
