package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IWidget;

import java.util.List;

/**
 * Interface for common scenarios report widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/20
 *
 * @author Uladzislau Shalamitski
 */
public interface ICommonScenariosReportWidget extends IWidget<ICommonScenariosReportController> {

    /**
     * @return list of selected scenarios.
     */
    List<Scenario> getSelectedScenarios();
}
