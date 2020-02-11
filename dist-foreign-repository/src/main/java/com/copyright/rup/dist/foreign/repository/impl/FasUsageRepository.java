package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

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

    /**
     * It's a max value for count of variables in statement.
     */
    private static final int MAX_VARIABLES_COUNT = 32000;
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String UPDATE_USER_KEY = "updateUser";
    private static final String STATUS_KEY = "status";
    private static final String PRODUCT_FAMILY_KEY = "productFamily";

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
    public Set<String> deleteFromScenarioByPayees(String scenarioId, Set<Long> accountNumbers, String userName) {
        Set<String> result = new HashSet<>();
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        Iterables.partition(Objects.requireNonNull(accountNumbers), MAX_VARIABLES_COUNT)
            .forEach(partition -> {
                parameters.put("accountNumbers", partition);
                result.addAll(selectList("IFasUsageMapper.deleteFromScenarioByPayees", parameters));
            });
        return result;
    }

    @Override
    public Set<String> redesignateToNtsWithdrawnByPayees(String scenarioId, Set<Long> accountNumbers, String userName) {
        Set<String> result = new HashSet<>();
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(STATUS_KEY, UsageStatusEnum.NTS_WITHDRAWN);
        parameters.put(PRODUCT_FAMILY_KEY, FdaConstants.NTS_PRODUCT_FAMILY);
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        Iterables.partition(Objects.requireNonNull(accountNumbers), MAX_VARIABLES_COUNT)
            .forEach(partition -> {
                parameters.put("accountNumbers", partition);
                result.addAll(selectList("IFasUsageMapper.redesignateToNtsWithdrawnByPayees", parameters));
            });
        return result;
    }
}
