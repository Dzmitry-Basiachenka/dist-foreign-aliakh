package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeByRightsholderWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenarioController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.PipedOutputStream;

/**
 * Implementation of {@link INtsScenarioController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsScenarioController extends CommonScenarioController implements INtsScenarioController {

    @Autowired
    private INtsDrillDownByRightsholderController drillDownByRightsholderController;
    @Autowired
    private INtsExcludeByRightsholderController excludeController;

    @Override
    protected INtsScenarioWidget instantiateWidget() {
        return new NtsScenarioWidget(this);
    }

    @Override
    protected ICommonDrillDownByRightsholderController getDrillDownByRightsholderController() {
        return drillDownByRightsholderController;
    }

    @Override
    protected void writeScenarioUsagesCsvReport(Scenario scenarioForReport, PipedOutputStream pos) {
        getReportService().writeNtsScenarioUsagesCsvReport(scenarioForReport, pos);
    }

    @Override
    public void onExcludeRhButtonClicked() {
        excludeController.setSelectedScenario(this.getScenario());
        INtsExcludeByRightsholderWidget widget = excludeController.initWidget();
        widget.addListener(this::fireWidgetEvent);
        Windows.showModalWindow((Window) widget);
    }

    @Override
    public void fireWidgetEvent(ExcludeUsagesEvent event) {
        ((INtsScenarioWidget) getWidget()).fireWidgetEvent(event);
    }
}
