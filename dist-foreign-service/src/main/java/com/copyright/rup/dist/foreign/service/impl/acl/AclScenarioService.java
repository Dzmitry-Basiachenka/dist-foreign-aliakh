package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
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
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
        //TODO {dbasiachenka} implement
        return Collections.emptyList();
    }

    @Override
    public int getCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        //TODO {dbasiachenka} implement
        return 0;
    }

    private void populateScenario(AclScenario aclScenario, String userName, String scenarioId) {
        aclScenario.setId(scenarioId);
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setCreateUser(userName);
        aclScenario.setUpdateUser(userName);
    }
}
