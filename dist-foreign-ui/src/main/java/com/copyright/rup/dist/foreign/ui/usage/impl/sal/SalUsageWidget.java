package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;

/**
 * Usage widget for SAL product families.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalUsageWidget extends CommonUsageWidget implements ISalUsageWidget {

    private final ISalUsageController controller;
    private MenuBar usageBatchMenuBar;
    private MenuBar.MenuItem loadItemBankMenuItem;

    /**
     * Controller.
     *
     * @param controller {@link ISalUsageController} instance
     */
    SalUsageWidget(ISalUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        SalUsageMediator mediator = new SalUsageMediator();
        mediator.setLoadItemBankMenuItem(loadItemBankMenuItem);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        //TODO: add grid columns
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        initUsageBatchMenuBar();
        HorizontalLayout layout = new HorizontalLayout(usageBatchMenuBar);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }

    private void initUsageBatchMenuBar() {
        usageBatchMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            usageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        loadItemBankMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load.item_bank"), null,
            item -> Windows.showModalWindow(new ItemBankUploadWindow(controller)));
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "v-menubar-df");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "v-menubar-df-sal");
    }
}
