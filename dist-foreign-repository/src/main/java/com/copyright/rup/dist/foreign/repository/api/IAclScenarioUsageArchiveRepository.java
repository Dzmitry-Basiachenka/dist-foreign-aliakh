package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;

import java.util.List;

/**
 * Interface for ACL scenario usage archive repository.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Anton Azarenka
 */
public interface IAclScenarioUsageArchiveRepository {

    /**
     * Copies details related to specified scenario to archive table.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.AclScenario} identifier
     * @param userName   name of user who performs action
     * @return list of moved detail ids
     */
    List<String> copyScenarioDetailsToArchiveByScenarioId(String scenarioId, String userName);

    /**
     * Copies shares related to specified scenario to archive table.
     *
     * @param scenarioId {@link com.copyright.rup.dist.foreign.domain.AclScenario} identifier
     * @param userName   name of user who performs action
     * @return list of moved detail ids
     */
    List<String> copyScenarioSharesToArchiveByScenarioId(String scenarioId, String userName);

    /**
     * Finds list of {@link AclScenarioDetail}s by ACL scenario uid.
     *
     * @param scenarioId scenario id
     * @return list of {@link AclScenarioDetail}s
     */
    List<AclScenarioDetail> findByScenarioId(String scenarioId);

    /**
     * Finds {@link AclScenarioLiabilityDetail} info for sending to LM.
     *
     * @param scenarioId scenario id
     * @return list of found {@link AclScenarioLiabilityDetail}s
     */
    List<AclScenarioLiabilityDetail> findForSendToLmByScenarioId(String scenarioId);
}
