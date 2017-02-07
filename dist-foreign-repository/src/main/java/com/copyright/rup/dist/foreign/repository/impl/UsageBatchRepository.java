package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link IUsageBatchRepository} for MyBatis.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/02/2017
 *
 * @author Mikalai Bezmen
 */
@Repository
public class UsageBatchRepository extends BaseRepository implements IUsageBatchRepository {

    @Override
    public int insert(UsageBatch usageBatch) {
        return insert("IUsageBatchMapper.insert", checkNotNull(usageBatch));
    }

    @Override
    public List<Integer> findFiscalYears() {
        return selectList("IUsageBatchMapper.findFiscalYears");
    }

    @Override
    public List<UsageBatch> findUsageBatches() {
        return selectList("IUsageBatchMapper.findUsageBatches");
    }
}
