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

    private Button loadResearchedUsagesButton;
    private Button addToScenarioButton;
    private Button sendForResearchButton;
    private MenuBar.MenuItem loadUsageBatchMenuItem;

    @Override
    public void applyPermissions() {
        loadUsageBatchMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        loadResearchedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadResearchedUsagePermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
        sendForResearchButton.setVisible(ForeignSecurityUtils.hasSendForWorkResearchPermission());
    }

    void setLoadResearchedUsagesButton(Button loadResearchedUsagesButton) {
        this.loadResearchedUsagesButton = loadResearchedUsagesButton;
    }

    void setAddToScenarioButton(Button addToScenarioButton) {
        this.addToScenarioButton = addToScenarioButton;
    }

    void setSendForResearchButton(Button sendForResearchButton) {
        this.sendForResearchButton = sendForResearchButton;
    }

    void setLoadUsageBatchMenuItem(MenuBar.MenuItem loadUsageBatchMenuItem) {
        this.loadUsageBatchMenuItem = loadUsageBatchMenuItem;
    }
}
