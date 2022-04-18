package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.vaadin.widget.api.IController;

/**
 * Interface for ACL fund pool controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
public interface IAclFundPoolController extends IController<IAclFundPoolWidget> {

    /**
     * Initializes {@link IAclUsageFilterWidget}.
     *
     * @return initialized {@link IAclUsageFilterWidget}
     */
    IAclFundPoolFilterWidget initAclFundPoolFilterWidget();
}
