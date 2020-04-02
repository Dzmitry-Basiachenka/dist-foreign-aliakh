package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Interface for AACL usage repository.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/21/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclUsageRepository {

    /**
     * Inserts AACL usage into database.
     *
     * @param usage {@link Usage} instance
     */
    void insert(Usage usage);

    /**
     * Inserts AACL usage from baseline.
     *
     * @param periods  baseline usage periods
     * @param batchId  AACL {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id to add created usages to
     * @param userName name of user
     * @return list of inserted {@link Usage} ids
     */
    List<String> insertFromBaseline(Set<Integer> periods, String batchId, String userName);

    /**
     * Updates classified fields for AACL usages in database.
     *
     * @param usages   {@link AaclClassifiedUsage} instance
     * @param userName name of user
     */
    void updateClassifiedUsages(List<AaclClassifiedUsage> usages, String userName);

    /**
     * Deletes AACL {@link Usage} by given id.
     *
     * @param usageId usage identifier
     */
    void deleteById(String usageId);

    /**
     * Updates given AACL {@link Usage} in case of its version is the same as in database.
     *
     * @param usage {@link Usage} to update
     * @return id of updated record, otherwise {@code null}
     */
    String updateProcessedUsage(Usage usage);

    /**
     * Finds baseline periods.
     *
     * @param startPeriod           period to start from
     * @param numberOfBaselineYears number of baseline years
     * @return set of baseline periods
     */
    Set<Integer> findBaselinePeriods(int startPeriod, int numberOfBaselineYears);

    /**
     * Sets payee account number for usages with given RH account number and scenario id.
     *
     * @param rhAccountNumber    RH account number
     * @param scenarioId         scenario id
     * @param payeeAccountNumber payee account number
     * @param userName           user name
     */
    void updatePayeeByAccountNumber(Long rhAccountNumber, String scenarioId, Long payeeAccountNumber, String userName);

    /**
     * Finds list of AACL {@link Usage}s by their ids.
     *
     * @param usageIds list of {@link Usage}s identifiers
     * @return list of {@link Usage}s
     */
    List<Usage> findByIds(List<String> usageIds);

    /**
     * Finds count of referenced usages in the df_usage_aacl table by ids.
     *
     * @param usageIds set of usage ids
     * @return the count of usages
     */
    int findReferencedAaclUsagesCountByIds(String... usageIds);

    /**
     * Finds list of AACL {@link UsageDto}s by usage filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds AACL usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(UsageFilter filter);

    /**
     * Finds list of AACL usage periods.
     *
     * @return list of AACL usage periods
     */
    List<Integer> findUsagePeriods();

    /**
     * Finds list of AACL usage periods by {@link UsageFilter}.
     *
     * @param filter {@link UsageFilter} instance
     * @return list of AACL usage periods found by {@link UsageFilter}
     */
    List<Integer> findUsagePeriodsByFilter(UsageFilter filter);

    /**
     * Verifies whether {@link Usage}s found by defined {@link UsageFilter} have specified status or not.
     *
     * @param filter {@link UsageFilter} instance
     * @param status {@link UsageStatusEnum} instance
     * @return {@code true} - if there are no {@link Usage}s found by defined {@link UsageFilter}
     * with status different from specified , {@code false} - otherwise
     */
    boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status);

    /**
     * Finds rightsholders account numbers that are not presented in database based on {@link UsageFilter}.
     *
     * @param filter instance of {@link UsageFilter}
     * @return list of rightsholders account numbers
     */
    List<Long> findInvalidRightsholdersByFilter(UsageFilter filter);

    /**
     * Deletes all {@link Usage}s from the batch with given id.
     *
     * @param batchId {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id
     */
    void deleteByBatchId(String batchId);

    /**
     * Finds whether there are usages that match {@link UsageFilter} and have the given Detail Licensee Class.
     *
     * @param filter                {@link UsageFilter} instance
     * @param detailLicenseeClassId Detail Licensee Class id
     * @return {@code true} if such usages exist, {@code false} otherwise
     */
    boolean usagesExistByDetailLicenseeClassAndFilter(UsageFilter filter, Integer detailLicenseeClassId);

    /**
     * Attaches usages to scenario.
     *
     * @param scenario {@link Scenario} to add usages to
     * @param filter   {@link UsageFilter} instance
     */
    void addToScenario(Scenario scenario, UsageFilter filter);

    /**
     * Calculates usages amounts and marks usages that under cutoff minimum amount as
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#SCENARIO_EXCLUDED}.
     *
     * @param scenarioId   scenario identifier
     * @param cutoffAmount minimum amount to exclude from scenario
     * @param userName     user name
     */
    void updateAaclUsagesUnderMinimum(String scenarioId, BigDecimal cutoffAmount, String userName);

    /**
     * Finds list of {@link UsageDto}s matching specified filter.
     *
     * @param filter   {@link AuditFilter}
     * @param pageable limit and offset
     * @param sort     sort criteria
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> findForAudit(AuditFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds count of {@link UsageDto}s by AACL {@link AuditFilter}.
     *
     * @param filter {@link AuditFilter}
     * @return count of {@link UsageDto}s matching filter
     */
    int findCountForAudit(AuditFilter filter);

    /**
     * Sets publication type weight for scenario usages with the given publication type.
     *
     * @param scenario          {@link Scenario}
     * @param publicationTypeId publication type id
     * @param weight            publication type weight
     */
    void updatePublicationTypeWeight(Scenario scenario, String publicationTypeId, BigDecimal weight);
}
