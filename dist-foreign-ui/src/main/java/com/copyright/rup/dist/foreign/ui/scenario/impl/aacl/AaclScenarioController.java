package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenarioController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.PipedOutputStream;

/**
 * Implementation of {@link IAaclScenarioController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/27/20
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclScenarioController extends CommonScenarioController implements IAaclScenarioController {

    private static final long serialVersionUID = -1907121306776405475L;

    @Autowired
    private IAaclDrillDownByRightsholderController drillDownByRightsholderController;
    @Autowired
    private IAaclExcludePayeeController excludePayeeController;

    @Override
    public void onExcludeByPayeeClicked() {
        excludePayeeController.setSelectedScenario(this.getScenario());
        IAaclExcludePayeeWidget widget = excludePayeeController.initWidget();
        widget.addListener(this::fireWidgetEvent);
        Windows.showModalWindow((Window) widget);
    }

    @Override
    protected IAaclScenarioWidget instantiateWidget() {
        return new AaclScenarioWidget(this);
    }

    @Override
    protected ICommonDrillDownByRightsholderController getDrillDownByRightsholderController() {
        return drillDownByRightsholderController;
    }

    @Override
    protected void writeScenarioUsagesCsvReport(Scenario scenarioForReport, PipedOutputStream pos) {
        getReportService().writeAaclScenarioUsagesCsvReport(scenarioForReport, pos);
    }

    private void fireWidgetEvent(ExcludeUsagesEvent event) {
        ((IAaclScenarioWidget) getWidget()).fireWidgetEvent(event);
    }
}
