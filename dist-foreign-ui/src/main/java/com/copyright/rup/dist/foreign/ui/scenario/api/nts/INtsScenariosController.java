package com.copyright.rup.dist.foreign.ui.scenario.api.nts;

import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;

/**
 * Controller interface for {@link INtsScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public interface INtsScenariosController extends ICommonScenariosController {

    /**
     * Handles send to LM action.
     */
    void sendToLm();
}
