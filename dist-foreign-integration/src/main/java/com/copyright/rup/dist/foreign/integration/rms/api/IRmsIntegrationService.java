package com.copyright.rup.dist.foreign.integration.rms.api;

import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;

import java.io.Serializable;
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
public interface IRmsIntegrationService extends Serializable {

    /**
     * Sends set of Wr Wrk Insts with provided job name to RMS for rights assignment.
     *
     * @param jobName    RA job name
     * @param wrWrkInsts set of Wr Wrk Insts
     * @return {@link RightsAssignmentResult} instance
     */
    RightsAssignmentResult sendForRightsAssignment(String jobName, Set<Long> wrWrkInsts);
}
