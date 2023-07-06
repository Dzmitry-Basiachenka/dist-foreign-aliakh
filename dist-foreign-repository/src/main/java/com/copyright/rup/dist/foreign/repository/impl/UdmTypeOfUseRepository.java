package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmTypeOfUseRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUdmTypeOfUseRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/2021
 *
 * @author Dzmitry Basiachenka
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmTypeOfUseRepository extends BaseRepository implements IUdmTypeOfUseRepository {

    @Override
    public List<String> findAllUdmTous() {
        return selectList("IUdmTouMapper.findAllUdmTous");
    }

    @Override
    public Map<String, String> findUdmTouToRmsTouMap() {
        UdmTouToRmsTouResultHandler handler = new UdmTouToRmsTouResultHandler();
        getTemplate().select("IUdmTouMapper.findUdmTouToRmsTouMap", handler);
        return handler.getUdmTousToRmsTous();
    }
}
