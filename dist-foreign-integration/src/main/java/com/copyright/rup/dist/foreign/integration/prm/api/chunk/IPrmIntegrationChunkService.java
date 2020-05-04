package com.copyright.rup.dist.foreign.integration.prm.api.chunk;

import java.util.Map;
import java.util.Set;

/**
 * Interface for service to encapsulate logic for PRM system.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Liakh
 */
public interface IPrmIntegrationChunkService {

    /**
     * Checks whether rightsholders are STM rightsholders.
     *
     * @param rightsholdersIds list of rightsholder ids
     * @param productFamily    product family
     * @return map from rightsholder ids to STM rightsholders flag
     */
    Map<String, Boolean> areStmRightsholders(Set<String> rightsholdersIds, String productFamily);
}
