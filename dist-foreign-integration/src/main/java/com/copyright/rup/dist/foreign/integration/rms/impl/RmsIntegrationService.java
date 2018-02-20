package com.copyright.rup.dist.foreign.integration.rms.impl;

import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsService;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsRightsAssignmentService;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult;

import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
    @Qualifier("dist.common.integration.rmsAsyncService")
    private IRmsService rmsAsyncService;

    @Autowired
    @Qualifier("dist.common.integration.rmsService")
    private IRmsService rmsService;

    @Value("$RUP{dist.foreign.integration.rest.rms.grants.async}")
    private boolean rmsGrantsAsync;

    @Autowired
    private IRmsRightsAssignmentService rmsRightsAssignmentService;

    @Override
    @Profiled(tag = "integration.RmsIntegrationService.getAllRmsGrants")
    public Set<RmsGrant> getAllRmsGrants(List<Long> wrWrkInsts) {
        return rmsGrantsAsync
            ? rmsAsyncService.getAllRmsGrants(wrWrkInsts, LocalDate.now())
            : rmsService.getAllRmsGrants(wrWrkInsts, LocalDate.now());
    }

    @Override
    public RightsAssignmentResult sendForRightsAssignment(Set<Long> wrWrkInsts) {
        return rmsRightsAssignmentService.sendForRightsAssignment(wrWrkInsts);
    }
}
