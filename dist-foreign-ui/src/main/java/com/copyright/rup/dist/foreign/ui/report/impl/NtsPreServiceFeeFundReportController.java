package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.report.api.INtsPreServiceFeeFundReportController;
import com.copyright.rup.dist.foreign.ui.report.api.INtsPreServiceFeeFundReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of controller for {@link NtsPreServiceFeeFundReportWidget}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/31/2024
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsPreServiceFeeFundReportController extends CommonController<INtsPreServiceFeeFundReportWidget>
    implements INtsPreServiceFeeFundReportController {

    private static final long serialVersionUID = 8678976704969839045L;

    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IFundPoolService fundPoolService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("nts_pre_service_fee_fund_report_%s_", getWidget().getFundPool().getName()),
            pos -> reportService.writeNtsPreServiceFeeFundCsvReport(getWidget().getFundPool(), pos));
    }

    @Override
    public List<FundPool> getPreServiceFeeFunds() {
        return fundPoolService.getFundPools(FdaConstants.NTS_PRODUCT_FAMILY);
    }

    @Override
    protected INtsPreServiceFeeFundReportWidget instantiateWidget() {
        return new NtsPreServiceFeeFundReportWidget();
    }
}
