package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * Scenario service implementation.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 3/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Ihar Suvorau
 * @author Mikalai Bezmen
 */
@Service
public class ScenarioService implements IScenarioService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioRepository.findAll();
    }

    @Override
    public boolean scenarioExists(String scenarioName) {
        return 0 < scenarioRepository.findCountByName(scenarioName);
    }

    @Override
    public List<String> getScenariosNamesByUsageBatchId(String usageBatchId) {
        return scenarioRepository.findNamesByUsageBatchId(usageBatchId);
    }

    @Override
    @Transactional
    public String createScenario(String scenarioName, String description, UsageFilter usageFilter) {
        List<Usage> usages = usageService.getUsagesWithAmounts(usageFilter);
        Scenario scenario = buildScenario(scenarioName, description, usages);
        scenarioRepository.insert(scenario);
        usageService.addUsagesToScenario(usages, scenario);
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        return scenario.getId();
    }

    @Override
    @Transactional
    public void deleteScenario(Scenario scenario) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete scenario. Started. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
        usageService.deleteFromScenario(scenario.getId());
        scenarioAuditService.deleteActions(scenario.getId());
        scenarioRepository.remove(scenario.getId());
        LOGGER.info("Delete scenario. Finished. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
    }

    @Override
    public Scenario getScenarioWithAmountsAndLastAction(String scenarioId) {
        return scenarioRepository.findWithAmountsAndLastAction(scenarioId);
    }

    @Override
    public List<Rightsholder> getSourceRros(String scenarioId) {
        return scenarioRepository.findSourceRros(scenarioId);
    }

    @Override
    public List<RightsholderPayeePair> getRightsholdersByScenarioAndSourceRro(String scenarioId,
                                                                              Long rroAccountNumber) {
        return scenarioRepository.findRightsholdersByScenarioIdAndSourceRro(scenarioId, rroAccountNumber);
    }

    @Override
    @Transactional
    public void submit(Scenario scenario, String reason) {
        changeScenarioState(scenario, ScenarioStatusEnum.SUBMITTED, ScenarioActionTypeEnum.SUBMITTED, reason);
    }

    @Override
    @Transactional
    public void reject(Scenario scenario, String reason) {
        changeScenarioState(scenario, ScenarioStatusEnum.IN_PROGRESS, ScenarioActionTypeEnum.REJECTED, reason);
    }

    @Override
    @Transactional
    public void approve(Scenario scenario, String reason) {
        changeScenarioState(scenario, ScenarioStatusEnum.APPROVED, ScenarioActionTypeEnum.APPROVED, reason);
    }

    @Override
    @Transactional
    @Profiled(tag = "service.ScenarioService.sendToLm")
    public void sendToLm(Scenario scenario) {
        LOGGER.info("Send scenario to LM. Started. {}, User={}", ForeignLogUtils.scenario(scenario),
            RupContextUtils.getUserName());
        usageService.moveToArchive(scenario);
        changeScenarioState(scenario, ScenarioStatusEnum.SENT_TO_LM, ScenarioActionTypeEnum.SENT_TO_LM,
            StringUtils.EMPTY);
        LOGGER.info("Send scenario to LM. Finished. {}, User={}", ForeignLogUtils.scenario(scenario),
            RupContextUtils.getUserName());
    }

    private Scenario buildScenario(String scenarioName, String description, List<Usage> usages) {
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName(scenarioName);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription(description);
        scenario.setNetTotal(usages.stream()
            .map(Usage::getNetAmount)
            .reduce(BigDecimal.ZERO.setScale(10, RoundingMode.HALF_UP), BigDecimal::add));
        scenario.setServiceFeeTotal(usages.stream()
            .map(Usage::getServiceFeeAmount)
            .reduce(BigDecimal.ZERO.setScale(10, RoundingMode.HALF_UP), BigDecimal::add));
        scenario.setGrossTotal(usages.stream()
            .map(Usage::getGrossAmount)
            .reduce(BigDecimal.ZERO.setScale(10, RoundingMode.HALF_UP), BigDecimal::add));
        scenario.setReportedTotal(usages.stream()
            .map(Usage::getReportedValue)
            .reduce(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), BigDecimal::add));
        String userName = RupContextUtils.getUserName();
        scenario.setCreateUser(userName);
        scenario.setUpdateUser(userName);
        return scenario;
    }

    private void changeScenarioState(Scenario scenario, ScenarioStatusEnum status, ScenarioActionTypeEnum action,
                                     String reason) {
        Objects.requireNonNull(scenario);
        String userName = RupContextUtils.getUserName();
        scenario.setStatus(status);
        scenario.setUpdateUser(userName);
        LOGGER.info("Change scenario status. {}, User={}, Reason={}", ForeignLogUtils.scenario(scenario), userName,
            reason);
        scenarioRepository.updateStatus(scenario);
        scenarioAuditService.logAction(scenario.getId(), action, reason);
    }
}
