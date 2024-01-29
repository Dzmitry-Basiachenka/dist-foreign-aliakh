package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import com.vaadin.flow.component.dialog.Dialog;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Common controller for {@link ICommonDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
public abstract class CommonDrillDownByRightsholderController
    extends CommonController<ICommonDrillDownByRightsholderWidget>
    implements ICommonDrillDownByRightsholderController {

    private static final long serialVersionUID = -2918812075901755563L;

    private Long selectedRightsholderAccountNumber;
    private Scenario selectedScenario;

    @Override
    public void showWidget(Long accountNumber, String rhName, Scenario scenario) {
        selectedRightsholderAccountNumber = Objects.requireNonNull(accountNumber);
        selectedScenario = Objects.requireNonNull(scenario);
        var drillDownWindow = (Dialog) initWidget();
        drillDownWindow.setHeaderTitle(ForeignUi.getMessage("table.foreign.rightsholder.format",
            StringUtils.defaultString(rhName), accountNumber));
        Windows.showModalWindow(drillDownWindow);
    }

    protected Long getSelectedRightsholderAccountNumber() {
        return selectedRightsholderAccountNumber;
    }

    protected Scenario getSelectedScenario() {
        return selectedScenario;
    }
}
