package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.vui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;

import java.util.Objects;

/**
 * Implementation of {@link IScenarioHistoryWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/18/2017
 *
 * @author Uladzislau Shalamitski
 */
public class ScenarioHistoryWidget extends CommonDialog implements IScenarioHistoryWidget, IDateFormatter {

    private static final long serialVersionUID = 505486427861007960L;

    private IScenarioHistoryController controller;
    private Grid<ScenarioAuditItem> grid;

    @Override
    @SuppressWarnings("unchecked")
    public ScenarioHistoryWidget init() {
        setWidth("1000px");
        setHeight("400px");
        add(initLayout());
        setModalWindowProperties("scenario-history-widget", true);
        return this;
    }

    @Override
    public void populateHistory(Scenario scenario) {
        if (Objects.nonNull(scenario)) {
            setHeaderTitle(ForeignUi.getMessage("window.caption.scenario_history", scenario.getName()));
            grid.setItems(controller.getActions(scenario.getId()));
        }
    }

    @Override
    public void setController(IScenarioHistoryController controller) {
        this.controller = controller;
    }

    private VerticalLayout initLayout() {
        initActionsGrid();
        var layout = new VerticalLayout(grid);
        layout.setSizeFull();
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        getFooter().add(Buttons.createCloseButton(this));
        return layout;
    }

    private void initActionsGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        addColumn(ScenarioAuditItem::getActionType, "table.column.type");
        addColumn(ScenarioAuditItem::getCreateUser, "table.column.action.user");
        addColumn(auditItem -> toLongFormat(auditItem.getCreateDate()), "table.column.date");
        addColumn(ScenarioAuditItem::getActionReason, "table.column.action.reason");
        VaadinUtils.setGridProperties(grid, "scenario-history-grid");
    }

    private void addColumn(ValueProvider<ScenarioAuditItem, ?> valueProvider, String caption) {
        grid.addColumn(valueProvider)
            .setHeader(ForeignUi.getMessage(caption))
            .setResizable(true)
            .setSortable(true)
            .setAutoWidth(true);
    }
}
