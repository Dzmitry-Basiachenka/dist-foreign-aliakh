package com.copyright.rup.dist.foreign.integration.rms.impl;

import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsAssignmentService;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IRmsIntegrationService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/2018
 *
 * @author Aliaksandr Liakh
 */
@Service("df.integration.rmsIntegrationService")
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class RmsIntegrationService implements IRmsIntegrationService {

    @Autowired
    private IRmsRightsAssignmentService rmsRightsAssignmentService;

    @Override
    public RightsAssignmentResult sendForRightsAssignment(String jobName, Set<Long> wrWrkInsts) {
        return rmsRightsAssignmentService.sendForRightsAssignment(jobName, wrWrkInsts);
    }
}
