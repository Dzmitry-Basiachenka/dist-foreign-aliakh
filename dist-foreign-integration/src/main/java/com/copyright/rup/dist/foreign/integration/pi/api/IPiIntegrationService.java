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
     * Finds {@link Work} for given IDNO and title.
     * Returns null if there are multiple search hits or empty result for particular IDNO and title is found.
     *
     * @param idno  indo to match
     * @param title title to match
     * @return instance of {@link Work}
     */
    Work findWorkByIdnoAndTitle(String idno, String title);

    /**
     * Finds Wr Wrk Inst for given title.
     * If work for particular title is found and they uniquely corresponds to each other, work will be returned.
     * If work for particular title is not found or multiple standard numbers correspond to one Wr Wrk Inst -
     * return {@code null}.
     *
     * @param title title to search work by
     * @return wr wrk inst value
     */
    Long findWrWrkInstByTitle(String title);
}
