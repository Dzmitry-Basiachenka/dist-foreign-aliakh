package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenariosController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IFasScenariosController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasScenariosController extends CommonScenariosController implements IFasScenariosController {

    @Autowired
    private IFasExcludePayeeController excludePayeesController;

    @Override
    protected IFasScenariosWidget instantiateWidget() {
        return new FasScenariosWidget();
    }

    @Override
    public boolean scenarioExists(String scenarioName) {
        return false;
    }

    @Override
    public void onExcludePayeesButtonClicked() {
        IFasExcludePayeeWidget widget = excludePayeesController.initWidget();
        //TODO {Vaadin23} add listener for updating scenario
        Windows.showModalWindow((CommonDialog) widget);
    }
}
