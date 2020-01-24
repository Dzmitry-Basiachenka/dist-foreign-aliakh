package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IAaclUsageRepository}.
 * <p>
 * Copyright (C) 2019 copyright.com
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

    @Override
    public void insert(Usage usage) {
        insert("IAaclUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public void deleteById(String usageId) {
        delete("IAaclUsageMapper.deleteById", Objects.requireNonNull(usageId));
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
}
