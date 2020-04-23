package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
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
     * Handles global product family selection.
     */
    void onProductFamilyChanged();

    /**
     * @return product family provider.
     */
    IProductFamilyProvider getProductFamilyProvider();

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
     * @return ownership adjustment report controller.
     */
    IOwnershipAdjustmentReportController getOwnershipAdjustmentReportController();

    /**
     * @return work shares by aggregate class report controller.
     */
    ICommonScenarioReportController getWorkSharesByAggLcClassReportController();

    /**
     * @return work shares by aggregate class summary report controller.
     */
    ICommonScenarioReportController getWorkSharesByAggLcClassSummaryReportController();

    /**
     * @return instance of {@link IStreamSource} for FAS/FAS2 Batch Summary Report.
     */
    IStreamSource getFasBatchSummaryReportStreamSource();

    /**
     * @return instance of {@link IStreamSource} for research status report.
     */
    IStreamSource getResearchStatusReportStreamSource();

    /**
     * @return instance of {@link IStreamSource} for NTS withdrawn Batch Summary Report.
     */
    IStreamSource getNtsWithdrawnBatchSummaryReportStreamSource();
}
