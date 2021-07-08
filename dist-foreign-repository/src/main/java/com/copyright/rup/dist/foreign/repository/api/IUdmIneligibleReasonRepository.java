package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;

import java.util.List;

/**
 * Represents interface of repository for UDM ineligible reasons.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 07/08/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmIneligibleReasonRepository {

    /**
     * Finds list of {@link UdmIneligibleReason}s.
     *
     * @return list of {@link UdmIneligibleReason}s
     */
    List<UdmIneligibleReason> findAll();
}
