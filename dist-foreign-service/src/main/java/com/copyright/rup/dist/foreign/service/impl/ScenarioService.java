package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IRmsGrantsService;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.perf4j.StopWatch;
import org.perf4j.aop.Profiled;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private ILmIntegrationService lmIntegrationService;
    @Autowired
    private IRmsGrantsService rmsGrantsService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;

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
    public Scenario createScenario(String scenarioName, String description, UsageFilter usageFilter) {
        StopWatch stopWatch = new Slf4JStopWatch();
        List<Usage> usages = usageService.getUsagesWithAmounts(usageFilter);
        stopWatch.lap("scenario.create_1_getUsagesWithAmounts");
        Scenario scenario = buildScenario(scenarioName, description, usages);
        scenarioRepository.insert(scenario);
        stopWatch.lap("scenario.create_2_insertScenario");
        usageService.addUsagesToScenario(usages, scenario);
        stopWatch.lap("scenario.create_3_addUsagesToScenario");
        scenarioUsageFilterService.insert(scenario.getId(), new ScenarioUsageFilter(usageFilter));
        stopWatch.lap("scenario.create_4_insertScenarioUsageFilter");
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        stopWatch.stop("scenario.create_5_logAddedUsagesAction");
        return scenario;
    }

    @Override
    @Transactional
    public void deleteScenario(Scenario scenario) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete scenario. Started. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
        usageService.deleteFromScenario(scenario.getId());
        scenarioAuditService.deleteActions(scenario.getId());
        scenarioUsageFilterService.removeByScenarioId(scenario.getId());
        scenarioRepository.remove(scenario.getId());
        LOGGER.info("Delete scenario. Finished. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
    }

    @Override
    @Transactional
    public void refreshScenario(Scenario scenario) {
        StopWatch stopWatch = new Slf4JStopWatch();
        ScenarioUsageFilter usageFilter = scenarioUsageFilterService.getByScenarioId(scenario.getId());
        stopWatch.lap("scenario.refresh_1_getByScenarioId");
        if (null != usageFilter) {
            usageService.recalculateUsagesForRefresh(new UsageFilter(usageFilter), scenario);
            stopWatch.lap("scenario.refresh_2_recalculateUsagesForRefresh");
            scenarioRepository.refresh(scenario);
            stopWatch.lap("scenario.refresh_3_updateScenario");
            scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
            stopWatch.lap("scenario.refresh_4_logAction");
        }
        stopWatch.stop();
    }

    @Override
    public Scenario getScenarioWithAmountsAndLastAction(Scenario scenario) {
        return ScenarioStatusEnum.SENT_TO_LM == scenario.getStatus()
            ? scenarioRepository.findArchivedWithAmountsAndLastAction(scenario.getId())
            : scenarioRepository.findWithAmountsAndLastAction(scenario.getId());
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
    public void sendToLm(Scenario scenario) {
        StopWatch stopWatch = new Slf4JStopWatch();
        LOGGER.info("Send scenario to LM. Started. {}, User={}", ForeignLogUtils.scenario(scenario),
            RupContextUtils.getUserName());
        List<Usage> usages = usageService.moveToArchive(scenario);
        stopWatch.lap("scenario.sendToLm_1_moveToArchive");
        if (CollectionUtils.isNotEmpty(usages)) {
            changeScenarioState(scenario, ScenarioStatusEnum.SENT_TO_LM, ScenarioActionTypeEnum.SENT_TO_LM,
                StringUtils.EMPTY);
            stopWatch.lap("scenario.sendToLm_2_changeScenarioState");
            lmIntegrationService.sendToLm(usages.stream().map(ExternalUsage::new).collect(Collectors.toList()));
            LOGGER.info("Send scenario to LM. Finished. {}, User={}", ForeignLogUtils.scenario(scenario),
                RupContextUtils.getUserName());
            stopWatch.stop("scenario.sendToLm_3_sendUsages");
        } else {
            stopWatch.stop();
            throw new RupRuntimeException(String.format("Send scenario to LM. Failed. %s. Reason=Scenario is empty",
                ForeignLogUtils.scenario(scenario)));
        }
    }

    @Override
    @Profiled(tag = "service.ScenarioService.getRightsholderDiscrepancies")
    public Set<RightsholderDiscrepancy> getRightsholderDiscrepancies(Scenario scenario) {
        LOGGER.info("Get ownership changes. Started. {}", ForeignLogUtils.scenario(Objects.requireNonNull(scenario)));
        Map<Long, List<Usage>> groupedByWrWrkInstUsages = usageService.getUsagesByScenarioId(scenario.getId())
            .stream()
            .collect(Collectors.groupingBy(Usage::getWrWrkInst));
        Map<Long, Long> wrWrkInstToRightsholderMap =
            rmsGrantsService.getAccountNumbersByWrWrkInsts(Lists.newArrayList(groupedByWrWrkInstUsages.keySet()));
        Map<Long, Rightsholder> rightsholdersMap =
            rightsholderService.updateAndGetRightsholders(Sets.newHashSet(wrWrkInstToRightsholderMap.values()));
        Set<RightsholderDiscrepancy> rightsholderDiscrepancies = Sets.newHashSet();
        groupedByWrWrkInstUsages.entrySet()
            .forEach(entry -> rightsholderDiscrepancies.addAll(
                getDiscrepanciesForNewRightsholder(entry.getValue(), wrWrkInstToRightsholderMap.get(entry.getKey()),
                    rightsholdersMap)));
        LOGGER.info("Get ownership changes. Finished. {}, RhDiscrepanciesCount={}", ForeignLogUtils.scenario(scenario),
            LogUtils.size(rightsholderDiscrepancies));
        return rightsholderDiscrepancies;
    }

    @Override
    public void updateRhParticipationAndAmounts(Scenario scenario) {
        usageService.updateRhPayeeAndAmounts(usageService.getUsagesByScenarioId(scenario.getId()));
    }

    @Override
    @Profiled(tag = "service.ScenarioService.approveOwnershipChanges")
    public void approveOwnershipChanges(Scenario scenario, Set<RightsholderDiscrepancy> discrepancies) {
        LOGGER.info("Approve Ownership Changes. Started. {}, RhDiscrepanciesCount={}",
            ForeignLogUtils.scenario(Objects.requireNonNull(scenario)), LogUtils.size(discrepancies));
        Map<Long, List<Usage>> groupedByWrWrkInstUsages = usageService.getUsagesByScenarioId(scenario.getId())
            .stream()
            .collect(Collectors.groupingBy(Usage::getWrWrkInst));
        Table<String, String, Long> rollUps = prmIntegrationService.getRollUps(discrepancies.stream()
            .map(discrepancy -> discrepancy.getNewRightsholder().getId())
            .collect(Collectors.toSet()));
        discrepancies.forEach(discrepancy -> {
                Rightsholder newRightsholder = discrepancy.getNewRightsholder();
                Long payeeAccountNumber = PrmRollUpService.getPayeeAccountNumber(rollUps, newRightsholder,
                    discrepancy.getProductFamily());
                groupedByWrWrkInstUsages.get(discrepancy.getWrWrkInst()).forEach(usage -> {
                    usage.setRightsholder(newRightsholder);
                    usage.getPayee().setAccountNumber(payeeAccountNumber);
                });
            }
        );
        usageService.updateRhPayeeAndAmounts(
            groupedByWrWrkInstUsages.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
        LOGGER.info("Approve Ownership Changes. Finished. {}, RhDiscrepanciesCount={}",
            ForeignLogUtils.scenario(scenario), LogUtils.size(discrepancies));
    }

    @Override
    @Transactional
    public int archiveScenarios(List<String> paidUsageIds) {
        List<String> paidScenariosIds = scenarioRepository.findIdsForArchiving(paidUsageIds);
        LOGGER.info("Archive scenarios. Started. PaidScenariosCount={}", LogUtils.size(paidScenariosIds));
        int archivedCount = paidScenariosIds.size();
        if (CollectionUtils.isNotEmpty(paidScenariosIds)) {
            scenarioRepository.updateStatus(paidScenariosIds, ScenarioStatusEnum.ARCHIVED);
            scenarioAuditService.logAction(Sets.newHashSet(paidScenariosIds), ScenarioActionTypeEnum.ARCHIVED,
                "All usages from scenario have been sent to CRM");
            LOGGER.info("Archive scenarios. Finished. PaidScenariosCount={}, ArchivedCount={}",
                LogUtils.size(paidScenariosIds), archivedCount);
        } else {
            LOGGER.info("Archive scenarios. Skipped. Reason=There are no scenarios to archive");
        }
        return archivedCount;
    }

    private Set<RightsholderDiscrepancy> getDiscrepanciesForNewRightsholder(List<Usage> usages, Long newAccountNumber,
                                                                            Map<Long, Rightsholder> rightsholdersMap) {
        Set<RightsholderDiscrepancy> rightsholderDiscrepancies = Sets.newHashSet();
        usages.forEach(usage -> {
            if (!Objects.equals(usage.getRightsholder().getAccountNumber(), newAccountNumber)) {
                rightsholderDiscrepancies.add(buildRightsholderDiscrepancy(usage, newAccountNumber, rightsholdersMap));
            }
        });
        return rightsholderDiscrepancies;
    }

    private RightsholderDiscrepancy buildRightsholderDiscrepancy(Usage usage, Long newAccountNumber,
                                                                 Map<Long, Rightsholder> rightsholdersMap) {
        RightsholderDiscrepancy rightsholderDiscrepancy = new RightsholderDiscrepancy();
        rightsholderDiscrepancy.setOldRightsholder(usage.getRightsholder());
        rightsholderDiscrepancy.setNewRightsholder(
            rightsholdersMap.computeIfAbsent(newAccountNumber, this::buildRightsholder));
        rightsholderDiscrepancy.setWrWrkInst(usage.getWrWrkInst());
        rightsholderDiscrepancy.setWorkTitle(usage.getWorkTitle());
        rightsholderDiscrepancy.setProductFamily(usage.getProductFamily());
        return rightsholderDiscrepancy;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
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
