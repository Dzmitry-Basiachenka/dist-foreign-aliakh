package com.copyright.rup.dist.foreign.integration.rms.api;

import java.util.Set;

/**
 * Interface for RMS service to send usages for rights assignment.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/24/18
 *
 * @author Darya Baraukova
 */
public interface IRmsRightsAssignmentService {

    /**
     * Sends set of Wr Wrk Insts to RMS for rights assignment.
     *
     * @param wrWrkInsts set of Wr Wrk Insts
     * @return {@link RightsAssignmentResult} instance
     */
    RightsAssignmentResult sendForRightsAssignment(Set<Long> wrWrkInsts);
}
