package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link IRightsholderDiscrepancyService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/19/2018
 *
 * @author Ihar Suvorau
 */
@Service
public class RightsholderDiscrepancyService implements IRightsholderDiscrepancyService {

    @Autowired
    private IRightsholderDiscrepancyRepository rightsholderDiscrepancyRepository;

    @Override
    public void insertDiscrepancies(List<RightsholderDiscrepancy> rightsholderDiscrepancies, String scenarioId) {
        rightsholderDiscrepancyRepository.insertAll(rightsholderDiscrepancies, scenarioId);
    }

    @Override
    public List<RightsholderDiscrepancy> getDiscrepanciesByScenarioId(String scenarioId, Pageable pageable, Sort sort) {
        return rightsholderDiscrepancyRepository.findByScenarioId(scenarioId, pageable, sort);
    }

    @Override
    public void deleteDiscrepanciesByScenarioId(String scenarioId) {
        rightsholderDiscrepancyRepository.deleteByScenarioId(scenarioId);
    }
}
