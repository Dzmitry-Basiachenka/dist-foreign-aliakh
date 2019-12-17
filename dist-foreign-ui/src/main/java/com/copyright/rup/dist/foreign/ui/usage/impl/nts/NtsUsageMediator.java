package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;

/**
 * Mediator for the NTS usages widget.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/05/19
 *
 * @author Uladzislau Shalamitski
 */
class NtsUsageMediator implements IMediator {

    private Button addToScenarioButton;
    private Button assignClassificationButton;
    private MenuBar withdrawnFundMenuBar;
    private MenuBar.MenuItem loadFundPoolMenuItem;

    @Override
    public void applyPermissions() {
        loadFundPoolMenuItem.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
        assignClassificationButton.setVisible(ForeignSecurityUtils.hasAssignClassificationPermission());
        withdrawnFundMenuBar.setVisible(ForeignSecurityUtils.hasCreateDeleteFundPermission());
    }

    void setAddToScenarioButton(Button addToScenarioButton) {
        this.addToScenarioButton = addToScenarioButton;
    }

    void setAssignClassificationButton(Button assignClassificationButton) {
        this.assignClassificationButton = assignClassificationButton;
    }

    void setWithdrawnFundMenuBar(MenuBar withdrawnFundMenuBar) {
        this.withdrawnFundMenuBar = withdrawnFundMenuBar;
    }

    void setLoadFundPoolMenuItem(MenuBar.MenuItem loadFundPoolMenuItem) {
        this.loadFundPoolMenuItem = loadFundPoolMenuItem;
    }
}
