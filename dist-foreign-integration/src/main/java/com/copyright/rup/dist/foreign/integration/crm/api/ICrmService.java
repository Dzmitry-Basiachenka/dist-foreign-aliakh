package com.copyright.rup.dist.foreign.integration.crm.api;

import java.util.List;
import java.util.Set;

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
     * Gets rights distribution by set of cccEventId from the CRM system.
     *
     * @param cccEventIds set of cccEventId
     * @return list of {@link GetRightsDistributionResponse}
     */
    List<GetRightsDistributionResponse> getRightsDistribution(Set<String> cccEventIds);

    /**
     * Inserts rights distribution by list of {@link InsertRightsDistributionRequest} to the CRM system.
     *
     * @param requests list of {@link InsertRightsDistributionRequest}s
     * @return instance of {@link InsertRightsDistributionResponse}
     */
    InsertRightsDistributionResponse insertRightsDistribution(List<InsertRightsDistributionRequest> requests);
}
