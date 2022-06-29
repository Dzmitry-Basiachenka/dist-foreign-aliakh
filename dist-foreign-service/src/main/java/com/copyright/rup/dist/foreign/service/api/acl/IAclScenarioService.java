package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.RightsholderAclTotalsHolder;

import java.util.List;

/**
 * Interface for ACL scenario service.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 06/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclScenarioService {

    /**
     * Gets list of {@link AclScenario}s.
     *
     * @return list of {@link AclScenario}s
     */
    List<AclScenario> getScenarios();

    /**
     * Checks whether {@link AclScenario} with specified name already exists in database.
     *
     * @param scenarioName name of {@link AclScenario} to check
     * @return {@code true} if {@link AclScenario} with specified name already exists in database,
     * {@code false} - if doesn't
     */
    boolean aclScenarioExists(String scenarioName);

    /**
     * Gets {@link AclScenarioDto} by scenario id.
     *
     * @param scenarioId scenario id
     * @return instance of {@link AclScenarioDto}
     */
    AclScenarioDto getAclScenarioWithAmountsAndLastAction(String scenarioId);

    /**
     * Gets {@link RightsholderAclTotalsHolder}s based on ACL scenario id.
     *
     * @param scenarioId  scenario id
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link RightsholderAclTotalsHolder}s
     */
    List<RightsholderAclTotalsHolder> getRightsholderAclTotalsHoldersByScenarioId(String scenarioId, String searchValue,
                                                                                  Pageable pageable, Sort sort);

    /**
     * Gets count of {@link RightsholderAclTotalsHolder}s based on ACL scenario id.
     *
     * @param scenarioId  scenario id
     * @param searchValue search value
     * @return count of {@link RightsholderAclTotalsHolder}s
     */
    int getRightsholderAclTotalsHolderCountByScenarioId(String scenarioId, String searchValue);
}
