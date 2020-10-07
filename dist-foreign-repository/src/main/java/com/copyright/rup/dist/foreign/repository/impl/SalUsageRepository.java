package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.GradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link ISalUsageRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class SalUsageRepository extends BaseRepository implements ISalUsageRepository {

    private static final int MAX_VARIABLES_COUNT = 32000;
    private static final String DETAIL_TYPE_KEY = "detailType";
    private static final String FILTER_KEY = "filter";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String UPDATE_USER_KEY = "updateUser";

    @Override
    public void insertItemBankDetail(Usage usage) {
        insert("ISalUsageMapper.insertItemBankDetail", Objects.requireNonNull(usage));
    }

    @Override
    public void insertUsageDataDetail(Usage usage) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("usage", Objects.requireNonNull(usage));
        parameters.put("createUser", Objects.requireNonNull(usage.getCreateUser()));
        parameters.put("updateUser", usage.getCreateUser());
        parameters.put("itemBankDetailType", SalDetailTypeEnum.IB);
        insert("ISalUsageMapper.insertUsageDataDetail", parameters);
    }

    @Override
    public List<Usage> findByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), MAX_VARIABLES_COUNT)
            .forEach(partition -> result.addAll(selectList("ISalUsageMapper.findByIds", partition)));
        return result;
    }

    @Override
    public int findCountByFilter(UsageFilter filter) {
        return selectOne("ISalUsageMapper.findCountByFilter",
            ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("ISalUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public boolean workPortionIdExists(String workPortionId) {
        return selectOne("ISalUsageMapper.workPortionIdExists", Objects.requireNonNull(workPortionId));
    }

    @Override
    public boolean workPortionIdExists(String workPortionId, String batchId) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("workPortionId", Objects.requireNonNull(workPortionId));
        parameters.put("batchId", Objects.requireNonNull(batchId));
        return selectOne("ISalUsageMapper.workPortionIdExistsInBatch", parameters);
    }

    @Override
    public String findItemBankDetailGradeByWorkPortionId(String workPortionId) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("workPortionId", Objects.requireNonNull(workPortionId));
        parameters.put(DETAIL_TYPE_KEY, SalDetailTypeEnum.IB);
        return selectOne("ISalUsageMapper.findItemBankDetailGradeByWorkPortionId", parameters);
    }

    @Override
    public boolean usageDataExist(String batchId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("batchId", Objects.requireNonNull(batchId));
        params.put(DETAIL_TYPE_KEY, SalDetailTypeEnum.UD);
        return selectOne("ISalUsageMapper.usageDataExist", params);
    }

    @Override
    public void deleteUsageData(String batchId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("batchId", Objects.requireNonNull(batchId));
        params.put(DETAIL_TYPE_KEY, SalDetailTypeEnum.UD);
        delete("ISalUsageMapper.deleteUsageData", params);
    }

    @Override
    public void deleteByBatchId(String batchId) {
        delete("ISalUsageMapper.deleteByBatchId", Objects.requireNonNull(batchId));
    }

    @Override
    public List<GradeGroupEnum> findUsageDataGradeGroups(UsageFilter filter) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put(DETAIL_TYPE_KEY, SalDetailTypeEnum.UD);
        return selectList("ISalUsageMapper.findUsageDataGradeGroups", params);
    }

    @Override
    public void addToScenario(String scenarioId, UsageFilter filter, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("ISalUsageMapper.addToScenario", params);
    }

    @Override
    public void updatePayeeByAccountNumber(Long rhAccountNumber, String scenarioId, Long payeeAccountNumber,
                                           String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("rhAccountNumber", Objects.requireNonNull(rhAccountNumber));
        params.put("payeeAccountNumber", Objects.requireNonNull(payeeAccountNumber));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("ISalUsageMapper.updatePayeeByAccountNumber", params);
    }

    @Override
    public void deleteFromScenario(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("updateStatusTo", UsageStatusEnum.ELIGIBLE);
        params.put("updateStatusFrom", UsageStatusEnum.LOCKED);
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("ISalUsageMapper.deleteFromScenario", params);
    }
}
