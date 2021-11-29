package com.copyright.rup.dist.foreign.repository.api;

import java.util.List;

/**
 * Interface for baseline value repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineValueRepository {

    /**
     * Finds list of periods from UDM baseline value.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();
}
