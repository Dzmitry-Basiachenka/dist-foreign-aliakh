package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.IAaclBaselineUsagesReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IAaclBaselineUsagesReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link IAaclBaselineUsagesReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 5/19/2020
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclBaselineUsagesReportController extends CommonController<IAaclBaselineUsagesReportWidget>
    implements IAaclBaselineUsagesReportController {

    private static final long serialVersionUID = 2305759302461204903L;

    @Autowired
    private IReportService reportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("baseline_usages_report_",
            pos -> reportService.writeAaclBaselineUsagesCsvReport(getWidget().getNumberOfBaselineYears(), pos));
    }

    @Override
    protected IAaclBaselineUsagesReportWidget instantiateWidget() {
        return new AaclBaselineUsagesReportWidget();
    }
}
