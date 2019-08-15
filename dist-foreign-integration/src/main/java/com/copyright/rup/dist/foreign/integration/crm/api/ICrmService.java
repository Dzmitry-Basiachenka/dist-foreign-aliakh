package com.copyright.rup.dist.foreign.integration.crm.api;

import java.util.List;

/**
 * Interface for service for sending information to the CRM system.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/29/18
 *
 * @author Darya Baraukova
 */
public interface ICrmService {

    /**
     * Gets rights distribution by list of cccEventId from the CRM system.
     *
     * @param cccEventIds list of cccEventId
     * @return list of {@link CrmRightsDistributionResponse}
     */
    List<CrmRightsDistributionResponse> getRightsDistribution(List<String> cccEventIds);

    /**
     * Sends list of {@link CrmRightsDistributionRequest} to the CRM system.
     *
     * @param crmRightsDistributionRequests list of {@link CrmRightsDistributionRequest}s
     * @return result of CRM process
     */
    CrmResult sendRightsDistributionRequests(List<CrmRightsDistributionRequest> crmRightsDistributionRequests);
}
