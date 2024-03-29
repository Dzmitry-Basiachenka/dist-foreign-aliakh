package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link ISalHistoricalItemBankDetailsReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalHistoricalItemBankDetailsReportController
    extends CommonController<ISalHistoricalItemBankDetailsReportWidget>
    implements ISalHistoricalItemBankDetailsReportController {

    private static final long serialVersionUID = -6971726011634540500L;

    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IReportService reportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("SAL_historical_item_bank_",
            os -> reportService.writeSalHistoricalItemBankDetailsReport(getWidget().getLicenseeAccountNumber(),
                getWidget().getPeriodEndYearFrom(), getWidget().getPeriodEndYearTo(), os));
    }

    @Override
    protected ISalHistoricalItemBankDetailsReportWidget instantiateWidget() {
        return new SalHistoricalItemBankDetailsReportWidget();
    }

    @Override
    public List<SalLicensee> getSalLicensees() {
        return usageBatchService.getSalLicensees();
    }
}
