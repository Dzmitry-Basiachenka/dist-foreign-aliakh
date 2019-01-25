package com.copyright.rup.dist.foreign.integration.rms.impl;

import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsAssignmentService;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
public class RmsIntegrationService implements IRmsIntegrationService {

    @Autowired
    private IRmsRightsAssignmentService rmsRightsAssignmentService;

    @Override
    public RightsAssignmentResult sendForRightsAssignment(Set<Long> wrWrkInsts) {
        return rmsRightsAssignmentService.sendForRightsAssignment(wrWrkInsts);
    }
}
