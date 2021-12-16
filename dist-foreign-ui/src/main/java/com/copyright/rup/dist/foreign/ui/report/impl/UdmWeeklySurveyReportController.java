package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmWeeklySurveyReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmWeeklySurveyReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of controller for {@link UdmWeeklySurveyReportWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmWeeklySurveyReportController extends CommonController<IUdmWeeklySurveyReportWidget>
    implements IUdmWeeklySurveyReportController {

    @Autowired
    private IUdmReportService udmReportService;
    @Autowired
    private IUdmUsageService udmUsageService;

    @Override
    public List<Integer> getAllPeriods() {
        return udmUsageService.getPeriods();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("weekly_survey_report_",
            os -> udmReportService.writeUdmWeeklySurveyCsvReport(
                getWidget().getChannels(), getWidget().getUsageOrigin(), getWidget().getPeriods(),
                getWidget().getDateReceivedFrom(), getWidget().getDateReceivedTo(), os));
    }

    @Override
    protected IUdmWeeklySurveyReportWidget instantiateWidget() {
        return new UdmWeeklySurveyReportWidget();
    }
}
