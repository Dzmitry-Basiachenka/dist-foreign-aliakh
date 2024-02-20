package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UdmActionReason;

import java.io.Serializable;
import java.util.List;

/**
 * Represents interface of repository for UDM action reasons.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 07/08/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmActionReasonRepository extends Serializable {

    /**
     * Finds list of {@link UdmActionReason}s.
     *
     * @return list of {@link UdmActionReason}s
     */
    List<UdmActionReason> findAll();
}
