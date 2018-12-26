package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Usage;

/**
 * Interface represents API for working with RA functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/22/2018
 *
 * @author Nikita Levyankov
 */
public interface IRightsService {

    /**
     * Finds list of {@link com.copyright.rup.dist.foreign.domain.Usage}s with specified product family and
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#WORK_FOUND} and
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SENT_FOR_RA} statuses and sends Wr Wrk Insts to RMS
     * to get Grants and update RHs information.
     *
     * @param productFamily product family
     */
    void updateRights(String productFamily);

    /**
     * Sends Wr Wrk Inst to RMS to get Grants and updates usage status based on response.
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE} if rhAccountNumber was found in RMS,
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND} otherwise.
     *
     * @param usage {@link Usage} to update
     */
    void updateRight(Usage usage);

    /**
     * Finds list of {@link com.copyright.rup.dist.foreign.domain.Usage}s with
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND}
     * status and sends Wr Wrk Insts to RMS for rights assignment.
     * Updates {@link com.copyright.rup.dist.foreign.domain.Usage}s status to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SENT_FOR_RA}
     * and writes audit with job id information only after successful sending.
     */
    void sendForRightsAssignment();
}
