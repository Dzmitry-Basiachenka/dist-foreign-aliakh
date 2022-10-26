package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for controller for UDM common user names reports.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 01/06/2022
 *
 * @author Ihar Suvorau
 */
public interface IUdmCommonUserNamesReportController extends IController<IUdmCommonUserNamesReportWidget>,
    ICsvReportProvider {

    /**
     * @return list of all periods.
     */
    List<Integer> getAllPeriods();

    /**
     * @return list of usernames.
     */
    List<String> getUserNames();
}
