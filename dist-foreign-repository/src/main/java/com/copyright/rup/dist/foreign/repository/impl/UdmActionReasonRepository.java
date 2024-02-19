package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.repository.api.IUdmActionReasonRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link IUdmActionReasonRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 07/08/2021
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class UdmActionReasonRepository extends BaseRepository implements IUdmActionReasonRepository {

    private static final long serialVersionUID = -7987466102114322737L;

    @Override
    public List<UdmActionReason> findAll() {
        return selectList("IUdmActionReasonMapper.findAll");
    }
}
