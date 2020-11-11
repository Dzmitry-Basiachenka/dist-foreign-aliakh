package com.copyright.rup.dist.foreign.ui.scenario.api.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;

import java.util.List;
import java.util.Set;

/**
 * Controller interface for {@link ISalScenariosWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalScenariosController extends ICommonScenariosController {

    /**
     * Gets Fund Pool name by provided identifier.
     *
     * @param fundPoolId fund poold identifier
     * @return fund pool name
     */
    String getFundPoolName(String fundPoolId);

    /**
     * @return list of {@link Scenario}s in
     * {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#APPROVED} status.
     */
    List<Scenario> getApprovedScenarios();

    /**
     * Sends {@link Scenario}s to LM.
     *
     * @param scenarios scenarios to send to LM
     */
    void sendToLm(Set<Scenario> scenarios);

    /**
     * Handles click on 'Send to LM' button.
     */
    void onSendToLmButtonClicked();
}
