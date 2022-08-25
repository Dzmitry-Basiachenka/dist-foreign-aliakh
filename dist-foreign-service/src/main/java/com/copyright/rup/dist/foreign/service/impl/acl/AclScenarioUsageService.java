package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IAclScenarioUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Ihar Suvorau
 */
@Service
public class AclScenarioUsageService implements IAclScenarioUsageService {

    @Autowired
    private IAclScenarioUsageRepository aclScenarioUsageRepository;

    @Override
    public void addUsagesToAclScenario(AclScenario aclScenario, String username) {
        aclScenarioUsageRepository.addToAclScenario(aclScenario, username);
    }

    @Override
    public void addScenarioShares(AclScenario aclScenario, String username) {
        aclScenarioUsageRepository.addScenarioShares(aclScenario, username);
    }

    @Override
    public void populatePubTypeWeights(String scenarioId, String userName) {
        aclScenarioUsageRepository.populatePubTypeWeights(scenarioId, userName);
    }

    @Override
    public void calculateScenarioShares(String scenarioId, String username) {
        aclScenarioUsageRepository.calculateScenarioShares(scenarioId, username);
    }

    @Override
    public void calculateScenarioAmounts(String scenarioId, String userName) {
        aclScenarioUsageRepository.calculateScenarioAmounts(scenarioId, userName);
    }

    @Override
    @Transactional
    public void deleteZeroAmountUsages(String scenarioId) {
        aclScenarioUsageRepository.deleteZeroAmountShares(scenarioId);
        aclScenarioUsageRepository.deleteZeroAmountUsages(scenarioId);
    }

    @Override
    public List<AclRightsholderTotalsHolder> getAclRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                         String searchValue,
                                                                                         Pageable pageable, Sort sort) {
        return aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
            scenarioId, searchValue, pageable, sort);
    }

    @Override
    public int getAclRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        return aclScenarioUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(scenarioId, searchValue);
    }

    @Override
    public AclScenarioDto getAclScenarioWithAmountsAndLastAction(String scenarioId) {
        return aclScenarioUsageRepository.findWithAmountsAndLastAction(scenarioId);
    }

    @Override
    public List<AclScenarioDetailDto> getByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                                        String searchValue, Pageable pageable,
                                                                        Sort sort) {
        return aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(accountNumber, scenarioId, searchValue,
            pageable, sort);
    }

    @Override
    public int getCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        return aclScenarioUsageRepository.findCountByScenarioIdAndRhAccountNumber(accountNumber, scenarioId,
            searchValue);
    }

    @Override
    public List<AclScenarioDetailDto> getByScenarioIdAndRhAccountNumberAndTitleAndAggLicClass(
        String scenarioId, Long accountNumber, String title, Integer aggLicClassId) {
        return aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumberAndTitleAndAggLicClass(
            scenarioId, accountNumber, title, aggLicClassId);
    }
}
