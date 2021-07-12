package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUdmBatchRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IUdmBatchRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/28/2021
 *
 * @author Uladzislau Shalamitski
 */
@Repository
public class UdmBatchRepository extends BaseRepository implements IUdmBatchRepository {

    @Override
    public void insert(UdmBatch udmBatch) {
        insert("IUdmBatchMapper.insert", Objects.requireNonNull(udmBatch));
    }

    @Override
    public UdmBatch findById(String udmBatchId) {
        return selectOne("IUdmBatchMapper.findById", Objects.requireNonNull(udmBatchId));
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IUdmBatchMapper.findPeriods");
    }

    @Override
    public List<UdmBatch> findAll() {
        return selectList("IUdmBatchMapper.findAll");
    }

    @Override
    public boolean udmBatchExists(String name) {
        return selectOne("IUdmBatchMapper.batchExists", Objects.requireNonNull(name));
    }

    @Override
    public boolean isUdmBatchProcessingCompleted(String udmBatchId, Set<UsageStatusEnum> statuses) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("udmBatchId", udmBatchId);
        params.put("statuses", statuses);
        return selectOne("IUdmBatchMapper.isUdmBatchProcessingCompleted", params);
    }

    @Override
    public void deleteById(String udmBatchId) {
        delete("IUdmBatchMapper.deleteById", Objects.requireNonNull(udmBatchId));
    }
}
