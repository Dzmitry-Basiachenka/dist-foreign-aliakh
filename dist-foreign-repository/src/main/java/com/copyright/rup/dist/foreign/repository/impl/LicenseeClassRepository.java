package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.repository.api.ILicenseeClassRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link ILicenseeClassRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
@Repository
public class LicenseeClassRepository extends BaseRepository implements ILicenseeClassRepository {

    private static final long serialVersionUID = 7410353313643877436L;

    @Override
    public boolean aaclDetailLicenseeClassExists(String enrollmentProfile, String discipline) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("enrollmentProfile", escapeSqlLikePattern(Objects.requireNonNull(enrollmentProfile)));
        parameters.put("discipline", escapeSqlLikePattern(Objects.requireNonNull(discipline)));
        return selectOne("ILicenseeClassMapper.aaclDetailLicenseeClassExists", parameters);
    }

    @Override
    public boolean detailLicenseeClassExists(Integer detailLicenseeClassId) {
        return selectOne("ILicenseeClassMapper.aclDetailLicenseeClassExists",
            Objects.requireNonNull(detailLicenseeClassId));
    }

    @Override
    public List<AggregateLicenseeClass> findAggregateLicenseeClassesByProductFamily(String productFamily) {
        return selectList("ILicenseeClassMapper.findAggregateLicenseeClassesByProductFamily",
            Objects.requireNonNull(productFamily));
    }

    @Override
    public List<DetailLicenseeClass> findDetailLicenseeClassesByProductFamily(String productFamily) {
        return selectList("ILicenseeClassMapper.findDetailLicenseeClassesByProductFamily",
            Objects.requireNonNull(productFamily));
    }

    @Override
    public List<DetailLicenseeClass> findDetailLicenseeClassesByScenarioId(String scenarioId) {
        return selectList("ILicenseeClassMapper.findDetailLicenseeClassesByScenarioId",
            Objects.requireNonNull(scenarioId));
    }
}
