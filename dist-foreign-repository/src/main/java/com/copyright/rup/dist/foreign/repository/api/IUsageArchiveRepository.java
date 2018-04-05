package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import java.io.PipedOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Interface for Usage archive repository.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/08/18
 *
 * @author Ihar Suvorau
 */
public interface IUsageArchiveRepository {

    /**
     * Inserts archive Usage into database.
     *
     * @param usage {@link Usage} instance
     */
    void insert(Usage usage);

    /**
     * Gets list of {@link RightsholderTotalsHolder}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier.
     *
     * @param scenarioId  {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link RightsholderTotalsHolder}s
     */
    List<RightsholderTotalsHolder> findRightsholderTotalsHoldersByScenarioId(String scenarioId, String searchValue,
                                                                             Pageable pageable, Sort sort);

    /**
     * Gets count of {@link RightsholderTotalsHolder}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier.
     *
     * @param scenarioId  {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param searchValue search value
     * @return count of {@link RightsholderTotalsHolder}s
     */
    int findRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue);

    /**
     * Gets list of {@link UsageDto}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
     * rightsholder account number.
     *
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> findByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue,
                                                      Pageable pageable, Sort sort);

    /**
     * Gets count of usage details based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
     * rightsholder account number.
     *
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @return count of usage details
     */
    int findCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue);

    /**
     * Finds usages by scenario id and writes them into the output stream in CSV format.
     *
     * @param scenarioId        scenario id
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream);

    /**
     * Removes usages from archive by {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier.
     *
     * @param scenarioId id of scenario for remove usages
     */
    void deleteByScenarioId(String scenarioId);

    /**
     * Finds detail id to {@link Usage} id map from archive by provided detail id.
     *
     * @param detailIds list of usage detail ids
     * @return found {@link Usage} detail id to id map
     */
    Map<Long, String> findDetailIdToIdMap(List<Long> detailIds);

    /**
     * Updates paid information for {@link PaidUsage} by id.
     *
     * @param usage {@link PaidUsage} instance
     */
    void updatePaidInfo(PaidUsage usage);

    /**
     * Finds list of {@link PaidUsage}s with {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#PAID} status.
     *
     * @param limit limitation for selection
     * @return list of found {@link PaidUsage}s
     */
    List<PaidUsage> findPaid(int limit);
}
