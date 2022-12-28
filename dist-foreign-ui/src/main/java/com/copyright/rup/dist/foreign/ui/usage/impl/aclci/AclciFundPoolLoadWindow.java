package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.ui.Window;

/**
 * Window for loading ACLCI fund pool.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/29/2022
 *
 * @author Aliaksandr Liakh
 */
class AclciFundPoolLoadWindow extends Window {

    /**
     * Constructor.
     */
    AclciFundPoolLoadWindow() {
        //TODO implement: setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.load_fund_pool"));
        setResizable(false);
        setWidth(500, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "fund-pool-upload-window");
    }
}
