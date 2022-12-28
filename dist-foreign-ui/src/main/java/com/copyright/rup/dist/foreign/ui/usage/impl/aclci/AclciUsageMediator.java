package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.vaadin.ui.MenuBar;

/**
 * Mediator for the ACLCI usages widget.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
class AclciUsageMediator implements IMediator {

    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private MenuBar.MenuItem loadFundPoolMenuItem;

    @Override
    public void applyPermissions() {
        loadUsageBatchMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        loadFundPoolMenuItem.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission());
    }

    public void setLoadUsageBatchMenuItem(MenuBar.MenuItem loadUsageBatchMenuItem) {
        this.loadUsageBatchMenuItem = loadUsageBatchMenuItem;
    }

    public void setLoadFundPoolMenuItem(MenuBar.MenuItem loadFundPoolMenuItem) {
        this.loadFundPoolMenuItem = loadFundPoolMenuItem;
    }
}
