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
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Autowired
    private IAclScenarioUsageService scenarioUsageService;
    @Autowired
    private IAclDrillDownByRightsholderController drillDownByRightsholderController;
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
        return scenarioUsageService.getAclRightsholderTotalsHoldersByScenarioId(aclScenario.getId(),
            aclScenario.getStatus());
    }

    @Override
    public void onRightsholderAccountNumberClicked(Long accountNumber, String rightsholderName) {
        drillDownByRightsholderController.showWidget(accountNumber, rightsholderName, aclScenario);
    }

    @Override
    public IStreamSource getExportAclScenarioDetailsStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> aclScenario.getName() + "_Details_",
            pos -> aclCalculationReportService.writeAclScenarioDetailsCsvReport(aclScenario.getId(), pos));
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
    protected IAclScenarioWidget instantiateWidget() {
        return new AclScenarioWidget(this);
    }
}
