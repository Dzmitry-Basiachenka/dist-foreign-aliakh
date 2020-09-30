package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;

import java.util.List;
import java.util.Set;

/**
 * Represents interface of service for fund pools business logic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
public interface IFundPoolService {

    /**
     * Gets {@link FundPool} by id.
     *
     * @param fundPoolId {@link FundPool} id
     * @return found {@link FundPool} or {@code null} if none found
     */
    FundPool getFundPoolById(String fundPoolId);

    /**
     * Gets all {@link FundPool}s related to selected product family.
     *
     * @param productFamily product family
     * @return list of {@link FundPool}s
     */
    List<FundPool> getFundPools(String productFamily);

    /**
     * Checks whether {@link FundPool} with provided name already exists.
     *
     * @param productFamily fund pool product family
     * @param name          fund pool name
     * @return {@code true} - if fund pool exists, {@code false} - otherwise
     */
    boolean fundPoolExists(String productFamily, String name);

    /**
     * Creates NTS fund pool.
     *
     * @param fundPool instance of {@link FundPool}
     * @param batchIds set of ids of usage batches
     */
    void createNtsFundPool(FundPool fundPool, Set<String> batchIds);

    /**
     * Creates AACL fund pool and its details.
     *
     * @param fundPool instance of {@link FundPool}
     * @param details  list of {@link FundPoolDetail}s
     * @return count of inserted details
     */
    int createAaclFundPool(FundPool fundPool, List<FundPoolDetail> details);

    /**
     * Gets NTS {@link FundPool}s not attached to scenario.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> getNtsNotAttachedToScenario();

    /**
     * Gets AACL {@link FundPool}s not attached to scenario.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> getAaclNotAttachedToScenario();

    /**
     * Gets SAL {@link FundPool}s not attached to scenario.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> getSalNotAttachedToScenario();

    /**
     * Deletes NTS {@link FundPool}.
     *
     * @param fundPool {@link FundPool} to delete
     */
    void deleteNtsFundPool(FundPool fundPool);

    /**
     * Deletes AACL {@link FundPool}.
     *
     * @param fundPool {@link FundPool} to delete
     */
    void deleteAaclFundPool(FundPool fundPool);

    /**
     * Deletes SAL {@link FundPool}.
     *
     * @param fundPool {@link FundPool} to delete
     */
    void deleteSalFundPool(FundPool fundPool);

    /**
     * Gets NTS fund pool names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of names
     */
    List<String> getNtsFundPoolNamesByUsageBatchId(String batchId);

    /**
     * Gets {@link FundPoolDetail}s by {@link FundPool} id.
     *
     * @param fundPoolId {@link FundPool} id
     * @return list of {@link FundPoolDetail}s
     */
    List<FundPoolDetail> getDetailsByFundPoolId(String fundPoolId);

    /**
     * Calculates Total Amount, Item Bank Amount, Item Bank Gross Amount, Grade K-5 Gross Amount,
     * Grade 6-8 Gross Amount, Grade 9-12 Gross Amount for given SAL {@link FundPool}.
     *
     * @param fundPool SAL fund pool to calculate
     * @return calculated SAL {@link FundPool}
     */
    FundPool calculateSalFundPoolAmounts(FundPool fundPool);
}
