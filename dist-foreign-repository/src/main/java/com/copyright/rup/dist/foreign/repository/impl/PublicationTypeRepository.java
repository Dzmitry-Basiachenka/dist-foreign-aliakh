package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IPublicationTypeRepository;

import org.springframework.stereotype.Repository;

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

    @Override
    public String findIdByName(String pubTypeName) {
        return selectOne("IPublicationTypeMapper.findIdByName", pubTypeName);
    }
}
