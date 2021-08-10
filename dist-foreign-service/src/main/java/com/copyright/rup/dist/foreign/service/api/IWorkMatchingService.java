package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.UdmUsage;
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
     * Finds Wr Wrk Inst for all usages for FAS product family.
     *
     * @param usages list of {@link Usage}'s
     */
    void matchingFasUsages(List<Usage> usages);

    /**
     * Finds Wr Wrk Inst for all usages for AACL product family.
     *
     * @param usages list of {@link Usage}'s
     */
    void matchingAaclUsages(List<Usage> usages);

    /**
     * Finds Wr Wrk Inst for all usages for SAL product family.
     *
     * @param usages list of {@link Usage}'s
     */
    void matchingSalUsages(List<Usage> usages);

    /**
     * Finds Wr Wrk Inst for all usages for ACL product family.
     *
     * @param usages list of {@link UdmUsage}'s
     */
    void matchingUdmUsages(List<UdmUsage> usages);
}
