package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAclFundPoolRepository;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public void insert(AclFundPool fundPool) {
        insert("IAclFundPoolMapper.insert", Objects.requireNonNull(fundPool));
    }

    @Override
    public AclFundPool findById(String fundPoolId) {
        return selectOne("IAclFundPoolMapper.findById", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public boolean isFundPoolExists(String name) {
        return selectOne("IAclFundPoolMapper.isFundPoolExist", Objects.requireNonNull(name));
    }

    @Override
    public void insertDetail(AclFundPoolDetail detail) {
        insert("IAclFundPoolMapper.insertDetail", Objects.requireNonNull(detail));
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
}
