package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link ICommonScenariosReportController} for Liabilities Summary by Rightsholder and Work Report.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/2020
 *
 * @author Aliaksandr Liakh
 */
@Component("df.salLiabilitiesSummaryByRhAndWorkReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalLiabilitiesSummaryByRhAndWorkReportController extends CommonController<ICommonScenariosReportWidget>
    implements ICommonScenariosReportController {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;
    @Autowired
    private IReportService reportService;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenariosByProductFamiliesAndStatuses(
            Collections.singleton(productFamilyProvider.getSelectedProductFamily()),
            Sets.newHashSet(ScenarioStatusEnum.values()));
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("liabilities_summary_by_rightsholder_and_work_",
            os -> reportService.writeSalLiabilitiesSummaryByRhAndWorkCsvReport(
                getWidget().getSelectedScenarios(), os));
    }

    @Override
    protected ICommonScenariosReportWidget instantiateWidget() {
        return new CommonScenariosReportWidget();
    }
}
