package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.repository.BaseRepository;
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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    private static final String STATUS_KEY = "status";

    @Override
    public void insert(Usage usage) {
        insert("IUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public List<UsageDto> findByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put(PAGEABLE_KEY, Objects.requireNonNull(pageable));
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.findByFilter", parameters);
    }

    @Override
    public int getCountByFilter(UsageFilter filter) {
        return selectOne("IUsageMapper.getCountByFilter", ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        try (ScenarioUsagesCsvReportHandler handler = new ScenarioUsagesCsvReportHandler(pipedOutputStream)) {
            if (Objects.nonNull(scenarioId)) {
                getTemplate().select("IUsageMapper.findByScenarioId", scenarioId, handler);
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
    public void deleteUsages(String batchId) {
        checkArgument(StringUtils.isNotBlank(batchId));
        delete("IUsageMapper.deleteUsages", batchId);
    }

    @Override
    public List<Usage> findWithAmountsAndRightsholders(UsageFilter filter) {
        return selectList("IUsageMapper.findWithAmountsAndRightsholders", ImmutableMap.of(
            FILTER_KEY, Objects.requireNonNull(filter)));
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
            params.put("usageId", usageId);
            update("IUsageMapper.deleteFromScenarioByUsageId", params);
        });
    }

    @Override
    public Set<Long> getDuplicateDetailIds(List<Long> detailIds) {
        checkArgument(CollectionUtils.isNotEmpty(detailIds));
        Set<Long> result = Sets.newHashSetWithExpectedSize(detailIds.size());
        for (List<Long> detailIdsPartition : Iterables.partition(detailIds, DUPLICATE_DETAILS_IDS_BATCH_SIZE)) {
            result.addAll(selectList("IUsageMapper.getDuplicateDetailIds", detailIdsPartition));
        }
        return result;
    }

    @Override
    public List<RightsholderTotalsHolder> getRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                   String searchValue,
                                                                                   Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put(PAGEABLE_KEY, Objects.requireNonNull(pageable));
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.getRightsholderTotalsHoldersByScenarioId", parameters);
    }

    @Override
    public int getRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        return selectOne("IUsageMapper.getRightsholderTotalsHolderCountByScenarioId", parameters);
    }

    @Override
    public int getCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        return selectOne("IUsageMapper.getCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<UsageDto> getByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                            String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put(PAGEABLE_KEY, Objects.requireNonNull(pageable));
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.getByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<String> getIdsByScenarioIdRroAccountNumberRhAccountNumbers(String scenarioId, Long rroAccountNumber,
                                                                           List<Long> accountNumbers) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("rroAccountNumber", Objects.requireNonNull(rroAccountNumber));
        List<String> result = new ArrayList<>(accountNumbers.size());
        Iterables.partition(accountNumbers, 32000).forEach(partition -> {
            parameters.put("accountNumbers", Objects.requireNonNull(partition));
            result.addAll(selectList("IUsageMapper.getIdsByScenarioIdRroAccountNumberRhAccountNumbers", parameters));
        });
        return result;
    }

    /**
     * Finds usage by provided detail id.
     *
     * @param detailId usage details id
     * @return found {@link Usage} instance
     */
    Usage findByDetailId(Long detailId) {
        return selectOne("IUsageMapper.findByDetailId", Objects.requireNonNull(detailId));
    }
}
