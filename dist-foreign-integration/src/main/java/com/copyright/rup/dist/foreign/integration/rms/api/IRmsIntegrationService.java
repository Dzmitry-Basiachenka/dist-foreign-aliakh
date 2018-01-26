package com.copyright.rup.dist.foreign.integration.rms.api;

import com.copyright.rup.dist.common.domain.RmsGrant;

import java.util.List;
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
     * Gets set of all {@link RmsGrant}s from RMS.
     *
     * @param wrWrkInsts list of wr wrk insts
     * @return set of {@link RmsGrant}s
     */
    Set<RmsGrant> getAllRmsGrants(List<Long> wrWrkInsts);

    /**
     * Sends set of Wr Wrk Insts to RMS for rights assignment.
     *
     * @param wrWrkInst set of Wr Wrk Insts
     * @return {@link RightsAssignmentResult} instance
     */
    RightsAssignmentResult sendForRightsAssignment(Set<Long> wrWrkInst);
}
