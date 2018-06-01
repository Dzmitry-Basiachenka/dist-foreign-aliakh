package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.IUndistributedLiabilitiesReportController;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IReportController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportController extends CommonController<IReportWidget> implements IReportController {

    @Autowired
    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    @Autowired
    private IReportService reportService;

    @Override
    public IUndistributedLiabilitiesReportController getUndistributedLiabilitiesReportController() {
        return undistributedLiabilitiesReportController;
    }

    @Override
    protected IReportWidget instantiateWidget() {
        return new ReportWidget();
    }

    @Override
    public IStreamSource getBatchSummaryReportStreamSource() {
        return new ByteArrayStreamSource("batch_summary_",
            outputStream -> reportService.writeBatchSummaryCsvReport(outputStream));
    }

    @Override
    public IStreamSource getResearchStatusReportStreamSource() {
        return new ByteArrayStreamSource("research_status_report_",
            outputStream -> reportService.writeResearchStatusCsvReport(outputStream));
    }
}
