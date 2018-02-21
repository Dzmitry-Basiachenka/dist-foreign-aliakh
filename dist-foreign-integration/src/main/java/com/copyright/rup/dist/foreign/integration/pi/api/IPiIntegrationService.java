package com.copyright.rup.dist.foreign.integration.pi.api;

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
     * Finds Wr Wrk Insts for given set of IDNOs.
     * If work for particular IDNO is found and they uniquely corresponds to each other, then they will be
     * returned in resulting map.
     * If work for particular IDNO is not found or multiple IDNOs correspond to one Wr Wrk Inst -
     * resulting map will not contain mapping for such IDNOs.
     *
     * @param idnos set of IDNOs to search works by
     * @return map with IDNOs as keys and Wr Wrk Insts as values
     */
    Map<String, Long> findWrWrkInstsByIdno(Set<String> idnos);

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
