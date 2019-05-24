package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;

/**
 * Mediator for the usages widget.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/22/17
 *
 * @author Ihar Suvorau
 */
class UsagesMediator implements IUsagesMediator {

    private Button loadResearchedUsagesButton;
    private Button deleteUsageButton;
    private Button addToScenarioButton;
    private Button sendForResearchButton;
    private Button assignClassificationButton;
    private MenuBar withdrawnFundMenuBar;
    private MenuBar usageBatchMenuBar;
    private MenuBar fundPoolMenuBar;
    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private MenuBar.MenuItem loadFundPoolMenuItem;

    @Override
    public void applyPermissions() {
        loadUsageBatchMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        loadFundPoolMenuItem.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission());
        loadResearchedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadResearchedUsagePermission());
        deleteUsageButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
        sendForResearchButton.setVisible(ForeignSecurityUtils.hasSendForWorkResearchPermission());
        assignClassificationButton.setVisible(ForeignSecurityUtils.hasAssignClassificationPermission());
        withdrawnFundMenuBar.setVisible(ForeignSecurityUtils.hasCreateDeleteFundPermission());
    }

    @Override
    public void onProductFamilyChanged(String productFamily) {
        boolean isFasFas2ProductFamily = FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET.contains(productFamily);
        boolean isNtsProductFamily = FdaConstants.NTS_PRODUCT_FAMILY.equals(productFamily);
        usageBatchMenuBar.setVisible(isFasFas2ProductFamily);
        fundPoolMenuBar.setVisible(isNtsProductFamily);
        loadUsageBatchMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission() && isFasFas2ProductFamily);
        loadFundPoolMenuItem.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission() && isNtsProductFamily);
        loadResearchedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadResearchedUsagePermission()
            && isFasFas2ProductFamily);
        sendForResearchButton.setVisible(ForeignSecurityUtils.hasSendForWorkResearchPermission()
            && isFasFas2ProductFamily);
        assignClassificationButton.setVisible(ForeignSecurityUtils.hasAssignClassificationPermission()
            && isNtsProductFamily);
        withdrawnFundMenuBar.setVisible(ForeignSecurityUtils.hasCreateDeleteFundPermission() && isNtsProductFamily);
        deleteUsageButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
    }

    void setLoadResearchedUsagesButton(Button loadResearchedUsagesButton) {
        this.loadResearchedUsagesButton = loadResearchedUsagesButton;
    }

    void setDeleteUsageButton(Button deleteUsageButton) {
        this.deleteUsageButton = deleteUsageButton;
    }

    void setAddToScenarioButton(Button addToScenarioButton) {
        this.addToScenarioButton = addToScenarioButton;
    }

    void setSendForResearchButton(Button sendForResearchButton) {
        this.sendForResearchButton = sendForResearchButton;
    }

    void setAssignClassificationButton(Button assignClassificationButton) {
        this.assignClassificationButton = assignClassificationButton;
    }

    void setWithdrawnFundMenuBar(MenuBar withdrawnFundMenuBar) {
        this.withdrawnFundMenuBar = withdrawnFundMenuBar;
    }

    void setLoadUsageBatchMenuItem(MenuBar.MenuItem loadUsageBatchMenuItem) {
        this.loadUsageBatchMenuItem = loadUsageBatchMenuItem;
    }

    void setLoadFundPoolMenuItem(MenuBar.MenuItem loadFundPoolMenuItem) {
        this.loadFundPoolMenuItem = loadFundPoolMenuItem;
    }

    void setUsageBatchMenuBar(MenuBar usageBatchMenuBar) {
        this.usageBatchMenuBar = usageBatchMenuBar;
    }

    void setFundPoolMenuBar(MenuBar fundPoolMenuBar) {
        this.fundPoolMenuBar = fundPoolMenuBar;
    }
}
