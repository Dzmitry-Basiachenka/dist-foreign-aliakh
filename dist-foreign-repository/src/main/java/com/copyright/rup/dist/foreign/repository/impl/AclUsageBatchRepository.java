package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageBatchRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IAclUsageBatchRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AclUsageBatchRepository extends BaseRepository implements IAclUsageBatchRepository {

    private static final long serialVersionUID = 3358113845495924846L;

    @Override
    public boolean isAclUsageBatchExist(String usageBatchName) {
        return selectOne("IAclUsageBatchMapper.isAclUsageBatchExist", Objects.requireNonNull(usageBatchName));
    }

    @Override
    public void insert(AclUsageBatch usageBatch) {
        insert("IAclUsageBatchMapper.insert", Objects.requireNonNull(usageBatch));
    }

    @Override
    public AclUsageBatch findById(String usageBatchId) {
        return selectOne("IAclUsageBatchMapper.findById", Objects.requireNonNull(usageBatchId));
    }

    @Override
    public List<AclUsageBatch> findAll() {
        return selectList("IAclUsageBatchMapper.findAll");
    }

    @Override
    public List<AclUsageBatch> findUsageBatchesByPeriod(Integer period, boolean editableFlag) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("editableFlag", editableFlag);
        return selectList("IAclUsageBatchMapper.findUsageBatchesByPeriod", parameters);
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IAclUsageBatchMapper.findPeriods");
    }

    @Override
    public void deleteById(String usageBatchId) {
        delete("IAclUsageBatchMapper.deleteById", Objects.requireNonNull(usageBatchId));
    }
}
