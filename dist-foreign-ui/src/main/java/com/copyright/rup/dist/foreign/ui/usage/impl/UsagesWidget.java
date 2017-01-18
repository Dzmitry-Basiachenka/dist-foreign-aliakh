package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.UsageDetail;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;

/**
 * Main widget for usages.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 */
class UsagesWidget extends HorizontalSplitPanel implements IUsagesWidget {

    private IUsagesController controller;
    private LazyTable<UsageBeanQuery, UsageDetail> usagesTable;

    @Override
    public void refresh() {
        usagesTable.getContainerDataSource().refresh();
    }

    @Override
    @SuppressWarnings("unchecked")
    public UsagesWidget init() {
        setFirstComponent(controller.initUsagesFilterWidget());
        setSecondComponent(initUsagesLayout());
        setSplitPosition(200, Unit.PIXELS);
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUsagesController controller) {
        this.controller = controller;
    }

    /**
     * @return instance of {@link IUsagesController}.
     */
    IUsagesController getController() {
        return controller;
    }

    private VerticalLayout initUsagesLayout() {
        usagesTable = new LazyTable<>(controller, UsageBeanQuery.class);
        usagesTable.addProperty("id", String.class, false);
        usagesTable.addProperty("wrWrkInst", Long.class, true);
        usagesTable.addProperty("workTitle", String.class, false);
        usagesTable.addProperty("grossAmount", BigDecimal.class, true);
        usagesTable.setVisibleColumns("wrWrkInst", "workTitle", "grossAmount");
        usagesTable.setColumnHeaders("Wr Wrk Inst", "Work Title", "Gross Amount");
        usagesTable.setSizeFull();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), usagesTable);
        layout.setSizeFull();
        layout.setExpandRatio(usagesTable, 1);
        return layout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button loadButton = Buttons.createButton("Load");
        loadButton.addClickListener(event -> Windows.showNotificationWindow("Load button clicked"));
        HorizontalLayout layout = new HorizontalLayout(loadButton);
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }
}
