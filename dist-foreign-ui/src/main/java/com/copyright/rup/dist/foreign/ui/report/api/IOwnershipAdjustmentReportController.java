package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for controller to generate Ownership Adjustment Report.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Aliaksandr Liakh
 */
public interface IOwnershipAdjustmentReportController extends IController<IOwnershipAdjustmentReportWidget> {

    /**
     * @return list of all {@link Scenario}s.
     */
    List<Scenario> getScenarios();

    /**
     * @return instance of {@link IStreamSource} for Ownership Adjustment Report.
     */
    IStreamSource getOwnershipAdjustmentReportStreamSource();
}
