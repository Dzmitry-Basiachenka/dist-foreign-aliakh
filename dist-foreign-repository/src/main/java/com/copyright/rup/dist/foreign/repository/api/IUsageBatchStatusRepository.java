package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;

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
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> findUsageBatchStatusesFas(Set<String> batchIds);

    /**
     * Finds list of NTS usage batch statuses for specified usage batch ids.
     *
     * @param batchIds set of usage batch ids
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> findUsageBatchStatusesNts(Set<String> batchIds);

    /**
     * Finds list of usage batch statuses for AACL product family.
     *
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> findUsageBatchStatusesAacl();

    /**
     * Finds list of usage batch statuses for SAL product family.
     *
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> findUsageBatchStatusesSal();

    /**
     * Finds usage batch ids not associated with scenarios for specified product family and start date.
     *
     * @param productFamily product family
     * @param startDate     start date of batch creation
     * @return set of usage batch ids
     */
    Set<String> findUsageBatchIdsByProductFamilyAndStartDateFrom(String productFamily, LocalDate startDate);
}
