package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of controller for {@link SummaryMarketReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SummaryMarketReportController extends CommonController<ISummaryMarketReportWidget>
    implements ISummaryMarketReportController {

    private static final long serialVersionUID = 1163601123485149996L;

    @Autowired
    private IReportService reportService;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchService.getUsageBatches(productFamilyProvider.getSelectedProductFamily());
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("summary_of_market_report_",
            os -> reportService.writeSummaryMarkerCsvReport(getWidget().getBatches(), os));
    }

    @Override
    protected ISummaryMarketReportWidget instantiateWidget() {
        return new SummaryMarketReportWidget();
    }
}
