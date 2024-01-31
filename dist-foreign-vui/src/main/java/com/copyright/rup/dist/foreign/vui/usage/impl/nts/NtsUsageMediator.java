package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;

/**
 * Mediator for the NTS usages widget.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/05/2019
 *
 * @author Uladzislau Shalamitski
 */
class NtsUsageMediator implements IMediator {

    private Button addToScenarioButton;
    private Button assignClassificationButton;
    private MenuBar additionalFundsMenuBar;
    private MenuItem loadFundPoolMenuItem;

    @Override
    public void applyPermissions() {
        loadFundPoolMenuItem.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
        assignClassificationButton.setVisible(ForeignSecurityUtils.hasAssignClassificationPermission());
        additionalFundsMenuBar.setVisible(ForeignSecurityUtils.hasCreateDeleteFundPermission());
    }

    void setAddToScenarioButton(Button addToScenarioButton) {
        this.addToScenarioButton = addToScenarioButton;
    }

    void setAssignClassificationButton(Button assignClassificationButton) {
        this.assignClassificationButton = assignClassificationButton;
    }

    void setAdditionalFundsMenuBar(MenuBar additionalFundsMenuBar) {
        this.additionalFundsMenuBar = additionalFundsMenuBar;
    }

    void setLoadFundPoolMenuItem(MenuItem loadFundPoolMenuItem) {
        this.loadFundPoolMenuItem = loadFundPoolMenuItem;
    }
}
