package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmProxyValueRepository;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IUdmProxyValueRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class UdmProxyValueRepository extends BaseRepository implements IUdmProxyValueRepository {

    @Override
    public void deleteProxyValues(Integer period) {
        delete("IUdmProxyValueMapper.deleteProxyValues", Objects.requireNonNull(period));
    }

    @Override
    public void insertProxyValues(Integer period, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("createUser", Objects.requireNonNull(userName));
        parameters.put("updateUser", userName);
        insert("IUdmProxyValueMapper.insertProxyValues", parameters);
    }

    @Override
    public int applyProxyValues(Integer period, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("updateUser", userName);
        return selectOne("IUdmProxyValueMapper.applyProxyValues", parameters);
    }
}
