package com.copyright.rup.dist.foreign.service.api.acl;

import java.util.List;

/**
 * Interface for baseline value service.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineValueService {

    /**
     * Gets all available periods for baseline value.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();
}
