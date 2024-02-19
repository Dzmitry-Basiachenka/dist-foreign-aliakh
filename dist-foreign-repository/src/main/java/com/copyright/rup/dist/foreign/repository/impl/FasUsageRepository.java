package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IFasUsageRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/11/2020
 *
 * @author Ihar Suvorau
 */
@Repository
public class FasUsageRepository extends BaseRepository implements IFasUsageRepository {

    private static final long serialVersionUID = -5236504758289576417L;
    /**
     * It's a max value for count of variables in statement.
     */
    private static final int MAX_VARIABLES_COUNT = 32000;
    private static final String SCENARIO_IDS_KEY = "scenarioIds";
    private static final String SCENARIO_STATUS_KEY = "scenarioStatus";
    private static final String ACCOUNT_NUMBERS_KEY = "accountNumbers";
    private static final String UPDATE_USER_KEY = "updateUser";
    private static final String STATUS_KEY = "status";

    @Override
    public void insert(Usage usage) {
        insert("IFasUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public void updateResearchedUsages(List<ResearchedUsage> researchedUsages) {
        Objects.requireNonNull(researchedUsages);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        parameters.put(STATUS_KEY, UsageStatusEnum.WORK_FOUND);
        researchedUsages.forEach(researchedUsage -> {
            parameters.put("usage", researchedUsage);
            update("IFasUsageMapper.updateResearchedUsage", parameters);
        });
    }

    @Override
    public Set<String> deleteFromScenarioByPayees(Set<String> scenarioIds, Set<Long> accountNumbers, String userName) {
        Set<String> result = new HashSet<>();
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put(SCENARIO_IDS_KEY, Objects.requireNonNull(scenarioIds));
        parameters.put(SCENARIO_STATUS_KEY, ScenarioStatusEnum.IN_PROGRESS);
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        Iterables.partition(Objects.requireNonNull(accountNumbers), MAX_VARIABLES_COUNT)
            .forEach(partition -> {
                parameters.put(ACCOUNT_NUMBERS_KEY, partition);
                result.addAll(selectList("IFasUsageMapper.deleteFromScenarioByPayees", parameters));
            });
        return result;
    }

    @Override
    public Set<String> redesignateToNtsWithdrawnByPayees(Set<String> scenarioIds, Set<Long> accountNumbers,
                                                         String userName) {
        Set<String> result = new HashSet<>();
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(6);
        parameters.put(SCENARIO_IDS_KEY, Objects.requireNonNull(scenarioIds));
        parameters.put(SCENARIO_STATUS_KEY, ScenarioStatusEnum.IN_PROGRESS);
        parameters.put(STATUS_KEY, UsageStatusEnum.NTS_WITHDRAWN);
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        Iterables.partition(Objects.requireNonNull(accountNumbers), MAX_VARIABLES_COUNT)
            .forEach(partition -> {
                parameters.put(ACCOUNT_NUMBERS_KEY, partition);
                result.addAll(selectList("IFasUsageMapper.redesignateToNtsWithdrawnByPayees", parameters));
            });
        return result;
    }

    @Override
    public Set<Long> findAccountNumbersInvalidForExclude(Set<String> scenarioIds, Set<Long> accountNumbers) {
        Set<Long> result = new HashSet<>();
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_IDS_KEY, Objects.requireNonNull(scenarioIds));
        parameters.put(SCENARIO_STATUS_KEY, ScenarioStatusEnum.IN_PROGRESS);
        Iterables.partition(Objects.requireNonNull(accountNumbers), MAX_VARIABLES_COUNT)
            .forEach(partition -> {
                parameters.put(ACCOUNT_NUMBERS_KEY, partition);
                result.addAll(selectList("IFasUsageMapper.findAccountNumbersInvalidForExclude", parameters));
            });
        return result;
    }

    @Override
    public List<Usage> findForReconcile(String scenarioId) {
        return selectList("IFasUsageMapper.findForReconcile", Objects.requireNonNull(scenarioId));
    }

    @Override
    public Map<Long, Usage> findRightsholdersInformation(String scenarioId) {
        var handler = new RightsholdersInfoResultHandler();
        getTemplate().select("IFasUsageMapper.findRightsholdersInformation", Objects.requireNonNull(scenarioId),
            handler);
        return handler.getRhToUsageMap();
    }

    @Override
    public List<Usage> findWithAmountsAndRightsholders(UsageFilter filter) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("filter", Objects.requireNonNull(filter));
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        return selectList("IFasUsageMapper.findWithAmountsAndRightsholders", parameters);
    }

    @Override
    public List<String> updateNtsWithdrawnUsagesAndGetIds() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put("productFamilies", FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET);
        params.put("statusToFind", UsageStatusEnum.RH_NOT_FOUND);
        params.put("statusToSet", UsageStatusEnum.NTS_WITHDRAWN);
        params.put("minimumTotal", new BigDecimal("100"));
        params.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        return selectList("IFasUsageMapper.updateNtsWithdrawnUsagesAndGetIds", params);
    }

    @Override
    public void updateUsagesWorkAndStatus(List<String> usageIds, Work work, UsageStatusEnum status, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("work", work);
        params.put(STATUS_KEY, status);
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        Iterables.partition(Objects.requireNonNull(usageIds), MAX_VARIABLES_COUNT).forEach(partition -> {
            params.put("usageIds", partition);
            update("IFasUsageMapper.updateUsagesWorkAndStatus", params);
        });
    }
}
