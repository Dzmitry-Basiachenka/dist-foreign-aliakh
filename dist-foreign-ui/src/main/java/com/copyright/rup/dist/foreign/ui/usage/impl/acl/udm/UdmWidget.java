package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import java.util.function.Supplier;

/**
 * Implementation of {@link IUdmWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/27/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmWidget extends TabSheet implements IUdmWidget {

    private IUdmController udmController;

    @Override
    @SuppressWarnings("unchecked")
    public IUdmWidget init() {
        this.addStyleName(Cornerstone.MAIN_TABSHEET);
        this.addStyleName("sub-tabsheet");
        initAndAddTab(() -> udmController.getUdmUsageController(), "tab.usages");
        initAndAddTab(() -> udmController.getUdmValueController(), "tab.values");
        if (ForeignSecurityUtils.hasSpecialistPermission()) {
            initAndAddTab(() -> udmController.getUdmProxyValueController(), "tab.proxy_values");
        }
        if (!ForeignSecurityUtils.hasResearcherPermission()) {
            initAndAddTab(() -> udmController.getUdmBaselineController(), "tab.baseline");
            initAndAddTab(() -> udmController.getUdmBaselineValueController(), "tab.baseline_values");
        }
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
    public void setController(IUdmController controller) {
        this.udmController = controller;
    }

    private <T extends IController<?>> void initAndAddTab(Supplier<T> supplier, String caption) {
        IController<?> controller = supplier.get();
        IWidget widget = controller.initWidget();
        widget.setController(controller);
        addTab(widget, ForeignUi.getMessage(caption));
    }
}
