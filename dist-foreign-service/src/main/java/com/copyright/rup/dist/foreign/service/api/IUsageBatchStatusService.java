package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import java.util.List;
import java.util.Set;

/**
 * Interface for usage batch status service.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
public interface IUsageBatchStatusService {

    /**
     * Gets list of usage batch statuses for FAS/FAS2 product families.
     *
     * @param productFamily product family
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> getUsageBatchStatusesFas(String productFamily);

    /**
     * Gets list of usage batch statuses for NTS product family.
     *
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> getUsageBatchStatusesNts();

    /**
     * Gets list of usage batch statuses for AACL product family.
     *
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> getUsageBatchStatusesAacl();

    /**
     * Gets list of usage batch statuses for SAL product family.
     *
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> getUsageBatchStatusesSal();

    /**
     * Returns batch status for selected batch based on provided product family specific statuses.
     *
     * @param batchId              batch identifier
     * @param intermediateStatuses intermediate statuses
     * @return true if batch processing is completed, false - otherwise
     */
    boolean isBatchProcessingCompleted(String batchId, Set<UsageStatusEnum> intermediateStatuses);
}
