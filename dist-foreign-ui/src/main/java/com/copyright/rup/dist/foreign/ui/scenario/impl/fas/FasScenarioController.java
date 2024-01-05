package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.fas.IFasScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenarioController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.PipedOutputStream;
import java.util.List;

/**
 * Implementation of {@link IFasScenarioController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasScenarioController extends CommonScenarioController implements IFasScenarioController {

    private static final long serialVersionUID = -5765806659495251970L;

    @Autowired
    private IFasDrillDownByRightsholderController drillDownByRightsholderController;
    @Autowired
    private IFasScenarioService fasScenarioService;

    @Override
    public void onExcludeByRroClicked() {
        Windows.showModalWindow(new FasExcludeSourceRroWindow(this));
    }

    @Override
    public List<Rightsholder> getSourceRros() {
        return fasScenarioService.getSourceRros(getScenario().getId());
    }

    @Override
    public void deleteFromScenario(Long rroAccountNumber, List<Long> accountNumbers, String reason) {
        getUsageService().deleteFromScenario(getScenario().getId(), rroAccountNumber, accountNumbers, reason);
    }

    @Override
    public List<RightsholderPayeePair> getRightsholdersPayeePairs(Long rroAccountNumber) {
        return fasScenarioService.getRightsholdersByScenarioAndSourceRro(getScenario().getId(), rroAccountNumber);
    }

    @Override
    public void fireWidgetEvent(ExcludeUsagesEvent event) {
        ((IFasScenarioWidget) getWidget()).fireWidgetEvent(event);
    }

    @Override
    protected IFasScenarioWidget instantiateWidget() {
        return new FasScenarioWidget(this);
    }

    @Override
    protected ICommonDrillDownByRightsholderController getDrillDownByRightsholderController() {
        return drillDownByRightsholderController;
    }

    @Override
    protected void writeScenarioUsagesCsvReport(Scenario scenarioForReport, PipedOutputStream pos) {
        getReportService().writeFasScenarioUsagesCsvReport(scenarioForReport, pos);
    }
}
