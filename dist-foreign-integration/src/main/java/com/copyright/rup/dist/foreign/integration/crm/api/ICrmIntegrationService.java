package com.copyright.rup.dist.foreign.integration.crm.api;

import java.util.List;
import java.util.Map;

/**
 * Interface for services to encapsulate logic for CRM system.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/02/18
 *
 * @author Darya Baraukova
 */
public interface ICrmIntegrationService {

    /**
     * Gets rights distribution by list of cccEventId from the CRM system.
     *
     * @param cccEventIds list of cccEventId
     * @return map of cccEventId to list of {@link CrmRightsDistributionResponse}
     */
    Map<String, List<CrmRightsDistributionResponse>> getRightsDistribution(List<String> cccEventIds);

    /**
     * Sends list of {@link CrmRightsDistributionRequest}s to the CRM system.
     *
     * @param crmRightsDistributionRequests list of {@link CrmRightsDistributionRequest} instances
     * @return {@link CrmResult} instance
     */
    CrmResult sendRightsDistributionRequests(List<CrmRightsDistributionRequest> crmRightsDistributionRequests);
}
