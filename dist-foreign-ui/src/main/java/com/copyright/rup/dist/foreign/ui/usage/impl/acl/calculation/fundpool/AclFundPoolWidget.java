package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

/**
 * Implementation of {@link IAclFundPoolWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolWidget extends HorizontalSplitPanel implements IAclFundPoolWidget {

    private IAclFundPoolController controller;

    @Override
    public IAclFundPoolWidget init() {
        setSplitPosition(270, Unit.PIXELS);
        setFirstComponent(controller.initAclFundPoolFilterWidget());
        setSecondComponent(initFundPoolLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    private VerticalLayout initFundPoolLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        VaadinUtils.addComponentStyle(layout, "acl-fund-pool-layout");
        return layout;
    }

    @Override
    public void setController(IAclFundPoolController controller) {
        this.controller = controller;
    }
}
