package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.ICommonRightsholderRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;

import java.util.List;

/**
 * Represents interface of repository for rightsholders.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
public interface IRightsholderRepository extends ICommonRightsholderRepository {

    /**
     * Finds list of RROs stored in DB associated with specified Product Family.
     *
     * @param productFamily Product Family
     * @return list of found RROs
     */
    List<Rightsholder> findRros(String productFamily);

    /**
     * Deletes {@link Rightsholder} from database table by account number.
     *
     * @param accountNumber account number of {@link Rightsholder}
     */
    void deleteByAccountNumber(Long accountNumber);

    /**
     * Finds list of unique {@link Rightsholder}s from all usages base on search value.
     *
     * @param productFamily product family
     * @param searchValue   value to search
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of unique {@link Rightsholder}s from all usages
     */
    List<Rightsholder> findFromUsages(String productFamily, String searchValue, Pageable pageable, Sort sort);

    /**
     * Finds count of unique rightsholders from usages based on search value.
     *
     * @param productFamily product family
     * @param searchValue   value to search
     * @return count of rightsholders
     */
    int findCountFromUsages(String productFamily, String searchValue);

    /**
     * Returns list of {@link Rightsholder}s from usages with given scenario identifier.
     *
     * @param scenarioId scenario identifier
     * @return list of {@link Rightsholder}s from usages with given scenario identifier
     */
    List<Rightsholder> findByScenarioId(String scenarioId);

    /**
     * Finds all {@link RightsholderPayeePair}s within the scenario with given id.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.Scenario} id
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> findRhPayeePairByScenarioId(String scenarioId);
}
