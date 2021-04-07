package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Interface for usage batch status repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/10/2021
 *
 * @author Ihar Suvorau
 */
public interface IUsageBatchStatusRepository {

    /**
     * Finds list of FAS/FAS2 usage batch statuses for specified usage batch ids.
     *
     * @param batchIds set of usage batch ids
     * @return list of {@link UsageBatchStatus}es
     */
    List<UsageBatchStatus> findUsageBatchStatusesFas(Set<String> batchIds);

    /**
     * Finds list of NTS usage batch statuses for specified usage batch ids.
     *
     * @param batchIds set of usage batch ids
     * @return list of {@link UsageBatchStatus}es
     */
    List<UsageBatchStatus> findUsageBatchStatusesNts(Set<String> batchIds);

    /**
     * Finds list of AACL usage batch statuses for specified usage batch ids.
     *
     * @param batchIds set of usage batch ids
     * @return list of {@link UsageBatchStatus}es
     */
    List<UsageBatchStatus> findUsageBatchStatusesAacl(Set<String> batchIds);

    /**
     * Finds list of SAL usage batch statuses for specified usage batch ids.
     *
     * @param batchIds set of usage batch ids
     * @return list of {@link UsageBatchStatus}es
     */
    List<UsageBatchStatus> findUsageBatchStatusesSal(Set<String> batchIds);

    /**
     * Finds usage batch ids not associated with scenarios and pre-service fee funds for FAS/FAS2 product families
     * and start date.
     *
     * @param productFamily product family
     * @param startDate     start date of batch creation
     * @return set of usage batch ids
     */
    Set<String> findFasUsageBatchIdsEligibleForStatistic(String productFamily, LocalDate startDate);

    /**
     * Finds usage batch ids not associated with scenarios for specified product family and start date.
     *
     * @param productFamily product family
     * @param startDate     start date of batch creation
     * @return set of usage batch ids
     */
    Set<String> findUsageBatchIdsEligibleForStatistic(String productFamily, LocalDate startDate);

    /**
     * Verifies that batch processing is completed for specified product family specific statuses.
     *
     * @param batchId              batch identifier
     * @param intermediateStatuses intermediate statuses
     * @return true if batch status is completed, false - otherwise
     */
    boolean isBatchProcessingCompleted(String batchId, Set<UsageStatusEnum> intermediateStatuses);
}
