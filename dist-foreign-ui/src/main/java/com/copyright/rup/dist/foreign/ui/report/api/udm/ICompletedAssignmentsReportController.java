package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for UDM Completed Assignments by Employee Report widget.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 01/06/2022
 *
 * @author Ihar Suvorau
 */
public interface ICompletedAssignmentsReportController extends IController<ICompletedAssignmentsReportWidget>,
    ICsvReportProvider {

    /**
     * @return list of all periods.
     */
    List<Integer> getAllPeriods();

    /**
     * @return list of usernames with unassigned audit actions.
     */
    List<String> getUserNames();
}
