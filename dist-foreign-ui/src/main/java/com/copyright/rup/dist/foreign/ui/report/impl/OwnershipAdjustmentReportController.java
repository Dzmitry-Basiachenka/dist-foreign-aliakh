package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link ICommonScenarioReportController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Aliaksandr Liakh
 */
@Component("df.ownershipAdjustmentReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OwnershipAdjustmentReportController extends CommonController<ICommonScenarioReportWidget>
    implements ICommonScenarioReportController {

    private static final Set<RightsholderDiscrepancyStatusEnum> REPORT_STATUSES =
        ImmutableSet.of(RightsholderDiscrepancyStatusEnum.DRAFT, RightsholderDiscrepancyStatusEnum.APPROVED);

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenarios(productFamilyProvider.getSelectedProductFamily());
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("ownership_adjustment_report_%s_", getWidget().getScenario().getName()),
            pos -> reportService.writeOwnershipAdjustmentCsvReport(getWidget().getScenario().getId(),
                REPORT_STATUSES, pos));
    }

    @Override
    protected ICommonScenarioReportWidget instantiateWidget() {
        return new CommonScenarioReportWidget();
    }
}
