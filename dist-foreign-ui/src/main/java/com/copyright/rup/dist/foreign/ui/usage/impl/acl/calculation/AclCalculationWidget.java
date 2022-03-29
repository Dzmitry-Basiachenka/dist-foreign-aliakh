package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import java.util.function.Supplier;

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

    private IAclCalculationController aclCalculationController;

    @Override
    @SuppressWarnings("unchecked")
    public IAclCalculationWidget init() {
        this.addStyleName(Cornerstone.MAIN_TABSHEET);
        this.addStyleName("sub-tabsheet");
        initAndAddTab(() -> aclCalculationController.getAclUsageController(), "tab.usages");
        initAndAddTab(() -> aclCalculationController.getAclGrantDetailController(), "tab.grant_set");
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
        this.aclCalculationController = controller;
    }

    private <T extends IController<?>> void initAndAddTab(Supplier<T> supplier, String caption) {
        IController<?> controller = supplier.get();
        IWidget widget = controller.initWidget();
        widget.setController(controller);
        addTab(widget, ForeignUi.getMessage(caption));
    }
}
