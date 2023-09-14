package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import com.google.common.collect.Iterables;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IAclScenarioService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Dzmitry Basiachenka
 */
@Service
public class AclScenarioService implements IAclScenarioService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int batchSize;
    @Autowired
    private IAclScenarioRepository aclScenarioRepository;
    @Autowired
    private IAclScenarioAuditService aclScenarioAuditService;
    @Autowired
    private IAclScenarioUsageService aclScenarioUsageService;
    @Autowired
    private IAclFundPoolService aclFundPoolService;
    @Autowired
    private IAclUsageService aclUsageService;
    @Autowired
    private ILmIntegrationService lmIntegrationService;

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void insertScenario(AclScenario aclScenario) {
        String scenarioId = RupPersistUtils.generateUuid();
        String userName = RupContextUtils.getUserName();
        populateScenario(aclScenario, userName, scenarioId);
        LOGGER.info("Insert ACL scenario. Started. {}, Description={}, User={}",
            ForeignLogUtils.aclScenario(aclScenario), aclScenario.getDescription(), userName);
        aclScenarioRepository.insertAclScenario(aclScenario);
        insertAclScenarioLicenseeClasses(aclScenario.getDetailLicenseeClasses(), scenarioId, userName);
        insertAclScenarioPubTypeWeights(aclScenario.getPublicationTypes(), scenarioId, userName);
        insertAclScenarioUsageAgeWeights(aclScenario.getUsageAges(), scenarioId, userName);
        aclScenarioAuditService.logAction(scenarioId, ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        aclScenarioUsageService.addUsagesToAclScenario(aclScenario, userName);
        aclScenarioUsageService.populatePubTypeWeights(scenarioId, userName);
        aclScenarioUsageService.addScenarioShares(aclScenario, userName);
        aclScenarioUsageService.calculateScenarioShares(scenarioId, userName);
        aclScenarioUsageService.calculateScenarioAmounts(scenarioId, userName);
        aclScenarioUsageService.deleteZeroAmountUsages(scenarioId);
        aclScenarioUsageService.populatePayees(scenarioId);
        LOGGER.info("Insert ACL scenario. Finished. ScenarioName={}, Description={}, UserName={}",
            aclScenario.getName(), aclScenario.getDescription(), userName);
    }

    @Override
    @Transactional
    public void insertAclScenarioUsageAgeWeights(List<UsageAge> usageAges, String scenarioId, String userName) {
        usageAges.forEach(
            usageAge -> aclScenarioRepository.insertAclScenarioUsageAgeWeight(usageAge, scenarioId, userName));
    }

    @Override
    @Transactional
    public void insertAclScenarioLicenseeClasses(List<DetailLicenseeClass> licenseeClasses, String scenarioId,
                                                 String userName) {
        licenseeClasses.forEach(
            licenseeClass -> aclScenarioRepository.insertAclScenarioLicenseeClass(licenseeClass, scenarioId, userName));
    }

    @Override
    @Transactional
    public void insertAclScenarioPubTypeWeights(List<AclPublicationType> publicationTypes, String scenarioId,
                                                String userName) {
        publicationTypes.forEach(
            publicationType -> aclScenarioRepository.insertAclScenarioPubTypeWeight(publicationType, scenarioId,
                userName));
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public List<AclScenario> getScenarios(AclScenarioFilter filter) {
        return aclScenarioRepository.findByFilter(filter);
    }

    @Override
    public List<AclScenario> getScenariosByPeriod(Integer period) {
        return aclScenarioRepository.findByPeriod(period);
    }

    @Override
    public List<Integer> getScenarioPeriods() {
        return aclScenarioRepository.findPeriods();
    }

    @Override
    public boolean aclScenarioExists(String scenarioName) {
        return 0 < aclScenarioRepository.findCountByName(scenarioName);
    }

    @Override
    public List<String> getScenarioNamesByUsageBatchId(String usageBatchId) {
        return aclScenarioRepository.findScenarioNamesByUsageBatchId(usageBatchId);
    }

    @Override
    public List<String> getScenarioNamesByFundPoolId(String fundPoolId) {
        return aclScenarioRepository.findScenarioNamesByFundPoolId(fundPoolId);
    }

    @Override
    public List<String> getScenarioNamesByGrantSetId(String grantSetId) {
        return aclScenarioRepository.findScenarioNamesByGrantSetId(grantSetId);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public Set<AclFundPoolDetailDto> getFundPoolDetailsNotToBeDistributed(String batchId, String fundPoolId,
                                                                          String grantSetId,
                                                                          List<DetailLicenseeClass> mapping) {
        List<AclFundPoolDetailDto> fundPoolDetails = aclFundPoolService.getDetailDtosByFundPoolId(fundPoolId);
        Map<Integer, Set<Integer>> aggregateToDetailClassIds = mapping.stream()
            .collect(Collectors.groupingBy(detailClass -> detailClass.getAggregateLicenseeClass().getId(),
                Collectors.mapping(DetailLicenseeClass::getId, Collectors.toSet())));
        Map<Integer, Integer> detailToAggregateClassIds = mapping.stream()
            .collect(Collectors.toMap(BaseEntity::getId, detail -> detail.getAggregateLicenseeClass().getId(),
                (agg1, agg2) -> agg1));
        Collection<AclFundPoolDetailDto> uniqueFundPoolDetailsByAggClass = fundPoolDetails.stream()
            .collect(
                Collectors.toMap(detail -> Pair.of(detail.getAggregateLicenseeClass().getId(), detail.getTypeOfUse()),
                    Function.identity(), (detail1, detail2) -> detail1))
            .values();
        return uniqueFundPoolDetailsByAggClass.stream()
            .filter(detail -> !aclUsageService.usageExistForLicenseeClassesAndTypeOfUse(batchId, grantSetId,
                aggregateToDetailClassIds.get(detailToAggregateClassIds.get(detail.getDetailLicenseeClass().getId())),
                detail.getTypeOfUse()))
            .sorted(Comparator.comparing(detail -> detail.getAggregateLicenseeClass().getId()))
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public AclScenario getScenarioById(String scenarioId) {
        return aclScenarioRepository.findById(scenarioId);
    }

    @Override
    public List<UsageAge> getUsageAgeWeightsByScenarioId(String scenarioId) {
        return aclScenarioRepository.findUsageAgeWeightsByScenarioId(scenarioId);
    }

    @Override
    public List<AclPublicationType> getAclPublicationTypesByScenarioId(String scenarioId) {
        return aclScenarioRepository.findAclPublicationTypesByScenarioId(scenarioId);
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClassesByScenarioId(String scenarioId) {
        return aclScenarioRepository.findDetailLicenseeClassesByScenarioId(scenarioId);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteAclScenario(AclScenario aclScenario) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete ACL scenario. Started. {}, User={}", ForeignLogUtils.aclScenario(aclScenario), userName);
        String scenarioId = aclScenario.getId();
        aclScenarioAuditService.deleteActions(scenarioId);
        aclScenarioRepository.deleteScenarioData(scenarioId);
        aclScenarioRepository.deleteScenario(scenarioId);
        LOGGER.info("Delete ACL scenario. Finished. {}, User={}", ForeignLogUtils.aclScenario(aclScenario), userName);
    }

    @Override
    public List<Scenario> getAclScenariosByStatuses(Set<ScenarioStatusEnum> statuses) {
        return aclScenarioRepository.findAclScenariosByStatuses(statuses);
    }

    @Override
    public boolean isNotExistsSubmittedScenario(AclScenario selectedScenario) {
        return !aclScenarioRepository.submittedScenarioExistWithLicenseTypeAndPeriod(selectedScenario.getLicenseType(),
            selectedScenario.getPeriodEndDate());
    }

    @Override
    @Transactional
    public void changeScenarioState(AclScenario scenario, ScenarioStatusEnum status, ScenarioActionTypeEnum action,
                                    String reason) {
        String userName = RupContextUtils.getUserName();
        scenario.setStatus(status);
        scenario.setUpdateUser(userName);
        LOGGER.info("Change scenario status. {}, User={}, Reason={}", ForeignLogUtils.aclScenario(scenario), userName,
            reason);
        aclScenarioRepository.updateStatus(scenario);
        aclScenarioAuditService.logAction(scenario.getId(), action, reason);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void sendToLm(AclScenario scenario) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Send ACL scenario to LM. Started. {}, User={}", ForeignLogUtils.aclScenario(scenario), userName);
        List<AclScenarioLiabilityDetail> liabilityDetails =
            aclScenarioUsageService.getLiabilityDetailsForSendToLmByIds(scenario.getId());
        Iterables.partition(liabilityDetails, batchSize)
            .forEach(partition -> lmIntegrationService.sendToLm(partition.stream()
                .map(ExternalUsage::new)
                .collect(Collectors.toList()),
                scenario.getId(), scenario.getName(), FdaConstants.ACL_PRODUCT_FAMILY, liabilityDetails.size()));
        aclScenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.SENT_TO_LM, StringUtils.EMPTY);
        changeScenarioState(scenario, ScenarioStatusEnum.ARCHIVED, ScenarioActionTypeEnum.ARCHIVED, StringUtils.EMPTY);
        LOGGER.info("Send ACL scenario to LM. Finished. {}, User={}", ForeignLogUtils.aclScenario(scenario),
            userName);
    }

    private void populateScenario(AclScenario aclScenario, String userName, String scenarioId) {
        aclScenario.setId(scenarioId);
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setCreateUser(userName);
        aclScenario.setUpdateUser(userName);
    }
}
