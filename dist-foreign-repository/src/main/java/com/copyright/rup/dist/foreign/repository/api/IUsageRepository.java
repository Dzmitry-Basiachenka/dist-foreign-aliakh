package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;

import java.io.OutputStream;
import java.util.List;

/**
 * Interface for Usage repository.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/02/17
 *
 * @author Darya Baraukova
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public interface IUsageRepository {

    /**
     * Inserts Usage into database.
     *
     * @param usage {@link Usage} instance
     */
    void insertUsage(Usage usage);

    /**
     * Finds the usage based on applied filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> findByFilter(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int getUsagesCount(UsageFilter filter);

    /**
     * Finds usages according to given {@link UsageFilter} and writes them to the output stream in CSV format.
     *
     * @param filter       instance of {@link UsageFilter}
     * @param outputStream instance of {@link OutputStream}
     */
    void writeUsagesCsvReport(UsageFilter filter, OutputStream outputStream);

    /**
     * Deletes all {@link Usage}s from the batch with given id.
     *
     * @param batchId {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id
     */
    void deleteUsageBatchDetails(String batchId);

    /**
     * Finds the {@link Usage}s only with information about gross amount, net amount and reported value
     * based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the list of {@link Usage}s only with information about gross amount, net amount and reported value
     */
    List<Usage> findUsagesWithAmounts(UsageFilter filter);

    /**
     * Updates scenario id, updated user name and status to 'LOCKED' for {@link Usage}s.
     *
     * @param usageIds   list of usage identifiers
     * @param scenarioId scenario identifier
     * @param updateUser name of user who performed this action
     */
    void addUsagesToScenario(List<String> usageIds, String scenarioId, String updateUser);

    /**
     * Deletes {@link Usage}s from scenario. Reverts status of {@link Usage}s
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE} and set scenario id as {@code null}.
     *
     * @param scenarioId scenario identifier
     * @param updateUser name of user who performed this action
     */
    void deleteUsagesFromScenario(String scenarioId, String updateUser);
}
