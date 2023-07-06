package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmProxyValueRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmProxyValueRepository extends BaseRepository implements IUdmProxyValueRepository {

    @Override
    public List<Integer> findPeriods() {
        return selectList("IUdmProxyValueMapper.findPeriods");
    }

    @Override
    public void deleteProxyValues(Integer period) {
        delete("IUdmProxyValueMapper.deleteProxyValues", Objects.requireNonNull(period));
    }

    @Override
    public void clearProxyValues(Integer period, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("updateUser", userName);
        update("IUdmProxyValueMapper.clearProxyValues", parameters);
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
    public List<String> applyProxyValues(Integer period, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("updateUser", userName);
        return selectList("IUdmProxyValueMapper.applyProxyValues", parameters);
    }

    @Override
    public List<UdmProxyValueDto> findDtosByFilter(UdmProxyValueFilter filter) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put("filter", Objects.requireNonNull(filter));
        return selectList("IUdmProxyValueMapper.findDtosByFilter", parameters);
    }
}
