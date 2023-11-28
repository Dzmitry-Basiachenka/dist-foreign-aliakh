package com.copyright.rup.dist.foreign.service.impl.nts;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.service.impl.converter.UsageFilterToScenarioUsageFilterConverter;

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
 * Implementation of {@link INtsScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/18/2020
 *
 * @author Ihar Suvorau
 */
@Service
public class NtsScenarioService implements INtsScenarioService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int batchSize;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private INtsUsageService ntsUsageService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private ILmIntegrationService lmIntegrationService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public Scenario createScenario(String scenarioName, NtsFields ntsFields, String description,
                                   UsageFilter usageFilter) {
        LOGGER.info("Insert NTS scenario. Started. Name={}, NtsFields={}, Description={}, UsageFilter={}",
            scenarioName, ntsFields, description, usageFilter);
        Scenario scenario = buildScenario(scenarioName, ntsFields, description, usageFilter);
        scenarioRepository.insertNtsScenarioAndAddUsages(scenario, usageFilter);
        ntsUsageService.populatePayeeAndCalculateAmountsForScenarioUsages(scenario);
        scenarioUsageFilterService.insert(scenario.getId(),
            new UsageFilterToScenarioUsageFilterConverter().apply(usageFilter));
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        LOGGER.info("Insert NTS scenario. Finished. Name={}, NtsFields={}, Description={}, UsageFilter={}",
            scenarioName, ntsFields, description, usageFilter);
        return scenario;
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteScenario(Scenario scenario) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete scenario. Started. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
        String scenarioId = scenario.getId();
        ntsUsageService.deleteBelletristicByScenarioId(scenarioId);
        ntsUsageService.deleteFromScenario(scenarioId);
        scenarioAuditService.deleteActions(scenarioId);
        scenarioUsageFilterService.removeByScenarioId(scenarioId);
        scenarioRepository.remove(scenarioId);
        LOGGER.info("Delete scenario. Finished. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
    }

    @Override
    public String getScenarioNameByFundPoolId(String fundPoolId) {
        return scenarioRepository.findNameByNtsFundPoolId(fundPoolId);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void sendToLm(Scenario scenario) {
        LogUtils.ILogWrapper scenarioWrapper = ForeignLogUtils.scenario(scenario);
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Send scenario to LM. Started. {}, User={}", scenarioWrapper, userName);
        List<String> usageIds = ntsUsageService.moveToArchive(scenario);
        if (CollectionUtils.isNotEmpty(usageIds)) {
            Iterables.partition(usageIds, batchSize)
                .forEach(partition -> {
                    List<Usage> usages = usageService.getArchivedUsagesForSendToLmByIds(partition);
                    usages.forEach(usage -> {
                        //for NTS usages System should not send work information to LM
                        usage.setWrWrkInst(null);
                        usage.setSystemTitle(null);
                    });
                    lmIntegrationService.sendToLm(usages.stream().map(ExternalUsage::new).collect(Collectors.toList()),
                        scenario, usageIds.size());
                });
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

    private Scenario buildScenario(String scenarioName, NtsFields ntsFields, String description,
                                   UsageFilter usageFilter) {
        Scenario scenario = new Scenario();
        scenario.setNtsFields(ntsFields);
        scenario.setId(RupPersistUtils.generateUuid());
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
