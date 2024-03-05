package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.api.ICommonRightsholderService;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTypeOfUsePair;
import com.copyright.rup.dist.foreign.domain.Usage;

import java.io.Serializable;
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
public interface IRightsholderService extends ICommonRightsholderService, Serializable {

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
     * @param searchValue value to search
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of unique {@link Rightsholder}s from all usages
     */
    List<Rightsholder> getAllWithSearch(String searchValue, Pageable pageable, Sort sort);

    /**
     * Gets count of unique rightsholders from usages based on search value.
     *
     * @param searchValue value to search
     * @return count of rightsholders
     */
    int getCountWithSearch(String searchValue);

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
     * Gets list of {@link RightsholderTypeOfUsePair}s from scenario share details with given ACL grant set id.
     *
     * @param grantSetId ACL grant set id
     * @return list of {@link RightsholderTypeOfUsePair}s
     */
    List<RightsholderTypeOfUsePair> getByAclGrantSetId(String grantSetId);

    /**
     * Finds all {@link RightsholderPayeePair}s within the {@link com.copyright.rup.dist.foreign.domain.Scenario}
     * with given id.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} id
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> getRhPayeePairByScenarioId(String scenarioId);

    /**
     * Returns list of {@link Rightsholder}s with given account numbers.
     *
     * @param accountNumbers set of {@link Rightsholder}s account numbers
     * @return list of {@link Rightsholder}s
     */
    List<Rightsholder> getRightsholdersByAccountNumbers(Set<Long> accountNumbers);
}
