package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyLicenseeReportController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IUdmSurveyLicenseeReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/05/2022
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmSurveyLicenseeReportController extends UdmCommonReportController
    implements IUdmSurveyLicenseeReportController {

    private static final long serialVersionUID = -9185384109673447105L;

    @Autowired
    private IUdmReportService udmReportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("survey_licensee_report_",
            os -> udmReportService.writeUdmSurveyLicenseeCsvReport(getWidget().getReportFilter(), os));
    }

    @Override
    public IUdmCommonReportWidget instantiateWidget() {
        return new UdmCommonReportWidget("Survey Start");
    }
}
