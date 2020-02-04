package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IDetailLicenseeClassRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IDetailLicenseeClassRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
@Repository
public class DetailLicenseeClassRepository extends BaseRepository implements IDetailLicenseeClassRepository {

    @Override
    public boolean isLicenseeClassIdExist(String enrollmentProfile, String discipline) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("enrollmentProfile", escapeSqlLikePattern(Objects.requireNonNull(enrollmentProfile)));
        parameters.put("discipline", escapeSqlLikePattern(Objects.requireNonNull(discipline)));
        return selectOne("IDetailLicenseeClassMapper.isLicenseeClassIdExist", parameters);
    }
}
