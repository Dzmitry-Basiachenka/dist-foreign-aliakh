package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.repository.api.IAclciUsageRepository;
import com.google.common.collect.Iterables;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IAclciUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AclciUsageRepository extends BaseRepository implements IAclciUsageRepository {

    private static final int MAX_VARIABLES_COUNT = 32000;

    @Override
    public void insert(Usage usage) {
        insert("IAclciUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public List<Usage> findByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), MAX_VARIABLES_COUNT)
            .forEach(partition -> result.addAll(selectList("IAclciUsageMapper.findByIds", partition)));
        return result;
    }
}
