package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IAclGrantDetailRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AclGrantDetailRepository extends BaseRepository implements IAclGrantDetailRepository {

    @Override
    public void insert(AclGrantDetail grantDetail) {
        insert("IAclGrantDetailMapper.insert", Objects.requireNonNull(grantDetail));
    }

    @Override
    public List<AclGrantDetail> findByIds(List<String> grantDetailIds) {
        return selectList("IAclGrantDetailMapper.findByIds", Objects.requireNonNull(grantDetailIds));
    }
}
