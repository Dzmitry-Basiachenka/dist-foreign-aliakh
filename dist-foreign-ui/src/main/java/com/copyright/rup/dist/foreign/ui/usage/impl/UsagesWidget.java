package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.common.domain.UsageDetailDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
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
    private LazyTable<UsageBeanQuery, UsageDetailDto> usagesTable;

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
        usagesTable.addGeneratedColumn("wrWrkInst", new LongColumnGenerator());
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
        Button loadButton = Buttons.createButton(ForeignUi.getMessage("button.load"));
        loadButton.addClickListener(event -> Windows.showNotificationWindow("Load button clicked"));
        Button addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> Windows.showNotificationWindow("Add to scenario button clicked"));
        HorizontalLayout layout = new HorizontalLayout(loadButton, addToScenarioButton);
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }
}
