package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IAclScenariosController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclScenariosController extends CommonController<IAclScenariosWidget> implements IAclScenariosController {

    @Autowired
    private IAclScenarioService aclScenarioService;


    @Override
    public List<AclScenario> getScenarios() {
        return aclScenarioService.getScenarios();
    }

    @Override
    protected IAclScenariosWidget instantiateWidget() {
        return new AclScenariosWidget(this);
    }

    @Override
    public AclScenario getScenarioWithAmountsAndLastAction(AclScenario scenario) {
        return new AclScenario(); // TODO {aliakh} implement
    }

    @Override
    public String getCriteriaHtmlRepresentation() {
        return ""; // TODO {aliakh} implement
    }

    @Override
    public boolean aclScenarioExists(String scenarioName) {
        //TODO will implement later
        return false;
    }

    @Override
    public List<AclUsageBatch> getUsageBatchesByPeriod(Integer period, boolean editableFlag) {
        //todo will be implemented later
        return new ArrayList<>();
    }

    @Override
    public List<AclFundPool> getFundPoolsByLicenseTypeAndPeriod(String licenseType, Integer period) {
        //todo will be implemented later
        return new ArrayList<>();
    }

    @Override
    public List<AclGrantSet> getGrantSetsByLicenseTypeAndPeriod(String licenseType, Integer period,
                                                                boolean editableFlag) {
        //todo will be implemented later
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getAclUsagePeriods() {
        //todo will be implemented later
        return new ArrayList<>();
    }

    @Override
    public void createAclScenario(AclScenario aclScenario) {
        //todo will be implemented later
    }
}
