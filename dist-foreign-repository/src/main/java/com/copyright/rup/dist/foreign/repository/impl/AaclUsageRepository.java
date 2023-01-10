package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.PayeeAccountAggregateLicenseeClassesPair;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IAaclUsageRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/21/2020
 *
 * @author Ihar Suvorau
 */
@Repository
public class AaclUsageRepository extends BaseRepository implements IAaclUsageRepository {

    /**
     * It's a max value for count of variables in statement.
     */
    private static final int MAX_VARIABLES_COUNT = 32000;
    private static final int PAYEES_BATCH_SIZE = 1000;
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String UPDATE_USER_KEY = "updateUser";
    private static final String STATUS_KEY = "status";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String ACCOUNT_NUMBER_KEY = "accountNumber";

    @Override
    public void insert(Usage usage) {
        insert("IAaclUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public List<String> insertFromBaseline(Set<Integer> periods, String batchId, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("periods", Objects.requireNonNull(periods));
        parameters.put("batchId", Objects.requireNonNull(batchId));
        parameters.put("createUser", Objects.requireNonNull(userName));
        parameters.put(UPDATE_USER_KEY, userName);
        return selectList("IAaclUsageMapper.insertFromBaseline", parameters);
    }

    @Override
    public void updateClassifiedUsages(List<AaclClassifiedUsage> aaclClassifiedUsages, String userName) {
        Objects.requireNonNull(aaclClassifiedUsages);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        aaclClassifiedUsages.forEach(aaclUsage -> {
            parameters.put("aaclUsage", aaclUsage);
            update("IAaclUsageMapper.updateClassifiedUsage", parameters);
        });
    }

    @Override
    public void deleteById(String usageId) {
        delete("IAaclUsageMapper.deleteById", Objects.requireNonNull(usageId));
    }

    @Override
    public String updateProcessedUsage(Usage usage) {
        return selectOne("IAaclUsageMapper.updateProcessedUsage", Objects.requireNonNull(usage));
    }

    @Override
    public List<Usage> findByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), MAX_VARIABLES_COUNT)
            .forEach(partition -> result.addAll(selectList("IAaclUsageMapper.findByIds", partition)));
        return result;
    }

    @Override
    public int findReferencedAaclUsagesCountByIds(String... usageIds) {
        return selectOne("IAaclUsageMapper.findReferencedAaclUsagesCountByIds",
            ImmutableMap.of("usageIds", Objects.requireNonNull(usageIds)));
    }

    @Override
    public List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IAaclUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public int findCountByFilter(UsageFilter filter) {
        return selectOne("IAaclUsageMapper.findCountByFilter",
            ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public Set<Integer> findBaselinePeriods(int startPeriod, int numberOfBaselineYears) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("startPeriod", startPeriod);
        parameters.put("numberOfBaselineYears", numberOfBaselineYears);
        return new HashSet<>(selectList("IAaclUsageMapper.findBaselinePeriods", parameters));
    }

    @Override
    public List<Usage> findByScenarioId(String scenarioId) {
        return selectList("IAaclUsageMapper.findByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void updatePayeeByAccountNumber(Long rhAccountNumber, String scenarioId, Long payeeAccountNumber,
                                           String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("rhAccountNumber", Objects.requireNonNull(rhAccountNumber));
        params.put("payeeAccountNumber", Objects.requireNonNull(payeeAccountNumber));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("IAaclUsageMapper.updatePayeeByAccountNumber", params);
    }

    @Override
    public List<Integer> findUsagePeriods() {
        return selectList("IAaclUsageMapper.findUsagePeriods");
    }

    @Override
    public List<Integer> findUsagePeriodsByFilter(UsageFilter filter) {
        return selectList("IAaclUsageMapper.findUsagePeriodsByFilter",
            ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put(STATUS_KEY, Objects.requireNonNull(status));
        return selectOne("IAaclUsageMapper.isValidFilteredUsageStatus", params);
    }

    @Override
    public boolean isValidForClassification(UsageFilter filter) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put(STATUS_KEY, UsageStatusEnum.RH_FOUND);
        return selectOne("IAaclUsageMapper.isValidForClassification", params);
    }

    @Override
    public List<Long> findInvalidRightsholdersByFilter(UsageFilter filter) {
        return selectList("IAaclUsageMapper.findInvalidRightsholdersByFilter",
            Map.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public void deleteByBatchId(String batchId) {
        delete("IAaclUsageMapper.deleteByBatchId", Objects.requireNonNull(batchId));
    }

    @Override
    public boolean usagesExistByDetailLicenseeClassAndFilter(UsageFilter filter, Integer detailLicenseeClassId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put("detailLicenseeClassId", Objects.requireNonNull(detailLicenseeClassId));
        return selectOne("IAaclUsageMapper.usagesExistByDetailLicenseeClassAndFilter", params);
    }

    @Override
    public void addToScenario(String scenarioId, UsageFilter filter, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("IAaclUsageMapper.addToScenario", params);
    }

    @Override
    public Set<String> excludeFromScenarioByPayees(String scenarioId, Set<Long> payeeAccountNumbers, String userName) {
        Set<String> result = new HashSet<>();
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        Iterables.partition(Objects.requireNonNull(payeeAccountNumbers), PAYEES_BATCH_SIZE).forEach(partition -> {
            params.put("payeeAccountNumbers", Objects.requireNonNull(partition));
            result.addAll(selectList("IAaclUsageMapper.excludeFromScenarioByPayees", params));
        });
        return result;
    }

    @Override
    public void calculateAmounts(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        insert("IAaclUsageMapper.calculateAmounts", params);
    }

    @Override
    public void excludeZeroAmountUsages(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        insert("IAaclUsageMapper.excludeZeroAmountUsages", params);
    }

    @Override
    public List<PayeeTotalHolder> findPayeeTotalHoldersByFilter(ExcludePayeeFilter filter) {
        return selectList("IAaclUsageMapper.findPayeeTotalHoldersByFilter",
            ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public List<UsageDto> findForAudit(AuditFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        params.put("pageable", pageable);
        params.put("sort", sort);
        return selectList("IAaclUsageMapper.findForAudit", params);
    }

    @Override
    public int findCountForAudit(AuditFilter filter) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        return selectOne("IAaclUsageMapper.findCountForAudit", params);
    }

    @Override
    public void updatePublicationTypeWeight(String scenarioId, String publicationTypeId,
                                            BigDecimal weight, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("publicationTypeId", Objects.requireNonNull(publicationTypeId));
        params.put("weight", Objects.requireNonNull(weight));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("IAaclUsageMapper.updatePublicationTypeWeight", params);
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(ACCOUNT_NUMBER_KEY, Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IAaclUsageMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
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
        return selectList("IAaclUsageMapper.findByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public void deleteFromScenario(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("updateStatusTo", UsageStatusEnum.ELIGIBLE);
        params.put("updateStatusesFrom", Sets.newHashSet(UsageStatusEnum.SCENARIO_EXCLUDED, UsageStatusEnum.LOCKED));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        update("IAaclUsageMapper.deleteFromScenario", params);
    }

    @Override
    public void addToBaselineByScenarioId(String scenarioId, String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        params.put("createUser", Objects.requireNonNull(userName));
        params.put(UPDATE_USER_KEY, userName);
        update("IAaclUsageMapper.addToBaselineByScenarioId", params);
    }

    @Override
    public List<Usage> findBaselineUsages() {
        return selectList("IAaclUsageMapper.findBaselineUsages");
    }

    @Override
    public void deleteLockedByScenarioId(String scenarioId) {
        delete("IAaclUsageMapper.deleteLockedByScenarioId",
            Map.of(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId)));
    }

    @Override
    public void deleteExcludedByScenarioId(String scenarioId) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(STATUS_KEY, UsageStatusEnum.SCENARIO_EXCLUDED);
        delete("IAaclUsageMapper.deleteExcludedByScenarioId", parameters);
    }

    @Override
    public List<PayeeAccountAggregateLicenseeClassesPair> findPayeeAggClassesPairsByScenarioId(String scenarioId) {
        return selectList("IAaclUsageMapper.findPayeeAggClassesPairsByScenarioId", scenarioId);
    }

    private AuditFilter escapeSqlLikePattern(AuditFilter auditFilter) {
        AuditFilter filterCopy = new AuditFilter(auditFilter);
        filterCopy.setCccEventId(escapeSqlLikePattern(filterCopy.getCccEventId()));
        filterCopy.setDistributionName(escapeSqlLikePattern(filterCopy.getDistributionName()));
        filterCopy.setSearchValue(escapeSqlLikePattern(filterCopy.getSearchValue()));
        return filterCopy;
    }
}
