package com.copyright.rup.dist.foreign.ui.scenario.api.aacl;

import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;

/**
 * Controller interface for {@link IAaclScenarioWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/27/20
 *
 * @author Stanislau Rudak
 */
public interface IAaclScenarioController extends ICommonScenarioController {

    /**
     * Handles click on "Exclude By Payee" button.
     */
    void onExcludeByPayeeClicked();
}
