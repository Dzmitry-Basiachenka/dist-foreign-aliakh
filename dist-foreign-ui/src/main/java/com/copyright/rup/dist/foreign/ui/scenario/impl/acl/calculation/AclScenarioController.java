package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

    @Override
    protected IAclScenarioWidget instantiateWidget() {
        return new AclScenarioWidget();
    }
}
