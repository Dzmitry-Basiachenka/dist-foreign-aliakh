package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.api.IRefreshable;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

/**
 * Implementation of {@link IAclCalculationWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclCalculationWidget extends TabSheet implements IAclCalculationWidget {

    @Override
    @SuppressWarnings("unchecked")
    public IAclCalculationWidget init() {
        this.addStyleName(Cornerstone.MAIN_TABSHEET);
        this.addStyleName("sub-tabsheet");
        setSizeFull();
        return this;
    }

    @Override
    public void refresh() {
        Component selectedTab = getSelectedTab();
        if (selectedTab instanceof IRefreshable) {
            ((IRefreshable) selectedTab).refresh();
        }
    }

    @Override
    public void setController(IAclCalculationController controller) {
    }
}
