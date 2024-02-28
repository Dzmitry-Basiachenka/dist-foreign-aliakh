package com.copyright.rup.dist.foreign.vui.report.impl.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasServiceFeeTrueUpReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasServiceFeeTrueUpReportWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link FasServiceFeeTrueUpReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 7/11/2018
 *
 * @author Uladzislau_Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasServiceFeeTrueUpReportController extends CommonController<IFasServiceFeeTrueUpReportWidget>
    implements IFasServiceFeeTrueUpReportController {

    private static final long serialVersionUID = 6740772978171194318L;

    @Autowired
    private IReportService reportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("service_fee_true_up_report_",
            os -> reportService.writeFasServiceFeeTrueUpCsvReport(getWidget().getFromDate(), getWidget().getToDate(),
                getWidget().getPaymentDateTo(), os));
    }

    @Override
    protected IFasServiceFeeTrueUpReportWidget instantiateWidget() {
        return new FasServiceFeeTrueUpReportWidget();
    }
}
