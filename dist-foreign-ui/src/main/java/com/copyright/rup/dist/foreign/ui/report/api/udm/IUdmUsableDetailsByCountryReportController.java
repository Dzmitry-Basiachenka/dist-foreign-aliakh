package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for Udm Usable Details by Country Report controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/09/2022
 *
 * @author Mikita Maistrenka
 */
public interface IUdmUsableDetailsByCountryReportController extends IController<IUdmUsableDetailsByCountryReportWidget>,
    ICsvReportProvider {

    /**
     * @return list of all periods.
     */
    List<Integer> getAllPeriods();
}
