package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Implementation of {@link IAclScenarioHistoryWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/30/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclScenarioHistoryWidget extends Window implements IAclScenarioHistoryWidget, IDateFormatter {

    private static final long serialVersionUID = -8535638969190092238L;

    private IAclScenarioHistoryController controller;
    private Grid<ScenarioAuditItem> grid;

    @Override
    @SuppressWarnings("unchecked")
    public AclScenarioHistoryWidget init() {
        setWidth(700, Unit.PIXELS);
        setHeight(350, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "scenario-history-widget");
        setContent(initLayout());
        return this;
    }

    @Override
    public void setController(IAclScenarioHistoryController controller) {
        this.controller = controller;
    }

    @Override
    public void populateHistory(AclScenario scenario) {
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
            .setCaption(ForeignUi.getMessage("table.column.type"));
        grid.addColumn(ScenarioAuditItem::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.action.user"));
        grid.addColumn(auditItem -> toLongFormat(auditItem.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.date"));
        grid.addColumn(ScenarioAuditItem::getActionReason)
            .setCaption(ForeignUi.getMessage("table.column.action.reason"));
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "scenario-history-grid");
    }
}
