package com.copyright.rup.dist.foreign.vui.report.api.nts;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportController;

/**
 * Interface for NTS report controller.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/22/2024
 *
 * @author Anton Azarenka
 */
public interface INtsReportController extends ICommonReportController {

    /**
     * @return instance of {@link IStreamSource} for NTS Undistributed Liabilities Report.
     */
    IStreamSource getNtsUndistributedLiabilitiesReportStreamSource();

    /**
     * @return instance of {@link IStreamSource} for NTS withdrawn Batch Summary Report.
     */
    IStreamSource getNtsWithdrawnBatchSummaryReportStreamSource();
    
    /**
     * @return service fee true-up report controller for NTS.
     */
    ICommonScenarioReportController getNtsServiceFeeTrueUpReportController();
}
