package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportWidget;

import com.vaadin.ui.Window;

import java.util.Set;

/**
 * Implementation of {@link ICommonScenariosReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/2020
 *
 * @author Uladzislau Shalamitski
 */
public class CommonScenariosReportWidget extends Window implements ICommonScenariosReportWidget {

    @Override
    @SuppressWarnings("unchecked")
    public ICommonScenariosReportWidget init() {
        //TODO {ushalamitki}: implement widget's content
        return this;
    }

    @Override
    public Set<Scenario> getSelectedScenarios() {
        //TODO {ushalamitki}: implement once widget's content is implemented
        return null;
    }

    @Override
    public void setController(ICommonScenariosReportController controller) {
        //TODO {ushalamitki}: set controllers
    }
}
