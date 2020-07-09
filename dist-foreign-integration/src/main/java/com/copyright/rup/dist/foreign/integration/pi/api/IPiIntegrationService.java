package com.copyright.rup.dist.foreign.integration.pi.api;

import com.copyright.rup.dist.foreign.domain.Work;

/**
 * Interface for service for integrating with RUP Elastic Search API.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 02/09/2018
 *
 * @author Aliaksandr Radkevich
 */
public interface IPiIntegrationService {

    /**
     * Finds {@link Work} for given standard number.
     *
     * @param standardNumber standard number to match
     * @return instance of {@link Work}
     */
    Work findWorkByStandardNumber(String standardNumber);

    /**
     * Finds {@link Work} for given title.
     * If work for particular title is found and they uniquely corresponds to each other, {@link Work} will be returned.
     *
     * @param title title to search work by
     * @return instance of {@link Work}
     */
    Work findWorkByTitle(String title);

    /**
     * Finds {@link Work} by Wr Wrk Inst.
     *
     * @param wrWrkInst Wr Wrk Inst to search work by
     * @return instance of {@link Work}
     */
    Work findWorkByWrWrkInst(Long wrWrkInst);
}
