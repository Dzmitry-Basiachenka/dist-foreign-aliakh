package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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

    private static final long serialVersionUID = 2069160090027856203L;

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
    public List<FundPool> findNtsNotAttachedToScenario() {
        return selectList("IFundPoolMapper.findNtsNotAttachedToScenario", FdaConstants.NTS_PRODUCT_FAMILY);
    }

    @Override
    public List<FundPool> findAaclNotAttachedToScenario() {
        return selectList("IFundPoolMapper.findAaclNotAttachedToScenario", FdaConstants.AACL_PRODUCT_FAMILY);
    }

    @Override
    public List<FundPool> findSalNotAttachedToScenario() {
        return selectList("IFundPoolMapper.findSalNotAttachedToScenario", FdaConstants.SAL_PRODUCT_FAMILY);
    }

    @Override
    public boolean fundPoolExists(String productFamily, String name) {
        checkArgument(StringUtils.isNotBlank(name));
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("productFamily", Objects.requireNonNull(productFamily));
        params.put("name", escapeSqlLikePattern(name));
        return selectOne("IFundPoolMapper.fundPoolExists", params);
    }

    @Override
    public void insertDetail(FundPoolDetail detail) {
        insert("IFundPoolMapper.insertDetail", Objects.requireNonNull(detail));
    }

    @Override
    public List<FundPoolDetail> findDetailsByFundPoolId(String fundPoolId) {
        return selectList("IFundPoolMapper.findDetailsByFundPoolId", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public void deleteDetailsByFundPoolId(String fundPoolId) {
        delete("IFundPoolMapper.deleteDetailsByFundPoolId", Objects.requireNonNull(fundPoolId));
    }
}
