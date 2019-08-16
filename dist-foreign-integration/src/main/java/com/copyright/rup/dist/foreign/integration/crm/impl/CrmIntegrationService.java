package com.copyright.rup.dist.foreign.integration.crm.impl;

import com.copyright.rup.dist.foreign.integration.crm.api.GetRightsDistributionResponse;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmIntegrationService;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmService;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Map<String, Set<String>> getRightsDistribution(Set<String> cccEventIds) {
        return cccEventIds.isEmpty()
            ? new HashMap<>()
            : crmService.getRightsDistribution(cccEventIds)
            .stream()
            .collect(Collectors.groupingBy(GetRightsDistributionResponse::getCccEventId,
                Collectors.mapping(GetRightsDistributionResponse::getOmOrderDetailNumber, Collectors.toSet())));
    }

    @Override
    public InsertRightsDistributionResponse insertRightsDistribution(List<InsertRightsDistributionRequest> requests) {
        return crmService.insertRightsDistribution(requests);
    }
}
