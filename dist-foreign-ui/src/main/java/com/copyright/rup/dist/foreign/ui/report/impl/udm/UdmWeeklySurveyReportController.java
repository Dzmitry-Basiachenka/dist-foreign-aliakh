package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmWeeklySurveyReportController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link IUdmCommonReportWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmWeeklySurveyReportController extends UdmCommonReportController
    implements IUdmWeeklySurveyReportController {

    @Autowired
    private IUdmReportService udmReportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("weekly_survey_report_",
            os -> udmReportService.writeUdmWeeklySurveyCsvReport(getWidget().getReportFilter(), os));
    }

    @Override
    public IUdmCommonReportWidget instantiateWidget() {
        return new UdmCommonReportWidget("Received");
    }
}
