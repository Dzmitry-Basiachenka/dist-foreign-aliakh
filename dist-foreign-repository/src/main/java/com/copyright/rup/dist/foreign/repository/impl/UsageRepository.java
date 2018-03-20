package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.PipedOutputStream;
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

    /**
     * Details ids batch size for finding duplicates. This size was obtained as (32000 / 2 = 16000)
     * where {@code 32000} it's a max value for count of variables in statement and {@code 2} means that statement uses
     * 'in' clause with the same parameters two times.
     */
    private static final int DUPLICATE_DETAILS_IDS_BATCH_SIZE = 16000;
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
    public Map<Long, Triple<String, Boolean, Long>> findRightsholdersInformation(String scenarioId) {
        RightsholdersInfoResultHandler handler = new RightsholdersInfoResultHandler();
        getTemplate().select("IUsageMapper.findRightsholdersInformation", Objects.requireNonNull(scenarioId), handler);
        return handler.getRhToIdParticipatingStatusAndPayee();
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
    public List<UsageDto> getAndWriteUsagesForResearch(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        List<UsageDto> usageDtos = Lists.newArrayList();
        try (UsageCsvReportHandler handler = new UsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                usageDtos = selectList("IUsageMapper.findByFilter", ImmutableMap.of(FILTER_KEY, filter));
                usageDtos.forEach(handler::handleResult);
            }
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
        return usageDtos;
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
            params.put("usageId", usageId);
            update("IUsageMapper.deleteFromScenarioByUsageId", params);
        });
    }

    @Override
    public Set<Long> findDuplicateDetailIds(List<Long> detailIds) {
        checkArgument(CollectionUtils.isNotEmpty(detailIds));
        Set<Long> result = Sets.newHashSetWithExpectedSize(detailIds.size());
        Iterables.partition(detailIds, DUPLICATE_DETAILS_IDS_BATCH_SIZE).forEach(
            detailIdsPartition -> result.addAll(selectList("IUsageMapper.findDuplicateDetailIds", detailIdsPartition)));
        return result;
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
    public Usage findByDetailId(Long detailId) {
        return selectOne("IUsageMapper.findByDetailId", Objects.requireNonNull(detailId));
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
    public void updateStatus(String usageId, UsageStatusEnum status) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        checkArgument(StringUtils.isNotBlank(usageId));
        parameters.put(USAGE_ID_KEY, usageId);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        update("IUsageMapper.updateStatus", parameters);
    }

    @Override
    @Profiled(tag = "repository.UsageRepository.updateStatus")
    public void updateStatus(Set<String> usageIds, UsageStatusEnum status) {
        usageIds.forEach(usageId -> updateStatus(usageId, status));
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

    @Profiled(tag = "repository.UsageRepository.findUsagesWithBlankWrWrkInst")
    @Override
    public List<Usage> findUsagesWithBlankWrWrkInst() {
        return selectList("IUsageMapper.findUsagesWithBlankWrWrkInst", UsageStatusEnum.NEW);
    }

    @Profiled(tag = "repository.UsageRepository.update")
    @Override
    public void update(List<Usage> usages) {
        checkArgument(CollectionUtils.isNotEmpty(usages));
        usages.forEach(usage -> update("IUsageMapper.update", usage));
    }
}
