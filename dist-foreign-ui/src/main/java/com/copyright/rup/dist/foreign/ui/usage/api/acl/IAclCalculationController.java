package com.copyright.rup.dist.foreign.ui.usage.api.acl;

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
     * @return instance of {@link IAclGrantDetailController}.
     */
    IAclGrantDetailController getAclGrantDetailController();
}
