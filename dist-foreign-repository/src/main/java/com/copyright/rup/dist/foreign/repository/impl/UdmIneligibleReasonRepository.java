package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.repository.api.IUdmIneligibleReasonRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link IUdmIneligibleReasonRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 07/08/2021
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class UdmIneligibleReasonRepository extends BaseRepository implements IUdmIneligibleReasonRepository {

    @Override
    public List<UdmIneligibleReason> findAll() {
        return selectList("IUdmIneligibleReasonMapper.findAll");
    }
}
