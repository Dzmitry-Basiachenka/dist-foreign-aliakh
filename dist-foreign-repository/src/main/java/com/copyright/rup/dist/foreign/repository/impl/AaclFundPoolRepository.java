package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IAaclFundPoolRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AaclFundPoolRepository extends BaseRepository implements IAaclFundPoolRepository {

    @Override
    public boolean aaclFundPoolExists(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IAaclFundPoolMapper.aaclFundPoolExists", escapeSqlLikePattern(name));
    }

    @Override
    public Set<Integer> findAggregateLicenseeClassIds() {
        return selectSet("IAaclFundPoolMapper.findAggregateLicenseeClassIds");
    }

    @Override
    public List<AaclFundPool> findAll() {
        return selectList("IAaclFundPoolMapper.findAll");
    }

    @Override
    public List<AaclFundPoolDetail> findDetailsByFundPoolId(String fundPoolId) {
        return selectList("IAaclFundPoolMapper.findDetailsByFundPoolId", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public void deleteById(String fundPoolId) {
        delete("IAaclFundPoolMapper.deleteById", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public void deleteDetailsByFundPoolId(String fundPoolId) {
        delete("IAaclFundPoolMapper.deleteDetailsByFundPoolId", Objects.requireNonNull(fundPoolId));
    }
}
