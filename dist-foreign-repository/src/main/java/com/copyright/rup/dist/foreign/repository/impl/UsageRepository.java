package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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

    /**
     * Details ids batch size for finding duplicates. This size was obtained as (32000 / 2 = 16000)
     * where {@code 32000} it's a max value for count of variables in statement and {@code 2} means that statement uses
     * 'in' clause with the same parameters two times.
     */
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String UPDATE_USER_KEY = "updateUser";
    private static final String USAGE_ID_KEY = "usageId";
    private static final String STATUS_KEY = "status";
    private static final String RH_ACCOUNT_NUMBER_KEY = "rhAccountNumber";

    @Override
    public void insert(Usage usage) {
        insert("IUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public List<UsageDto> findByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.findByFilter", parameters);
    }

    @Override
    public List<Usage> findByScenarioId(String scenarioId) {
        return selectList("IUsageMapper.findByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<Usage> findForReconcile(String scenarioId) {
        return selectList("IUsageMapper.findForReconcile", Objects.requireNonNull(scenarioId));
    }

    @Override
    public Map<Long, Usage> findRightsholdersInformation(String scenarioId) {
        RightsholdersInfoResultHandler handler = new RightsholdersInfoResultHandler();
        getTemplate().select("IUsageMapper.findRightsholdersInformation", Objects.requireNonNull(scenarioId), handler);
        return handler.getRhToUsageMap();
    }

    @Override
    public int findCountByFilter(UsageFilter filter) {
        return selectOne("IUsageMapper.findCountByFilter", ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        try (ScenarioUsagesCsvReportHandler handler = new ScenarioUsagesCsvReportHandler(pipedOutputStream)) {
            if (Objects.nonNull(scenarioId)) {
                getTemplate().select("IUsageMapper.findDtoByScenarioId", scenarioId, handler);
            }
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    @Override
    public void writeUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        try (UsageCsvReportHandler handler = new UsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                getTemplate().select("IUsageMapper.findByFilter", ImmutableMap.of(FILTER_KEY, filter), handler);
            }
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    @Override
    public Set<String> writeUsagesForResearchAndFindIds(UsageFilter filter, OutputStream outputStream) {
        Set<String> usageIds = new HashSet<>();
        try (SendForResearchCsvReportHandler handler =
                 new SendForResearchCsvReportHandler(Objects.requireNonNull(outputStream))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                getTemplate().select("IUsageMapper.findByFilter", ImmutableMap.of(FILTER_KEY, filter), handler);
                usageIds = handler.getUsagesIds();
            }
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
        return usageIds;
    }

    @Override
    public void deleteUsages(String batchId) {
        checkArgument(StringUtils.isNotBlank(batchId));
        delete("IUsageMapper.deleteUsages", batchId);
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        delete("IUsageMapper.deleteByScenarioId", scenarioId);
    }

    @Override
    public List<Usage> findWithAmountsAndRightsholders(UsageFilter filter) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        return selectList("IUsageMapper.findWithAmountsAndRightsholders", parameters);
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
    @Profiled(tag = "repository.UsageRepository.deleteFromScenario")
    public void deleteFromScenario(String scenarioId, String updateUser) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(updateUser));
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        update("IUsageMapper.deleteFromScenario", parameters);
    }

    @Override
    @Profiled(tag = "repository.UsageRepository.deleteFromScenario(usagesIds, userName)")
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
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.findRightsholderTotalsHoldersByScenarioId", parameters);
    }

    @Override
    public int findRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        return selectOne("IUsageMapper.findRightsholderTotalsHolderCountByScenarioId", parameters);
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        return selectOne("IUsageMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<UsageDto> findByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                             String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
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
        Iterables.partition(accountNumbers, 32000).forEach(partition -> {
            parameters.put("accountNumbers", Objects.requireNonNull(partition));
            result.addAll(selectList("IUsageMapper.findIdsByScenarioIdRroAccountNumberRhAccountNumbers", parameters));
        });
        return result;
    }

    @Override
    public Usage findById(String usageId) {
        return selectOne("IUsageMapper.findById", Objects.requireNonNull(usageId));
    }

    @Override
    public int findCountForAudit(AuditFilter filter) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        return selectOne("IUsageMapper.findCountForAudit", params);
    }

    @Override
    public List<UsageDto> findForAudit(AuditFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put("pageable", pageable);
        params.put("sort", sort);
        return selectList("IUsageMapper.findForAudit", params);
    }

    @Override
    public void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        try (AuditCsvReportHandler handler = new AuditCsvReportHandler(Objects.requireNonNull(pipedOutputStream))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
                params.put(FILTER_KEY, filter);
                getTemplate().select("IUsageMapper.findForAudit", params, handler);
            }
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    @Override
    @Profiled(tag = "repository.UsageRepository.findByStatus")
    public List<Usage> findByStatuses(UsageStatusEnum... statuses) {
        return selectList("IUsageMapper.findByStatuses", Objects.requireNonNull(statuses));
    }

    @Override
    @Profiled(tag = "repository.UsageRepository.updateStatus")
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
    @Profiled(tag = "repository.UsageRepository.updateStatusAndRhAccountNumber")
    public void updateStatusAndRhAccountNumber(Set<String> usageIds, UsageStatusEnum status, Long rhAccountNumber) {
        checkArgument(CollectionUtils.isNotEmpty(usageIds));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put(RH_ACCOUNT_NUMBER_KEY, Objects.requireNonNull(rhAccountNumber));
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        Iterables.partition(usageIds, 32000).forEach(partition -> {
            parameters.put("usageIds", partition);
            update("IUsageMapper.updateStatusAndRhAccountNumber", parameters);
        });
    }

    @Override
    public List<String> findProductFamiliesForFilter() {
        return selectList("IUsageMapper.findProductFamiliesForFilter", UsageStatusEnum.LOCKED);
    }

    @Override
    public List<String> findProductFamiliesForAuditFilter() {
        return selectList("IUsageMapper.findProductFamiliesForAuditFilter");
    }

    @Override
    public int findStandardNumbersCount() {
        return selectOne("IUsageMapper.findStandardNumbersCount", UsageStatusEnum.NEW);
    }

    @Override
    public int findTitlesCount() {
        return selectOne("IUsageMapper.findTitlesCount", UsageStatusEnum.NEW);
    }

    @Override
    public List<Usage> findWithStandardNumber(int limit, int offset) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, UsageStatusEnum.NEW);
        parameters.put("limit", limit);
        parameters.put("offset", offset);
        return selectList("IUsageMapper.findWithStandardNumber", parameters);
    }

    @Override
    public List<Usage> findWithTitle(int limit, int offset) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, UsageStatusEnum.NEW);
        parameters.put("limit", limit);
        parameters.put("offset", offset);
        return selectList("IUsageMapper.findWithTitle", parameters);
    }

    @Override
    public List<Usage> findByStandardNumberAndStatus(String standardNumber, UsageStatusEnum status) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put("standardNumber", Objects.requireNonNull(standardNumber));
        return selectList("IUsageMapper.findByStandardNumberAndStatus", parameters);
    }

    @Override
    public List<Usage> findByTitleAndStatus(String title, UsageStatusEnum status) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put("workTitle", Objects.requireNonNull(title));
        return selectList("IUsageMapper.findByTitleAndStatus", parameters);
    }

    @Override
    public List<Usage> findWithoutStandardNumberAndTitle() {
        return selectList("IUsageMapper.findWithoutStandardNumberAndTitle", UsageStatusEnum.NEW);
    }

    @Profiled(tag = "repository.UsageRepository.update")
    @Override
    public void update(List<Usage> usages) {
        checkArgument(CollectionUtils.isNotEmpty(usages));
        usages.forEach(usage -> update("IUsageMapper.update", usage));
    }

    @Override
    public void updateStatusAndWrWrkInstByStandardNumber(List<Usage> usages) {
        checkArgument(CollectionUtils.isNotEmpty(usages));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, UsageStatusEnum.NEW);
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        usages.forEach(usage -> {
            parameters.put("usage", usage);
            update("IUsageMapper.updateStatusAndWrWrkInstByStandardNumber", parameters);
        });
    }

    @Override
    public void updateStatusAndWrWrkInstByTitle(List<Usage> usages) {
        checkArgument(CollectionUtils.isNotEmpty(usages));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, UsageStatusEnum.NEW);
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        usages.forEach(usage -> {
            parameters.put("usage", usage);
            update("IUsageMapper.updateStatusAndWrWrkInstByTitle", parameters);
        });
    }

    @Override
    public void updateResearchedUsages(Collection<ResearchedUsage> researchedUsages) {
        Objects.requireNonNull(researchedUsages);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        parameters.put(STATUS_KEY, UsageStatusEnum.WORK_FOUND);
        researchedUsages.forEach(researchedUsage -> {
            parameters.put(USAGE_ID_KEY, researchedUsage.getUsageId());
            parameters.put("wrWrkInst", researchedUsage.getWrWrkInst());
            update("IUsageMapper.updateResearchedUsage", parameters);
        });
    }
}
