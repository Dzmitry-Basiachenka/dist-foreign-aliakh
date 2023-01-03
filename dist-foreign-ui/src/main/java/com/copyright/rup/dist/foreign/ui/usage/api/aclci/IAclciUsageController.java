package com.copyright.rup.dist.foreign.ui.usage.api.aclci;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.impl.csv.AclciUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;

import java.util.List;
import java.util.Set;

/**
 * Interface for ACLCI usages controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
public interface IAclciUsageController extends ICommonUsageController {

    /**
     * Gets CSV processor to parse usages.
     *
     * @return instance of {@link AclciUsageCsvProcessor}
     */
    AclciUsageCsvProcessor getAclciUsageCsvProcessor();

    /**
     * Inserts usage batch and its usages.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     */
    void loadAclciUsageBatch(UsageBatch usageBatch, List<Usage> usages);

    /**
     * Gets instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult<Usage> processingResult);

    /**
     * Gets licensee name by account number.
     *
     * @param licenseeAccountNumber licensee account number
     * @return licensee name
     */
    String getLicenseeName(Long licenseeAccountNumber);

    /**
     * Checks whether ACLCI fund pool with the name already exists.
     *
     * @param name ACLCI fund pool name
     * @return {@code true} if fund pool exists, otherwise {@code false}
     */
    boolean aclciFundPoolExists(String name);

    /**
     * Calculates amounts for the ACLCI fund pool.
     *
     * @param fundPool instance of {@link FundPool} to calculate
     * @return calculated ACLCI fund pool
     */
    FundPool calculateAclciFundPoolAmounts(FundPool fundPool);

    /**
     * Creates ACLCI fund pool.
     *
     * @param fundPool instance of {@link FundPool}
     */
    void createAclciFundPool(FundPool fundPool);

    /**
     * Verifies whether status filter is applied
     * and has value {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND}
     * or {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#WORK_NOT_GRANTED}.
     *
     * @return {@code true} - if status filter is applied, {@code false} - otherwise
     */
    boolean isValidStatusFilterApplied();

    /**
     * Gets list of {@link UsageDto}s for updating.
     *
     * @return list of found {@link UsageDto}s
     */
    List<UsageDto> getUsageDtosToUpdate();

    /**
     * Updates usages to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets new RH Account #
     * and Wr Wrk Inst by provided identifiers.
     *
     * @param usageIds        usage ids to update
     * @param rhAccountNumber rh account number to set
     * @param wrWrkInst       wr wrk inst to set (can be {@code null}
     * @param reason          action reason
     */
    void updateToEligibleByIds(Set<String> usageIds, Long rhAccountNumber, Long wrWrkInst, String reason);
}
