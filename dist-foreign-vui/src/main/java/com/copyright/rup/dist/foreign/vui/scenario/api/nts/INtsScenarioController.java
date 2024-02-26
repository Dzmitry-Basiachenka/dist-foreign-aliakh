package com.copyright.rup.dist.foreign.vui.scenario.api.nts;

import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioController;

/**
 * Controller interface for {@link INtsScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/2019
 *
 * @author Stanislau Rudak
 */
public interface INtsScenarioController extends ICommonScenarioController {

    /**
     * Handles click on 'Exclude by RH' button.
     */
    void onExcludeRhButtonClicked();
}
