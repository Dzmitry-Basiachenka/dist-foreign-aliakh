package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.Set;

/**
 * Represents interface of service for usage business logic.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 * @author Mikalai Bezmen
 */
public interface IUsageService {

    /**
     * Gets list of {@link UsageDto}s based on applied filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> getUsages(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}.
     * @return count of usages
     */
    int getUsagesCount(UsageFilter filter);

    /**
     * Writes usages found by filter into csv output stream.
     *
     * @param filter            instance of {@link UsageFilter}
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream);

    /**
     * Writes scenario usages into csv output stream.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Writes errors information into csv output stream.
     *
     * @param csvProcessingResult instance of {@link CsvProcessingResult}
     * @param outputStream        instance of {@link OutputStream}
     */
    void writeErrorsToFile(CsvProcessingResult<Usage> csvProcessingResult, OutputStream outputStream);

    /**
     * Inserts usages.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int insertUsages(UsageBatch usageBatch, List<Usage> usages);

    /**
     * Deletes all {@link Usage}s associated with the given {@link UsageBatch}.
     *
     * @param usageBatch {@link UsageBatch} to delete usages from
     */
    void deleteUsageBatchDetails(UsageBatch usageBatch);

    /**
     * Gets the {@link Usage}s based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the list of {@link Usage}s only with information about gross amount, net amount, reported value and
     * rightsholder
     */
    List<Usage> getUsagesWithAmounts(UsageFilter filter);

    /**
     * Updates {@link Scenario} id, updated user name and status to 'LOCKED' for {@link Usage}s.
     *
     * @param usages   list of {@link Usage}s
     * @param scenario {@link Scenario}
     */
    void addUsagesToScenario(List<Usage> usages, Scenario scenario);

    /**
     * Deletes {@link Usage}s from {@link Scenario}. Reverts status of {@link Usage}s
     * to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE} and set scenario id as {@code null}.
     *
     * @param scenarioId scenario identifier
     */
    void deleteUsagesFromScenario(String scenarioId);

    /**
     * Gets duplicate detail ids of {@link Usage}s which are already presented in database.
     *
     * @param detailIds list of detail ids
     * @return set of duplicate detail ids
     */
    Set<Long> getDuplicateDetailIds(List<Long> detailIds);

    /**
     * Gets {@link RightsholderTotalsHolder}s based on {@link Scenario} identifier.
     *
     * @param scenarioId  {@link Scenario} identifier
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link RightsholderTotalsHolder}s
     */
    List<RightsholderTotalsHolder> getRightsholderTotalsHoldersByScenarioId(String scenarioId, String searchValue,
                                                                            Pageable pageable, Sort sort);

    /**
     * Gets count of {@link RightsholderTotalsHolder}s based on {@link Scenario} identifier.
     *
     * @param scenarioId  {@link Scenario} identifier
     * @param searchValue search value
     * @return count of {@link RightsholderTotalsHolder}s
     */
    int getRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue);

    /**
     * Gets count of usage details based on {@link Scenario} identifier and rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenarioId    {@link Scenario} identifier
     * @param searchValue   search value
     * @return count of usage details
     */
    int getCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue);

    /**
     * Gets list of {@link UsageDto}s based on {@link Scenario} identifier and rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenarioId    {@link Scenario} identifier
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> getByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue,
                                                     Pageable pageable, Sort sort);
}
