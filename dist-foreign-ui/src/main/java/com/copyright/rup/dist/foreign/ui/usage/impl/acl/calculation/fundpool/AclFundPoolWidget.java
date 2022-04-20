package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar;
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
    @SuppressWarnings("unchecked")
    public IAclFundPoolWidget init() {
        setSplitPosition(270, Unit.PIXELS);
        setFirstComponent(controller.initAclFundPoolFilterWidget());
        setSecondComponent(initFundPoolLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    private VerticalLayout initFundPoolLayout() {
        VerticalLayout layout = new VerticalLayout(initAclFundPoolMenuBar());
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        VaadinUtils.addComponentStyle(layout, "acl-fund-pool-layout");
        return layout;
    }

    private HorizontalLayout initAclFundPoolMenuBar() {
        MenuBar aclFundPoolMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            aclFundPoolMenuBar.addItem(ForeignUi.getMessage("menu.caption.fund_pool"), null, null);
        menuItem.addItem(ForeignUi.getMessage("menu.item.create"), null,
            item -> Windows.showModalWindow(new CreateAclFundPoolWindow(controller)));
        HorizontalLayout layout = new HorizontalLayout(aclFundPoolMenuBar);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(aclFundPoolMenuBar, "acl-fund-pool-menu-bar");
        VaadinUtils.addComponentStyle(aclFundPoolMenuBar, "v-menubar-df");
        VaadinUtils.addComponentStyle(layout, "acl-fund-pool-buttons");
        return layout;
    }

    @Override
    public void setController(IAclFundPoolController controller) {
        this.controller = controller;
    }
}
