package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String ACCOUNT_NUMBER_KEY = "accountNumber";
    private static final String WR_WRK_INST_KEY = "wrWrkInst";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String UPDATE_USER = "updateUser";
    private static final String CREATE_USER = "createUser";
    private static final List<String> ELIGIBLE_GRANT_STATUSES = Arrays.asList("Print&Digital", "Different RH");

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
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IAclScenarioUsageMapper.findByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IAclScenarioUsageMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<AclScenarioDetailDto> findRightsholderDetailsResults(RightsholderResultsFilter filter) {
        Objects.requireNonNull(filter);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(filter.getScenarioId()));
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(filter.getRhAccountNumber()));
        parameters.put(WR_WRK_INST_KEY, Objects.requireNonNull(filter.getWrWrkInst()));
        parameters.put("aggregateLicenseeClassId", Objects.requireNonNull(filter.getAggregateLicenseeClassId()));
        return selectList("IAclScenarioUsageMapper.findRightsholderDetailsResults", parameters);
    }

    @Override
    public List<AclRightsholderTotalsHolderDto> findRightsholderTitleResults(RightsholderResultsFilter filter) {
        Objects.requireNonNull(filter);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(filter.getScenarioId()));
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(filter.getRhAccountNumber()));
        parameters.put("aggregateLicenseeClassId", filter.getAggregateLicenseeClassId());
        return selectList("IAclScenarioUsageMapper.findRightsholderTitleResults", parameters);
    }

    @Override
    public List<AclRightsholderTotalsHolderDto> findRightsholderAggLcClassResults(RightsholderResultsFilter filter) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(filter.getScenarioId()));
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(filter.getRhAccountNumber()));
        parameters.put(WR_WRK_INST_KEY, filter.getWrWrkInst());
        return selectList("IAclScenarioUsageMapper.findAclAggregateLicenseeClassesByRightsholder", parameters);
    }

    @Override
    public void updatePayeeByAccountNumber(Long rhAccountNumber, String scenarioId, Long payeeAccountNumber,
                                           String typeOfUse) {
        //TODO will be implement later
    }
}
