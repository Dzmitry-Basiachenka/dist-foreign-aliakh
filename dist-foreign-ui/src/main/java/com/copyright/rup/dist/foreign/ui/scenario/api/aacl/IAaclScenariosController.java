package com.copyright.rup.dist.foreign.ui.scenario.api.aacl;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;

import java.util.List;

/**
 * Controller interface for {@link IAaclScenariosWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/19/20
 *
 * @author Uladzislau Shalamitski
 */
public interface IAaclScenariosController extends ICommonScenariosController {

    /**
     * Gets list of {@link DetailLicenseeClass}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link DetailLicenseeClass}es
     */
    List<DetailLicenseeClass> getDetailLicenseeClassesByScenarioId(String scenarioId);

    /**
     * Handles send to LM action.
     */
    void sendToLm();
}
