package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Mediator for the SAL usages widget.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
class SalUsageMediator implements IMediator {

    private MenuBar.MenuItem loadItemBankMenuItem;
    private MenuBar.MenuItem loadUsageDataMenuItem;
    private MenuBar.MenuItem loadFundPoolMenuItem;
    private Button addToScenarioButton;

    @Override
    public void applyPermissions() {
        loadItemBankMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        loadUsageDataMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        loadFundPoolMenuItem.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
    }

    void setLoadItemBankMenuItem(MenuItem loadItemBankMenuItem) {
        this.loadItemBankMenuItem = loadItemBankMenuItem;
    }

    void setLoadUsageDataMenuItem(MenuItem loadUsageDataMenuItem) {
        this.loadUsageDataMenuItem = loadUsageDataMenuItem;
    }

    void setLoadFundPoolMenuItem(MenuItem loadFundPoolMenuItem) {
        this.loadFundPoolMenuItem = loadFundPoolMenuItem;
    }

    void setAddToScenarioButton(Button addToScenarioButton) {
        this.addToScenarioButton = addToScenarioButton;
    }
}
