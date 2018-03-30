package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;

/**
 * Mediator for the usages widget.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/22/17
 *
 * @author Ihar Suvorau
 */
class UsagesMediator implements IMediator {

    private Button loadUsageBatchButton;
    private Button loadResearchedUsagesButton;
    private Button deleteUsageButton;
    private Button addToScenarioButton;
    private Button sendForResearchButton;

    /**
     * Sets load usage batch button.
     *
     * @param loadUsageBatchButton load usage batch button
     */
    void setLoadUsageBatchButton(Button loadUsageBatchButton) {
        this.loadUsageBatchButton = loadUsageBatchButton;
    }

    /**
     * Sets load researched details button.
     *
     * @param loadResearchedUsagesButton load researched details button
     */
    void setLoadResearchedUsagesButton(Button loadResearchedUsagesButton) {
        this.loadResearchedUsagesButton = loadResearchedUsagesButton;
    }

    /**
     * Sets delete usage button.
     *
     * @param deleteUsageButton delete usage button
     */
    void setDeleteUsageButton(Button deleteUsageButton) {
        this.deleteUsageButton = deleteUsageButton;
    }

    /**
     * Sets add to scenario button.
     *
     * @param addToScenarioButton add to scenario button
     */
    void setAddToScenarioButton(Button addToScenarioButton) {
        this.addToScenarioButton = addToScenarioButton;
    }

    /**
     * Sets send for research button.
     *
     * @param sendForResearchButton send for research button
     */
    void setSendForResearchButton(Button sendForResearchButton) {
        this.sendForResearchButton = sendForResearchButton;
    }

    @Override
    public void applyPermissions() {
        loadUsageBatchButton.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        loadResearchedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadResearchedUsagePermission());
        deleteUsageButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
        sendForResearchButton.setVisible(ForeignSecurityUtils.hasSendForWorkResearchPermission());
    }
}
