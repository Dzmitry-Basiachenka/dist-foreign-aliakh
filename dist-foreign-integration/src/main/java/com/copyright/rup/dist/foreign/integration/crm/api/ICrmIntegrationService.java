package com.copyright.rup.dist.foreign.integration.crm.api;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for services to encapsulate logic for CRM system.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/02/18
 *
 * @author Darya Baraukova
 */
public interface ICrmIntegrationService extends Serializable {

    /**
     * Sends list of {@link CrmRightsDistributionRequest}s to the CRM system.
     *
     * @param crmRightsDistributionRequests list of {@link CrmRightsDistributionRequest} instances
     * @return {@link CrmResult} instance
     */
    CrmResult insertRightsDistribution(List<CrmRightsDistributionRequest> crmRightsDistributionRequests);
}
