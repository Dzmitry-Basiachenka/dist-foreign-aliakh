package com.copyright.rup.dist.foreign.repository.api;

import java.util.List;

/**
 * Represents interface of repository for UDM values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
public interface IUdmValueRepository {

    /**
     * Finds list of periods from UDM value.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();
}
