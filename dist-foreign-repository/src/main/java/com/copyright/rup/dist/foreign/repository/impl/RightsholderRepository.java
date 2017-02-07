package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link IRightsholderRepository} for MyBatis.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 */
@Repository
public class RightsholderRepository extends BaseRepository implements IRightsholderRepository {

    @Override
    public List<Rightsholder> findRros() {
        return selectList("IRightsholderMapper.findRros");
    }
}
