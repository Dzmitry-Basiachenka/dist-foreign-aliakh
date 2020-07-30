package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

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

    @Override
    public void applyPermissions() {
        loadItemBankMenuItem.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
    }

    void setLoadItemBankMenuItem(MenuItem loadItemBankMenuItem) {
        this.loadItemBankMenuItem = loadItemBankMenuItem;
    }
}
