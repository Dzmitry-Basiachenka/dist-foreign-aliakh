package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public List<String> insertUsages(UsageBatch usageBatch, String userName) {
        Objects.requireNonNull(usageBatch);
        FundPool fundPool = Objects.requireNonNull(usageBatch.getFundPool());
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(13);
        params.put("batchId", Objects.requireNonNull(usageBatch.getId()));
        params.put("marketPeriodFrom", Objects.requireNonNull(fundPool.getFundPoolPeriodFrom()));
        params.put("marketPeriodTo", Objects.requireNonNull(fundPool.getFundPoolPeriodTo()));
        params.put("markets", Objects.requireNonNull(fundPool.getMarkets()));
        params.put("status", UsageStatusEnum.ARCHIVED);
        params.put("excludeClassification", FdaConstants.BELLETRISTIC_CLASSIFICATION);
        params.put("fundPoolPeriodDividend", fundPool.getFundPoolPeriodTo() - fundPool.getFundPoolPeriodFrom() + 1);
        params.put("stmMinAmount", fundPool.getStmMinimumAmount());
        params.put("stmAmount", fundPool.getStmAmount());
        params.put("nonStmMinAmount", fundPool.getNonStmMinimumAmount());
        params.put("nonStmAmount", fundPool.getNonStmAmount());
        params.put("createUser", Objects.requireNonNull(userName));
        params.put("updateUser", Objects.requireNonNull(userName));
        return selectList("INtsUsageMapper.insertUsages", params);
    }

    @Override
    public void deleteFromPreServiceFeeFund(String fundPoolId, String updateUser) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("fundPoolId", Objects.requireNonNull(fundPoolId));
        params.put("status", UsageStatusEnum.NTS_WITHDRAWN);
        params.put("updateUser", Objects.requireNonNull(updateUser));
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
        params.put("updateUser", Objects.requireNonNull(userName));
        update("INtsUsageMapper.deleteFromScenario", params);
    }
}
