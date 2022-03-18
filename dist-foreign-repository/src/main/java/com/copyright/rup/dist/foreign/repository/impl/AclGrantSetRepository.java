package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantSetRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IAclGrantSetRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AclGrantSetRepository extends BaseRepository implements IAclGrantSetRepository {

    @Override
    public void insert(AclGrantSet grantSet) {
        insert("IAclGrantSetMapper.insert", Objects.requireNonNull(grantSet));
    }

    @Override
    public AclGrantSet findById(String grantSetId) {
        return selectOne("IAclGrantSetMapper.findById", Objects.requireNonNull(grantSetId));
    }

    @Override
    public boolean isGrantSetExist(String grantSetName) {
        return selectOne("IAclGrantSetMapper.isGrantSetExist", Objects.requireNonNull(grantSetName));
    }

    @Override
    public List<AclGrantSet> findAll() {
        return selectList("IAclGrantSetMapper.findAll");
    }

    @Override
    public List<Integer> findGrantPeriods() {
        return selectList("IAclGrantSetMapper.findGrantPeriods");
    }

    @Override
    public void deleteById(String grantSetId) {
        delete("IAclGrantSetMapper.deleteById", Objects.requireNonNull(grantSetId));
    }
}
