package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    @Transactional
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
    public List<AclScenario> getScenarios() {
        return aclScenarioRepository.findAll();
    }

    @Override
    public boolean aclScenarioExists(String scenarioName) {
        return 0 < aclScenarioRepository.findCountByName(scenarioName);
    }

    @Override
    public AclScenarioDto getAclScenarioWithAmountsAndLastAction(String scenarioId) {
        return aclScenarioRepository.findWithAmountsAndLastAction(scenarioId);
    }

    @Override
    public List<AclScenarioDetailDto> getByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                                        String searchValue, Pageable pageable,
                                                                        Sort sort) {
        return aclScenarioRepository.findByScenarioIdAndRhAccountNumber(accountNumber, scenarioId, searchValue,
            pageable, sort);
    }

    @Override
    public int getCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        return aclScenarioRepository.findCountByScenarioIdAndRhAccountNumber(accountNumber, scenarioId, searchValue);
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
    public Set<AclFundPoolDetailDto> getFundPoolDetailsNotToBeDistributed(String batchId, String fundPoolId,
                                                                          String grantSetId,
                                                                          List<DetailLicenseeClass> mapping) {
        List<AclFundPoolDetailDto> fundPoolDetails = aclFundPoolService.getDetailDtosByFundPoolId(fundPoolId);
        Map<Integer, Set<Integer>> aggregateToDetailClassIds = mapping.stream()
            .collect(Collectors.groupingBy(detailClass -> detailClass.getAggregateLicenseeClass().getId(),
                Collectors.mapping(DetailLicenseeClass::getId, Collectors.toSet())));
        Set<AclFundPoolDetailDto> unmappedFundPoolDetails = fundPoolDetails.stream()
            .filter(detail -> !aggregateToDetailClassIds.containsKey(detail.getAggregateLicenseeClass().getId()))
            .collect(Collectors.toSet());
        Set<AclFundPoolDetailDto> absentFundPoolDetails = fundPoolDetails.stream()
            .filter(detail -> aggregateToDetailClassIds.containsKey(detail.getAggregateLicenseeClass().getId()))
            .filter(detail -> !aclUsageService.usageExistForLicenseeClassesAndTypeOfUse(batchId, grantSetId,
                aggregateToDetailClassIds.get(detail.getAggregateLicenseeClass().getId()), detail.getTypeOfUse())
            ).collect(Collectors.toSet());
        Collection<AclFundPoolDetailDto> invalidDetails = Sets.union(unmappedFundPoolDetails, absentFundPoolDetails)
            .stream()
            .collect(Collectors.toMap(
                detail ->
                    Pair.of(detail.getAggregateLicenseeClass().getId(), detail.getTypeOfUse()), Function.identity(),
                (detail1, detail2) -> detail1))
            .values();
        return invalidDetails.stream()
            .sorted(Comparator.comparing(detail -> detail.getAggregateLicenseeClass().getId()))
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void populateScenario(AclScenario aclScenario, String userName, String scenarioId) {
        aclScenario.setId(scenarioId);
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setCreateUser(userName);
        aclScenario.setUpdateUser(userName);
    }
}
