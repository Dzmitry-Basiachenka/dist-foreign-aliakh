package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Implementation of {@link ISalScenarioWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalScenarioWidget extends CommonScenarioWidget implements ISalScenarioWidget {

    private final ISalScenarioController scenarioController;

    /**
     * Constructor.
     *
     * @param salScenarioController instance of {@link ISalScenarioController}
     */
    public SalScenarioWidget(ISalScenarioController salScenarioController) {
        this.scenarioController = salScenarioController;
    }

    @Override
    protected VerticalLayout initLayout(VerticalLayout searchLayout, Grid<RightsholderTotalsHolder> grid,
                                        VerticalLayout emptyUsagesLayout, HorizontalLayout buttons) {
        VerticalLayout layout = new VerticalLayout(searchLayout, grid, emptyUsagesLayout, buttons);
        layout.setExpandRatio(grid, 1);
        layout.setExpandRatio(emptyUsagesLayout, 1);
        updateLayouts();
        return layout;
    }

    @Override
    protected HorizontalLayout initButtons() {
        // TODO implement action buttons for SAL scenario
        HorizontalLayout buttons = new HorizontalLayout(Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }

    private void updateLayouts() {
        boolean scenarioEmpty = scenarioController.isScenarioEmpty();
        // TODO implement export buttons for SAL scenario
        getRightsholdersGrid().setVisible(!scenarioEmpty);
        getSearchWidget().setVisible(!scenarioEmpty);
        getEmptyUsagesLayout().setVisible(scenarioEmpty);
    }
}
