package com.copyright.rup.dist.foreign.vui.scenario.api.nts;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

import java.util.List;
import java.util.Set;

/**
 * Interface for exclude rightsholder controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/03/2020
 *
 * @author Anton Azarenka
 */
public interface INtsExcludeRightsholderController extends IController<INtsExcludeRightsholderWidget> {

    /**
     * Finds all {@link RightsholderPayeePair}s within the chosen scenario.
     *
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> getRightsholderPayeePairs();

    /**
     * Excludes details from corresponding scenario with selected payees' account numbers.
     *
     * @param rightsholderAccountNumbers rightsholder's account numbers
     * @param reason                     reason of exclusion
     */
    void excludeDetails(Set<Long> rightsholderAccountNumbers, String reason);

    /**
     * Sets scenario.
     *
     * @param scenario selected scenario
     */
    void setSelectedScenario(Scenario scenario);
}
