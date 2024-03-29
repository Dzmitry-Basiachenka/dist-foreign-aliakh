package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IAclScenarioUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/22/2022
 *
 * @author Mikita Maistrenka
 */
@Repository
public class AclScenarioUsageRepository extends AclBaseRepository implements IAclScenarioUsageRepository {

    private static final long serialVersionUID = 5108317885409637331L;
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String ACCOUNT_NUMBER_KEY = "accountNumber";
    private static final String WR_WRK_INST_KEY = "wrWrkInst";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String UPDATE_USER = "updateUser";
    private static final String CREATE_USER = "createUser";
    private static final List<String> ELIGIBLE_GRANT_STATUSES = List.of("Print&Digital", "Different RH");

    @Override
    public void addToAclScenario(AclScenario scenario, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("scenario", Objects.requireNonNull(scenario));
        params.put("grantStatuses", ELIGIBLE_GRANT_STATUSES);
        params.put(CREATE_USER, Objects.requireNonNull(userName));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        insert("IAclScenarioUsageMapper.addToScenario", params);
    }

    @Override
    public void addScenarioShares(AclScenario scenario, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("scenario", Objects.requireNonNull(scenario));
        params.put("grantStatuses", ELIGIBLE_GRANT_STATUSES);
        params.put(CREATE_USER, Objects.requireNonNull(userName));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        insert("IAclScenarioUsageMapper.addScenarioShares", params);
    }

    @Override
    public void populatePubTypeWeights(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        update("IAclScenarioUsageMapper.populatePubTypeWeights", params);
    }

    @Override
    public void calculateScenarioShares(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        update("IAclScenarioUsageMapper.calculateScenarioShares", params);
    }

    @Override
    public void calculateScenarioAmounts(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER, Objects.requireNonNull(userName));
        update("IAclScenarioUsageMapper.calculateScenarioAmounts", params);
    }

    @Override
    public void deleteZeroAmountShares(String scenarioId) {
        delete("IAclScenarioUsageMapper.deleteZeroAmountShares", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void deleteZeroAmountUsages(String scenarioId) {
        delete("IAclScenarioUsageMapper.deleteZeroAmountUsages", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<AclScenarioDetail> findScenarioDetailsByScenarioId(String scenarioId) {
        return selectList("IAclScenarioUsageMapper.findScenarioDetailsByScenarioId", scenarioId);
    }

    @Override
    public List<AclRightsholderTotalsHolder> findAclRightsholderTotalsHoldersByScenarioId(String scenarioId) {
        return selectList("IAclScenarioUsageMapper.findAclRightsholderTotalsHoldersByScenarioId",
            Objects.requireNonNull(scenarioId));
    }

    @Override
    public AclScenarioDto findWithAmountsAndLastAction(String scenarioId) {
        return selectOne("IAclScenarioUsageMapper.findWithAmountsAndLastAction", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<AclScenarioDetailDto> findByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                                         String searchValue, Pageable pageable,
                                                                         Sort sort) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        params.put(PAGEABLE_KEY, pageable);
        params.put(SORT_KEY, sort);
        return selectList("IAclScenarioUsageMapper.findByScenarioIdAndRhAccountNumber", params);
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IAclScenarioUsageMapper.findCountByScenarioIdAndRhAccountNumber", params);
    }

    @Override
    public List<AclScenarioDetailDto> findByScenarioId(String scenarioId, String searchValue, Pageable pageable,
                                                       Sort sort) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        params.put(PAGEABLE_KEY, pageable);
        params.put(SORT_KEY, sort);
        return selectList("IAclScenarioUsageMapper.findByScenarioId", params);
    }

    @Override
    public int findCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IAclScenarioUsageMapper.findCountByScenarioId", params);
    }

    @Override
    public List<AclScenarioDetailDto> findRightsholderDetailsResults(RightsholderResultsFilter filter) {
        Objects.requireNonNull(filter);
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(filter.getScenarioId()));
        params.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(filter.getRhAccountNumber()));
        params.put(WR_WRK_INST_KEY, Objects.requireNonNull(filter.getWrWrkInst()));
        params.put("aggregateLicenseeClassId", Objects.requireNonNull(filter.getAggregateLicenseeClassId()));
        return selectList("IAclScenarioUsageMapper.findRightsholderDetailsResults", params);
    }

    @Override
    public List<AclRightsholderTotalsHolderDto> findRightsholderTitleResults(RightsholderResultsFilter filter) {
        Objects.requireNonNull(filter);
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(filter.getScenarioId()));
        params.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(filter.getRhAccountNumber()));
        params.put("aggregateLicenseeClassId", filter.getAggregateLicenseeClassId());
        return selectList("IAclScenarioUsageMapper.findRightsholderTitleResults", params);
    }

    @Override
    public List<AclRightsholderTotalsHolderDto> findRightsholderAggLcClassResults(RightsholderResultsFilter filter) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(filter.getScenarioId()));
        params.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(filter.getRhAccountNumber()));
        params.put(WR_WRK_INST_KEY, filter.getWrWrkInst());
        return selectList("IAclScenarioUsageMapper.findAclAggregateLicenseeClassesByRightsholder", params);
    }

    @Override
    public List<RightsholderPayeeProductFamilyHolder> findRightsholderPayeeProductFamilyHoldersByAclScenarioIds(
        Set<String> scenarioIds) {
        return selectList("IAclScenarioUsageMapper.findRightsholderPayeeProductFamilyHoldersByAclScenarioIds",
            Objects.requireNonNull(scenarioIds));
    }

    @Override
    public List<AclScenarioLiabilityDetail> findForSendToLmByScenarioId(String scenarioId) {
        return selectList("IAclScenarioUsageMapper.findForSendToLmByScenarioId", scenarioId);
    }
}
