package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.List;

/**
 * Interface for service to match usages with works.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 2/21/18
 *
 * @author Aliaksandr Radkevich
 */
public interface IWorkMatchingService {

    /**
     * Finds Wr Wrk Insts for given usages and matches result by standard number.
     *
     * @param usages list of {@link Usage}s to be matched
     * @return list of matched {@link Usage}s
     */
    List<Usage> matchByIdno(List<Usage> usages);

    /**
     * Finds Wr Wrk Insts for given usages and matches result by work title.
     *
     * @param usages list of {@link Usage}s to be matched
     * @return list of matched {@link Usage}s
     */
    List<Usage> matchByTitle(List<Usage> usages);

    /**
     * Updates {@link Usage}s status and Wr Wrk Inst.
     *
     * @param usages list of {@link Usage}s with not blank standard number and title
     */
    void updateStatusForUsagesWithNoStandardNumberAndTitle(List<Usage> usages);
}
