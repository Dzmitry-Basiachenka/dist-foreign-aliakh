package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Scenario service implementation.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Ihar Suvorau
 * @author Mikalai Bezmen
 */
@Service
public class ScenarioService implements IScenarioService {

    @Autowired
    private IScenarioRepository scenarioRepository;

    @Autowired
    private IUsageService usageService;

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

    @Override
    @Transactional
    public String createScenario(String scenarioName, String description, UsageFilter usageFilter) {
        List<Usage> usages = usageService.getUsagesWithAmounts(usageFilter);
        Scenario scenario = buildScenario(scenarioName, description, usages);
        scenarioRepository.insert(scenario);
        usageService.addUsagesToScenario(usages.stream().map(Usage::getId).collect(Collectors.toList()), scenario);
        return scenario.getId();
    }

    @Override
    @Transactional
    public void deleteScenario(String scenarioId) {
        usageService.deleteUsagesFromScenario(scenarioId);
        scenarioRepository.deleteScenario(scenarioId);
    }

    private Scenario buildScenario(String scenarioName, String description, List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName(scenarioName);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription(description);
        // TODO {mbezmen} net amount will be implemented after service fee logic will be added
        scenario.setGrossTotal(usages.stream()
            .map(Usage::getGrossAmount)
            .reduce(BigDecimal.ZERO.setScale(10, RoundingMode.HALF_UP), BigDecimal::add));
        scenario.setReportedTotal(usages.stream()
            .map(Usage::getReportedValue)
            .reduce(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), BigDecimal::add));
        scenario.setCreateUser(userName);
        scenario.setUpdateUser(userName);
        return scenario;
    }
}
