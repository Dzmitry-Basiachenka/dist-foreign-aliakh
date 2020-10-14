package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link ICommonScenarioReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/2020
 *
 * @author Ihar Suvorau
 */
@Component("df.workSharesByAggLcClassReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WorkSharesByAggLcClassReportController extends CommonController<ICommonScenarioReportWidget>
    implements ICommonScenarioReportController {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IReportService reportService;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenarios(productFamilyProvider.getSelectedProductFamily());
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("work_shares_by_agg_lc_class_report_%s_", getWidget().getScenario().getName()),
            pos -> reportService.writeWorkSharesByAggLcClassCsvReport(getWidget().getScenario(), pos));
    }

    @Override
    protected ICommonScenarioReportWidget instantiateWidget() {
        return new CommonScenarioReportWidget();
    }
}
