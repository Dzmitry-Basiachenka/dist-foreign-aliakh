package com.copyright.rup.dist.foreign.integration.crm.impl;

import com.copyright.rup.dist.foreign.integration.crm.api.CrmResult;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmIntegrationService;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class CrmIntegrationService implements ICrmIntegrationService {

    private static final long serialVersionUID = -4563378742368912213L;
    @Autowired
    private ICrmService crmService;

    @Override
    public CrmResult insertRightsDistribution(List<CrmRightsDistributionRequest> crmRightsDistributionRequests) {
        return crmService.insertRightsDistribution(crmRightsDistributionRequests);
    }
}
