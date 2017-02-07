package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Implementation of Usage repository.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/02/17
 *
 * @author Darya Baraukova
 */
@Repository
public class UsageRepository extends BaseRepository implements IUsageRepository {

    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";

    @Override
    public int insertUsage(Usage usage) {
        return insert("IUsageMapper.insertUsage", checkNotNull(usage));
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
    public int getUsagesCount(UsageFilter filter) {
        return selectOne("IUsageMapper.getUsagesCount", ImmutableMap.of(FILTER_KEY, checkNotNull(filter)));
    }
}
