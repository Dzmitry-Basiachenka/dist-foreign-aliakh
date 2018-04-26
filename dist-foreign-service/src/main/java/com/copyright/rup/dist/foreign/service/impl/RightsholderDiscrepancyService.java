package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
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
    public int getDiscrepanciesCountByScenarioIdAndStatus(String scenarioId, RightsholderDiscrepancyStatusEnum status) {
        return rightsholderDiscrepancyRepository.findCountByScenarioIdAndStatus(scenarioId, status);
    }

    @Override
    public List<Long> getProhibitedAccountNumbers(String scenarioId) {
        return rightsholderDiscrepancyRepository.findProhibitedAccountNumbers(scenarioId);
    }

    @Override
    public List<RightsholderDiscrepancy> getDiscrepanciesByScenarioIdAndStatus(String scenarioId,
                                                                               RightsholderDiscrepancyStatusEnum status,
                                                                               Pageable pageable, Sort sort) {
        return rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(scenarioId, status, pageable, sort);
    }

    @Override
    public void deleteDiscrepanciesByScenarioIdAndStatus(String scenarioId, RightsholderDiscrepancyStatusEnum status) {
        rightsholderDiscrepancyRepository.deleteByScenarioIdAndStatus(scenarioId, status);
    }

    @Override
    public void approveDiscrepanciesByScenarioId(String scenarioId) {
        rightsholderDiscrepancyRepository.approveByScenarioId(scenarioId);
    }
}
