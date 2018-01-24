package com.copyright.rup.dist.foreign.integration.rms.impl;

import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsService;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
@Service("sf.integration.rmsIntegrationService")
public class RmsIntegrationService implements IRmsIntegrationService {

    @Autowired
    @Qualifier("dist.common.integration.rmsService")
    private IRmsService rmsService;

    @Override
    public Set<RmsGrant> getAllRmsGrants(List<Long> wrWrkInsts) {
        return rmsService.getAllRmsGrants(wrWrkInsts,
            Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * Sets instance of {@link IRmsService}.
     *
     * @param rmsService instance of {@link IRmsService} to set
     */
    void setRmsService(IRmsService rmsService) {
        this.rmsService = rmsService;
    }
}
