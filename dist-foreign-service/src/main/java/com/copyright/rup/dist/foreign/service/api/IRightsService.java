package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.job.JobInfo;
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
     *
     * @return {@link JobInfo} instance with information about job name, execution status and job result
     */
    JobInfo updateRightsSentForRaUsages();

    /**
     * Sends Wr Wrk Inst to RMS to get Grants and updates usage status and usage RH based on response. Sets usage status
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_FOUND} if RH was found in RMS, and
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND} otherwise.
     * In case of logAction parameter is {@code true} it writes
     * {@link com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum#RH_FOUND} audit if RH was found in RMS,
     * and {@link com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum#RH_NOT_FOUND} audit if RH wasn't found.
     *
     * @param usage     {@link Usage} to update
     * @param logAction defines whether usage audit should be added or not
     */
    void updateRight(Usage usage, boolean logAction);

    /**
     * Finds list of {@link com.copyright.rup.dist.foreign.domain.Usage}s with
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND}
     * status and sends Wr Wrk Insts to RMS for rights assignment.
     * Updates {@link com.copyright.rup.dist.foreign.domain.Usage}s status to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SENT_FOR_RA}
     * and writes audit with job name information only after successful sending.
     *
     * @return {@link JobInfo} instance with information about job name, execution status and job result
     */
    JobInfo sendForRightsAssignment();
}
