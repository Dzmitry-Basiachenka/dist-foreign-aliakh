package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.DateColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Implementation of {@link IScenarioHistoryWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/18/2017
 *
 * @author Uladzislau Shalamitski
 */
public class ScenarioHistoryWidget extends Window implements IScenarioHistoryWidget {

    private IScenarioHistoryController controller;
    private BeanContainer<String, ScenarioAuditItem> container;

    @Override
    @SuppressWarnings("unchecked")
    public ScenarioHistoryWidget init() {
        setWidth(700, Unit.PIXELS);
        setHeight(350, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "scenario-history-widget");
        setContent(initLayout());
        return this;
    }

    @Override
    public void setController(IScenarioHistoryController controller) {
        this.controller = controller;
    }

    @Override
    public void populateHistory(Scenario scenario) {
        container.removeAllItems();
        if (null != scenario) {
            setCaption(ForeignUi.getMessage("window.caption.scenario_history", scenario.getName()));
            container.addAll(controller.getActions(scenario.getId()));
        }
    }

    private VerticalLayout initLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setSizeFull();
        Button closeButton = Buttons.createCloseButton(this);
        Table table = initActionsTable();
        layout.addComponents(table, closeButton);
        layout.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        layout.setExpandRatio(table, 1);
        return layout;
    }

    private Table initActionsTable() {
        container = new BeanContainer<>(ScenarioAuditItem.class);
        container.setBeanIdResolver(BaseEntity::getId);
        Table table = new Table(null, container);
        table.setVisibleColumns(
            "actionType",
            "createUser",
            "createDate",
            "actionReason"
        );
        table.setColumnHeaders(
            ForeignUi.getMessage("table.column.type"),
            ForeignUi.getMessage("table.column.action.user"),
            ForeignUi.getMessage("table.column.date"),
            ForeignUi.getMessage("table.column.action.reason")
        );
        table.addGeneratedColumn("createDate", new DateColumnGenerator(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG));
        table.setSizeFull();
        table.setColumnExpandRatio("actionReason", 1);
        table.setSortEnabled(false);
        VaadinUtils.addComponentStyle(table, "scenario-history-table");
        return table;
    }
}
