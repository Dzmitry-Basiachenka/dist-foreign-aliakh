package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;

/**
 * Mediator for the FAS and FAS2 usages widgets.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/05/2019
 *
 * @author Uladzislau Shalamitski
 */
class FasUsageMediator implements IMediator {

    private MenuItem loadUsageBatchMenuItem;
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

    void setLoadUsageBatchMenuItem(MenuItem loadUsageBatchMenuItem) {
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
