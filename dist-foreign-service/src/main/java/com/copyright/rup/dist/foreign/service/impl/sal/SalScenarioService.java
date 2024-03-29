package com.copyright.rup.dist.foreign.service.impl.sal;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.converter.UsageFilterToScenarioUsageFilterConverter;

import com.google.common.collect.Iterables;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

/**
 * Implements of {@link ISalScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/24/2020
 *
 * @author Aliaksandr Liakh
 */
@Service
public class SalScenarioService implements ISalScenarioService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int batchSize;
    @Autowired
    private ILmIntegrationService lmIntegrationService;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;

    @Override
    public String getScenarioNameByFundPoolId(String fundPoolId) {
        return scenarioRepository.findNameBySalFundPoolId(fundPoolId);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public Scenario createScenario(String scenarioName, String fundPoolId, String description,
                                   UsageFilter usageFilter) {
        LOGGER.info("Insert SAL scenario. Started. Name={}, FundPoolId={}, Description={}, UsageFilter={}",
            scenarioName, fundPoolId, description, usageFilter);
        Scenario scenario = buildScenario(scenarioName, fundPoolId, description);
        scenarioRepository.insert(scenario);
        salUsageService.addUsagesToScenario(scenario, usageFilter);
        scenarioUsageFilterService.insert(scenario.getId(),
            new UsageFilterToScenarioUsageFilterConverter().apply(usageFilter));
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        salUsageService.calculateAmounts(scenario.getId(), scenario.getCreateUser());
        salUsageService.populatePayees(scenario.getId());
        LOGGER.info("Insert SAL scenario. Finished. Name={}, Description={}, UsageFilter={}",
            scenarioName, description, usageFilter);
        return scenario;
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteScenario(Scenario scenario) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete scenario. Started. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
        String scenarioId = scenario.getId();
        salUsageService.deleteFromScenario(scenarioId);
        scenarioAuditService.deleteActions(scenarioId);
        scenarioUsageFilterService.removeByScenarioId(scenarioId);
        scenarioRepository.remove(scenarioId);
        LOGGER.info("Delete scenario. Finished. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void sendToLm(Scenario scenario) {
        LogUtils.ILogWrapper scenarioWrapper = ForeignLogUtils.scenario(scenario);
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Send SAL scenario to LM. Started. {}, User={}", scenarioWrapper, userName);
        List<String> usageIds = salUsageService.moveToArchive(scenario);
        Iterables.partition(usageIds, batchSize).forEach(partition -> {
            List<ExternalUsage> externalUsages = usageService.getArchivedUsagesForSendToLmByIds(partition)
                .stream()
                .map(ExternalUsage::new)
                .collect(Collectors.toList());
            lmIntegrationService.sendToLm(externalUsages, scenario, usageIds.size());
        });
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        scenario.setUpdateUser(userName);
        scenarioRepository.updateStatus(scenario);
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.SENT_TO_LM, StringUtils.EMPTY);
        LOGGER.info("Send SAL scenario to LM. Finished. {}, User={}", scenarioWrapper, userName);
    }

    @Override
    @Transactional
    public void changeScenariosState(Set<Scenario> scenarios, ScenarioStatusEnum status, ScenarioActionTypeEnum action,
                                      String reason) {
        String userName = RupContextUtils.getUserName();
        scenarios.forEach(scenario -> {
            Objects.requireNonNull(scenario);
            scenario.setStatus(status);
            scenario.setUpdateUser(userName);
            LOGGER.info("Change scenario status. {}, User={}, Reason={}", ForeignLogUtils.scenario(scenario), userName,
                reason);
        });
        scenarioRepository.updateStatus(scenarios, status);
        Set<String> scenarioIds = scenarios.stream().map(Scenario::getId).collect(Collectors.toSet());
        scenarioAuditService.logAction(scenarioIds, action, reason);
    }

    private Scenario buildScenario(String scenarioName, String fundPoolId, String description) {
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName(scenarioName);
        scenario.setProductFamily(FdaConstants.SAL_PRODUCT_FAMILY);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription(description);
        String userName = RupContextUtils.getUserName();
        scenario.setCreateUser(userName);
        scenario.setUpdateUser(userName);
        SalFields salFields = new SalFields();
        salFields.setFundPoolId(fundPoolId);
        scenario.setSalFields(salFields);
        return scenario;
    }
}

