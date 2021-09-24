package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link IUdmValueRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
@Repository
public class UdmValueRepository extends BaseRepository implements IUdmValueRepository {

    @Override
    public List<Integer> findPeriods() {
        return selectList("IUdmValueMapper.findPeriods");
    }
}
