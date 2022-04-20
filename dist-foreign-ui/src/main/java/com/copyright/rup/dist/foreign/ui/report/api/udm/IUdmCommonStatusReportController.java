package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for controller for UDM common by Status Reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Ihar Suvorau
 */
public interface IUdmCommonStatusReportController
    extends IController<IUdmCommonStatusReportWidget>, ICsvReportProvider {

    /**
     * @return list of all periods.
     */
    List<Integer> getPeriods();
}
