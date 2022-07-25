package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;

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
     * Finds {@link AclRightsholderTotalsHolder}s based on ACL scenario id.
     *
     * @param scenarioId  scenario id
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link AclRightsholderTotalsHolder}s
     */
    List<AclRightsholderTotalsHolder> findAclRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                   String searchValue,
                                                                                   Pageable pageable, Sort sort);

    /**
     * Finds count of {@link AclRightsholderTotalsHolder}s based on ACL scenario id.
     *
     * @param scenarioId  scenario id
     * @param searchValue search value
     * @return count of {@link AclRightsholderTotalsHolder}s
     */
    int findAclRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue);
}
