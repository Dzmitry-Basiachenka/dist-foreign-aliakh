package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for controller for UDM Usage Edits in Baseline Report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/11/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmUsageEditsInBaselineReportController extends IController<IUdmUsageEditsInBaselineReportWidget>,
    ICsvReportProvider {

    /**
     * @return list of all periods.
     */
    List<Integer> getAllPeriods();
}
