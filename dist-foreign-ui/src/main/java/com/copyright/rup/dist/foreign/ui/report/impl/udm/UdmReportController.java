package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonUserNamesReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyLicenseeReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsableDetailsByCountryReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsageEditsInBaselineReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsagesByStatusReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmValuesByStatusReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmVerifiedDetailsBySourceReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmWeeklySurveyReportController;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IUdmReportController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/16/2021
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmReportController extends CommonController<IUdmReportWidget> implements IUdmReportController {

    @Autowired
    private IUdmWeeklySurveyReportController udmWeeklySurveyReportController;
    @Autowired
    @Qualifier("df.completedAssignmentsReportController")
    private IUdmCommonUserNamesReportController completedAssignmentsReportController;
    @Autowired
    private IUdmSurveyLicenseeReportController udmSurveyLicenseeReportController;
    @Autowired
    private IUdmVerifiedDetailsBySourceReportController udmVerifiedDetailsBySourceReportController;
    @Autowired
    private IUdmUsableDetailsByCountryReportController udmUsableDetailsByCountryReportController;
    @Autowired
    private IUdmUsageEditsInBaselineReportController udmUsageEditsInBaselineReportController;
    @Autowired
    private IUdmUsagesByStatusReportController udmUsagesByStatusReportController;
    @Autowired
    private IUdmValuesByStatusReportController udmValuesByStatusReportController;
    @Autowired
    @Qualifier("df.udmBaselineValueUpdatesReportController")
    private IUdmCommonUserNamesReportController udmBaselineValueUpdatesReportController;

    @Override
    public IUdmWeeklySurveyReportController getUdmWeeklySurveyReportController() {
        return udmWeeklySurveyReportController;
    }

    @Override
    public IUdmSurveyLicenseeReportController getUdmSurveyLicenseeReportController() {
        return udmSurveyLicenseeReportController;
    }

    @Override
    public IUdmCommonUserNamesReportController getCompletedAssignmentsReportController() {
        return completedAssignmentsReportController;
    }

    @Override
    public IUdmVerifiedDetailsBySourceReportController getUdmVerifiedDetailsBySourceReportController() {
        return udmVerifiedDetailsBySourceReportController;
    }

    @Override
    public IUdmUsableDetailsByCountryReportController getUdmUsableDetailsByCountryReportController() {
        return udmUsableDetailsByCountryReportController;
    }

    @Override
    public IUdmUsageEditsInBaselineReportController getUdmUsageEditsInBaselineReportController() {
        return udmUsageEditsInBaselineReportController;
    }

    @Override
    public IUdmUsagesByStatusReportController getUdmUsagesByStatusReportController() {
        return udmUsagesByStatusReportController;
    }

    @Override
    public IUdmValuesByStatusReportController getUdmValuesByStatusReportController() {
        return udmValuesByStatusReportController;
    }

    @Override
    public IUdmCommonUserNamesReportController getUdmBaselineValueUpdatesReportController() {
        return udmBaselineValueUpdatesReportController;
    }

    @Override
    protected IUdmReportWidget instantiateWidget() {
        return new UdmReportWidget();
    }
}
