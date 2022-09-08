package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;

import java.util.List;

/**
 * Interface for ACL scenario usage repository.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/22/2022
 *
 * @author Mikita Maistrenka
 */
public interface IAclScenarioUsageRepository {

    /**
     * Attaches usages to scenario.
     *
     * @param aclScenario ACL scenario to add ACL usages to
     * @param userName    user name
     */
    void addToAclScenario(AclScenario aclScenario, String userName);

    /**
     * Creates scenario shares for each usage in scenario.
     *
     * @param aclScenario ACL scenario to create shares
     * @param userName    user name
     */
    void addScenarioShares(AclScenario aclScenario, String userName);

    /**
     * Populates Pub Type Weights for all scenario usages.
     *
     * @param scenarioId scenario identifier
     * @param userName   username
     */
    void populatePubTypeWeights(String scenarioId, String userName);

    /**
     * Calculates and updates volume, value and detail shares for specified scenario.
     *
     * @param scenarioId scenario identifier
     * @param userName   username
     */
    void calculateScenarioShares(String scenarioId, String userName);

    /**
     * Calculates and updates gross, net and service_fee amounts for specified scenario.
     *
     * @param scenarioId scenario identifier
     * @param userName   username
     */
    void calculateScenarioAmounts(String scenarioId, String userName);

    /**
     * Finds list of {@link AclScenarioDetail}s by ACL scenario uid.
     *
     * @param scenarioId scenario id
     * @return list of {@link AclScenarioDetail}s
     */
    List<AclScenarioDetail> findScenarioDetailsByScenarioId(String scenarioId);

    /**
     * Delete shares with zero amounts for specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteZeroAmountShares(String scenarioId);

    /**
     * Delete usages without corresponding shares for specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteZeroAmountUsages(String scenarioId);

    /**
     * Finds {@link AclRightsholderTotalsHolder}s based on ACL scenario id.
     *
     * @param scenarioId  scenario id
     * @return list of {@link AclRightsholderTotalsHolder}s
     */
    List<AclRightsholderTotalsHolder> findAclRightsholderTotalsHoldersByScenarioId(String scenarioId);

    /**
     * Finds {@link AclScenarioDto} by scenario id.
     *
     * @param scenarioId scenario id
     * @return instance of {@link AclScenarioDto}
     */
    AclScenarioDto findWithAmountsAndLastAction(String scenarioId);

    /**
     * Finds list of {@link AclScenarioDetailDto}s based on {@link AclScenario} identifier and
     * rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenarioId    {@link AclScenario} identifier
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link AclScenarioDetailDto}s
     */
    List<AclScenarioDetailDto> findByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                                  String searchValue, Pageable pageable, Sort sort);

    /**
     * Finds count of {@link AclScenarioDetailDto}s based on {@link AclScenario} identifier and
     * rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenarioId    {@link AclScenario} identifier
     * @param searchValue   search value
     * @return count of usage details
     */
    int findCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue);

    /**
     * Finds list of {@link AclScenarioDetailDto}s based on {@link AclScenario} identifier,
     * rightsholder account number, title, and aggregate licensee class id.
     *
     * @param filter instance of {@link RightsholderResultsFilter}
     * @return list of {@link AclScenarioDetailDto}s
     */
    List<AclScenarioDetailDto> findRightsholderDetailsResults(RightsholderResultsFilter filter);

    /**
     * Finds list of {@link AclRightsholderTotalsHolderDto}s based on {@link AclScenario} identifier,
     * rightsholder account number, and aggregate licensee class id.
     *
     * @param filter instanse of {@link RightsholderResultsFilter}
     * @return list of {@link AclRightsholderTotalsHolderDto}s
     */
    List<AclRightsholderTotalsHolderDto> findRightsholderTitleResults(RightsholderResultsFilter filter);

    /**
     * Finds list of {@link AclRightsholderTotalsHolderDto}s based on {@link AclScenario} identifier,
     * rightsholder account number and title.
     *
     * @param filter instance of {@link RightsholderResultsFilter}
     * @return list of {@link AclRightsholderTotalsHolderDto}s
     */
    List<AclRightsholderTotalsHolderDto> findRightsholderAggLcClassResults(RightsholderResultsFilter filter);
}
