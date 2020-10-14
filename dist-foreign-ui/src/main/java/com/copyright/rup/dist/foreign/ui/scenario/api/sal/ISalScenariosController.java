package com.copyright.rup.dist.foreign.ui.scenario.api.sal;

import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;

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
}
