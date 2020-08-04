package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeByRightsholderWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link INtsExcludeByRightsholderController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/03/20
 *
 * @author Anton Azarenka
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsExcludeByRightsholderController extends CommonController<INtsExcludeByRightsholderWidget>
    implements INtsExcludeByRightsholderController {

    private Scenario selectedScenario;

    @Autowired
    private INtsScenarioService service;
    @Autowired
    private IUsageService usageService;

    @Override
    protected INtsExcludeByRightsholderWidget instantiateWidget() {
        return new NtsExcludeByRightsholderWidget();
    }

    @Override
    public List<RightsholderPayeePair> getRightsholderPayeePair() {
        return service.getRightsholdersByScenarioId(selectedScenario.getId());
    }

    @Override
    public void excludeDetails(Set<Long> rightsholderAccountNumbers, String reason) {
        usageService.deleteFromScenarioByRightsHolders(selectedScenario.getId(), rightsholderAccountNumbers, reason);
    }

    @Override
    public void setSelectedScenario(Scenario scenario) {
        this.selectedScenario = scenario;
    }
}
