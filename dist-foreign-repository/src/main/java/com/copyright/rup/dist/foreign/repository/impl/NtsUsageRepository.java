package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.NtsFundPool;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    private static final String UPDATE_USER_KEY = "updateUser";

    @Override
    public List<String> insertUsages(UsageBatch usageBatch, String userName) {
        Objects.requireNonNull(usageBatch);
        NtsFundPool ntsFundPool = Objects.requireNonNull(usageBatch.getNtsFundPool());
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(13);
        params.put("batchId", Objects.requireNonNull(usageBatch.getId()));
        params.put("marketPeriodFrom", Objects.requireNonNull(ntsFundPool.getFundPoolPeriodFrom()));
        params.put("marketPeriodTo", Objects.requireNonNull(ntsFundPool.getFundPoolPeriodTo()));
        params.put("markets", Objects.requireNonNull(ntsFundPool.getMarkets()));
        params.put("status", UsageStatusEnum.ARCHIVED);
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
        params.put("status", UsageStatusEnum.ARCHIVED);
        params.put("excludeClassification", FdaConstants.BELLETRISTIC_CLASSIFICATION);
        return selectOne("INtsUsageMapper.findCountForBatch", params);
    }

    @Override
    public List<String> updateNtsWithdrawnUsagesAndGetIds() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put("statusToFind", UsageStatusEnum.RH_NOT_FOUND);
        params.put("statusToSet", UsageStatusEnum.NTS_WITHDRAWN);
        params.put("productFamily", FdaConstants.NTS_PRODUCT_FAMILY);
        params.put("minimumTotal", new BigDecimal("100"));
        params.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        return selectList("INtsUsageMapper.updateNtsWithdrawnUsagesAndGetIds", params);
    }

    @Override
    public void calculateAmountsAndUpdatePayeeByAccountNumber(Long rhAccountNumber, String scenarioId,
                                                              BigDecimal serviceFee, boolean rhParticipating,
                                                              Long payeeAccountNumber, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(6);
        params.put("scenarioId", Objects.requireNonNull(scenarioId));
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
    public void deleteFromPreServiceFeeFund(String fundPoolId, String updateUser) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("fundPoolId", Objects.requireNonNull(fundPoolId));
        params.put("status", UsageStatusEnum.NTS_WITHDRAWN);
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(updateUser));
        update("INtsUsageMapper.deleteFromPreServiceFeeFund", params);
    }

    @Override
    public void deleteBelletristicByScenarioId(String usageId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("scenarioId", Objects.requireNonNull(usageId));
        params.put("belletristicClassification", FdaConstants.BELLETRISTIC_CLASSIFICATION);
        delete("INtsUsageMapper.deleteBelletristicByScenarioId", params);
    }

    @Override
    public void deleteFromScenario(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put("scenarioId", Objects.requireNonNull(scenarioId));
        params.put("eligibleStatus", UsageStatusEnum.ELIGIBLE);
        params.put("unclassifiedStatus", UsageStatusEnum.UNCLASSIFIED);
        params.put("statusesToUpdate", Sets.newHashSet(UsageStatusEnum.NTS_EXCLUDED, UsageStatusEnum.LOCKED));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("INtsUsageMapper.deleteFromScenario", params);
    }
}
