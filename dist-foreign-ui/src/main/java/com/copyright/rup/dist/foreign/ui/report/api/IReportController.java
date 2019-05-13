package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.IController;

/**
 * Controller for report menu.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public interface IReportController extends IController<IReportWidget> {

    /**
     * @return undistributed liabilities report controller.
     */
    IUndistributedLiabilitiesReportController getUndistributedLiabilitiesReportController();

    /**
     * @return service fee true-up report controller.
     */
    IServiceFeeTrueUpReportController getServiceFeeTrueUpReportController();

    /**
     * @return summary market report controller.
     */
    ISummaryMarketReportController getSummaryMarketReportController();

    /**
     * @return instance of {@link IStreamSource} for FAS/FAS2 batch summary report.
     */
    IStreamSource getFasBatchSummaryReportStreamSource();

    /**
     * @return instance of {@link IStreamSource} for research status report.
     */
    IStreamSource getResearchStatusReportStreamSource();
}
