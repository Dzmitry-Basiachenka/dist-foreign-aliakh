package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenarioWidget;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Implementation of {@link IFasScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public class FasScenarioWidget extends CommonScenarioWidget implements IFasScenarioWidget {

    @Override
    protected VerticalLayout initLayout(VerticalLayout searchLayout, Grid<RightsholderTotalsHolder> grid,
                                        VerticalLayout emptyUsagesVerticalLayout, HorizontalLayout buttons) {
        return new VerticalLayout();
    }

    @Override
    public FasScenarioWidget init() {
        return this;
    }

    @Override
    public void setController(ICommonScenarioController controller) {

    }
}
