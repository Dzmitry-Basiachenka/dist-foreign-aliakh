package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
}
