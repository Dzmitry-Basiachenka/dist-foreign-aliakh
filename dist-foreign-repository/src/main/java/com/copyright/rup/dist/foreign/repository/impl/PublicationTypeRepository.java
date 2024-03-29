package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.repository.api.IPublicationTypeRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IPublicationTypeRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
@Repository
public class PublicationTypeRepository extends BaseRepository implements IPublicationTypeRepository {

    private static final long serialVersionUID = -2221375931172421668L;

    @Override
    public boolean isExistForProductFamily(String pubTypeName, String productFamily) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("pubTypeName", escapeSqlLikePattern(Objects.requireNonNull(pubTypeName)));
        params.put("productFamily", Objects.requireNonNull(productFamily));
        return selectOne("IPublicationTypeMapper.isExistForProductFamily", params);
    }

    @Override
    public List<PublicationType> findByProductFamily(String productFamily) {
        return selectList("IPublicationTypeMapper.findByProductFamily", Objects.requireNonNull(productFamily));
    }

    @Override
    public List<AclPublicationType> findAclHistoricalPublicationTypes() {
        return selectList("IPublicationTypeMapper.findAclHistoricalPublicationTypes");
    }

    @Override
    public void insertAclHistoricalPublicationType(AclPublicationType publicationType) {
        insert("IPublicationTypeMapper.insertAclHistoricalPublicationType", Objects.requireNonNull(publicationType));
    }
}
