package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.udm.ICompletedAssignmentsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyLicenseeReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmVerifiedDetailsBySourceReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmWeeklySurveyReportController;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
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
    private ICompletedAssignmentsReportController completedAssignmentsReportController;
    @Autowired
    private IUdmSurveyLicenseeReportController udmSurveyLicenseeReportController;
    @Autowired
    private IUdmVerifiedDetailsBySourceReportController udmVerifiedDetailsBySourceReportController;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public void onProductFamilyChanged() {
        refreshWidget();
    }

    @Override
    public IProductFamilyProvider getProductFamilyProvider() {
        return productFamilyProvider;
    }

    @Override
    public IUdmWeeklySurveyReportController getUdmWeeklySurveyReportController() {
        return udmWeeklySurveyReportController;
    }

    @Override
    public IUdmSurveyLicenseeReportController getUdmSurveyLicenseeReportController() {
        return udmSurveyLicenseeReportController;
    }

    @Override
    public ICompletedAssignmentsReportController getCompletedAssignmentsReportController() {
        return completedAssignmentsReportController;
    }

    @Override
    public IUdmVerifiedDetailsBySourceReportController getUdmVerifiedDetailsBySourceReportController() {
        return udmVerifiedDetailsBySourceReportController;
    }

    @Override
    protected IUdmReportWidget instantiateWidget() {
        return new UdmReportWidget();
    }
}
