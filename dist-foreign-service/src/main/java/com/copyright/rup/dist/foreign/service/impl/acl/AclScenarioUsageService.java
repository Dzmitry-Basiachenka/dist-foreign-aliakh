package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderTypeOfUsePair;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

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

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IAclScenarioUsageRepository aclScenarioUsageRepository;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void addUsagesToAclScenario(AclScenario aclScenario, String username) {
        aclScenarioUsageRepository.addToAclScenario(aclScenario, username);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void addScenarioShares(AclScenario aclScenario, String username) {
        aclScenarioUsageRepository.addScenarioShares(aclScenario, username);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void populatePubTypeWeights(String scenarioId, String userName) {
        aclScenarioUsageRepository.populatePubTypeWeights(scenarioId, userName);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void calculateScenarioShares(String scenarioId, String username) {
        aclScenarioUsageRepository.calculateScenarioShares(scenarioId, username);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void calculateScenarioAmounts(String scenarioId, String userName) {
        aclScenarioUsageRepository.calculateScenarioAmounts(scenarioId, userName);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteZeroAmountUsages(String scenarioId) {
        aclScenarioUsageRepository.deleteZeroAmountShares(scenarioId);
        aclScenarioUsageRepository.deleteZeroAmountUsages(scenarioId);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public List<AclRightsholderTotalsHolder> getAclRightsholderTotalsHoldersByScenarioId(String scenarioId) {
        return aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(scenarioId);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public AclScenarioDto getAclScenarioWithAmountsAndLastAction(String scenarioId) {
        return aclScenarioUsageRepository.findWithAmountsAndLastAction(scenarioId);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public List<AclScenarioDetailDto> getByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                                        String searchValue, Pageable pageable,
                                                                        Sort sort) {
        return aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(accountNumber, scenarioId, searchValue,
            pageable, sort);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int getCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        return aclScenarioUsageRepository.findCountByScenarioIdAndRhAccountNumber(accountNumber, scenarioId,
            searchValue);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public List<AclScenarioDetailDto> getByScenarioId(String scenarioId, String searchValue, Pageable pageable,
                                                      Sort sort) {
        return aclScenarioUsageRepository.findByScenarioId(scenarioId, searchValue, pageable, sort);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int getCountByScenarioId(String scenarioId, String searchValue) {
        return aclScenarioUsageRepository.findCountByScenarioId(scenarioId, searchValue);
    }

    @Override
    public List<AclScenarioDetailDto> getRightsholderDetailsResults(RightsholderResultsFilter filter) {
        return aclScenarioUsageRepository.findRightsholderDetailsResults(filter);
    }

    @Override
    public List<AclRightsholderTotalsHolderDto> getRightsholderTitleResults(RightsholderResultsFilter filter) {
        return aclScenarioUsageRepository.findRightsholderTitleResults(filter);
    }

    @Override
    public List<AclRightsholderTotalsHolderDto> getRightsholderAggLcClassResults(RightsholderResultsFilter filter) {
        return aclScenarioUsageRepository.findRightsholderAggLcClassResults(filter);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void populatePayees(String scenarioId) {
        Set<Long> payeeAccountNumbers = new HashSet<>();
        List<RightsholderTypeOfUsePair> rightsholderTypeOfUsePairs = rightsholderService.getByAclScenarioId(scenarioId);
        Set<String> rightsholdersIds =
            rightsholderTypeOfUsePairs.stream().map(pair -> pair.getRightsholder().getId()).collect(Collectors.toSet());
        LOGGER.info("Get Payees for ACL Scenario. Started. ScenarioId={}, RightsholdersIdsCount={}", scenarioId,
            rightsholdersIds.size());
        Map<String, Map<String, Rightsholder>> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        rightsholderTypeOfUsePairs.forEach(pair -> {
            Long payeeAccountNumber = PrmRollUpService.getPayee(
                rollUps, pair.getRightsholder(), FdaConstants.ACL_PRODUCT_FAMILY + pair.getTypeOfUse()
            ).getAccountNumber();
            payeeAccountNumbers.add(payeeAccountNumber);
            aclScenarioUsageRepository.updatePayeeByAccountNumber(pair.getRightsholder().getAccountNumber(), scenarioId,
                payeeAccountNumber, pair.getTypeOfUse());
        });
        rightsholderService.updateRighstholdersAsync(payeeAccountNumbers);
        LOGGER.info("Get Payees for ACL Scenario. Finished. ScenarioId={}, RightsholdersIdsCount={}", scenarioId,
            rightsholdersIds.size());
    }

    @Override
    public List<RightsholderPayeeProductFamilyHolder> getRightsholderPayeeProductFamilyHoldersByAclScenarioIds(
        Set<String> scenarioIds) {
        return aclScenarioUsageRepository.findRightsholderPayeeProductFamilyHoldersByAclScenarioIds(scenarioIds);
    }

    @Override
    public List<AclScenarioLiabilityDetail> getLiabilityDetailsForSendToLmByIds(String scenarioId) {
        return aclScenarioUsageRepository.findForSendToLmByScenarioId(scenarioId);
    }
}
