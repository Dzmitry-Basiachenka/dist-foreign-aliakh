package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.common.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Main widget for usages.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 */
class UsagesWidget extends HorizontalSplitPanel implements IUsagesWidget {

    private static final String RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY = "rightsholder.accountNumber";
    private IUsagesController controller;
    private LazyTable<UsageBeanQuery, UsageDto> usagesTable;

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
        usagesTable = new LazyTable<>(controller, UsageBeanQuery.class, 1);
        usagesTable.addProperty("id", String.class, false);
        usagesTable.addProperty("wrWrkInst", Long.class, true);
        usagesTable.addProperty("workTitle", String.class, false);
        usagesTable.addProperty(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, Long.class, false);
        usagesTable.addProperty("rightsholder.name", String.class, false);
        usagesTable.addProperty("paymentDate", LocalDate.class, false);
        usagesTable.addProperty("grossAmount", BigDecimal.class, true);
        usagesTable.addProperty("rro.accountNumber", Long.class, false);
        usagesTable.addProperty("eligible", Boolean.class, false);

        LongColumnGenerator longColumnGenerator = new LongColumnGenerator();
        usagesTable.addGeneratedColumn("wrWrkInst", longColumnGenerator);
        usagesTable.addGeneratedColumn(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, longColumnGenerator);
        usagesTable.addGeneratedColumn("paymentDate", new LocalDateColumnGenerator());
        usagesTable.addGeneratedColumn("grossAmount", new MoneyColumnGenerator());
        usagesTable.addGeneratedColumn("rro.accountNumber", longColumnGenerator);

        usagesTable
            .setVisibleColumns("wrWrkInst", "workTitle", RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, "rightsholder.name",
                "paymentDate", "grossAmount", "rro.accountNumber", "eligible");
        usagesTable
            .setColumnHeaders("Wr Wrk Inst", "Work Title", "RH Acct #", "RH Name", "Payment Date", "Gross Amount",
                "RRO", "Eligible");
        usagesTable.setSizeFull();
        VaadinUtils.addComponentStyle(usagesTable, "usages-table");
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), usagesTable);
        layout.setSizeFull();
        layout.setExpandRatio(usagesTable, 1);
        VaadinUtils.addComponentStyle(layout, "usages-layout");
        return layout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button loadButton = Buttons.createButton(ForeignUi.getMessage("button.load"));
        loadButton.addClickListener(event -> Windows.showModalWindow(new UsageBatchUploadWindow()));
        Button addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> Windows.showNotificationWindow("Add to scenario button clicked"));
        HorizontalLayout layout = new HorizontalLayout(loadButton, addToScenarioButton);
        layout.setSpacing(true);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }
}
