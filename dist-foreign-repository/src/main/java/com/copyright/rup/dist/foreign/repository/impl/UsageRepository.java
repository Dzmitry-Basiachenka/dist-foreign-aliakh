package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of Usage repository.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/02/17
 *
 * @author Darya Baraukova
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@Repository
public class UsageRepository extends BaseRepository implements IUsageRepository {

    private static final long serialVersionUID = -2571942278644516424L;
    /**
     * It's a max value for count of variables in statement.
     */
    private static final int MAX_VARIABLES_COUNT = 32000;
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String UPDATE_USER_KEY = "updateUser";
    private static final String USAGE_ID_KEY = "usageId";
    private static final String STATUS_KEY = "status";
    private static final String RH_ACCOUNT_NUMBER_KEY = "rhAccountNumber";
    private static final String PRODUCT_FAMILY_KEY = "productFamily";
    private static final String BATCH_ID_KEY = "batchId";

    @Override
    public List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public List<String> findIdsByStatusAndProductFamily(UsageStatusEnum status, String productFamily) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put(PRODUCT_FAMILY_KEY, Objects.requireNonNull(productFamily));
        return selectList("IUsageMapper.findIdsByStatusAndProductFamily", parameters);
    }

    @Override
    public List<Usage> findByStatusAndProductFamily(UsageStatusEnum status, String productFamily) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put(PRODUCT_FAMILY_KEY, Objects.requireNonNull(productFamily));
        return selectList("IUsageMapper.findByStatusAndProductFamily", parameters);
    }

    @Override
    public List<Usage> findByScenarioId(String scenarioId) {
        return selectList("IUsageMapper.findByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<Usage> findByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), MAX_VARIABLES_COUNT)
            .forEach(partition -> result.addAll(selectList("IUsageMapper.findByIds", partition)));
        return result;
    }

    @Override
    public int findCountByFilter(UsageFilter filter) {
        return selectOne("IUsageMapper.findCountByFilter", ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public BigDecimal getTotalAmountByStandardNumberAndBatchId(String standardNumber, String batchId) {
        Set<UsageStatusEnum> statuses =
            Set.of(UsageStatusEnum.NEW, UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.WORK_NOT_FOUND);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("statuses", Objects.requireNonNull(statuses));
        parameters.put("standardNumber", standardNumber);
        parameters.put(BATCH_ID_KEY, batchId);
        BigDecimal totalAmount =
            ObjectUtils.defaultIfNull(selectOne("IUsageMapper.getTotalAmountByStandardNumberAndBatchId", parameters),
                BigDecimal.ZERO);
        return totalAmount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getTotalAmountByTitleAndBatchId(String title, String batchId) {
        Set<UsageStatusEnum> statuses =
            Set.of(UsageStatusEnum.NEW, UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.WORK_NOT_FOUND);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("statuses", Objects.requireNonNull(statuses));
        parameters.put("title", title);
        parameters.put(BATCH_ID_KEY, batchId);
        BigDecimal totalAmount =
            ObjectUtils.defaultIfNull(selectOne("IUsageMapper.getTotalAmountByTitleAndBatchId", parameters),
                BigDecimal.ZERO);
        return totalAmount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void deleteByBatchId(String batchId) {
        delete("IUsageMapper.deleteByBatchId", Objects.requireNonNull(batchId));
    }

    @Override
    public void deleteById(String usageId) {
        delete("IUsageMapper.deleteByUsageId", Objects.requireNonNull(usageId));
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        delete("IUsageMapper.deleteByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<Long> findInvalidRightsholdersByFilter(UsageFilter filter) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        return selectList("IUsageMapper.findInvalidRightsholdersByFilter", parameters);
    }

    @Override
    public void addToScenario(List<Usage> usages) {
        Objects.requireNonNull(usages).forEach(usage -> update("IUsageMapper.addToScenario", usage));
    }

    @Override
    public void deleteFromScenario(String scenarioId, String updateUser) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(updateUser));
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        update("IUsageMapper.deleteFromScenario", parameters);
    }

    @Override
    public void deleteFromScenario(List<String> usagesIds, String userName) {
        checkArgument(CollectionUtils.isNotEmpty(usagesIds));
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        params.put(UPDATE_USER_KEY, userName);
        usagesIds.forEach(usageId -> {
            params.put(USAGE_ID_KEY, usageId);
            update("IUsageMapper.deleteFromScenarioByUsageId", params);
        });
    }

    @Override
    public int findCountByUsageIdAndStatus(String usageId, UsageStatusEnum statusEnum) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(USAGE_ID_KEY, Objects.requireNonNull(usageId));
        parameters.put(STATUS_KEY, Objects.requireNonNull(statusEnum));
        return selectOne("IUsageMapper.findCountByUsageIdAndStatus", parameters);
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
        return selectList("IUsageMapper.findRightsholderTotalsHoldersByScenarioId", parameters);
    }

    @Override
    public List<PayeeTotalHolder> findPayeeTotalHoldersByFilter(ExcludePayeeFilter filter) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put("scenarioStatus", ScenarioStatusEnum.IN_PROGRESS);
        return selectList("IUsageMapper.findPayeeTotalHoldersByFilter", parameters);
    }

    @Override
    public int findRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IUsageMapper.findRightsholderTotalsHolderCountByScenarioId", parameters);
    }

    @Override
    public boolean isScenarioEmpty(String scenarioId) {
        return selectOne("IUsageMapper.isScenarioEmpty", Objects.requireNonNull(scenarioId));
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IUsageMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<UsageDto> findByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                             String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.findByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<String> findIdsByScenarioIdRroAccountNumberRhAccountNumbers(String scenarioId, Long rroAccountNumber,
                                                                            List<Long> accountNumbers) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("rroAccountNumber", Objects.requireNonNull(rroAccountNumber));
        List<String> result = new ArrayList<>(accountNumbers.size());
        Iterables.partition(accountNumbers, MAX_VARIABLES_COUNT).forEach(partition -> {
            parameters.put("accountNumbers", Objects.requireNonNull(partition));
            result.addAll(selectList("IUsageMapper.findIdsByScenarioIdRroAccountNumberRhAccountNumbers", parameters));
        });
        return result;
    }

    @Override
    public int findCountForAudit(AuditFilter filter) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        return selectOne("IUsageMapper.findCountForAudit", params);
    }

    @Override
    public List<UsageDto> findForAudit(AuditFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        params.put(PAGEABLE_KEY, pageable);
        params.put("sort", sort);
        return selectList("IUsageMapper.findForAudit", params);
    }

    @Override
    public List<Usage> findByStatuses(UsageStatusEnum... statuses) {
        return selectList("IUsageMapper.findByStatuses", Objects.requireNonNull(statuses));
    }

    @Override
    public void updateStatus(Set<String> usageIds, UsageStatusEnum status) {
        checkArgument(CollectionUtils.isNotEmpty(usageIds));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        usageIds.forEach(usageId -> {
            parameters.put(USAGE_ID_KEY, usageId);
            update("IUsageMapper.updateStatus", parameters);
        });
    }

    @Override
    public void updateStatusAndRhAccountNumber(Set<String> usageIds, UsageStatusEnum status, Long rhAccountNumber) {
        checkArgument(CollectionUtils.isNotEmpty(usageIds));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put(RH_ACCOUNT_NUMBER_KEY, Objects.requireNonNull(rhAccountNumber));
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        Iterables.partition(usageIds, MAX_VARIABLES_COUNT).forEach(partition -> {
            parameters.put("usageIds", partition);
            update("IUsageMapper.updateStatusAndRhAccountNumber", parameters);
        });
    }

    @Override
    public void update(List<Usage> usages) {
        checkArgument(CollectionUtils.isNotEmpty(usages));
        usages.forEach(usage -> update("IUsageMapper.update", usage));
    }

    @Override
    public String updateProcessedUsage(Usage usage) {
        return selectOne("IUsageMapper.updateProcessedUsage", Objects.requireNonNull(usage));
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put(STATUS_KEY, Objects.requireNonNull(status));
        return selectOne("IUsageMapper.isValidFilteredUsageStatus", params);
    }

    @Override
    public Map<Long, Set<String>> findWrWrkInstToUsageIdsByBatchNameAndUsageStatus(String batchName,
                                                                                   UsageStatusEnum status) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("batchName", Objects.requireNonNull(batchName));
        params.put(STATUS_KEY, Objects.requireNonNull(status));
        var handler = new WrWrkInstToUsageIdResultHandler();
        getTemplate().select("IUsageMapper.findWrWrkInstToUsageIdsByBatchNameAndUsageStatus", params, handler);
        return handler.getWrWrkInstToUsageIdsMap();
    }

    @Override
    public int findReferencedUsagesCountByIds(String... usageIds) {
        return selectOne("IUsageMapper.findReferencedUsagesCountByIds",
            ImmutableMap.of("usageIds", Objects.requireNonNull(usageIds)));
    }

    @Override
    public List<RightsholderPayeeProductFamilyHolder> findRightsholderPayeeProductFamilyHoldersByScenarioIds(
        Set<String> scenarioIds) {
        return selectList("IUsageMapper.findRightsholderPayeeProductFamilyHoldersByScenarioIds", scenarioIds);
    }

    private AuditFilter escapeSqlLikePattern(AuditFilter auditFilter) {
        AuditFilter filterCopy = new AuditFilter(auditFilter);
        filterCopy.setCccEventId(escapeSqlLikePattern(filterCopy.getCccEventId()));
        filterCopy.setDistributionName(escapeSqlLikePattern(filterCopy.getDistributionName()));
        filterCopy.setSearchValue(escapeSqlLikePattern(filterCopy.getSearchValue()));
        return filterCopy;
    }
}
