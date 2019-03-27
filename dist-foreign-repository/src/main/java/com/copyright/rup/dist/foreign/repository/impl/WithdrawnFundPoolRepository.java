package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
import com.copyright.rup.dist.foreign.repository.api.IWithdrawnFundPoolRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link IWithdrawnFundPoolRepository}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/26/2019
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class WithdrawnFundPoolRepository extends BaseRepository implements IWithdrawnFundPoolRepository {

    @Override
    public void insert(WithdrawnFundPool fundPool) {
        insert("IWithdrawnFundPoolMapper.insert", checkNotNull(fundPool));
    }

    @Override
    public WithdrawnFundPool findById(String fundPoolId) {
        checkArgument(StringUtils.isNotBlank(fundPoolId));
        return selectOne("IWithdrawnFundPoolMapper.findById", fundPoolId);
    }

    @Override
    public int delete(String fundPoolId) {
        checkArgument(StringUtils.isNotBlank(fundPoolId));
        return delete("IWithdrawnFundPoolMapper.delete", fundPoolId);
    }
}
