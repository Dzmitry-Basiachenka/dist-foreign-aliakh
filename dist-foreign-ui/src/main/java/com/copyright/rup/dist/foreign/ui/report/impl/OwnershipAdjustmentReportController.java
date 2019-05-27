package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.report.api.IOwnershipAdjustmentReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IOwnershipAdjustmentReportWidget;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IOwnershipAdjustmentReportController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OwnershipAdjustmentReportController extends CommonController<IOwnershipAdjustmentReportWidget>
    implements IOwnershipAdjustmentReportController {

    @Autowired
    private IScenarioService scenarioService;

    @Autowired
    private IReportService reportService;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenarios();
    }

    @Override
    public IStreamSource getOwnershipAdjustmentReportStreamSource() {
        return new OwnershipAdjustmentCsvReportExportStreamSource(() -> getWidget().getScenario(),
            pipedStream -> reportService.writeOwnershipAdjustmentCsvReport(getWidget().getScenario().getId(),
                null, pipedStream));
    }

    @Override
    protected IOwnershipAdjustmentReportWidget instantiateWidget() {
        return new OwnershipAdjustmentReportWidget();
    }
}
