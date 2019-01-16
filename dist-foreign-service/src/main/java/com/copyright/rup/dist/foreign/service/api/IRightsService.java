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
     * Finds list of {@link com.copyright.rup.dist.foreign.domain.Usage}s with FAS and FAS2 product families and
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SENT_FOR_RA} status, sends their Wr Wrk Insts
     * to RMS for getting Grants and updates RHs information.
     */
    void updateRightsSentForRaUsages();

    /**
     * Sends Wr Wrk Inst to RMS to get Grants and updates usage status and usage RH and writes audit based on response.
     * Sets usage status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_FOUND} and writes
     * {@link com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum#RH_FOUND} audit if RH was found in RMS,
     * and sets usage status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND} and writes
     * {@link com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum#RH_NOT_FOUND} audit otherwise.
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
