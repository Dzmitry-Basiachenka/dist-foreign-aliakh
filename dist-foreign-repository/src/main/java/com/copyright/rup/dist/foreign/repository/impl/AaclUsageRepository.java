package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";

    @Override
    public void insert(Usage usage) {
        insert("IAaclUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public void update(List<Usage> usages) {
        checkArgument(CollectionUtils.isNotEmpty(usages));
        usages.forEach(usage -> update("IAaclUsageMapper.update", usage));
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
    // TODO {isuvorau} should be used on service layer for AACL product family
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
    public List<Integer> findUsagePeriods() {
        return selectList("IAaclUsageMapper.findUsagePeriods");
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put("status", Objects.requireNonNull(status));
        return selectOne("IAaclUsageMapper.isValidFilteredUsageStatus", params);
    }
}
