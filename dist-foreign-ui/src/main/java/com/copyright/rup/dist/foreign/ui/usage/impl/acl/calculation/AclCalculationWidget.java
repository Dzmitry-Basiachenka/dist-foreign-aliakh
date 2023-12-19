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

    private static final long serialVersionUID = -1763139522782105204L;

    private IAclCalculationController aclCalculationController;

    @Override
    @SuppressWarnings("unchecked")
    public IAclCalculationWidget init() {
        this.addStyleName(Cornerstone.MAIN_TABSHEET);
        this.addStyleName("sub-tabsheet");
        initAndAddTab(() -> aclCalculationController.getAclUsageController(), "tab.usages");
        initAndAddTab(() -> aclCalculationController.getAclFundPoolController(), "tab.fund_pool");
        initAndAddTab(() -> aclCalculationController.getAclGrantDetailController(), "tab.grant_set");
        initAndAddTab(() -> aclCalculationController.getAclScenariosController(), "tab.scenarios");
        setSizeFull();
        addSelectedTabChangeListener(this::selectedTabChange);
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

    private void selectedTabChange(SelectedTabChangeEvent event) {
        Component selectedTab = getSelectedTab();
        if (selectedTab instanceof SelectedTabChangeListener) {
            ((SelectedTabChangeListener) selectedTab).selectedTabChange(event);
        }
    }

    private <T extends IController<?>> void initAndAddTab(Supplier<T> supplier, String caption) {
        IController<?> controller = supplier.get();
        IWidget widget = controller.initWidget();
        widget.setController(controller);
        addTab(widget, ForeignUi.getMessage(caption));
    }
}
