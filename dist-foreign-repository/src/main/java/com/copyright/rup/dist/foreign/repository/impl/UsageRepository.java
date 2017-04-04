package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.repository.BaseRepository;
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
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
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
     * Spring uses 2-bytes integer which max value is 32767, so set batch size to 32000 to avoid issues.
     */
    // TODO {mbezmen} move to BaseRepository in dist-common
    private static final int BATCH_SIZE_FOR_SELECT = 32000;
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String UPDATE_USER_KEY = "updateUser";
    private static final String USAGE_IDS_KEY = "usageIds";
    private static final String STATUS_KEY = "status";

    @Override
    public void insert(Usage usage) {
        insert("IUsageMapper.insert", checkNotNull(usage));
    }

    @Override
    public List<UsageDto> findByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, checkNotNull(filter));
        parameters.put(PAGEABLE_KEY, checkNotNull(pageable));
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.findByFilter", parameters);
    }

    @Override
    public int getCountByFilter(UsageFilter filter) {
        return selectOne("IUsageMapper.getCountByFilter", ImmutableMap.of(FILTER_KEY, checkNotNull(filter)));
    }

    @Override
    public void writeUsagesCsvReport(UsageFilter filter, OutputStream outputStream) {
        checkNotNull(outputStream);
        try {
            UsageCsvReportHandler handler = new UsageCsvReportHandler(outputStream);
            if (!checkNotNull(filter).isEmpty()) {
                getTemplate()
                    .select("IUsageMapper.findByFilter", ImmutableMap.of(FILTER_KEY, filter), handler);
            }
            handler.closeStream();
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
    public List<Usage> findWithAmounts(UsageFilter filter) {
        return selectList("IUsageMapper.findWithAmounts", ImmutableMap.of(FILTER_KEY, checkNotNull(filter)));
    }

    @Override
    public void addToScenario(List<String> usageIds, String scenarioId, String updateUser) {
        checkArgument(CollectionUtils.isNotEmpty(usageIds));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, checkNotNull(scenarioId));
        parameters.put(UPDATE_USER_KEY, checkNotNull(updateUser));
        parameters.put(STATUS_KEY, UsageStatusEnum.LOCKED);
        for (List<String> usageIdsPartition : Iterables.partition(usageIds, BATCH_SIZE_FOR_SELECT)) {
            parameters.put(USAGE_IDS_KEY, usageIdsPartition);
            update("IUsageMapper.addToScenario", parameters);
        }
    }

    @Override
    public void deleteFromScenario(String scenarioId, String updateUser) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, checkNotNull(scenarioId));
        parameters.put(UPDATE_USER_KEY, checkNotNull(updateUser));
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        update("IUsageMapper.deleteFromScenario", parameters);
    }

    @Override
    public Set<Long> getDuplicateDetailIds(List<Long> detailIds) {
        checkArgument(CollectionUtils.isNotEmpty(detailIds));
        Set<Long> result = Sets.newHashSetWithExpectedSize(CollectionUtils.size(detailIds));
        for (List<Long> detailIdsPartition : Iterables.partition(detailIds, BATCH_SIZE_FOR_SELECT)) {
            result.addAll(selectList("IUsageMapper.getDuplicateDetailIds", detailIdsPartition));
        }
        return result;
    }

    /**
     * Finds usage by provided detail id.
     *
     * @param detailId usage details id
     * @return found {@link Usage} instance
     */
    Usage findByDetailId(Long detailId) {
        return selectOne("IUsageMapper.findByDetailId", checkNotNull(detailId));
    }
}
