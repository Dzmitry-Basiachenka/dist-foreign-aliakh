package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclFundPoolRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IAclFundPoolRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Anton Azarenka
 */
@Repository
public class AclFundPoolRepository extends AclBaseRepository implements IAclFundPoolRepository {

    private static final long serialVersionUID = -8805961809668214827L;

    @Override
    public void insert(AclFundPool fundPool) {
        insert("IAclFundPoolMapper.insert", Objects.requireNonNull(fundPool));
    }

    @Override
    public AclFundPool findById(String fundPoolId) {
        return selectOne("IAclFundPoolMapper.findById", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public List<AclFundPoolDetailDto> findDetailDtosByFundPoolId(String fundPoolId) {
        return selectList("IAclFundPoolMapper.findDetailDtosByFundPoolId", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public boolean isFundPoolExists(String name) {
        return selectOne("IAclFundPoolMapper.isFundPoolExist", Objects.requireNonNull(name));
    }

    @Override
    public boolean isLdmtDetailExist(String licenseType) {
        return selectOne("IAclFundPoolMapper.isLdmtDetailExist", Objects.requireNonNull(licenseType));
    }

    @Override
    public void insertDetail(AclFundPoolDetail detail) {
        insert("IAclFundPoolMapper.insertDetail", Objects.requireNonNull(detail));
    }

    @Override
    public List<AclFundPoolDetailDto> findDtosByFilter(AclFundPoolDetailFilter filter) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put("filter", Objects.requireNonNull(filter));
        return selectList("IAclFundPoolMapper.findDtosByFilter", parameters);
    }

    @Override
    public List<AclFundPoolDetail> findDetailsByFundPoolId(String fundPoolId) {
        return selectList("IAclFundPoolMapper.findDetailsByFundPoolId", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public int addLdmtDetailsToFundPool(String fundPoolId, String licenseType, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("fundPoolId", Objects.requireNonNull(fundPoolId));
        parameters.put("licenseType", Objects.requireNonNull(licenseType));
        parameters.put("updateUser", Objects.requireNonNull(userName));
        return selectOne("IAclFundPoolMapper.addLdmtDetailsToFundPool", parameters);
    }

    @Override
    public List<AclFundPool> findAll() {
        return selectList("IAclFundPoolMapper.findAllFundPools");
    }

    @Override
    public List<AclFundPool> findFundPoolsByPeriods(Set<Integer> periods) {
        return selectList("IAclFundPoolMapper.findFundPoolsByPeriods", periods);
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IAclFundPoolMapper.findPeriods");
    }

    @Override
    public void deleteDetailsByFundPoolId(String fundPoolId) {
        delete("IAclFundPoolMapper.deleteDetailsByFundPoolId", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public void deleteById(String fundPoolId) {
        delete("IAclFundPoolMapper.deleteById", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public List<AclFundPool> findFundPoolsByLicenseTypeAndPeriod(String licenseType, Integer period) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("licenseType", Objects.requireNonNull(licenseType));
        parameters.put("period", Objects.requireNonNull(period));
        return selectList("IAclFundPoolMapper.findFundPoolsByLicenseTypeAndPeriod", parameters);
    }
}
