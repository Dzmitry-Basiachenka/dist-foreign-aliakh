package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.repository.api.IUdmBatchRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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
}
