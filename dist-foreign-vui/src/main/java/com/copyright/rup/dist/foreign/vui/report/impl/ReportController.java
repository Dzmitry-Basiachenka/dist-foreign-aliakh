package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IFasServiceFeeTrueUpReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.vui.report.api.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ITaxNotificationReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IUndistributedLiabilitiesReportController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private static final long serialVersionUID = -395767068632612665L;

    @Autowired
    @Qualifier("df.ownershipAdjustmentReportController")
    private ICommonScenarioReportController ownershipAdjustmentReportController;
    @Autowired
    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    @Autowired
    private ISummaryMarketReportController summaryMarketReportController;
    @Autowired
    private ITaxNotificationReportController taxNotificationReportController;
    @Autowired
    private IFasServiceFeeTrueUpReportController fasServiceFeeTrueUpReportController;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;
    @Autowired
    private IReportService reportService;

    @Override
    public void onProductFamilyChanged() {
        refreshWidget();
    }

    @Override
    public IProductFamilyProvider getProductFamilyProvider() {
        return productFamilyProvider;
    }

    @Override
    public IUndistributedLiabilitiesReportController getUndistributedLiabilitiesReportController() {
        return undistributedLiabilitiesReportController;
    }

    @Override
    public IStreamSource getFasBatchSummaryReportStreamSource() {
        return new ByteArrayStreamSource("fas_batch_summary_report_",
            outputStream -> reportService.writeFasBatchSummaryCsvReport(outputStream));
    }

    @Override
    public ISummaryMarketReportController getSummaryMarketReportController() {
        return summaryMarketReportController;
    }

    @Override
    public IStreamSource getResearchStatusReportStreamSource() {
        return new ByteArrayStreamSource("research_status_report_",
            outputStream -> reportService.writeResearchStatusCsvReport(outputStream));
    }

    @Override
    public IFasServiceFeeTrueUpReportController getFasServiceFeeTrueUpReportController() {
        return fasServiceFeeTrueUpReportController;
    }

    @Override
    public ICommonScenarioReportController getOwnershipAdjustmentReportController() {
        return ownershipAdjustmentReportController;
    }

    @Override
    public ITaxNotificationReportController getTaxNotificationReportController() {
        return taxNotificationReportController;
    }

    @Override
    protected IReportWidget instantiateWidget() {
        return new ReportWidget();
    }
}
