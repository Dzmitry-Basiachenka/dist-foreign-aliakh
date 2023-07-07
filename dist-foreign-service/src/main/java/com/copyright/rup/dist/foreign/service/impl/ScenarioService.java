package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IRightsholderDiscrepancyService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class ScenarioService implements IScenarioService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;

    @Override
    public List<Scenario> getScenarios(String productFamily) {
        return scenarioRepository.findByProductFamily(productFamily);
    }

    @Override
    public List<Scenario> getScenariosByProductFamiliesAndStatuses(Set<String> productFamilies,
                                                                   Set<ScenarioStatusEnum> statuses) {
        return scenarioRepository.findByProductFamiliesAndStatuses(productFamilies, statuses);
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
    public Scenario createScenario(String scenarioName, String description, UsageFilter usageFilter) {
        LOGGER.info("Insert scenario. Started. Name={}, Description={}, UsageFilter={}",
            scenarioName, description, usageFilter);
        List<Usage> usages = fasUsageService.getUsagesWithAmounts(usageFilter);
        Scenario scenario = buildScenario(scenarioName, description, usageFilter);
        scenarioRepository.insert(scenario);
        fasUsageService.addUsagesToScenario(usages, scenario);
        scenarioUsageFilterService.insert(scenario.getId(), new ScenarioUsageFilter(usageFilter));
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        LOGGER.info("Insert scenario. Finished. Name={}, Description={}, UsageFilter={}",
            scenarioName, description, usageFilter);
        return scenario;
    }

    @Override
    public void updateName(String scenarioId, String name) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Update scenario name. Started. ScenarioId={}, NewName={}, User={}", scenarioId, name, userName);
        scenarioRepository.updateNameById(scenarioId, name, userName);
        LOGGER.info("Update scenario name. Finished. ScenarioId={}, NewName={}, User={}", scenarioId, name, userName);
    }

    @Override
    @Transactional
    public void deleteScenario(Scenario scenario) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete scenario. Started. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
        String scenarioId = scenario.getId();
        usageService.deleteFromScenario(scenarioId);
        rightsholderDiscrepancyService.deleteByScenarioId(scenarioId);
        scenarioAuditService.deleteActions(scenarioId);
        scenarioUsageFilterService.removeByScenarioId(scenarioId);
        scenarioRepository.remove(scenarioId);
        LOGGER.info("Delete scenario. Finished. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
    }

    @Override
    @Transactional
    public void refreshScenario(Scenario scenario) {
        ScenarioUsageFilter usageFilter = scenarioUsageFilterService.getByScenarioId(scenario.getId());
        if (null != usageFilter) {
            fasUsageService.recalculateUsagesForRefresh(new UsageFilter(usageFilter), scenario);
            scenarioRepository.refresh(scenario);
            scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        }
    }

    @Override
    public Scenario getScenarioWithAmountsAndLastAction(Scenario scenario) {
        return FdaConstants.ARCHIVED_SCENARIO_STATUSES.contains(scenario.getStatus())
            ? scenarioRepository.findArchivedWithAmountsAndLastAction(scenario.getId())
            : scenarioRepository.findWithAmountsAndLastAction(scenario.getId());
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
        rightsholderDiscrepancyService.deleteByScenarioIdAndStatus(scenario.getId(),
            RightsholderDiscrepancyStatusEnum.DRAFT);
        changeScenarioState(scenario, ScenarioStatusEnum.APPROVED, ScenarioActionTypeEnum.APPROVED, reason);
    }

    @Override
    @Transactional
    public int archiveScenarios() {
        List<String> paidScenariosIds = scenarioRepository.findIdsForArchiving();
        LOGGER.info("Archive scenarios. Started. PaidScenariosCount={}", LogUtils.size(paidScenariosIds));
        int archivedCount = paidScenariosIds.size();
        if (CollectionUtils.isNotEmpty(paidScenariosIds)) {
            scenarioRepository.updateStatus(paidScenariosIds, ScenarioStatusEnum.ARCHIVED);
            scenarioAuditService.logAction(new HashSet<>(paidScenariosIds), ScenarioActionTypeEnum.ARCHIVED,
                "All usages from scenario have been sent to CRM");
            LOGGER.info("Archive scenarios. Finished. PaidScenariosCount={}, ArchivedCount={}",
                LogUtils.size(paidScenariosIds), archivedCount);
        } else {
            LOGGER.info("Archive scenarios. Skipped. Reason=There are no scenarios to archive");
        }
        return archivedCount;
    }

    private Scenario buildScenario(String scenarioName, String description, UsageFilter usageFilter) {
        Scenario scenario = new Scenario();
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
