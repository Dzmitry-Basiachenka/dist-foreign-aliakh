package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;

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
    public Set<Integer> findAggregateLicenseeClassIds() {
        return selectSet("IAaclFundPoolMapper.findAggregateLicenseeClassIds");
    }

    @Override
    public List<FundPool> findAll() {
        return selectList("IAaclFundPoolMapper.findAll", FdaConstants.AACL_PRODUCT_FAMILY);
    }

    @Override
    public List<FundPoolDetail> findDetailsByFundPoolId(String fundPoolId) {
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

    @Override
    public void insertDetail(FundPoolDetail detail) {
        insert("IAaclFundPoolMapper.insertDetail", Objects.requireNonNull(detail));
    }
}
