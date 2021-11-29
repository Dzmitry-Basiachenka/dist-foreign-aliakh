package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineValueRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link IUdmBaselineValueRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
@Repository
public class UdmBaselineValueRepository extends BaseRepository implements IUdmBaselineValueRepository {

    @Override
    public List<Integer> findPeriods() {
        return selectList("IUdmBaselineValueMapper.findPeriods");
    }
}
