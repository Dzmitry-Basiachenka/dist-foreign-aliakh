package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;

import java.util.List;

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
     * Gets list of usage batch statuses for FAS product family.
     *
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> getUsageBatchStatusesFas();

    /**
     * Gets list of usage batch statuses for FAS2 product family.
     *
     * @return list of {@link UsageBatchStatus}
     */
    List<UsageBatchStatus> getUsageBatchStatusesFas2();

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
}
