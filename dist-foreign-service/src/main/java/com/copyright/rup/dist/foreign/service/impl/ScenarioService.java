package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.service.api.discrepancy.ICommonDiscrepancyService;
import com.copyright.rup.dist.common.service.api.discrepancy.ICommonDiscrepancyService.IDiscrepancyBuilder;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
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

    @Value("$RUP{dist.foreign.discrepancy.partition_size}")
    private int discrepancyPartitionSize;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int batchSize;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private ILmIntegrationService lmIntegrationService;
    @Autowired
    private ICommonDiscrepancyService<Usage, RightsholderDiscrepancy> commonDiscrepancyService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    @Autowired
    private IRightsholderService rightsholderService;

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
    public String getScenarioNameByPreServiceFeeFundId(String fundId) {
        return scenarioRepository.findNameByPreServiceFeeFundId(fundId);
    }

    @Override
    @Transactional
    public Scenario createScenario(String scenarioName, String description, UsageFilter usageFilter) {
        LOGGER.info("Insert scenario. Started. Name={}, Description={}, UsageFilter={}",
            scenarioName, description, usageFilter);
        List<Usage> usages = usageService.getUsagesWithAmounts(usageFilter);
        Scenario scenario = buildScenario(scenarioName, description, usageFilter);
        scenarioRepository.insert(scenario);
        usageService.addUsagesToScenario(usages, scenario);
        scenarioUsageFilterService.insert(scenario.getId(), new ScenarioUsageFilter(usageFilter));
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        LOGGER.info("Insert scenario. Finished. Name={}, Description={}, UsageFilter={}",
            scenarioName, description, usageFilter);
        return scenario;
    }

    @Override
    @Transactional
    public Scenario createNtsScenario(String scenarioName, NtsFields ntsFields, String description,
                                      UsageFilter usageFilter) {
        LOGGER.info("Insert NTS scenario. Started. Name={}, NtsFields={}, Description={}, UsageFilter={}",
            scenarioName, ntsFields, description, usageFilter);
        Scenario scenario = buildNtsScenario(scenarioName, ntsFields, description, usageFilter);
        scenarioRepository.insertNtsScenarioAndAddUsages(scenario, usageFilter);
        usageService.populatePayeeAndCalculateAmountsForNtsScenarioUsages(scenario);
        scenarioUsageFilterService.insert(scenario.getId(), new ScenarioUsageFilter(usageFilter));
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        LOGGER.info("Insert NTS scenario. Finished. Name={}, NtsFields={}, Description={}, UsageFilter={}",
            scenarioName, ntsFields, description, usageFilter);
        return scenario;
    }

    @Override
    @Transactional
    public void deleteScenario(Scenario scenario) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete scenario. Started. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
        String scenarioId = scenario.getId();
        if (FdaConstants.NTS_PRODUCT_FAMILY.equals(scenario.getProductFamily())) {
            usageService.deleteBelletristicByScenarioId(scenarioId);
            usageService.deleteFromNtsScenario(scenarioId);
        } else {
            usageService.deleteFromScenario(scenarioId);
            rightsholderDiscrepancyService.deleteByScenarioId(scenarioId);
        }
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
            usageService.recalculateUsagesForRefresh(new UsageFilter(usageFilter), scenario);
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
        rightsholderDiscrepancyService.deleteByScenarioIdAndStatus(scenario.getId(),
            RightsholderDiscrepancyStatusEnum.DRAFT);
        changeScenarioState(scenario, ScenarioStatusEnum.APPROVED, ScenarioActionTypeEnum.APPROVED, reason);
    }

    @Override
    @Transactional
    public void sendToLm(Scenario scenario) {
        LOGGER.info("Send scenario to LM. Started. {}, User={}", ForeignLogUtils.scenario(scenario),
            RupContextUtils.getUserName());
        List<String> usageIds = usageService.moveToArchive(scenario);
        if (CollectionUtils.isNotEmpty(usageIds)) {
            Iterables.partition(usageIds, batchSize)
                .forEach(partition -> {
                    List<Usage> usages = usageService.getArchivedUsagesByIds(partition);
                    if (FdaConstants.NTS_PRODUCT_FAMILY.equals(scenario.getProductFamily())) {
                        usages.forEach(usage -> {
                            //for NTS usages System should not send work information to LM
                            usage.setWrWrkInst(null);
                            usage.setSystemTitle(null);
                        });
                    }
                    lmIntegrationService.sendToLm(usages.stream().map(ExternalUsage::new).collect(Collectors.toList()));
                });
            changeScenarioState(scenario, ScenarioStatusEnum.SENT_TO_LM, ScenarioActionTypeEnum.SENT_TO_LM,
                StringUtils.EMPTY);
            LOGGER.info("Send scenario to LM. Finished. {}, User={}", ForeignLogUtils.scenario(scenario),
                RupContextUtils.getUserName());
        } else {
            throw new RupRuntimeException(String.format("Send scenario to LM. Failed. %s. Reason=Scenario is empty",
                ForeignLogUtils.scenario(scenario)));
        }
    }

    @Override
    @Transactional
    public void reconcileRightsholders(Scenario scenario) {
        LOGGER.info("Reconcile rightsholders. Started. {}", ForeignLogUtils.scenario(Objects.requireNonNull(scenario)));
        rightsholderDiscrepancyService.deleteByScenarioIdAndStatus(scenario.getId(),
            RightsholderDiscrepancyStatusEnum.DRAFT);
        List<Usage> usagesForReconcile = usageService.getUsagesForReconcile(scenario.getId());
        Map<Long, List<Usage>> groupedByWrWrkInstUsages =
            usagesForReconcile.stream().collect(Collectors.groupingBy(Usage::getWrWrkInst));
        String productFamily = usagesForReconcile.iterator().next().getProductFamily();
        String userName = RupContextUtils.getUserName();
        Iterables.partition(groupedByWrWrkInstUsages.entrySet(), discrepancyPartitionSize).forEach(entries -> {
            List<RightsholderDiscrepancy> discrepancies =
                commonDiscrepancyService.getDiscrepancies(
                    entries.stream().flatMap(entry -> entry.getValue().stream()).collect(Collectors.toList()),
                    Usage::getWrWrkInst, productFamily, new DiscrepancyBuilder(userName));
            if (CollectionUtils.isNotEmpty(discrepancies)) {
                rightsholderDiscrepancyService.insertDiscrepancies(discrepancies, scenario.getId());
                rightsholderService.updateRighstholdersAsync(
                    discrepancies.stream()
                        .map(discrepancy -> discrepancy.getNewRightsholder().getAccountNumber())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()));
            }
        });
        LOGGER.info("Reconcile rightsholders. Finished. {}, RhDiscrepanciesCount={}",
            ForeignLogUtils.scenario(scenario),
            rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(scenario.getId(),
                RightsholderDiscrepancyStatusEnum.DRAFT));
    }

    @Override
    public void updateParticipatingAndAmounts(Scenario scenario) {
        usageService.updateRhPayeeAmountsAndParticipating(usageService.getUsagesByScenarioId(scenario.getId()));
    }

    @Override
    @Transactional
    public void approveOwnershipChanges(Scenario scenario) {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyService.getByScenarioIdAndStatus(
                Objects.requireNonNull(scenario).getId(), RightsholderDiscrepancyStatusEnum.DRAFT, null, null);
        LOGGER.info("Approve Ownership Changes. Started. {}, RhDiscrepanciesCount={}",
            ForeignLogUtils.scenario(scenario), LogUtils.size(discrepancies));
        Map<Long, List<Usage>> groupedByWrWrkInstUsages = usageService.getUsagesForReconcile(scenario.getId())
            .stream()
            .collect(Collectors.groupingBy(Usage::getWrWrkInst));
        Table<String, String, Rightsholder> rollUps = prmIntegrationService.getRollUps(discrepancies.stream()
            .map(discrepancy -> discrepancy.getNewRightsholder().getId())
            .collect(Collectors.toSet()));
        discrepancies.forEach(discrepancy -> {
                Rightsholder newRightsholder = discrepancy.getNewRightsholder();
                Rightsholder payee =
                    PrmRollUpService.getPayee(rollUps, newRightsholder, discrepancy.getProductFamily());
                groupedByWrWrkInstUsages.get(discrepancy.getWrWrkInst()).forEach(usage -> {
                    usage.setRightsholder(newRightsholder);
                    usage.setPayee(payee);
                    usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.RH_UPDATED, String.format(
                        "Rightsholder account %s found during reconciliation", newRightsholder.getAccountNumber()));
                });
            }
        );
        List<Usage> usages = groupedByWrWrkInstUsages.values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        usageService.updateRhPayeeAmountsAndParticipating(usages);
        rightsholderService.updateUsagesPayeesAsync(usages);
        rightsholderDiscrepancyService.approveByScenarioId(scenario.getId());
        LOGGER.info("Approve Ownership Changes. Finished. {}, RhDiscrepanciesCount={}",
            ForeignLogUtils.scenario(scenario), LogUtils.size(discrepancies));
    }

    @Override
    @Transactional
    public int archiveScenarios() {
        List<String> paidScenariosIds = scenarioRepository.findIdsForArchiving();
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

    private Scenario buildNtsScenario(String scenarioName, NtsFields ntsFields, String description,
                                      UsageFilter usageFilter) {
        Scenario scenario = buildScenario(scenarioName, description, usageFilter);
        scenario.setNtsFields(ntsFields);
        return scenario;
    }

    private Scenario buildScenario(String scenarioName, String description, UsageFilter usageFilter) {
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName(scenarioName);
        scenario.setProductFamily(usageFilter.getProductFamilies().iterator().next());
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

    private static class DiscrepancyBuilder implements IDiscrepancyBuilder<Usage, RightsholderDiscrepancy> {

        private final String userName;

        DiscrepancyBuilder(String userName) {
            this.userName = userName;
        }

        @Override
        public Set<RightsholderDiscrepancy> build(List<Usage> usages, Long newAccountNumber,
                                                  Map<Long, Rightsholder> accountNumberToRightsholder) {
            Set<RightsholderDiscrepancy> rightsholderDiscrepancies = new HashSet<>();
            usages.forEach(usage -> {
                if (!Objects.equals(usage.getRightsholder().getAccountNumber(), newAccountNumber)) {
                    rightsholderDiscrepancies.add(
                        buildDiscrepancy(usage, newAccountNumber, accountNumberToRightsholder));
                }
            });
            return rightsholderDiscrepancies;
        }

        private RightsholderDiscrepancy buildDiscrepancy(Usage usage, Long newAccountNumber,
                                                         Map<Long, Rightsholder> accountNumberToRightsholder) {
            RightsholderDiscrepancy discrepancy = new RightsholderDiscrepancy();
            discrepancy.setId(RupPersistUtils.generateUuid());
            discrepancy.setOldRightsholder(usage.getRightsholder());
            discrepancy.setNewRightsholder(
                accountNumberToRightsholder.computeIfAbsent(newAccountNumber, this::buildRightsholder));
            discrepancy.setWrWrkInst(usage.getWrWrkInst());
            discrepancy.setWorkTitle(usage.getWorkTitle());
            discrepancy.setProductFamily(usage.getProductFamily());
            discrepancy.setStatus(RightsholderDiscrepancyStatusEnum.DRAFT);
            discrepancy.setCreateUser(userName);
            discrepancy.setUpdateUser(userName);
            return discrepancy;
        }
    }
}
