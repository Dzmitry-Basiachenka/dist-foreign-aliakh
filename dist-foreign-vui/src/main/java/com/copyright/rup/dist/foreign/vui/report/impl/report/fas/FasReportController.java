package com.copyright.rup.dist.foreign.vui.report.impl.report.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IFasServiceFeeTrueUpReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IUndistributedLiabilitiesReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasReportController;
import com.copyright.rup.dist.foreign.vui.report.impl.report.CommonReportController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IFasReportController}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/22/2024
 *
 * @author Anton Azarenka
 */
@Component("df.fasReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasReportController extends CommonReportController implements IFasReportController {

    @Autowired
    private IFasServiceFeeTrueUpReportController fasServiceFeeTrueUpReportController;
    @Autowired
    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    @Autowired
    private ISummaryMarketReportController summaryMarketReportController;
    @Autowired
    @Qualifier("df.ownershipAdjustmentReportController")
    private ICommonScenarioReportController ownershipAdjustmentReportController;

    @Override
    public IFasServiceFeeTrueUpReportController getFasServiceFeeTrueUpReportController() {
        return fasServiceFeeTrueUpReportController;
    }

    @Override
    public ISummaryMarketReportController getSummaryMarketReportController() {
        return summaryMarketReportController;
    }

    @Override
    public ICommonScenarioReportController getOwnershipAdjustmentReportController() {
        return ownershipAdjustmentReportController;
    }

    @Override
    public IStreamSource getFasBatchSummaryReportStreamSource() {
        return new ByteArrayStreamSource("fas_batch_summary_report_",
            outputStream -> getReportService().writeFasBatchSummaryCsvReport(outputStream));
    }

    @Override
    public IUndistributedLiabilitiesReportController getUndistributedLiabilitiesReportController() {
        return undistributedLiabilitiesReportController;
    }

    @Override
    public IStreamSource getResearchStatusReportStreamSource() {
        return new ByteArrayStreamSource("research_status_report_",
            outputStream -> getReportService().writeResearchStatusCsvReport(outputStream));
    }
}
