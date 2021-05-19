package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.Usage;

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
     * Finds Wr Wrk Inst for given usage and matches result by standard number.
     *
     * @param usage {@link Usage} to be matched
     */
    void matchByStandardNumber(Usage usage);

    /**
     * Finds Wr Wrk Inst for given UDM usage and matches result by standard number.
     *
     * @param usage {@link UdmUsage} to be matched
     */
    void matchByStandardNumber(UdmUsage usage);

    /**
     * Finds Wr Wrk Inst for given usage and matches result by work title.
     *
     * @param usage {@link Usage} to be matched
     */
    void matchByTitle(Usage usage);

    /**
     * Finds Wr Wrk Inst for given UDM usage and matches result by reported title.
     *
     * @param usage {@link Usage} to be matched
     */
    void matchByTitle(UdmUsage usage);

    /**
     * Matches usage by Wr Wrk Inst.
     *
     * @param usage {@link Usage} to be matched
     */
    void matchByWrWrkInst(Usage usage);

    /**
     * Updates {@link Usage} status and Wr Wrk Inst.
     *
     * @param usage {@link Usage} to update
     */
    void updateStatusForUsageWithoutStandardNumberAndTitle(Usage usage);
}
