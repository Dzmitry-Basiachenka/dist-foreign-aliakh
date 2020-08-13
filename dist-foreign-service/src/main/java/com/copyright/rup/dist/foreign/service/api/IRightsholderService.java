package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.api.ICommonRightsholderService;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents interface of service for rightsholder business logic.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 */
public interface IRightsholderService extends ICommonRightsholderService {

    /**
     * Gets list of RROs presented in DB associated with specified Product Family.
     *
     * @param productFamily Product Family
     * @return list of found RROs
     */
    List<Rightsholder> getRros(String productFamily);

    /**
     * Inserts specified rightsholder into database if it does not exist.
     *
     * @param rightsholder {@link Rightsholder} to insert
     */
    void updateRightsholder(Rightsholder rightsholder);

    /**
     * Gets list of unique {@link Rightsholder}s from all usages base on search value.
     *
     * @param productFamily product family
     * @param searchValue   value to search
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of unique {@link Rightsholder}s from all usages
     */
    List<Rightsholder> getFromUsages(String productFamily, String searchValue, Pageable pageable, Sort sort);

    /**
     * Gets count of unique rightsholders from usages based on search value.
     *
     * @param productFamily product family
     * @param searchValue   value to search
     * @return count of rightsholders
     */
    int getCountFromUsages(String productFamily, String searchValue);

    /**
     * Updates RHs information for account numbers absent in database based on PRM information.
     * Updates information in background thread.
     *
     * @param accountNumbers set of RH account numbers
     */
    void updateRighstholdersAsync(Set<Long> accountNumbers);

    /**
     * Updates RHs information for account numbers of usages' payees absent in database based on PRM information.
     * Updates information in background thread.
     *
     * @param usages list of {@link Usage}
     */
    void updateUsagesPayeesAsync(List<Usage> usages);

    /**
     * Finds map of {@link Rightsholder}s' ids to account numbers.
     *
     * @param rhIds set of {@link Rightsholder}s identifiers
     * @return map of {@link Rightsholder}s' ids to account numbers
     */
    Map<String, Long> findAccountNumbersByRightsholderIds(Set<String> rhIds);

    /**
     * Returns list of {@link Rightsholder}s from usages with given scenario identifier.
     *
     * @param scenarioId scenario identifier
     * @return list of {@link Rightsholder}s from usages with given scenario identifier
     */
    List<Rightsholder> getByScenarioId(String scenarioId);

    /**
     * Finds all {@link RightsholderPayeePair}s within the {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * with given id.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} id
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> getRhPayeePairByScenarioId(String scenarioId);
}
