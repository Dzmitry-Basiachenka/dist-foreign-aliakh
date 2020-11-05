package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.ISalFundPoolsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISalFundPoolsReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link ISalFundPoolsReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/20
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalFundPoolsReportController extends CommonController<ISalFundPoolsReportWidget>
    implements ISalFundPoolsReportController {

    @Autowired
    private IReportService reportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("sal_fund_pools_",
            pos -> reportService.writeSalFundPoolsCsvReport(getWidget().getDistributionYear(), pos));
    }

    @Override
    protected ISalFundPoolsReportWidget instantiateWidget() {
        return new SalFundPoolsReportWidget();
    }
}
