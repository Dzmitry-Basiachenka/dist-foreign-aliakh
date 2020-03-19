package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IAaclScenariosController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/19/20
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclScenariosController extends CommonScenariosController implements IAaclScenariosController {

    @Override
    public void sendToLm() {
        //TODO: implement logic for sending to LM in scope of corresponding story
    }

    @Override
    protected IAaclScenariosWidget instantiateWidget() {
        return new AaclScenariosWidget(this, null);
    }

    @Override
    protected ICommonScenarioController getScenarioController() {
        //TODO init scenario controller in scope of B-55091 story
        return null;
    }

    @Override
    protected ICommonScenarioWidget initScenarioWidget() {
        //TODO init scenario widget in scope of B-55091 story
        return null;
    }
}
