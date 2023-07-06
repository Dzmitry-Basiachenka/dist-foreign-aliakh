package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmPriceTypeRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmPriceTypeRepository extends BaseRepository implements IUdmPriceTypeRepository {

    @Override
    public List<String> findAllPriceTypes() {
        return selectList("IUdmPriceTypeMapper.findAllPriceTypes");
    }

    @Override
    public List<String> findAllPriceAccessTypes() {
        return selectList("IUdmPriceTypeMapper.findAllPriceAccessTypes");
    }
}
