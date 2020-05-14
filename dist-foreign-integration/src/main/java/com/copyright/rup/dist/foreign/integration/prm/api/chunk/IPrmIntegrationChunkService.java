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
     * Gets map from rightsholder ids to STM rightsholder flag.
     * Gets STM flag {@link com.copyright.rup.dist.foreign.domain.FdaConstants#IS_RH_STM_IPRO_CODE} by specified
     * rightsholder id and product family.
     * </br>
     * If preferences for NTS product family were not found System finds preferences for the same rightsholder id
     * and <b>{@code '*'}</b> ('*' is passed as a product family).
     *
     * @param rightsholdersIds list of rightsholder ids
     * @param productFamily    product family
     * @return map from rightsholder ids to STM rightsholders flag
     */
    Map<String, Boolean> getStmRightsholderPreferenceMap(Set<String> rightsholdersIds, String productFamily);
}
