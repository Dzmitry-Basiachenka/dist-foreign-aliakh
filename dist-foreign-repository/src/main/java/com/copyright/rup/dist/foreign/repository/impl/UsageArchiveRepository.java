package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of Usage archive repository.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/08/18
 *
 * @author Ihar Suvorau
 */
@Repository
public class UsageArchiveRepository extends BaseRepository implements IUsageArchiveRepository {

    private static final int BATCH_SIZE = 32000;
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String STATUS_KEY = "status";
    private static final String ACCOUNT_NUMBER_KEY = "accountNumber";

    @Override
    public void deleteByBatchId(String batchId) {
        delete("IUsageArchiveMapper.deleteByBatchId", Objects.requireNonNull(batchId));
    }

    @Override
    public void deleteByIds(List<String> usageIds) {
        Iterables.partition(Objects.requireNonNull(usageIds), BATCH_SIZE)
            .forEach(partition -> delete("IUsageArchiveMapper.deleteByIds", partition));
    }

    @Override
    public List<RightsholderTotalsHolder> findRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                    String searchValue,
                                                                                    Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageArchiveMapper.findRightsholderTotalsHoldersByScenarioId", parameters);
    }

    @Override
    public int findRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IUsageArchiveMapper.findRightsholderTotalsHolderCountByScenarioId", parameters);
    }

    @Override
    public List<UsageDto> findByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber,
                                                             String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageArchiveMapper.findByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IUsageArchiveMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<UsageDto> findAaclByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber,
                                                                 String searchValue, Pageable pageable,
                                                                 Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageArchiveMapper.findAaclByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public int findSalCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IUsageArchiveMapper.findSalCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<UsageDto> findSalByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber,
                                                                String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageArchiveMapper.findSalByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public int findAaclCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber,
                                                           String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IUsageArchiveMapper.findAaclCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public void updatePaidInfo(PaidUsage usage) {
        update("IUsageArchiveMapper.updatePaidInfo", Objects.requireNonNull(usage));
    }

    @Override
    public void updateStatus(Set<String> usageIds, UsageStatusEnum status) {
        checkArgument(CollectionUtils.isNotEmpty(usageIds));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put("updateUser", StoredEntity.DEFAULT_USER);
        usageIds.forEach(usageId -> {
            parameters.put("usageId", usageId);
            update("IUsageArchiveMapper.updateStatusById", parameters);
        });
    }

    @Override
    public List<PaidUsage> findByIdAndStatus(List<String> usageIds, UsageStatusEnum status) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(STATUS_KEY, status);
        parameters.put("usageIds", Objects.requireNonNull(usageIds));
        return selectList("IUsageArchiveMapper.findByIdAndStatus", parameters);
    }

    @Override
    public List<String> findPaidIds() {
        return selectList("IUsageArchiveMapper.findPaidIds", UsageStatusEnum.PAID);
    }

    @Override
    public void insertPaid(PaidUsage paidUsage) {
        insert("IUsageArchiveMapper.insertPaid", Objects.requireNonNull(paidUsage));
    }

    @Override
    public void insertAaclPaid(PaidUsage paidUsage) {
        insert("IUsageArchiveMapper.insertAaclPaid", Objects.requireNonNull(paidUsage));
    }

    @Override
    public void moveFundUsagesToArchive(String scenarioId) {
        insert("IUsageArchiveMapper.moveFundUsagesToArchive", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<String> copyToArchiveByScenarioId(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(STATUS_KEY, UsageStatusEnum.SENT_TO_LM);
        params.put("createUser", Objects.requireNonNull(userName));
        params.put("updateUser", userName);
        return selectList("IUsageArchiveMapper.copyToArchiveByScenarioId", params);
    }

    @Override
    public List<String> copyNtsToArchiveByScenarioId(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(STATUS_KEY, UsageStatusEnum.SENT_TO_LM);
        params.put("createUser", Objects.requireNonNull(userName));
        params.put("updateUser", userName);
        return selectList("IUsageArchiveMapper.copyNtsToArchiveByScenarioId", params);
    }

    @Override
    public List<Usage> findByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), BATCH_SIZE)
            .forEach(partition -> result.addAll(selectList("IUsageArchiveMapper.findByIds", partition)));
        return result;
    }

    @Override
    public List<Usage> findForSendToLmByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), BATCH_SIZE)
            .forEach(partition -> result.addAll(selectList("IUsageArchiveMapper.findForSendToLmByIds", partition)));
        return result;
    }

    @Override
    public List<UsageDto> findAaclDtosByScenarioId(String scenarioId) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("pageable", null);
        return selectList("IUsageArchiveMapper.findAaclDtosByScenarioId", parameters);
    }

    @Override
    public List<Usage> findAaclByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), BATCH_SIZE)
            .forEach(partition -> result.addAll(selectList("IUsageArchiveMapper.findAaclByIds", partition)));
        return result;
    }

    @Override
    public List<Usage> findSalByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), BATCH_SIZE)
            .forEach(partition -> result.addAll(selectList("IUsageArchiveMapper.findSalByIds", partition)));
        return result;
    }
}
