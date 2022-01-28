package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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

    @Override
    public int findCountByFilter(AclGrantDetailFilter filter) {
        return selectOne("IAclGrantDetailMapper.findCountByFilter",
            ImmutableMap.of("filter", Objects.requireNonNull(filter)));
    }

    @Override
    public List<AclGrantDetailDto> findDtosByFilter(AclGrantDetailFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("filter", Objects.requireNonNull(filter));
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("IAclGrantDetailMapper.findDtosByFilter", parameters);
    }
}
