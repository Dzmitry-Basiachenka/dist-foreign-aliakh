package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of {@link INtsUsageRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class NtsUsageRepository extends BaseRepository implements INtsUsageRepository {

    private static final long serialVersionUID = -8130442869624704626L;
    /**
     * It's a max value for count of variables in statement.
     */
    private static final int MAX_VARIABLES_COUNT = 32000;
    private static final String PRODUCT_FAMILY_KEY = "productFamily";
    private static final String UPDATE_USER_KEY = "updateUser";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String STATUS_KEY = "status";

    @Override
    public List<String> insertUsages(UsageBatch usageBatch, String userName) {
        Objects.requireNonNull(usageBatch);
        NtsFields ntsFundPool = Objects.requireNonNull(usageBatch.getNtsFields());
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(13);
        params.put("batchId", Objects.requireNonNull(usageBatch.getId()));
        params.put("marketPeriodFrom", Objects.requireNonNull(ntsFundPool.getFundPoolPeriodFrom()));
        params.put("marketPeriodTo", Objects.requireNonNull(ntsFundPool.getFundPoolPeriodTo()));
        params.put("markets", Objects.requireNonNull(ntsFundPool.getMarkets()));
        params.put(STATUS_KEY, UsageStatusEnum.ARCHIVED);
        params.put("excludeClassification", FdaConstants.BELLETRISTIC_CLASSIFICATION);
        params.put("fundPoolPeriodDividend",
            ntsFundPool.getFundPoolPeriodTo() - ntsFundPool.getFundPoolPeriodFrom() + 1);
        params.put("stmMinAmount", ntsFundPool.getStmMinimumAmount());
        params.put("stmAmount", ntsFundPool.getStmAmount());
        params.put("nonStmMinAmount", ntsFundPool.getNonStmMinimumAmount());
        params.put("nonStmAmount", ntsFundPool.getNonStmAmount());
        params.put("createUser", Objects.requireNonNull(userName));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        return selectList("INtsUsageMapper.insertUsages", params);
    }

    @Override
    public int findCountForBatch(Integer marketPeriodFrom, Integer marketPeriodTo, Set<String> markets) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put("marketPeriodFrom", Objects.requireNonNull(marketPeriodFrom));
        params.put("marketPeriodTo", Objects.requireNonNull(marketPeriodTo));
        params.put("markets", Objects.requireNonNull(markets));
        params.put(STATUS_KEY, UsageStatusEnum.ARCHIVED);
        params.put("excludeClassification", FdaConstants.BELLETRISTIC_CLASSIFICATION);
        return selectOne("INtsUsageMapper.findCountForBatch", params);
    }

    @Override
    public int findUnclassifiedUsagesCountByWrWrkInsts(Set<Long> wrWrkInsts) {
        AtomicInteger count = new AtomicInteger(0);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(STATUS_KEY, UsageStatusEnum.UNCLASSIFIED);
        Iterables.partition(Objects.requireNonNull(wrWrkInsts), MAX_VARIABLES_COUNT)
            .forEach(partition -> {
                parameters.put("wrWrkInsts", partition);
                count.addAndGet(
                    selectOne("INtsUsageMapper.findUnclassifiedUsagesCountByWrWrkInsts", parameters));
            });
        return count.get();
    }

    @Override
    public void calculateAmountsAndUpdatePayeeByAccountNumber(Long rhAccountNumber, String scenarioId,
                                                              BigDecimal serviceFee, boolean rhParticipating,
                                                              Long payeeAccountNumber, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(6);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("rhAccountNumber", Objects.requireNonNull(rhAccountNumber));
        params.put("payeeAccountNumber", Objects.requireNonNull(payeeAccountNumber));
        params.put("serviceFee", Objects.requireNonNull(serviceFee));
        params.put("rhParticipating", rhParticipating);
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("INtsUsageMapper.calculateAmountsAndUpdatePayeeByAccountNumber", params);
    }

    @Override
    public void applyPostServiceFeeAmount(String scenarioId) {
        update("INtsUsageMapper.applyPostServiceFeeAmount", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(STATUS_KEY, UsageStatusEnum.SCENARIO_EXCLUDED);
        delete("INtsUsageMapper.deleteByScenarioId", parameters);
    }

    @Override
    public void deleteFromNtsFundPool(String fundPoolId, String updateUser) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("fundPoolId", Objects.requireNonNull(fundPoolId));
        params.put(STATUS_KEY, UsageStatusEnum.NTS_WITHDRAWN);
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(updateUser));
        update("INtsUsageMapper.deleteFromNtsFundPool", params);
    }

    @Override
    public void deleteBelletristicByScenarioId(String usageId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(usageId));
        params.put("belletristicClassification", FdaConstants.BELLETRISTIC_CLASSIFICATION);
        delete("INtsUsageMapper.deleteBelletristicByScenarioId", params);
    }

    @Override
    public void deleteFromScenario(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("eligibleStatus", UsageStatusEnum.ELIGIBLE);
        params.put("unclassifiedStatus", UsageStatusEnum.UNCLASSIFIED);
        params.put("statusesToUpdate", Set.of(UsageStatusEnum.SCENARIO_EXCLUDED, UsageStatusEnum.LOCKED));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("INtsUsageMapper.deleteFromScenario", params);
    }

    @Override
    public List<String> findUsageIdsForClassificationUpdate() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("unclassifiedStatus", UsageStatusEnum.UNCLASSIFIED);
        params.put("belletristicClassification", FdaConstants.BELLETRISTIC_CLASSIFICATION);
        params.put("eligibleStatus", UsageStatusEnum.ELIGIBLE);
        params.put(PRODUCT_FAMILY_KEY, FdaConstants.NTS_PRODUCT_FAMILY);
        return selectList("INtsUsageMapper.findUsageIdsForClassificationUpdate", params);
    }

    @Override
    public void updateUsagesStatusToUnclassified(List<Long> wrWrkInsts, String userName) {
        checkArgument(CollectionUtils.isNotEmpty(wrWrkInsts));
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put("statusToFind", UsageStatusEnum.ELIGIBLE);
        params.put("statusToSet", UsageStatusEnum.UNCLASSIFIED);
        params.put(PRODUCT_FAMILY_KEY, FdaConstants.NTS_PRODUCT_FAMILY);
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        Iterables.partition(wrWrkInsts, MAX_VARIABLES_COUNT).forEach(partition -> {
            params.put("wrWrkInsts", Objects.requireNonNull(partition));
            update("INtsUsageMapper.updateUsagesStatusToUnclassified", params);
        });
    }

    @Override
    public void addWithdrawnUsagesToNtsFundPool(String fundPoolId, Set<String> batchIds, String userName) {
        checkArgument(StringUtils.isNotBlank(fundPoolId));
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put("fundPoolId", fundPoolId);
        params.put("statusToFind", UsageStatusEnum.NTS_WITHDRAWN);
        params.put("statusToSet", UsageStatusEnum.TO_BE_DISTRIBUTED);
        params.put("batchIds", Objects.requireNonNull(batchIds));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("INtsUsageMapper.addWithdrawnUsagesToNtsFundPool", params);
    }

    @Override
    public Set<String> deleteFromScenarioByRightsholder(String scenarioId, Set<Long> accountNumbers, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("accountNumbers", Objects.requireNonNull(accountNumbers));
        params.put(STATUS_KEY, UsageStatusEnum.SCENARIO_EXCLUDED);
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        return new HashSet<>(selectList("INtsUsageMapper.deleteFromScenarioByRightsholder", params));
    }

    @Override
    public void recalculateAmountsFromExcludedRightshoders(String scenarioId, Set<Long> accountNumbers) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("accountNumbers", Objects.requireNonNull(accountNumbers));
        update("INtsUsageMapper.recalculateAmountsFromExcludedRightshoders", params);
    }
}
