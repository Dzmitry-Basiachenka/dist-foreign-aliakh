package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IFundPoolRepository}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/26/2019
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class FundPoolRepository extends BaseRepository implements IFundPoolRepository {

    @Override
    public void insert(FundPool fundPool) {
        insert("IFundPoolMapper.insert", Objects.requireNonNull(fundPool));
    }

    @Override
    public FundPool findById(String fundPoolId) {
        checkArgument(StringUtils.isNotBlank(fundPoolId));
        return selectOne("IFundPoolMapper.findById", fundPoolId);
    }

    @Override
    public int delete(String fundPoolId) {
        checkArgument(StringUtils.isNotBlank(fundPoolId));
        return delete("IFundPoolMapper.delete", fundPoolId);
    }

    @Override
    public List<String> findNamesByUsageBatchId(String batchId) {
        checkArgument(StringUtils.isNotBlank(batchId));
        return selectList("IFundPoolMapper.findNamesByUsageBatchId", batchId);
    }

    @Override
    public List<FundPool> findByProductFamily(String productFamily) {
        return selectList("IFundPoolMapper.findByProductFamily", Objects.requireNonNull(productFamily));
    }

    @Override
    public List<FundPool> findNotAttachedToScenario() {
        return selectList("IFundPoolMapper.findNotAttachedToScenario");
    }

    @Override
    public int findCountByName(String fundPoolName) {
        checkArgument(StringUtils.isNotBlank(fundPoolName));
        return selectOne("IFundPoolMapper.findCountByName", fundPoolName);
    }
}
