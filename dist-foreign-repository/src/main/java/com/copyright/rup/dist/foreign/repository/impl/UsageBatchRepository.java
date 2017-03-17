package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;

import org.apache.commons.lang3.StringUtils;
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
 * @author Aliaksandr Radkevich
 */
@Repository
public class UsageBatchRepository extends BaseRepository implements IUsageBatchRepository {

    @Override
    public void insert(UsageBatch usageBatch) {
        insert("IUsageBatchMapper.insert", checkNotNull(usageBatch));
    }

    @Override
    public List<Integer> findFiscalYears() {
        return selectList("IUsageBatchMapper.findFiscalYears");
    }

    @Override
    public List<UsageBatch> findUsageBatches() {
        return selectList("IUsageBatchMapper.findUsageBatches");
    }

    @Override
    public int getUsageBatchesCountByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IUsageBatchMapper.getUsageBatchesCountByName", name);
    }

    @Override
    public void deleteUsageBatch(String batchId) {
        checkArgument(StringUtils.isNotBlank(batchId));
        delete("IUsageBatchMapper.deleteUsageBatch", batchId);
    }

    /**
     * Finds usage batch by provided name.
     *
     * @param name usage batch name
     * @return found {@link UsageBatch} instance
     */
    UsageBatch findUsageBatchByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IUsageBatchMapper.findUsageBatchByName", name);
    }
}
