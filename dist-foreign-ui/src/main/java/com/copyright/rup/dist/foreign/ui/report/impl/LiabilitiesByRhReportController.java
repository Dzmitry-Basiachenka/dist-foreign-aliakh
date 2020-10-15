package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
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
 * Implementation of {@link ICommonScenariosReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/20
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.liabilitiesByRhReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LiabilitiesByRhReportController extends CommonController<ICommonScenariosReportWidget>
    implements ICommonScenariosReportController {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenariosByProductFamiliesAndStatuses(
            Collections.singleton(productFamilyProvider.getSelectedProductFamily()),
            Sets.newHashSet(ScenarioStatusEnum.values()));
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("liabilities_by_rightsholder_", os -> {
        });
    }

    @Override
    protected ICommonScenariosReportWidget instantiateWidget() {
        return new CommonScenariosReportWidget();
    }
}
