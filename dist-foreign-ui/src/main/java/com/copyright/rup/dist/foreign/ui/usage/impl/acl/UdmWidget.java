package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;
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
        IUdmValueController udmValueController = controller.getUdmValueController();
        IUdmValueWidget udmValueWidget = udmValueController.initWidget();
        udmValueWidget.setController(udmValueController);
        udmValueWidget.init();
        addTab(udmValueWidget, ForeignUi.getMessage("tab.values"));
        addTab(new Panel(), ForeignUi.getMessage("tab.baseline")); //TODO implement the Baseline tab
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUdmController controller) {
        this.controller = controller;
    }
}
