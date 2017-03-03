package com.copyright.rup.dist.foreign.integration.prm.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmRightsholderService;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IPrmIntegrationService} for PRM system.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
@Service
public class PrmIntegrationService implements IPrmIntegrationService {

    @Autowired
    private IPrmRightsholderService prmRightsholderService;


    @Override
    @Profiled(tag = "integration.PrmRightsholderService.getRightsholders")
    public List<Rightsholder> getRightsholders(Set<Long> accountNumbers) {
        return null != accountNumbers && !accountNumbers.isEmpty()
            ? Lists.newArrayList(prmRightsholderService.getRightsholders(accountNumbers))
            : Collections.emptyList();
    }

    @Override
    public Rightsholder getRightsholder(Long accountNumber) {
        List<Rightsholder> rightsholders = getRightsholders(Sets.newHashSet(accountNumber));
        return !rightsholders.isEmpty() ? rightsholders.get(0) : null;
    }
}
