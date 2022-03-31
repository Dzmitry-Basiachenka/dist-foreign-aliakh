package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageBatchRepository;
import org.springframework.stereotype.Repository;

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
}
