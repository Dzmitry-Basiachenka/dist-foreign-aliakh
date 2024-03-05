package com.copyright.rup.dist.foreign.integration.crm.api;

import java.io.Serializable;
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
public interface ICrmService extends Serializable {

    /**
     * Sends list of {@link CrmRightsDistributionRequest} to the CRM system.
     *
     * @param crmRightsDistributionRequests list of {@link CrmRightsDistributionRequest}s
     * @return result of CRM process
     */
    CrmResult insertRightsDistribution(List<CrmRightsDistributionRequest> crmRightsDistributionRequests);
}
