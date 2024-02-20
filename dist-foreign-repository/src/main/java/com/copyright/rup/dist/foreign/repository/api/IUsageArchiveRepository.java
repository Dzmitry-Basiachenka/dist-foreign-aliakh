package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import java.io.Serializable;
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
public interface IUsageArchiveRepository extends Serializable {

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
     * Finds list of {@link RightsholderTotalsHolder}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
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
     * Finds count of {@link RightsholderTotalsHolder}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier.
     *
     * @param scenarioId  {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param searchValue search value
     * @return count of {@link RightsholderTotalsHolder}s
     */
    int findRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue);

    /**
     * Finds list of {@link UsageDto}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
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
     * Finds count of usage details based on {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier and
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
     * Insert AACL usage with paid information.
     *
     * @param paidUsage {@link PaidUsage} instance
     */
    void insertAaclPaid(PaidUsage paidUsage);

    /**
     * Insert SAL usage with paid information.
     *
     * @param paidUsage {@link PaidUsage} instance
     */
    void insertSalPaid(PaidUsage paidUsage);

    /**
     * Moves usages from {@link com.copyright.rup.dist.foreign.domain.FundPool} related to selected
     * {@link com.copyright.rup.dist.foreign.domain.Scenario} to archive table.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     */
    void moveFundUsagesToArchive(String scenarioId);

    /**
     * Copies usages related to specified scenario to archive table with {@link UsageStatusEnum#SENT_TO_LM} status.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param userName   name of user who performs action
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

    /**
     * Finds AACL {@link Usage}s with populated RH uid information by specified ids.
     *
     * @param usageIds list of usage ids
     * @return list of found {@link Usage}s
     */
    List<Usage> findAaclByIds(List<String> usageIds);

    /**
     * Finds SAL {@link Usage}s with populated RH uid information by specified ids.
     *
     * @param usageIds list of usage ids
     * @return list of found {@link Usage}s
     */
    List<Usage> findSalByIds(List<String> usageIds);

    /**
     * Finds {@link Usage} info for sending to LM.
     *
     * @param usageIds list of usage ids
     * @return list of found {@link Usage}s
     */
    List<Usage> findForSendToLmByIds(List<String> usageIds);

    /**
     * Finds {@link UsageDto}s by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> findAaclDtosByScenarioId(String scenarioId);

    /**
     * Finds count of archived AACL usage details based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier and rightsholder account number.
     *
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @return count of usage details
     */
    int findAaclCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue);

    /**
     * Finds list of archived AACL {@link UsageDto}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier and rightsholder account number.
     *
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> findAaclByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber,
                                                          String searchValue, Pageable pageable, Sort sort);

    /**
     * Finds count of archived SAL usage details based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier and rightsholder account number.
     *
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @return count of usage details
     */
    int findSalCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue);

    /**
     * Finds list of archived SAL {@link UsageDto}s based on {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * identifier and rightsholder account number.
     *
     * @param scenarioId    {@link com.copyright.rup.dist.foreign.domain.Scenario} identifier
     * @param accountNumber selected rightsholder account number
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> findSalByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber,
                                                         String searchValue, Pageable pageable, Sort sort);
}
