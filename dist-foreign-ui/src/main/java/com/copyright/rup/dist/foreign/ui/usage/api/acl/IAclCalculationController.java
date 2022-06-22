package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.vaadin.widget.api.IController;

/**
 * Interface for Calculations tab controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclCalculationController extends IController<IAclCalculationWidget> {

    /**
     * @return instance of {@link IAclUsageController}.
     */
    IAclUsageController getAclUsageController();

    /**
     * @return instance of {@link IAclGrantDetailController}.
     */
    IAclGrantDetailController getAclGrantDetailController();

    /**
     * @return instance of {@link IAclFundPoolController}.
     */
    IAclFundPoolController getAclFundPoolController();

    /**
     * @return instance of {@link IAclScenariosController}.
     */
    IAclScenariosController getAclScenariosController();
}
