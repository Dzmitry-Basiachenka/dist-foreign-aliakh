package com.copyright.rup.dist.foreign.integration.pi.api;

/**
 * Interface for integrating service with RUP Open Search API.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 03/06/2023
 *
 * @author Mikita Maistrenka
 */
public interface IPiDeletedWorkIntegrationService {

    /**
     * Finds deleted Work by Wr Wrk Inst.
     *
     * @param wrWrkInst Wr Wrk Inst to search work by
     * @return boolean result shows was soft deleted work from MDWMS
     */
    boolean isDeletedWork(Long wrWrkInst);
}
