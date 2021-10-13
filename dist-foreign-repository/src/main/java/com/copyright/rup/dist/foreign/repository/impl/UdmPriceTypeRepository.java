package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmPriceTypeRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link IUdmPriceTypeRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 10/13/2021
 *
 * @author Ihar Suvorau
 */
@Repository
public class UdmPriceTypeRepository extends BaseRepository implements IUdmPriceTypeRepository {

    @Override
    public List<String> findPriceTypes() {
        return selectList("IUdmPriceTypeMapper.findPriceTypes");
    }

    @Override
    public List<String> findPriceAccessTypes() {
        return selectList("IUdmPriceTypeMapper.findPriceAccessTypes");
    }
}
