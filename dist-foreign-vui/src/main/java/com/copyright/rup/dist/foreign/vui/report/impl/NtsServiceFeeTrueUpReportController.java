package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link ICommonScenarioReportController} for Service Fee True-up report for NTS.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/31/2020
 *
 * @author Stanislau Rudak
 */
@Component("df.ntsServiceFeeTrueUpReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsServiceFeeTrueUpReportController extends CommonController<ICommonScenarioReportWidget>
    implements ICommonScenarioReportController {

    private static final long serialVersionUID = 1929074207467193116L;

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IReportService reportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("service_fee_true_up_report_",
            os -> reportService.writeNtsServiceFeeTrueUpCsvReport(getWidget().getScenario(), os));
    }

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenariosByProductFamiliesAndStatuses(
            Set.of(FdaConstants.NTS_PRODUCT_FAMILY), Set.of(ScenarioStatusEnum.SENT_TO_LM));
    }

    @Override
    protected ICommonScenarioReportWidget instantiateWidget() {
        return new CommonScenarioReportWidget();
    }
}
