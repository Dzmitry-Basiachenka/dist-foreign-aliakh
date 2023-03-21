package com.copyright.rup.dist.foreign.service.impl.fas;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.GrantPriority;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.repository.api.IGrantPriorityRepository;
import com.copyright.rup.dist.common.service.api.discrepancy.ICommonDiscrepancyService;
import com.copyright.rup.dist.common.service.api.discrepancy.ICommonDiscrepancyService.IDiscrepancyBuilder;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasScenarioService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IRightsholderDiscrepancyService;

import com.google.common.collect.Iterables;

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
 * Implementation of {@link IFasScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/18/2020
 *
 * @author Ihar Suvorau
 */
@Service
public class FasScenarioService implements IFasScenarioService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("#{$RUP{dist.foreign.rest.rms.rights.statuses}}")
    private Map<String, Set<String>> productFamilyToRightStatusesMap;
    @Value("$RUP{dist.foreign.discrepancy.partition_size}")
    private int discrepancyPartitionSize;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int batchSize;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IFasUsageService fasUsageService;
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
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IGrantPriorityRepository grantPriorityRepository;

    @Override
    @Transactional
    public void sendToLm(Scenario scenario) {
        LOGGER.info("Send scenario to LM. Started. {}, User={}", ForeignLogUtils.scenario(scenario),
            RupContextUtils.getUserName());
        List<String> usageIds = fasUsageService.moveToArchive(scenario);
        if (CollectionUtils.isNotEmpty(usageIds)) {
            Iterables.partition(usageIds, batchSize)
                .forEach(partition ->
                    lmIntegrationService.sendToLm(usageService.getArchivedUsagesForSendToLmByIds(partition)
                        .stream().map(ExternalUsage::new).collect(Collectors.toList()),
                        scenario, usageIds.size()));
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
        List<Usage> usagesForReconcile = fasUsageService.getUsagesForReconcile(scenario.getId());
        Map<Long, List<Usage>> groupedByWrWrkInstUsages =
            usagesForReconcile.stream().collect(Collectors.groupingBy(Usage::getWrWrkInst));
        String productFamily = usagesForReconcile.iterator().next().getProductFamily();
        String userName = RupContextUtils.getUserName();
        Set<String> licenseTypes = grantPriorityRepository.findByProductFamily(productFamily).stream()
            .map(GrantPriority::getLicenseType)
            .collect(Collectors.toSet());
        Iterables.partition(groupedByWrWrkInstUsages.entrySet(), discrepancyPartitionSize).forEach(entries -> {
            List<RightsholderDiscrepancy> discrepancies =
                commonDiscrepancyService.getDiscrepancies(
                    entries.stream().flatMap(entry -> entry.getValue().stream()).collect(Collectors.toList()),
                    Usage::getWrWrkInst, productFamily, productFamilyToRightStatusesMap.get(productFamily),
                    Set.of(), licenseTypes, new DiscrepancyBuilder(userName));
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
    public void updateRhPayeeParticipating(Scenario scenario) {
        fasUsageService.updateRhPayeeAmountsAndParticipating(usageService.getUsagesByScenarioId(scenario.getId()));
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
    public void approveOwnershipChanges(Scenario scenario) {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyService.getByScenarioIdAndStatus(
                Objects.requireNonNull(scenario).getId(), RightsholderDiscrepancyStatusEnum.DRAFT, null, null);
        LOGGER.info("Approve Ownership Changes. Started. {}, RhDiscrepanciesCount={}",
            ForeignLogUtils.scenario(scenario), LogUtils.size(discrepancies));
        Map<Long, List<Usage>> groupedByWrWrkInstUsages = fasUsageService.getUsagesForReconcile(scenario.getId())
            .stream()
            .collect(Collectors.groupingBy(Usage::getWrWrkInst));
        Map<String, Map<String, Rightsholder>> rollUps = prmIntegrationService.getRollUps(discrepancies.stream()
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
        });
        List<Usage> usages = groupedByWrWrkInstUsages.values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        fasUsageService.updateRhPayeeAmountsAndParticipating(usages);
        rightsholderService.updateUsagesPayeesAsync(usages);
        rightsholderDiscrepancyService.approveByScenarioId(scenario.getId());
        LOGGER.info("Approve Ownership Changes. Finished. {}, RhDiscrepanciesCount={}",
            ForeignLogUtils.scenario(scenario), LogUtils.size(discrepancies));
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
            discrepancy.setWorkTitle(usage.getSystemTitle());
            discrepancy.setProductFamily(usage.getProductFamily());
            discrepancy.setStatus(RightsholderDiscrepancyStatusEnum.DRAFT);
            discrepancy.setCreateUser(userName);
            discrepancy.setUpdateUser(userName);
            return discrepancy;
        }
    }
}
