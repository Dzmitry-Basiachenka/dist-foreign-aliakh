package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ITaxNotificationReportController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Represents common controller for generating reports.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/22/2024
 *
 * @author Anton Azarenka
 */
public abstract class CommonReportController implements ICommonReportController {

    @Autowired
    private IReportService reportService;
    @Autowired
    private ITaxNotificationReportController taxNotificationReportController;

    @Override
    public ITaxNotificationReportController getTaxNotificationReportController() {
        return taxNotificationReportController;
    }

    public IReportService getReportService() {
        return reportService;
    }
}
