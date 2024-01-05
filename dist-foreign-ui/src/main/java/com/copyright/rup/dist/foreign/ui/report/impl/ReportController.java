package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.IAaclBaselineUsagesReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IFasServiceFeeTrueUpReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.ISalFundPoolsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ITaxNotificationReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IUndistributedLiabilitiesReportController;
import com.copyright.rup.vaadin.widget.api.CommonController;

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
    private IUndistributedLiabilitiesReportController undistributedLiabilitiesReportController;
    @Autowired
    private IFasServiceFeeTrueUpReportController fasServiceFeeTrueUpReportController;
    @Autowired
    @Qualifier("df.ntsServiceFeeTrueUpReportController")
    private ICommonScenarioReportController ntsServiceFeeTrueUpReportController;
    @Autowired
    private ISummaryMarketReportController summaryMarketReportController;
    @Autowired
    @Qualifier("df.ownershipAdjustmentReportController")
    private ICommonScenarioReportController ownershipAdjustmentReportController;
    @Autowired
    @Qualifier("df.workSharesByAggLcClassReportController")
    private ICommonScenarioReportController workSharesByAggLcClassReportController;
    @Autowired
    @Qualifier("df.workSharesByAggLcClassSummaryReportController")
    private ICommonScenarioReportController workSharesByAggLcClassSummaryReportController;
    @Autowired
    @Qualifier("df.salLiabilitiesByRhReportController")
    private ICommonScenariosReportController salLiabilitiesByRhReportController;
    @Autowired
    @Qualifier("df.salLiabilitiesSummaryByRhAndWorkReportController")
    private ICommonScenariosReportController salLiabilitiesSummaryByRhAndWorkReportController;
    @Autowired
    private ISalHistoricalItemBankDetailsReportController salHistoricalItemBankDetailsReportController;
    @Autowired
    private IAaclBaselineUsagesReportController aaclBaselineUsagesReportController;
    @Autowired
    private ITaxNotificationReportController taxNotificationReportController;
    @Autowired
    private ISalFundPoolsReportController salFundPoolsReportController;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

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
    public IFasServiceFeeTrueUpReportController getFasServiceFeeTrueUpReportController() {
        return fasServiceFeeTrueUpReportController;
    }

    @Override
    public ICommonScenarioReportController getNtsServiceFeeTrueUpReportController() {
        return ntsServiceFeeTrueUpReportController;
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
    public ICommonScenarioReportController getWorkSharesByAggLcClassReportController() {
        return workSharesByAggLcClassReportController;
    }

    @Override
    public ICommonScenarioReportController getWorkSharesByAggLcClassSummaryReportController() {
        return workSharesByAggLcClassSummaryReportController;
    }

    @Override
    public IAaclBaselineUsagesReportController getAaclBaselineUsagesReportController() {
        return aaclBaselineUsagesReportController;
    }

    @Override
    public ITaxNotificationReportController getTaxNotificationReportController() {
        return taxNotificationReportController;
    }

    @Override
    public IStreamSource getFasBatchSummaryReportStreamSource() {
        return new ByteArrayStreamSource("fas_batch_summary_report_",
            outputStream -> reportService.writeFasBatchSummaryCsvReport(outputStream));
    }

    @Override
    public IStreamSource getResearchStatusReportStreamSource() {
        return new ByteArrayStreamSource("research_status_report_",
            outputStream -> reportService.writeResearchStatusCsvReport(outputStream));
    }

    @Override
    public IStreamSource getNtsWithdrawnBatchSummaryReportStreamSource() {
        return new ByteArrayStreamSource("nts_withdrawn_batch_summary_report_",
            outputStream -> reportService.writeNtsWithdrawnBatchSummaryCsvReport(outputStream));
    }

    @Override
    public IStreamSource getNtsUndistributedLiabilitiesReportStreamSource() {
        return new ByteArrayStreamSource("undistributed_liabilities_",
            outputStream -> reportService.writeNtsUndistributedLiabilitiesReport(outputStream));
    }

    @Override
    public IStreamSource getAaclUndistributedLiabilitiesReportStreamSource() {
        return new ByteArrayStreamSource("undistributed_liabilities_",
            outputStream -> reportService.writeAaclUndistributedLiabilitiesCsvReport(outputStream));
    }

    @Override
    public ICommonScenariosReportController getSalLiabilitiesByRhReportController() {
        return salLiabilitiesByRhReportController;
    }

    @Override
    public ICommonScenariosReportController getSalLiabilitiesSummaryByRhAndWorkReportController() {
        return salLiabilitiesSummaryByRhAndWorkReportController;
    }

    @Override
    public IStreamSource getSalUndistributedLiabilitiesReportStreamSource() {
        return new ByteArrayStreamSource("undistributed_liabilities_",
            outputStream -> reportService.writeSalUndistributedLiabilitiesCsvReport(outputStream));
    }

    @Override
    public ISalFundPoolsReportController getSalFundPoolsReportController() {
        return salFundPoolsReportController;
    }

    @Override
    public ISalHistoricalItemBankDetailsReportController getSalHistoricalItemBankDetailsReportController() {
        return salHistoricalItemBankDetailsReportController;
    }

    @Override
    public IStreamSource getNtsFundPoolsReportStreamSource() {
        return new ByteArrayStreamSource("nts_fund_pools_",
            outputStream -> reportService.writeNtsFundPoolsCsvReport(outputStream));
    }

    @Override
    protected IReportWidget instantiateWidget() {
        return new ReportWidget();
    }
}
