package com.copyright.rup.dist.foreign.integration.rms.api;

import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;

import java.util.Set;

/**
 * Interface for services to encapsulate logic for RMS system.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/2018
 *
 * @author Aliaksandr Liakh
 */
public interface IRmsIntegrationService {

    /**
     * Sends set of Wr Wrk Insts to RMS for rights assignment.
     *
     * @param wrWrkInsts set of Wr Wrk Insts
     * @return {@link RightsAssignmentResult} instance
     */
    RightsAssignmentResult sendForRightsAssignment(Set<Long> wrWrkInsts);
}
