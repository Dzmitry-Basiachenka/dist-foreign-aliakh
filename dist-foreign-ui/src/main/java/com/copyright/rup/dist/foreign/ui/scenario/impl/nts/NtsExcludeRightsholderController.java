package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeRightsholderWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link INtsExcludeRightsholderController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/03/20
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsExcludeRightsholderController extends CommonController<INtsExcludeRightsholderWidget>
    implements INtsExcludeRightsholderController {

    private Scenario selectedScenario;

    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private INtsUsageService ntsUsageService;

    @Override
    protected INtsExcludeRightsholderWidget instantiateWidget() {
        return new NtsExcludeRightsholderWidget();
    }

    @Override
    public List<RightsholderPayeePair> getRightsholderPayeePairs() {
        return rightsholderService.getRhPayeePairByScenarioId(selectedScenario.getId());
    }

    @Override
    public void excludeDetails(Set<Long> rightsholderAccountNumbers, String reason) {
        ntsUsageService.deleteFromScenarioByRightsholders(selectedScenario.getId(), rightsholderAccountNumbers, reason);
    }

    @Override
    public void setSelectedScenario(Scenario scenario) {
        this.selectedScenario = scenario;
    }
}
