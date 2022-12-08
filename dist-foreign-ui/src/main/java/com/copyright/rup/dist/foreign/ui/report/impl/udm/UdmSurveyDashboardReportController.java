package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyDashboardReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyDashboardReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmSurveyDashboardReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/07/2022
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmSurveyDashboardReportController extends CommonController<IUdmSurveyDashboardReportWidget>
    implements IUdmSurveyDashboardReportController {

    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUdmReportService udmReportService;

    @Override
    public List<Integer> getPeriods() {
        return udmUsageService.getPeriods();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("survey_dashboard_report_",
            os -> udmReportService.writeUdmSurveyDashboardCsvReport(getWidget().getSelectedPeriods(), os));
    }

    @Override
    public IUdmSurveyDashboardReportWidget instantiateWidget() {
        return new UdmSurveyDashboardReportWidget();
    }
}
