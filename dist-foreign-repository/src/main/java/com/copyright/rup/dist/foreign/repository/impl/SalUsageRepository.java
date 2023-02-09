package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.SalGradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;

import com.google.common.collect.ImmutableMap;
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
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String ACCOUNT_NUMBER_KEY = "accountNumber";

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
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("ISalUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public List<Rightsholder> findRightsholders() {
        return selectList("ISalUsageMapper.findRightsholders");
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
    public List<SalGradeGroupEnum> findUsageDataGradeGroups(UsageFilter filter) {
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

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("ISalUsageMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<UsageDto> findByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue,
                                                             Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("ISalUsageMapper.findByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<Usage> findByScenarioId(String scenarioId) {
        return selectList("ISalUsageMapper.findByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void calculateAmounts(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("ISalUsageMapper.calculateAmounts", params);
    }

    @Override
    public void updateRhAccountNumberAndStatusByIds(Set<String> usageIds, Long rhAccountNumber, UsageStatusEnum status,
                                                    String userName) {
        checkArgument(CollectionUtils.isNotEmpty(usageIds));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("status", Objects.requireNonNull(status));
        parameters.put("rhAccountNumber", Objects.requireNonNull(rhAccountNumber));
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        Iterables.partition(usageIds, MAX_VARIABLES_COUNT).forEach(partition -> {
            parameters.put("usageIds", partition);
            update("ISalUsageMapper.updateRhAccountNumberAndStatusByIds", parameters);
        });
    }

    @Override
    public int findCountForAudit(AuditFilter filter) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        return selectOne("ISalUsageMapper.findCountForAudit", params);
    }

    @Override
    public List<UsageDto> findForAudit(AuditFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        params.put("pageable", pageable);
        params.put("sort", sort);
        return selectList("ISalUsageMapper.findForAudit", params);
    }

    private AuditFilter escapeSqlLikePattern(AuditFilter auditFilter) {
        AuditFilter filterCopy = new AuditFilter(auditFilter);
        filterCopy.setCccEventId(escapeSqlLikePattern(filterCopy.getCccEventId()));
        filterCopy.setDistributionName(escapeSqlLikePattern(filterCopy.getDistributionName()));
        filterCopy.setSearchValue(escapeSqlLikePattern(filterCopy.getSearchValue()));
        return filterCopy;
    }
}
