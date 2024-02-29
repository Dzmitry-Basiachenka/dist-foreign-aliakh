package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsExcludeRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsExcludeRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenarioController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.ComponentUtil;

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
 * Date: 12/10/2019
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsScenarioController extends CommonScenarioController implements INtsScenarioController {

    private static final long serialVersionUID = -3984338084924174512L;

    @Autowired
    private INtsDrillDownByRightsholderController drillDownByRightsholderController;
    @Autowired
    private INtsExcludeRightsholderController excludeController;

    @Override
    public void onExcludeRhButtonClicked() {
        excludeController.setSelectedScenario(this.getScenario());
        INtsExcludeRightsholderWidget widget = excludeController.initWidget();
        ComponentUtil.addListener(
            (NtsExcludeRightsholderWidget) widget, ExcludeUsagesEvent.class, this::fireWidgetEvent);
        Windows.showModalWindow((CommonDialog) widget);
    }

    @Override
    public void fireWidgetEvent(ExcludeUsagesEvent event) {
        ((INtsScenarioWidget) getWidget()).fireWidgetEvent(event);
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
    protected INtsScenarioWidget instantiateWidget() {
        return new NtsScenarioWidget(this);
    }
}
