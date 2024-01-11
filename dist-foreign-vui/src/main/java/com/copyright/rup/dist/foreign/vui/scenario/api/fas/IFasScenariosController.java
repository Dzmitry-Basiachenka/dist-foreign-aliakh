package com.copyright.rup.dist.foreign.vui.scenario.api.fas;

import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;

/**
 * Controller interface for {@link IFasScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public interface IFasScenariosController extends ICommonScenariosController {

    /**
     * Handles click on 'Exclude Payees' button.
     */
    void onExcludePayeesButtonClicked();
}
