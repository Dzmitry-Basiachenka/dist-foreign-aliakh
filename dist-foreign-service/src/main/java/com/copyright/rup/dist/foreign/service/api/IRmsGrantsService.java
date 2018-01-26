package com.copyright.rup.dist.foreign.service.api;

import java.util.List;
import java.util.Map;

/**
 * Interface for service to read grants from RMS system.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/2018
 *
 * @author Aliaksandr Liakh
 */
public interface IRmsGrantsService {

    /**
     * Gets rightsholders' account numbers by the list of wr wrk insts.
     *
     * @param wrWrkInsts list of wr wrk insts
     * @return map from WrWrkInsts to rightsholders' account numbers,
     *         WrWrkInsts without suitable grants not presented in key set
     */
    Map<Long, Long> getAccountNumbersByWrWrkInsts(List<Long> wrWrkInsts);
}
