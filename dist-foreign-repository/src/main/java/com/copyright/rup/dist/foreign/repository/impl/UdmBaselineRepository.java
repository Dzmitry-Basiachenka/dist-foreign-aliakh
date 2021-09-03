package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IUdmBaselineRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/02/21
 *
 * @author Anton Azarenka
 */
@Repository
public class UdmBaselineRepository extends BaseRepository implements IUdmBaselineRepository {

    @Override
    public Set<String> removeUmdUsagesFromBaseline(Integer period, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("createUser", Objects.requireNonNull(userName));
        return new HashSet<>(selectList("IUdmBaselineMapper.removeFromBaseline", parameters));
    }
}
