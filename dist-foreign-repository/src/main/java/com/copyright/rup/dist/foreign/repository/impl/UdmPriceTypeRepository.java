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

    private static final long serialVersionUID = -2557037867795039284L;

    @Override
    public List<String> findAllPriceTypes() {
        return selectList("IUdmPriceTypeMapper.findAllPriceTypes");
    }

    @Override
    public List<String> findAllPriceAccessTypes() {
        return selectList("IUdmPriceTypeMapper.findAllPriceAccessTypes");
    }
}
