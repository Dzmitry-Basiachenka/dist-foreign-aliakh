package com.copyright.rup.dist.foreign.vui.report.api;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

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
     * @return instance of {@link IStreamSource} for FAS/FAS2 Batch Summary Report.
     */
    IStreamSource getFasBatchSummaryReportStreamSource();

    /**
     * @return summary market report controller.
     */
    ISummaryMarketReportController getSummaryMarketReportController();

    /**
     * @return instance of {@link IStreamSource} for research status report.
     */
    IStreamSource getResearchStatusReportStreamSource();

    /**
     * @return service fee true-up report controller for FAS/FAS2.
     */
    IFasServiceFeeTrueUpReportController getFasServiceFeeTrueUpReportController();

    /**
     * @return ownership adjustment report controller.
     */
    ICommonScenarioReportController getOwnershipAdjustmentReportController();

    /**
     * @return tax notification report controller.
     */
    ITaxNotificationReportController getTaxNotificationReportController();

    /**
     * @return instance of {@link IStreamSource} for NTS withdrawn Batch Summary Report.
     */
    IStreamSource getNtsWithdrawnBatchSummaryReportStreamSource();
}
