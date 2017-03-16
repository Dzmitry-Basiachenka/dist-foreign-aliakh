package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Scenario service implementation.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Ihar Suvorau
 */
@Service
public class ScenarioService implements IScenarioService {

    @Autowired
    private IScenarioRepository scenarioRepository;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioRepository.getScenarios();
    }

    @Override
    public boolean isScenarioExists(String scenarioName) {
        return 0 < scenarioRepository.getCountByName(scenarioName);
    }

    @Override
    public List<String> getScenariosNamesByUsageBatchId(String usageBatchId) {
        return scenarioRepository.findScenariosNamesByUsageBatchId(usageBatchId);
    }
}
