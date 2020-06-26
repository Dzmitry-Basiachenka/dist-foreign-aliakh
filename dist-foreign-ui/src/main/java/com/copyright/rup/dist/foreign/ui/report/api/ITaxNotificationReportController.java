package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for tax notification report controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/24/20
 *
 * @author Stanislau Rudak
 */
public interface ITaxNotificationReportController
    extends IController<ITaxNotificationReportWidget>, ICsvReportProvider {

    /**
     * Gets {@link Scenario}s for Tax Notification report.
     *
     * @return list of {@link Scenario}s
     */
    List<Scenario> getScenarios();
}
