package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclScenarioService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

/**
 * Implements of {@link IAaclScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/08/2020
 *
 * @author Anton Azarenka
 */
@Service
public class AaclScenarioService implements IAaclScenarioService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int batchSize;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private ILmIntegrationService lmIntegrationService;

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public Scenario createScenario(String scenarioName, AaclFields aaclFields, String description,
                                   UsageFilter usageFilter) {
        LOGGER.info("Insert AACL scenario. Started. Name={}, AaclFields={}, Description={}, UsageFilter={}",
            scenarioName, ForeignLogUtils.scenarioAaclFields(aaclFields), description, usageFilter);
        Scenario scenario = buildAaclScenario(scenarioName, aaclFields, description, usageFilter);
        scenarioRepository.insert(scenario);
        aaclUsageService.addUsagesToScenario(scenario, usageFilter);
        scenarioUsageFilterService.insert(scenario.getId(), new ScenarioUsageFilter(usageFilter));
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        aaclUsageService.calculateAmounts(scenario.getId(), scenario.getCreateUser());
        aaclUsageService.excludeZeroAmountUsages(scenario.getId(), scenario.getCreateUser());
        aaclUsageService.populatePayees(scenario.getId());
        LOGGER.info("Insert AACL scenario. Finished. Name={}, Description={}, UsageFilter={}",
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
        aaclUsageService.deleteFromScenario(scenarioId);
        scenarioAuditService.deleteActions(scenarioId);
        scenarioUsageFilterService.removeByScenarioId(scenarioId);
        scenarioRepository.remove(scenarioId);
        LOGGER.info("Delete scenario. Finished. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
    }

    @Override
    public String getScenarioNameByFundPoolId(String fundPoolId) {
        return scenarioRepository.findNameByAaclFundPoolId(fundPoolId);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void sendToLm(Scenario scenario) {
        LogUtils.ILogWrapper scenarioWrapper = ForeignLogUtils.scenario(scenario);
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Send scenario to LM. Started. {}, User={}", scenarioWrapper, userName);
        List<String> usageIds = aaclUsageService.moveToArchive(scenario);
        if (CollectionUtils.isNotEmpty(usageIds)) {
            Iterables.partition(usageIds, batchSize)
                .forEach(partition ->
                    lmIntegrationService.sendToLm(usageService.getArchivedUsagesForSendToLmByIds(partition)
                        .stream().map(ExternalUsage::new).collect(Collectors.toList()),
                        scenario, usageIds.size()));
            changeScenarioState(scenario, ScenarioStatusEnum.SENT_TO_LM, ScenarioActionTypeEnum.SENT_TO_LM,
                StringUtils.EMPTY);
            LOGGER.info("Send scenario to LM. Finished. {}, User={}", scenarioWrapper, userName);
        } else {
            throw new RupRuntimeException(String.format("Send scenario to LM. Failed. %s. Reason=Scenario is empty",
                scenarioWrapper));
        }
    }

    private void changeScenarioState(Scenario scenario, ScenarioStatusEnum status, ScenarioActionTypeEnum action,
                                     String reason) {
        Objects.requireNonNull(scenario);
        String userName = RupContextUtils.getUserName();
        scenario.setStatus(status);
        scenario.setUpdateUser(userName);
        scenarioRepository.updateStatus(scenario);
        scenarioAuditService.logAction(scenario.getId(), action, reason);
    }

    private Scenario buildAaclScenario(String scenarioName, AaclFields aaclFields, String description,
                                       UsageFilter usageFilter) {
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setAaclFields(aaclFields);
        scenario.setName(scenarioName);
        scenario.setProductFamily(usageFilter.getProductFamily());
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription(description);
        String userName = RupContextUtils.getUserName();
        scenario.setCreateUser(userName);
        scenario.setUpdateUser(userName);
        return scenario;
    }
}
