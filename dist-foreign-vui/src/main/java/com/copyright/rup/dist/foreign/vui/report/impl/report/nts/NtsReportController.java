package com.copyright.rup.dist.foreign.vui.report.impl.report.nts;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.report.api.nts.INtsReportController;
import com.copyright.rup.dist.foreign.vui.report.impl.report.CommonReportController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link INtsReportController}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/22/2024
 *
 * @author Anton Azarenka
 */
@Component("df.ntsReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsReportController extends CommonReportController implements INtsReportController {

    @Override
    public IStreamSource getNtsWithdrawnBatchSummaryReportStreamSource() {
        return new ByteArrayStreamSource("nts_withdrawn_batch_summary_report_",
            outputStream -> getReportService().writeNtsWithdrawnBatchSummaryCsvReport(outputStream));
    }

    @Override
    public IStreamSource getNtsUndistributedLiabilitiesReportStreamSource() {
        return new ByteArrayStreamSource("undistributed_liabilities_",
            outputStream -> getReportService().writeNtsUndistributedLiabilitiesReport(outputStream));
    }
}
