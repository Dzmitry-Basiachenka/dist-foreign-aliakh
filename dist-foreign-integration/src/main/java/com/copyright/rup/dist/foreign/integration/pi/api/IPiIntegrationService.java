package com.copyright.rup.dist.foreign.integration.pi.api;

import com.copyright.rup.dist.foreign.domain.Work;

import java.util.Map;
import java.util.Set;

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
     * Finds Wr Wrk Insts for given set of titles.
     * If work for particular title is found and they uniquely corresponds to each other, then they will be
     * returned in resulting map.
     * If work for particular title is not found or multiple standard numbers correspond to one Wr Wrk Inst -
     * resulting map will not contain mapping for such titles.
     *
     * @param titles set of work titles to search works by
     * @return map with titles as keys and Wr Wrk Insts as values
     */
    Map<String, Long> findWrWrkInstsByTitles(Set<String> titles);
}
