package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for controller for UDM common Report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/03/2022
 *
 * @author Anton Azarenka
 */
public interface IUdmCommonReportController extends IController<IUdmCommonReportWidget>, ICsvReportProvider {

    /**
     * @return list of all periods.
     */
    List<Integer> getAllPeriods();
}
