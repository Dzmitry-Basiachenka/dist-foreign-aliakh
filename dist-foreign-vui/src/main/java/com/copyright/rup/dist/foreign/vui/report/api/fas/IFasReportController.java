package com.copyright.rup.dist.foreign.vui.report.api.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportController;

/**
 * Interface for FAS report controller.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/22/2024
 *
 * @author Anton Azarenka
 */
public interface IFasReportController extends ICommonReportController {

    /**
     * @return service fee true-up report controller for FAS/FAS2.
     */
    IFasServiceFeeTrueUpReportController getFasServiceFeeTrueUpReportController();

    /**
     * @return summary market report controller.
     */
    ISummaryMarketReportController getSummaryMarketReportController();

    /**
     * @return instance of {@link IStreamSource} for FAS/FAS2 Batch Summary Report.
     */
    IStreamSource getFasBatchSummaryReportStreamSource();

    /**
     * @return undistributed liabilities report controller.
     */
    IUndistributedLiabilitiesReportController getUndistributedLiabilitiesReportController();

    /**
     * @return ownership adjustment report controller.
     */
    ICommonScenarioReportController getOwnershipAdjustmentReportController();

    /**
     * @return instance of {@link IStreamSource} for research status report.
     */
    IStreamSource getResearchStatusReportStreamSource();
}
