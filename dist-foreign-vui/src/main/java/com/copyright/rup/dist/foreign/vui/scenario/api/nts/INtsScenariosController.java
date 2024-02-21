package com.copyright.rup.dist.foreign.vui.scenario.api.nts;

import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;

/**
 * Controller interface for {@link INtsScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/2019
 *
 * @author Stanislau Rudak
 */
public interface INtsScenariosController extends ICommonScenariosController {

    /**
     * Handles send to LM action.
     */
    void sendToLm();
}
