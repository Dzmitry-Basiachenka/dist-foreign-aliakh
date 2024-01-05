package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.ui.Window;
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
        Window drillDownWindow = (Window) initWidget();
        drillDownWindow.setCaption(ForeignUi.getMessage("table.foreign.rightsholder.format",
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
