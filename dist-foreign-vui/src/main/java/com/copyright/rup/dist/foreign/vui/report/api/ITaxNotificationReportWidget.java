package com.copyright.rup.dist.foreign.vui.report.api;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import java.util.Set;

/**
 * Interface for tax notification report widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/24/20
 *
 * @author Stanislau Rudak
 */
public interface ITaxNotificationReportWidget extends IWidget<ITaxNotificationReportController> {

    /**
     * @return list of ids of selected scenarios.
     */
    Set<String> getSelectedScenarioIds();

    /**
     * @return number of days since last notification.
     */
    int getNumberOfDays();
}
