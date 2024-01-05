package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioDetailsByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioDetailsController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.ui.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IAclScenarioController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclScenarioController extends CommonController<IAclScenarioWidget> implements IAclScenarioController {

    private static final long serialVersionUID = 6406874531879043851L;

    @Autowired
    private IAclScenarioUsageService scenarioUsageService;
    @Autowired
    private IAclScenarioDetailsByRightsholderController scenarioDetailsByRightsholderController;
    @Autowired
    private IAclScenarioDetailsController scenarioDetailsController;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IAclCalculationReportService aclCalculationReportService;
    private AclScenario aclScenario;

    @Override
    public AclScenario getScenario() {
        return aclScenario;
    }

    @Override
    public void setScenario(AclScenario scenario) {
        this.aclScenario = scenario;
    }

    @Override
    public List<AclRightsholderTotalsHolder> getAclRightsholderTotalsHolders() {
        return scenarioUsageService.getAclRightsholderTotalsHoldersByScenarioId(aclScenario.getId());
    }

    @Override
    public void onRightsholderAccountNumberClicked(Long accountNumber, String rightsholderName) {
        scenarioDetailsByRightsholderController.showWidget(accountNumber, rightsholderName, aclScenario);
    }

    @Override
    public void onViewDetailsClicked() {
        scenarioDetailsController.showWidget(aclScenario);
    }

    @Override
    public List<AclScenarioDetailDto> getRightsholderDetailsResults(RightsholderResultsFilter filter) {
        return scenarioUsageService.getRightsholderDetailsResults(filter);
    }

    @Override
    public List<AclRightsholderTotalsHolderDto> getRightsholderTitleResults(RightsholderResultsFilter filter) {
        return scenarioUsageService.getRightsholderTitleResults(filter);
    }

    @Override
    public List<AclRightsholderTotalsHolderDto> getRightsholderAggLcClassResults(RightsholderResultsFilter filter) {
        return scenarioUsageService.getRightsholderAggLcClassResults(filter);
    }

    @Override
    public IStreamSource getExportAclScenarioRightsholderTotalsStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> aclScenario.getName() + "_",
            pos -> aclCalculationReportService.writeAclScenarioRightsholderTotalsCsvReport(aclScenario.getId(), pos));
    }

    @Override
    //TODO: move this logic to CommonDrillDownWindow
    public void openAclScenarioDrillDownWindow(RightsholderResultsFilter filter) {
        Window window;
        if (Objects.isNull(filter.getWrWrkInst())) {
           window = new AclScenarioDrillDownTitlesWindow(this, new RightsholderResultsFilter(filter));
        } else if (Objects.isNull(filter.getAggregateLicenseeClassId())) {
            window = new AclScenarioDrillDownAggLcClassesWindow(this, new RightsholderResultsFilter(filter));
        } else {
            window = new AclScenarioDrillDownUsageDetailsWindow(this, new RightsholderResultsFilter(filter));
        }
        Windows.showModalWindow(window);
    }

    @Override
    protected IAclScenarioWidget instantiateWidget() {
        return new AclScenarioWidget(this);
    }
}
