package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;

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

    private MenuItem loadUsageBatchMenuItem;
    private MenuItem loadFundPoolMenuItem;
    private Button sendForClassificationButton;
    private OnDemandFileDownloader sendForClassificationDownloader;
    private Button loadClassifiedUsagesButton;
    private Button addToScenarioButton;

    @Override
    public void applyPermissions() {
        loadUsageBatchMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        loadFundPoolMenuItem.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission());
        sendForClassificationButton.setVisible(ForeignSecurityUtils.hasSendForClassificationPermission());
        sendForClassificationDownloader.setVisible(ForeignSecurityUtils.hasSendForClassificationPermission());
        loadClassifiedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadClassifiedUsagePermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
    }

    void setLoadUsageBatchMenuItem(MenuItem loadUsageBatchMenuItem) {
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

    void setSendForClassificationDownloader(OnDemandFileDownloader sendForClassificationDownloader) {
        this.sendForClassificationDownloader = sendForClassificationDownloader;
    }

    void setAddToScenarioButton(Button addToScenarioButton) {
        this.addToScenarioButton = addToScenarioButton;
    }
}
