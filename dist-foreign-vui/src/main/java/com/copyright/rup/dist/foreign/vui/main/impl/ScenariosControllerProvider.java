package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.vui.main.api.IControllerProvider} for scenario controllers.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/6/19
 *
 * @author Stanislau Rudak
 */
@Component("dist.foreign.scenariosControllerProvider")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScenariosControllerProvider extends CommonControllerProvider<ICommonScenariosController> {

    @Autowired
    private IFasScenariosController fasScenariosController;
    @Autowired
    private INtsScenariosController ntsScenariosController;

    @Override
    protected Map<String, ICommonScenariosController> getProductFamilyToControllerMap() {
        return Map.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasScenariosController,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasScenariosController,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsScenariosController
        );
    }
}
