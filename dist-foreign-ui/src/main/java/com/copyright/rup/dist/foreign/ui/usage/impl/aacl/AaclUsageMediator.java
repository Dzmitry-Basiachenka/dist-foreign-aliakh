package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Mediator for the AACL usages widget.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
class AaclUsageMediator implements IMediator {

    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private MenuBar.MenuItem loadFundPoolMenuItem;
    private Button sendForClassificationButton;
    private Button loadClassifiedUsagesButton;

    @Override
    public void applyPermissions() {
        loadUsageBatchMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        loadFundPoolMenuItem.setVisible(ForeignSecurityUtils.hasLoadAaclFundPoolPermission());
        sendForClassificationButton.setVisible(ForeignSecurityUtils.hasSendForClassificationPermission());
        loadClassifiedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadClassifiedUsagePermission());
    }

    void setLoadUsageBatchMenuItem(MenuBar.MenuItem loadUsageBatchMenuItem) {
        this.loadUsageBatchMenuItem = loadUsageBatchMenuItem;
    }

    void setLoadFundPoolMenuItem(MenuItem loadFundPoolMenuItem) {
        this.loadFundPoolMenuItem = loadFundPoolMenuItem;
    }

    void setSendForClassificationButton(Button sendForClassificationButton) {
        this.sendForClassificationButton = sendForClassificationButton;
    }

    void setLoadClassifiedUsagesButton(Button loadClassifiedUsagesButton) {
        this.loadClassifiedUsagesButton = loadClassifiedUsagesButton;
    }
}
