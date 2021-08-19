package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;

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

    private IUdmController controller;

    @Override
    @SuppressWarnings("unchecked")
    public IUdmWidget init() {
        VaadinUtils.addComponentStyle(this, Cornerstone.MAIN_TABSHEET);
        IUdmUsageController udmUsageController = controller.getUdmUsageController();
        IUdmUsageWidget udmUsageWidget = udmUsageController.initWidget();
        udmUsageWidget.setController(udmUsageController);
        udmUsageWidget.init();
        addTab(udmUsageWidget, ForeignUi.getMessage("tab.usages"));
        addTab(new Panel(), ForeignUi.getMessage("tab.values")); //TODO implement the Values tab
        addTab(new Panel(), ForeignUi.getMessage("tab.baseline")); //TODO implement the Baseline tab
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUdmController controller) {
        this.controller = controller;
    }
}
