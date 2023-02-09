package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;

/**
 * Mediator for the FAS and FAS2 usages widgets.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/05/19
 *
 * @author Uladzislau Shalamitski
 */
class FasUsageMediator implements IMediator {

    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private Button sendForResearchButton;
    private Button loadResearchedUsagesButton;
    private Button updateUsagesButton;
    private Button addToScenarioButton;

    @Override
    public void applyPermissions() {
        loadUsageBatchMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        sendForResearchButton.setVisible(ForeignSecurityUtils.hasSendForWorkResearchPermission());
        loadResearchedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadResearchedUsagePermission());
        updateUsagesButton.setVisible(ForeignSecurityUtils.hasUpdateRightsholderPermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
    }

    void setLoadUsageBatchMenuItem(MenuBar.MenuItem loadUsageBatchMenuItem) {
        this.loadUsageBatchMenuItem = loadUsageBatchMenuItem;
    }

    void setSendForResearchButton(Button sendForResearchButton) {
        this.sendForResearchButton = sendForResearchButton;
    }

    void setLoadResearchedUsagesButton(Button loadResearchedUsagesButton) {
        this.loadResearchedUsagesButton = loadResearchedUsagesButton;
    }

    void setUpdateUsagesButton(Button updateUsagesButton) {
        this.updateUsagesButton = updateUsagesButton;
    }

    void setAddToScenarioButton(Button addToScenarioButton) {
        this.addToScenarioButton = addToScenarioButton;
    }
}
