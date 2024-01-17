package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Contains common functionality for scenario view widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public abstract class CommonScenarioWidget extends CommonDialog implements ICommonScenarioWidget {

    private static final long serialVersionUID = 2553035495189233138L;

    /**
     * Inits {@link VerticalLayout} that contains widget components.
     *
     * @param searchLayout              a {@link VerticalLayout} with search widget
     * @param emptyUsagesVerticalLayout a {@link VerticalLayout} with empty usages message
     * @param grid                      rightsholders grid
     * @param buttons                   a {@link HorizontalLayout} with buttons
     * @return a {@link VerticalLayout} with components
     */
    protected abstract VerticalLayout initLayout(VerticalLayout searchLayout, Grid<RightsholderTotalsHolder> grid,
                                                 VerticalLayout emptyUsagesVerticalLayout, HorizontalLayout buttons);
}
