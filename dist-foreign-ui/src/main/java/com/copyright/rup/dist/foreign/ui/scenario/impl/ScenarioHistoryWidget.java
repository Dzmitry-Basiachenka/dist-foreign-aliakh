package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private Grid<ScenarioAuditItem> grid;

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
        if (null != scenario) {
            setCaption(ForeignUi.getMessage("window.caption.scenario_history", scenario.getName()));
            grid.setItems(controller.getActions(scenario.getId()));
        }
    }

    private VerticalLayout initLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setSizeFull();
        Button closeButton = Buttons.createCloseButton(this);
        initActionsGrid();
        layout.addComponents(grid, closeButton);
        layout.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        layout.setExpandRatio(grid, 1);
        return layout;
    }

    private void initActionsGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.addColumn(ScenarioAuditItem::getActionType)
            .setCaption(ForeignUi.getMessage("table.column.type"))
            .setSortProperty("oldRightsholder.rhAccountNumber");
        grid.addColumn(ScenarioAuditItem::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.action.user"))
            .setSortProperty("oldRightsholder.rhName");
        grid.addColumn(auditItem -> getStringFromDate(auditItem.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.date"))
            .setSortProperty("newRightsholder.rhAccountNumber");
        grid.addColumn(ScenarioAuditItem::getActionReason)
            .setCaption(ForeignUi.getMessage("table.column.action.reason"))
            .setSortProperty("newRightsholder.rhName");
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "scenario-history-grid");
    }

    private String getStringFromDate(Date date) {
        return null != date ? new SimpleDateFormat(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG, Locale.getDefault())
            .format(date) : StringUtils.EMPTY;
    }
}
