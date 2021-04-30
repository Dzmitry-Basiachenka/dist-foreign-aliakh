package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of UDM usage repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/29/21
 *
 * @author Uladzislau Shalamitski
 */
@Repository
public class UdmUsageRepository extends BaseRepository implements IUdmUsageRepository {

    @Override
    public void insert(UdmUsage udmUsage) {
        insert("IUdmUsageMapper.insert", Objects.requireNonNull(udmUsage));
    }

    @Override
    public boolean isOriginalDetailIdExist(String originalDetailId) {
        return selectOne("IUdmUsageMapper.isOriginalDetailIdExist", Objects.requireNonNull(originalDetailId));
    }

    @Override
    public List<UdmUsage> findByIds(List<String> udmBatchIds) {
        return selectList("IUdmUsageMapper.findByIds", Objects.requireNonNull(udmBatchIds));
    }
}
