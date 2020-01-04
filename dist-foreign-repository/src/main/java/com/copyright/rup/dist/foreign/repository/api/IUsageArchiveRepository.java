package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import java.util.List;
import java.util.Set;

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
     * Deletes archived {@link Usage}s by given batch identifier.
     *
     * @param batchId batch identifier
     */
    void deleteByBatchId(String batchId);

    /**
     * Deletes archived {@link Usage}s by their identifiers.
     *
     * @param usageIds list of usage identifiers
     */
    void deleteByIds(List<String> usageIds);

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
     * Updates paid information for {@link PaidUsage} by id.
     *
     * @param usage {@link PaidUsage} instance
     */
    void updatePaidInfo(PaidUsage usage);

    /**
     * Updates usages status by provided id.
     *
     * @param usageIds set of usages ids
     * @param status   {@link UsageStatusEnum}
     */
    void updateStatus(Set<String> usageIds, UsageStatusEnum status);

    /**
     * Finds list of {@link PaidUsage}s by provided ids and status.
     *
     * @param usageIds list of usages ids
     * @param status   {@link UsageStatusEnum} instance
     * @return list of found {@link PaidUsage}s
     */
    List<PaidUsage> findByIdAndStatus(List<String> usageIds, UsageStatusEnum status);

    /**
     * Gets count of archived {@link Usage}s based on markets and period for NTS batch creation.
     *
     * @param marketPeriodFrom market period from
     * @param marketPeriodTo   market period to
     * @param markets          set of selected markets
     * @return usages count
     */
    int findCountForNtsBatch(Integer marketPeriodFrom, Integer marketPeriodTo, Set<String> markets);

    /**
     * Finds usages ids with {@link UsageStatusEnum#PAID} status.
     *
     * @return list of found usages ids
     */
    List<String> findPaidIds();

    /**
     * Insert usage with paid information.
     *
     * @param paidUsage {@link PaidUsage} instance
     */
    void insertPaid(PaidUsage paidUsage);

    /**
     * Moves usages from {@link com.copyright.rup.dist.foreign.domain.PreServiceFeeFund} related to selected
     * {@link com.copyright.rup.dist.foreign.domain.Scenario} to archive table.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     */
    void moveFundUsagesToArchive(String scenarioId);

    /**
     * Copies usages related to specified scenario to archive table with {@link UsageStatusEnum#SENT_TO_LM} status.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param userName   name of user who peforms action
     * @return list of moved usage ids
     */
    List<String> copyToArchiveByScenarioId(String scenarioId, String userName);

    /**
     * Copies NTS usages grouped by RH and related to specified scenario to archive table
     * with {@link UsageStatusEnum#SENT_TO_LM} status.
     * Generates new UUID for usage and sets Wr Wrk Inst and Work Title predefined for NTS.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param userName   name of user who peforms action
     * @return list of moved usage ids
     */
    List<String> copyNtsToArchiveByScenarioId(String scenarioId, String userName);

    /**
     * Finds {@link Usage}s with populated RH uid information by specified ids.
     *
     * @param usageIds list of usage ids
     * @return list of found {@link Usage}s
     */
    List<Usage> findByIds(List<String> usageIds);
}
